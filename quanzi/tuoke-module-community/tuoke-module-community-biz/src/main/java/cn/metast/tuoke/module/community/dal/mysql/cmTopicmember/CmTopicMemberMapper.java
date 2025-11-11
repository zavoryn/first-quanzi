package cn.metast.tuoke.module.community.dal.mysql.cmTopicmember;

import java.time.LocalDateTime;
import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicRespVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 圈子成员 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmTopicMemberMapper extends BaseMapperX<CmTopicMemberDO> {
    /**
     * 加入的星球
     * @param reqVO
     * @return
     */
    default List<CmTopicMemberDO> getJoinTopicList(CmTopicRespVO reqVO) {
        return selectList(new LambdaQueryWrapper<CmTopicMemberDO>()
                .eq(CmTopicMemberDO::getUserId, reqVO.getUserId()));
    }

    default PageResult<CmTopicMemberDO> selectPage(CmTopicMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmTopicMemberDO>()
                .eqIfPresent(CmTopicMemberDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(CmTopicMemberDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmTopicMemberDO::getRole, reqVO.getRole())
                .eqIfPresent(CmTopicMemberDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(CmTopicMemberDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmTopicMemberDO::getMuteEndTime, reqVO.getMuteEndTime())
                .betweenIfPresent(CmTopicMemberDO::getJoinTime, reqVO.getJoinTime())
                .betweenIfPresent(CmTopicMemberDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(CmTopicMemberDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(CmTopicMemberDO::getType, reqVO.getType())
                .eqIfPresent(CmTopicMemberDO::getInterNum, reqVO.getInterNum())
                .eqIfPresent(CmTopicMemberDO::getBlockRemark, reqVO.getBlockRemark())
                .eqIfPresent(CmTopicMemberDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(CmTopicMemberDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmTopicMemberDO::getId));
    }
    /**
     * 会员详情
     */
    IPage<CmTopicMemberDO> selectMemberPage(IPage<CmTopicMemberDO> page,@Param("reqvo") CmTopicMemberPageReqVO reqvo);

    /**
     * 会员列表包含自己
     */
    IPage<CmTopicMemberDO> getCmTopicMemberOwnList(IPage<CmTopicMemberDO> page,@Param("reqvo") CmTopicMemberPageReqVO reqvo);
    /**
     * 收入明细
     */
    IPage<CmTopicMemberDO> getIncomeDetailList(IPage<CmTopicMemberDO> page,@Param("reqvo") CmTopicMemberPageReqVO reqvo);
    /**
     * 我的购买
     * @param reqVO
     * @return
     */
    @Select("SELECT t1.id as id,t1.topic_id AS topicId,t1.user_id AS userId, t1.order_num AS orderNum,"+
            "t1.join_time as joinTime,t1.status as status,t1.end_time as endTime,t1.type as type,t1.create_time as createTime,"+
            "t2.topic_name AS topicName,t2.cover_image as coverImage FROM "+
            "cm_topic_member t1 LEFT JOIN cm_topic t2 ON t1.topic_id = t2.id "+
            "where t1.user_id=#{reqvo.userId} " +
            "ORDER BY t1.create_time DESC")
    IPage<CmTopicMemberDO> selectMyShopPage(IPage<?> page,@Param("reqvo") CmTopicMemberPageReqVO reqVO);

    /**
     * 私信会话列表
     * @param
     * @return
     */
    IPage<CmTopicMemberDO> getCmTopicMemberConversationList(IPage<CmTopicMemberDO> page,@Param("reqvo") CmTopicMemberPageReqVO reqvo);

    /**
     * 查询指定圈子的免费会员数量
     */
    default Long countFreeMemberByTopicId(Long topicId) {
        return selectCount(new LambdaQueryWrapper<CmTopicMemberDO>()
                .eq(CmTopicMemberDO::getTopicId, topicId)
                .eq(CmTopicMemberDO::getFreeStatus, 1)
                .gt(CmTopicMemberDO::getEndTime, LocalDateTime.now()));
    }

    /**
     * 查询用户在指定圈子的有效免费会员记录
     */
    default CmTopicMemberDO selectValidFreeMember(Long topicId, Long userId) {
        return selectOne(new LambdaQueryWrapper<CmTopicMemberDO>()
                .eq(CmTopicMemberDO::getTopicId, topicId)
                .eq(CmTopicMemberDO::getUserId, userId)
                .eq(CmTopicMemberDO::getFreeStatus, 1)
                .gt(CmTopicMemberDO::getEndTime, LocalDateTime.now()));
    }
    /**
     * 根据用户ID删除所有会员记录（逻辑删除）
     */
    default int deleteByUserId(Long userId) {
        return delete(new LambdaQueryWrapper<CmTopicMemberDO>()
                .eq(CmTopicMemberDO::getUserId, userId));
    }
}
