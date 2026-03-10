package cn.metast.tuoke.module.member.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.api.cmBuyLog.BuyLogApi;
import cn.metast.tuoke.module.community.api.cmConfig.CmConfigApi;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserImportExcelVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserImportRespVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import cn.metast.tuoke.module.member.controller.app.user.vo.*;
import cn.metast.tuoke.module.member.convert.auth.AuthConvert;
import cn.metast.tuoke.module.member.convert.user.MemberUserConvert;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.dal.mysql.user.MemberUserMapper;
import cn.metast.tuoke.module.member.mq.producer.user.MemberUserProducer;
import cn.metast.tuoke.module.system.api.sms.SmsCodeApi;
import cn.metast.tuoke.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import cn.metast.tuoke.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import cn.metast.tuoke.module.system.enums.sms.SmsSceneEnum;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.metast.tuoke.module.member.enums.ErrorCodeConstants.USER_MOBILE_USED;
import static cn.metast.tuoke.module.member.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static cn.metast.tuoke.module.system.enums.ErrorCodeConstants.*;

/**
 * 会员 User Service 实现类
 *
 * @author metast.cn
 */
@Service
@Valid
@Slf4j
public class MemberUserServiceImpl implements MemberUserService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MemberUserProducer memberUserProducer;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BuyLogApi buyLogApi;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private CmConfigApi cmConfigApi;

    @Override
    public MemberUserDO getUserByMobile(String mobile) {
        return memberUserMapper.selectByMobile(mobile);
    }
    @Override
    public MemberUserDO getUserByNickName(String nickname) {
        return memberUserMapper.selectByNickname(nickname);
    }

    @Override
    public MemberUserDO selectByTechUserId(String userId) {
        return memberUserMapper.selectByTechUserId(userId);
    }

    @Override
    public List<MemberUserDO> getUserListByNickname(String nickname) {
        return memberUserMapper.selectListByNicknameLike(nickname);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUserIfAbsent(String mobile, String registerIp, Integer terminal) {
        // 用户已经存在
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user != null) {
            return user;
        }
        // 用户不存在，则进行创建
        return createUser(mobile, null, null, registerIp, terminal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUserIfAbsent(String mobile, String registerIp, Integer terminal, String anchorId) {
        // 用户已经存在
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user != null) {
            if(StringUtils.isNotBlank(anchorId) && StringUtils.isBlank(user.getAnchorId())){
                user.setAnchorId(anchorId);
                memberUserMapper.updateById(user);
            }
            return user;
        }
        // 用户不存在，则进行创建
        return createUser(mobile, null, null, registerIp, terminal, anchorId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUser(String nickname, String avtar, String registerIp, Integer terminal) {
        return createUser(null, nickname, avtar, registerIp, terminal);
    }

    private MemberUserDO createUser(String mobile, String nickname, String avtar,
                                    String registerIp, Integer terminal) {
        // 生成密码
        String password = IdUtil.fastSimpleUUID();
        // 插入用户
        MemberUserDO user = new MemberUserDO();
        user.setMobile(mobile);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(encodePassword(password)); // 加密密码
        user.setRegisterIp(registerIp).setRegisterTerminal(terminal);
        user.setNickname(nickname).setAvatar(avtar); // 基础信息
        if (StrUtil.isEmpty(nickname)) {
            // 昵称为空时，随机一个名字，避免一些依赖 nickname 的逻辑报错，或者有点丑。例如说，短信发送有昵称时~
            user.setNickname("用户" + RandomUtil.randomNumbers(6));
        }
        memberUserMapper.insert(user);

        // 发送 MQ 消息：用户创建
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                memberUserProducer.sendUserCreateMessage(user.getId());
            }

        });
        return user;
    }

    private MemberUserDO createUser(String mobile, String nickname, String avtar,
                                    String registerIp, Integer terminal, String anchorId) {
        // 生成密码
        String password = IdUtil.fastSimpleUUID();
        // 插入用户
        MemberUserDO user = new MemberUserDO();
        user.setMobile(mobile);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(encodePassword(password)); // 加密密码
        user.setRegisterIp(registerIp).setRegisterTerminal(terminal);
        user.setNickname(nickname).setAvatar(avtar); // 基础信息
        user.setAnchorId(anchorId); // 直播系统的主播id
        if (StrUtil.isEmpty(nickname)) {
            // 昵称为空时，随机一个名字，避免一些依赖 nickname 的逻辑报错，或者有点丑。例如说，短信发送有昵称时~
            user.setNickname("用户" + RandomUtil.randomNumbers(6));
        }
        memberUserMapper.insert(user);

        // 发送 MQ 消息：用户创建
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                memberUserProducer.sendUserCreateMessage(user.getId());
            }

        });
        return user;
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        memberUserMapper.updateById(new MemberUserDO().setId(id)
                .setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    public MemberUserDO getUser(Long id) {
        return memberUserMapper.selectById(id);
    }
    @Override
    public MemberUserDO getUserByAnchorId(Long anchorId) {
        return memberUserMapper.selectOne(MemberUserDO::getAnchorId, anchorId);
    }

    @Override
    public List<MemberUserDO> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return memberUserMapper.selectBatchIds(ids);
    }

    @Override
    public void updateUser(Long userId, AppMemberUserUpdateReqVO reqVO) {
        MemberUserDO updateObj = BeanUtils.toBean(reqVO, MemberUserDO.class).setId(userId);
        memberUserMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserMobile(Long userId, AppMemberUserUpdateMobileReqVO reqVO) {
        // 1.1 检测用户是否存在
        MemberUserDO user = validateUserExists(userId);
        // 1.2 校验新手机是否已经被绑定
        validateMobileUnique(null, reqVO.getMobile());

        // 2.1 校验旧手机和旧验证码
        // 补充说明：从安全性来说，老手机也校验 oldCode 验证码会更安全。但是由于 uni-app 商城界面暂时没做，所以这里不强制校验
        if (StrUtil.isNotEmpty(reqVO.getOldCode())) {
            smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getOldCode())
                    .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));
        }
        // 2.2 使用新验证码
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(reqVO.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));

        // 3. 更新用户手机
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(reqVO.getMobile()).build());
    }

    @Override
    public void updateUserMobileByWeixin(Long userId, AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        // 1.1 获得对应的手机号信息
        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
                UserTypeEnum.MEMBER.getValue(), reqVO.getCode());
        Assert.notNull(phoneNumberInfo, "获得手机信息失败，结果为空");
        // 1.2 校验新手机是否已经被绑定
        validateMobileUnique(userId, phoneNumberInfo.getPhoneNumber());

        // 2. 更新用户手机
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(phoneNumberInfo.getPhoneNumber()).build());
    }

    @Override
    public void updateUserPassword(Long userId, AppMemberUserUpdatePasswordReqVO reqVO) {
        // 检测用户是否存在
        MemberUserDO user = validateUserExists(userId);
        // 校验验证码
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene()).setUsedIp(getClientIP()));

        // 更新用户密码
        memberUserMapper.updateById(MemberUserDO.builder().id(userId)
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    @Override
    public void resetUserPassword(AppMemberUserResetPasswordReqVO reqVO) {
        // 检验用户是否存在
        MemberUserDO user = validateUserExists(reqVO.getMobile());

        // 使用验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_RESET_PASSWORD,
                getClientIP()));

        // 更新密码
        memberUserMapper.updateById(MemberUserDO.builder().id(user.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    private MemberUserDO validateUserExists(String mobile) {
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(MemberUserUpdateReqVO updateReqVO) {
        // 校验存在
        validateUserExists(updateReqVO.getId());
        // 校验手机唯一
        validateMobileUnique(updateReqVO.getId(), updateReqVO.getMobile());

        // 更新
        MemberUserDO updateObj = MemberUserConvert.INSTANCE.convert(updateReqVO);
        memberUserMapper.updateById(updateObj);
    }

    @VisibleForTesting
    MemberUserDO validateUserExists(Long id) {
        if (id == null) {
            return null;
        }
        MemberUserDO user = memberUserMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_USED, mobile);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_USED, mobile);
        }
    }

    @Override
    public PageResult<MemberUserDO> getUserPage(MemberUserPageReqVO pageReqVO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();

        // 超级管理员(deptId=115)，返回所有数据，不过滤
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return memberUserMapper.selectPage(pageReqVO);
    }

        // 通过 API 获取部门对应的用户ID列表（避免循环依赖）
        List<Long> userIds = cmConfigApi.getUserIdsByDeptId(loginUserDeptId);

        if (userIds.isEmpty()) {
            return PageResult.empty(); // 该部门没有用户，返回空结果
        }

        return memberUserMapper.selectPageByUserIds(pageReqVO, userIds);
    }

    @Override
    public void updateUserLevel(Long id, Long levelId, Integer experience) {
        // 0 代表无等级：防止UpdateById时，会被过滤掉的问题
        levelId = ObjectUtil.defaultIfNull(levelId, 0L);
        memberUserMapper.updateById(new MemberUserDO()
                .setId(id)
                .setLevelId(levelId).setExperience(experience)
        );
    }

    @Override
    public Long getUserCountByGroupId(Long groupId) {
        return memberUserMapper.selectCountByGroupId(groupId);
    }

    @Override
    public Long getUserCountByLevelId(Long levelId) {
        return memberUserMapper.selectCountByLevelId(levelId);
    }

    @Override
    public Long getUserCountByTagId(Long tagId) {
        return memberUserMapper.selectCountByTagId(tagId);
    }

    @Override
    public boolean updateUserPoint(Long id, Integer point) {
        if (point > 0) {
            memberUserMapper.updatePointIncr(id, point);
        } else if (point < 0) {
            return memberUserMapper.updatePointDecr(id, point) > 0;
        }
        return true;
    }

    @Override
    public boolean updateUserName(Long userId, String name) {
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).name(name).build());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public MemberUserImportRespVO importUserList(List<MemberUserImportExcelVO> importUsers, boolean isUpdateSupport) {
            // 1.1 参数校验
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        String initPassword = IdUtil.fastSimpleUUID();//默认密码
        // 2. 遍历，逐个创建 or 更新
        MemberUserImportRespVO respVO = MemberUserImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        importUsers.forEach(importUser -> {
            // 2.1.1 校验字段是否符合要求
            if (StrUtil.isEmpty(importUser.getNickname())) {
                // 昵称为空时，随机一个名字，避免一些依赖 nickname 的逻辑报错，或者有点丑。例如说，短信发送有昵称时~
                importUser.setNickname("用户" + RandomUtil.randomNumbers(6));
            }
            if(StringUtils.isNotBlank(importUser.getSex())){
                if("--".equals(importUser.getSex())){
                    importUser.setSex(null);
                }else if("男".equals(importUser.getSex())){
                    importUser.setSex("1");
                }else if("女".equals(importUser.getSex())){
                    importUser.setSex("2");
                }
            }
            LocalDateTime loginDate=null;
            LocalDateTime birthday=null;
            Integer registerTerminal=null;
            if(StringUtils.isNotBlank(importUser.getRegisterTerminal())){
                if("微信".equals(importUser.getRegisterTerminal())){
                    registerTerminal=10;
                    importUser.setRegisterTerminal("10");
                }else if("手机号".equals(importUser.getRegisterTerminal())){
                    registerTerminal=31;
                    importUser.setRegisterTerminal("31");
                }else{
                    registerTerminal=0;
                    importUser.setRegisterTerminal("0");
                }
            }
            if(StringUtils.isNotBlank(importUser.getLoginDate())){
                // 解析字符串为 LocalDateTime 对象
                 loginDate = LocalDateTime.parse(importUser.getLoginDate(), formatter);
            }
            /*if(StringUtils.isNotBlank(importUser.getBirthday())){
                // 解析字符串为 LocalDateTime 对象
                birthday = LocalDateTime.parse(importUser.getBirthday(), formatter1);
            }*/
            // 2.2.1 判断如果不存在，在进行插入
            MemberUserDO existUser=null;
            if(StringUtils.isNotBlank(importUser.getTechUserId())) {
                 existUser = memberUserMapper.selectByTechUserId(importUser.getTechUserId());
                if (existUser == null) {
                    MemberUserDO user = BeanUtils.toBean(importUser, MemberUserDO.class)
                            .setPassword(encodePassword(initPassword)).setStatus(CommonStatusEnum.ENABLE.getStatus())
                            .setLoginDate(loginDate).setRegisterTerminal(registerTerminal)
                            .setRegisterIp("127.0.0.1");
                    memberUserMapper.insert(user);
                    respVO.getCreateUsernames().add(importUser.getNickname());
                    return;
                }
            }
            // 2.2.2 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getNickname(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            MemberUserDO updateUser = BeanUtils.toBean(importUser, MemberUserDO.class)
                    .setLoginDate(loginDate).setRegisterTerminal(registerTerminal);
            updateUser.setId(existUser.getId());
            memberUserMapper.updateById(updateUser);
            respVO.getUpdateUsernames().add(importUser.getNickname());
        });
        return respVO;
    }

}
