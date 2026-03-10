package cn.metast.tuoke.module.member.service.point;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointImportExcelVO;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointImportRespVO;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointRecordPageReqVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserImportRespVO;
import cn.metast.tuoke.module.member.controller.app.point.vo.AppMemberPointRecordPageReqVO;
import cn.metast.tuoke.module.member.dal.dataobject.point.MemberPointRecordDO;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.dal.mysql.point.MemberPointRecordMapper;
import cn.metast.tuoke.module.member.dal.mysql.user.MemberUserMapper;
import cn.metast.tuoke.module.member.enums.point.MemberPointBizTypeEnum;
import cn.metast.tuoke.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.module.member.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static cn.metast.tuoke.module.member.enums.ErrorCodeConstants.USER_POINT_NOT_ENOUGH;
import static cn.metast.tuoke.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;
import static cn.metast.tuoke.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;


/**
 * 积分记录 Service 实现类
 *
 * @author QingX
 */
@Slf4j
@Service
@Validated
public class MemberPointRecordServiceImpl implements MemberPointRecordService {

    @Resource
    private MemberPointRecordMapper memberPointRecordMapper;

    @Resource
    private MemberUserService memberUserService;

    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    public PageResult<MemberPointRecordDO> getPointRecordPage(MemberPointRecordPageReqVO pageReqVO) {
        // 根据用户昵称查询出用户 ids
        Set<Long> userIds = null;
        if (StringUtils.isNotBlank(pageReqVO.getNickname())) {
            List<MemberUserDO> users = memberUserService.getUserListByNickname(pageReqVO.getNickname());
            // 如果查询用户结果为空直接返回无需继续查询
            if (CollectionUtils.isEmpty(users)) {
                return PageResult.empty();
            }
            userIds = convertSet(users, MemberUserDO::getId);
        }
        // 执行查询
        return memberPointRecordMapper.selectPage(pageReqVO, userIds);
    }

    @Override
    public PageResult<MemberPointRecordDO> getPointRecordPage(Long userId, AppMemberPointRecordPageReqVO pageReqVO) {
        return memberPointRecordMapper.selectPage(userId, pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPointRecord(Long userId, Integer point, MemberPointBizTypeEnum bizType, String bizId) {
        if (point == 0) {
            return;
        }
        // 1. 校验用户积分余额
        MemberUserDO user = memberUserService.getUser(userId);
        Integer userPoint = ObjectUtil.defaultIfNull(user.getPoint(), 0);
        int totalPoint = userPoint + point; // 用户变动后的积分
        if (totalPoint < 0) {
            log.error("[createPointRecord][userId({}) point({}) bizType({}) bizId({}) {}]", userId, point, bizType, bizId,
                    USER_POINT_NOT_ENOUGH);
            return;
        }

        // 2. 更新用户积分
        boolean success = memberUserService.updateUserPoint(userId, point);
        if (!success) {
            throw exception(USER_POINT_NOT_ENOUGH);
        }

        // 3. 增加积分记录
        MemberPointRecordDO record = new MemberPointRecordDO()
                .setUserId(userId).setBizId(bizId).setBizType(bizType.getType())
                .setTitle(bizType.getName()).setDescription(StrUtil.format(bizType.getDescription(), point))
                .setPoint(point).setTotalPoint(totalPoint);
        memberPointRecordMapper.insert(record);
    }

    @Override
    public MemberPointImportRespVO importUserList(List<MemberPointImportExcelVO> importUsers, boolean isUpdateSupport) {
        // 1.1 参数校验
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_NOT_EXISTS);
        }
        String initPassword = IdUtil.fastSimpleUUID();//默认密码
        // 2. 遍历，逐个创建 or 更新
        MemberPointImportRespVO respVO = MemberPointImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // 2.2.1 判断如果不存在，在进行插入
            MemberUserDO existUser=null;
            MemberPointRecordDO memberPointRecordDO=null;
            if(StringUtils.isNotBlank(importUser.getTechUserId())) {
                existUser = memberUserMapper.selectByTechUserId(importUser.getTechUserId());
                if (existUser != null) {
                     memberPointRecordDO=memberPointRecordMapper.selectByTechUserId(importUser.getTechUserId());
                    if(memberPointRecordDO==null) {
                        MemberPointRecordDO record = new MemberPointRecordDO()
                                .setUserId(existUser.getId()).setBizId("0").setBizType(2)
                                .setTitle("管理员修改").setDescription(StrUtil.format("管理员修改 {} 积分", importUser.getPoint()))
                                .setPoint(importUser.getPoint()).setTotalPoint(importUser.getTotalPoint())
                                .setTechUserId(importUser.getTechUserId());
                        memberPointRecordMapper.insert(record);
                        respVO.getCreateUsernames().add(importUser.getNickname());
                        return;
                    }
                }
            }
           /* if(StringUtils.isNotBlank(importUser.getMobile())) {
                existUser = memberUserMapper.selectByMobile(importUser.getMobile());
                if (existUser != null) {
                    MemberPointRecordDO record = new MemberPointRecordDO()
                            .setUserId(existUser.getId()).setBizId("0").setBizType(2)
                            .setTitle("管理员修改").setDescription(StrUtil.format("管理员修改 {} 积分", importUser.getPoint()))
                            .setPoint(importUser.getPoint()).setTotalPoint(importUser.getTotalPoint());
                    memberPointRecordMapper.insert(record);
                    respVO.getCreateUsernames().add(importUser.getNickname());
                    return;
                }
            }else{
                existUser = memberUserMapper.selectByNickname(importUser.getNickname());
                if (existUser != null) {
                    MemberPointRecordDO record = new MemberPointRecordDO()
                            .setUserId(existUser.getId()).setBizId("0").setBizType(2)
                            .setTitle("管理员修改").setDescription(StrUtil.format("管理员修改 {} 积分", importUser.getPoint()))
                            .setPoint(importUser.getPoint()).setTotalPoint(importUser.getTotalPoint());
                    memberPointRecordMapper.insert(record);
                    respVO.getCreateUsernames().add(importUser.getNickname());
                    return;
                }
            }*/
            // 2.2.2 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getNickname(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            MemberPointRecordDO updateRecord = new MemberPointRecordDO()
                    .setUserId(existUser.getId()).setBizId("0").setBizType(2)
                    .setTitle("管理员修改").setDescription(StrUtil.format("管理员修改 {} 积分", importUser.getPoint()))
                    .setPoint(importUser.getPoint()).setTotalPoint(importUser.getTotalPoint())
                    .setTechUserId(importUser.getTechUserId());
            updateRecord.setId(memberPointRecordDO.getId());
            memberPointRecordMapper.updateById(updateRecord);
            respVO.getUpdateUsernames().add(importUser.getNickname());
        });
        return respVO;
    }

}
