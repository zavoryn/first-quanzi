package cn.metast.tuoke.module.community.service.cmTopicmember;

import cn.metast.tuoke.framework.common.util.collection.MapUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmTopicmember.CmTopicMemberMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 圈子成员 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmTopicMemberServiceImpl implements CmTopicMemberService {

    private static final int MAX_FREE_MEMBER_PER_TOPIC = 2;

    @Resource
    private CmTopicMemberMapper cmTopicMemberMapper;
    @Resource
    private CmTopicMapper cmTopicMapper;
    @Resource
    private MemberUserApi memberUserApi;
    @Override
    public Long createCmTopicMember(CmTopicMemberSaveReqVO createReqVO) {
        // 插入
        CmTopicMemberDO cmTopicMember = BeanUtils.toBean(createReqVO, CmTopicMemberDO.class);
        cmTopicMemberMapper.insert(cmTopicMember);
        // 返回
        return cmTopicMember.getId();
    }

    @Override
    public void updateCmTopicMember(CmTopicMemberSaveReqVO updateReqVO) {
        // 校验存在
        validateCmTopicMemberExists(updateReqVO.getId());
        // 更新
        CmTopicMemberDO updateObj = BeanUtils.toBean(updateReqVO, CmTopicMemberDO.class);
        cmTopicMemberMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmTopicMember(Long id) {
        // 校验存在
        validateCmTopicMemberExists(id);
        // 删除
        cmTopicMemberMapper.deleteById(id);
    }

    private void validateCmTopicMemberExists(Long id) {
        if (cmTopicMemberMapper.selectById(id) == null) {
            throw exception(CM_TOPIC_MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public CmTopicMemberDO getCmTopicMember(Long id) {
        return cmTopicMemberMapper.selectById(id);
    }

    @Override
    public PageResult<CmTopicMemberDO> getCmTopicMemberPage(CmTopicMemberPageReqVO pageReqVO) {
        return cmTopicMemberMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CmTopicMemberDO> getCmTopicMemberList(CmTopicMemberPageReqVO pageReqVO) {
        IPage<CmTopicMemberDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmTopicMemberMapper.selectMemberPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmTopicMemberDO> getCmTopicMemberOwnList(CmTopicMemberPageReqVO pageReqVO) {
        IPage<CmTopicMemberDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmTopicMemberMapper.getCmTopicMemberOwnList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmTopicMemberDO> getIncomeDetailList(CmTopicMemberPageReqVO pageReqVO) {
        IPage<CmTopicMemberDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmTopicMemberMapper.getIncomeDetailList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmTopicMemberDO> getMyShopList(CmTopicMemberPageReqVO pageReqVO) {
        IPage<CmTopicMemberDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmTopicMemberMapper.selectMyShopPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public Long joinCmTopicMember(CmTopicMemberSaveReqVO createReqVO) {
        Long userId = createReqVO.getUserId();
        Long topIcId = createReqVO.getTopicId();
        //查询圈子是否需要审核
        CmTopicDO cmTopic = cmTopicMapper.selectOne(CmTopicDO::getId, topIcId);
        if(cmTopic==null){
            return 0L;
        }
        //查询是否第一次买
        CmTopicMemberDO cmTopicMember = cmTopicMemberMapper.selectOne(CmTopicMemberDO::getUserId, userId, CmTopicMemberDO::getTopicId, topIcId);
        if(cmTopicMember==null) {
            createReqVO.setUserId(userId);
            createReqVO.setRole(0);
            createReqVO.setOrderNum(1);
            createReqVO.setIsContract(0);
            if("1".equals(cmTopic.getJoinMode())){
                //首次加入需要审核
                createReqVO.setStatus(1);
                createReqVO.setStartTime(LocalDateTime.now());
            }else{
                //不需要审核
                createReqVO.setStatus(0);
                //购买时间
                createReqVO.setStartTime(LocalDateTime.now());
                //加入时间
                createReqVO.setJoinTime(LocalDateTime.now());
                //付费时长1一月2两月3个月4四个月5半年
                switch (createReqVO.getType()) {
                    case 1:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(1));
                        break;
                    case 2:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(2));
                        break;
                    case 3:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(3));
                        break;
                    case 4:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(4));
                        break;
                    case 5:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(6));
                        break;
                }
            }
            //插入
            Long id=createCmTopicMember(createReqVO);
            return id;
        }else{
            //续费加入
            createReqVO.setId(cmTopicMember.getId());
            createReqVO.setIsContract(1);
            createReqVO.setOrderNum(cmTopicMember.getOrderNum()>0?cmTopicMember.getOrderNum()+1:1);
            if("1".equals(cmTopic.getIsRenew())){
                //续费加入需要审核
                createReqVO.setStatus(4);
                createReqVO.setStartTime(LocalDateTime.now());
            }else{
                //不需要审核
                createReqVO.setStatus(0);
                //购买时间
                createReqVO.setStartTime(LocalDateTime.now());

                // 计算续费基准时间：如果原会员未过期，从原到期时间累加；否则从当前时间开始
                LocalDateTime baseTime = LocalDateTime.now();
                if (cmTopicMember.getEndTime() != null && cmTopicMember.getEndTime().isAfter(LocalDateTime.now())) {
                    baseTime = cmTopicMember.getEndTime();
                }
                //付费时长1一月2两月3个月4四个月5半年
                switch (createReqVO.getType()) {
                    case 1:
                        createReqVO.setEndTime(baseTime.plusMonths(1));
                        break;
                    case 2:
                        createReqVO.setEndTime(baseTime.plusMonths(2));
                        break;
                    case 3:
                        createReqVO.setEndTime(baseTime.plusMonths(3));
                        break;
                    case 4:
                        createReqVO.setEndTime(baseTime.plusMonths(4));
                        break;
                    case 5:
                        createReqVO.setEndTime(baseTime.plusMonths(6));
                        break;
                }
            }
            //更新
            updateCmTopicMember(createReqVO);
        }
        return cmTopicMember.getId();
    }

    @Override
    public Long checkMember(CmTopicMemberSaveReqVO createReqVO) {
        //查询圈子
        CmTopicDO cmTopic = cmTopicMapper.selectOne(CmTopicDO::getId, createReqVO.getTopicId());
        if(cmTopic==null){
            return 0L;
        }
        //判断状态=审核成功
        if("0".equals(createReqVO.getNewStatus())){
            if("1".equals(createReqVO.getStatus())){
                //首次审核
                createReqVO.setStatus(0);
                //购买时间
                createReqVO.setStartTime(LocalDateTime.now());
                //加入时间
                createReqVO.setJoinTime(LocalDateTime.now());
                //付费时长1一月2两月3个月4四个月5半年
                switch (createReqVO.getType()) {
                    case 1:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(1));
                        break;
                    case 2:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(2));
                        break;
                    case 3:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(3));
                        break;
                    case 4:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(4));
                        break;
                    case 5:
                        createReqVO.setEndTime(createReqVO.getStartTime().plusMonths(6));
                        break;
                }
            }else if("4".equals(createReqVO.getStatus())){
                //续费审核
                createReqVO.setStatus(0);
                //购买时间
                LocalDateTime baseTime = LocalDateTime.now();
                createReqVO.setStartTime(baseTime);

                // 查询原会员记录，判断是否续费场景
                CmTopicMemberDO existMember = cmTopicMemberMapper.selectById(createReqVO.getId());

                // 如果原会员未过期，从原到期时间累加（续费场景）
                if (existMember != null && existMember.getEndTime() != null
                        && existMember.getEndTime().isAfter(LocalDateTime.now())) {
                    baseTime = existMember.getEndTime();
                }

                // 首次加入才设置加入时间
                if (existMember == null) {
                    createReqVO.setJoinTime(LocalDateTime.now());
                }
                //付费时长1一月2两月3个月4四个月5半年
                switch (createReqVO.getType()) {
                    case 1:
                        createReqVO.setEndTime(baseTime.plusMonths(1));
                        break;
                    case 2:
                        createReqVO.setEndTime(baseTime.plusMonths(2));
                        break;
                    case 3:
                        createReqVO.setEndTime(baseTime.plusMonths(3));
                        break;
                    case 4:
                        createReqVO.setEndTime(baseTime.plusMonths(4));
                        break;
                    case 5:
                        createReqVO.setEndTime(baseTime.plusMonths(6));
                        break;
                }
            }
        }else{
            createReqVO.setStatus(createReqVO.getNewStatus());
        }
        updateCmTopicMember(createReqVO);
        return createReqVO.getTopicId();
    }

    @Override
    public CmTopicMemberDO getCmTopicMember(Long userId, Long topicId) {
        return cmTopicMemberMapper.selectOne(CmTopicMemberDO::getUserId, userId, CmTopicMemberDO::getTopicId, topicId);
    }

    @Override
    public PageResult<CmTopicMemberDO> getCmTopicMemberConversationList(CmTopicMemberPageReqVO pageReqVO) {
        IPage<CmTopicMemberDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmTopicMemberMapper.getCmTopicMemberConversationList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public List<TopicSimpleRespVO> getTopicSimpleList() {
        List<CmTopicDO> topicList = cmTopicMapper.selectList(
                new LambdaQueryWrapperX<CmTopicDO>()
                        .eq(CmTopicDO::getStatus, 0)
                        .orderByDesc(CmTopicDO::getId)
        );
        return topicList.stream().map(topic -> {
            TopicSimpleRespVO vo = new TopicSimpleRespVO();
            vo.setId(topic.getId());
            vo.setTopicName(topic.getTopicName());
            return vo;
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addFreeMember(FreeMemberAddReqVO reqVO) {
        Long topicId = reqVO.getTopicId();
        Long userId = reqVO.getUserId();

        // 1. 校验圈子是否存在
        CmTopicDO topic = cmTopicMapper.selectById(topicId);
        if (topic == null) {
            throw exception(CM_TOPIC_NOT_EXISTS);
        }

        // 2. 校验该用户是否已是该圈子的有效免费会员
        CmTopicMemberDO existFreeMember = cmTopicMemberMapper.selectValidFreeMember(topicId, userId);
        if (existFreeMember != null) {
            throw exception(CM_TOPIC_MEMBER_ALREADY_FREE);
        }

        // 3. 校验该圈子免费会员数量是否已达上限
        Long freeMemberCount = cmTopicMemberMapper.countFreeMemberByTopicId(topicId);
        if (freeMemberCount >= MAX_FREE_MEMBER_PER_TOPIC) {
            throw exception(CM_TOPIC_FREE_MEMBER_LIMIT);
        }

        // 4. 查询用户是否已是该圈子成员
        CmTopicMemberDO existMember = cmTopicMemberMapper.selectOne(
                CmTopicMemberDO::getTopicId, topicId,
                CmTopicMemberDO::getUserId, userId
        );

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = calculateEndTime(now, reqVO.getType());
        Long operatorId = SecurityFrameworkUtils.getLoginUserId();

        if (existMember != null) {
            // 更新现有记录
            existMember.setFreeStatus(1);
            existMember.setStartTime(now);
            existMember.setEndTime(endTime);
            existMember.setType(reqVO.getType());
            existMember.setStatus(0);
            cmTopicMemberMapper.updateById(existMember);
            return existMember.getId();
        } else {
            // 插入新记录
            CmTopicMemberDO member = new CmTopicMemberDO();
            member.setTopicId(topicId);
            member.setUserId(userId);
            member.setRole(0);
            member.setStatus(0);
            member.setOrderNum(1);
            member.setJoinTime(now);
            member.setStartTime(now);
            member.setEndTime(endTime);
            member.setType(reqVO.getType());
            member.setFreeStatus(1);
            member.setCreator(String.valueOf(operatorId));
            cmTopicMemberMapper.insert(member);
            return member.getId();
        }
    }

    private LocalDateTime calculateEndTime(LocalDateTime startTime, Integer type) {
        return switch (type) {
            case 0 -> startTime.plusMonths(1);
            case 1 -> startTime.plusMonths(2);
            case 2 -> startTime.plusMonths(3);
            case 3 -> startTime.plusMonths(4);
            case 4 -> startTime.plusMonths(5);
            case 5 -> startTime.plusMonths(6);
            default -> startTime.plusMonths(1);
        };
    }

    @Override
    public void cancelMemberByUserId(Long userId) {
        // 根据用户ID逻辑删除所有会员记录（将deleted字段设为true）
        cmTopicMemberMapper.deleteByUserId(userId);
    }
}
