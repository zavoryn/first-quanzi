package cn.metast.tuoke.module.promotion.dal.mysql.kefu;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.query.QueryWrapperX;
import cn.metast.tuoke.module.promotion.controller.admin.coupon.vo.coupon.CouponPageReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageListReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessagePageReqVO;
import cn.metast.tuoke.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuMessageDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.micrometer.common.util.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 客服消息 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface KeFuMessageMapper extends BaseMapperX<KeFuMessageDO> {

    /**
     * 获得消息列表
     * 1. 第一次查询时，不带时间，默认查询最新的十条消息
     * 2. 第二次查询时，带时间，查询历史消息
     *
     * @param reqVO 列表请求
     * @return 消息列表
     */
    default List<KeFuMessageDO> selectList(KeFuMessageListReqVO reqVO) {
        return selectList(new QueryWrapperX<KeFuMessageDO>()
                .eqIfPresent("conversation_id", reqVO.getConversationId())
                .ltIfPresent("create_time", reqVO.getCreateTime())
                .orderByDesc("create_time")
                .limitN(reqVO.getLimit()));
    }
    /**
     * 消息
     */
    default List<KeFuMessageDO> selectListTopic(KeFuMessageListReqVO reqVO) {
        return selectList(new QueryWrapperX<KeFuMessageDO>()
                .eqIfPresent("conversation_id", reqVO.getConversationId())
                .and(wrapper -> wrapper
                        .eq("sender_id", reqVO.getSendId())  // 自己的消息
                        .or()
                        .eq("status", 0)  // 或者其他人已审核的消息
                )
                //.ltIfPresent("create_time", reqVO.getCreateTime())
                .orderByDesc("create_time"));
    }

    default PageResult<KeFuMessageDO> getKeFuMessageListNewPage(AppKeFuMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<KeFuMessageDO>()
                .eqIfPresent(KeFuMessageDO::getConversationId, reqVO.getConversationId())
                .notIn(reqVO.getBlockUserIds() != null && !reqVO.getBlockUserIds().isEmpty(),
                        KeFuMessageDO::getSenderId, reqVO.getBlockUserIds())
                .and(wrapper -> wrapper
                        .eq(KeFuMessageDO::getSenderId, reqVO.getSendId())  // 自己的消息
                        .or()
                        .eq(KeFuMessageDO::getStatus, 0)  // 或者其他人已审核的消息
                )
                .like(StringUtils.isNotBlank(reqVO.getKeyword()),
                        KeFuMessageDO::getContent, reqVO.getKeyword())
                .orderByDesc(KeFuMessageDO::getCreateTime));
    }

    default List<KeFuMessageDO> selectListByConversationIdAndUserTypeAndReadStatus(Long conversationId, Integer userType,
                                                                                   Boolean readStatus) {
        return selectList(new LambdaQueryWrapper<KeFuMessageDO>()
                .eq(KeFuMessageDO::getConversationId, conversationId)
                .ne(KeFuMessageDO::getSenderType, userType) // 管理员：查询出未读的会员消息，会员：查询出未读的客服消息
                .eq(KeFuMessageDO::getReadStatus, readStatus));
    }

    default List<KeFuMessageDO> selectListByConversationIdAndUserTypeAndReadStatusNew(Long conversationId, Integer userType,Long userId,
                                                                                      Boolean readStatus) {
        return selectList(new LambdaQueryWrapper<KeFuMessageDO>()
                .eq(KeFuMessageDO::getConversationId, conversationId)
                //.ne(KeFuMessageDO::getSenderType, userType) // 管理员：查询出未读的会员消息，会员：查询出未读的客服消息
                .eq(KeFuMessageDO::getReadStatus, readStatus)
                .eq(KeFuMessageDO::getReceiverId, userId));
    }


    default void updateReadStatusBatchByIds(Collection<Long> ids, KeFuMessageDO keFuMessageDO) {
        update(keFuMessageDO, new LambdaUpdateWrapper<KeFuMessageDO>()
                .in(KeFuMessageDO::getId, ids));
    }

    default PageResult<KeFuMessageDO> getKeFuMessageListPage(AppKeFuMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<KeFuMessageDO>()
                .eqIfPresent(KeFuMessageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(KeFuMessageDO::getTopicId, reqVO.getTopicId())
                .orderByDesc(KeFuMessageDO::getCreateTime));
    }
}
