package cn.metast.tuoke.module.promotion.dal.mysql.kefu;

import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuConversationDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客服会话 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface KeFuConversationMapper extends BaseMapperX<KeFuConversationDO> {

    default List<KeFuConversationDO> selectConversationList() {
        return selectList(new LambdaQueryWrapperX<KeFuConversationDO>()
                .eq(KeFuConversationDO::getAdminDeleted, Boolean.FALSE)
                .orderByDesc(KeFuConversationDO::getCreateTime));
    }

    default void updateAdminUnreadMessageCountIncrement(Long id) {
        update(new LambdaUpdateWrapper<KeFuConversationDO>()
                .eq(KeFuConversationDO::getId, id)
                .setSql("admin_unread_message_count = admin_unread_message_count + 1"));
    }
    default List<KeFuConversationDO> selectConversationList_admin() {
        return selectList(new LambdaQueryWrapperX<KeFuConversationDO>()
                .eq(KeFuConversationDO::getAdminDeleted, Boolean.FALSE)
                .isNull(KeFuConversationDO::getReceiverId)
//                .orderByDesc(KeFuConversationDO::getCreateTime));
                .orderByDesc(KeFuConversationDO::getLastMessageTime));
    }
    default List<KeFuConversationDO> selectConversationList_web(Long receiverId) {
        return selectList(new LambdaQueryWrapperX<KeFuConversationDO>()
                .eq(KeFuConversationDO::getAdminDeleted, Boolean.FALSE)
                .eq(KeFuConversationDO::getGroupStatus, 0L)
                .isNotNull(KeFuConversationDO::getReceiverId)
                .and(wrapper -> wrapper
                        .eq(KeFuConversationDO::getUserId, receiverId)
                        .or()
                        .eq(KeFuConversationDO::getReceiverId, receiverId)
                )
                .orderByDesc(KeFuConversationDO::getLastMessageTime));
    }
    default KeFuConversationDO selectByUserId(Long userId, Long receiverId) {
        return selectOne(
                new LambdaUpdateWrapper<KeFuConversationDO>()
                        .isNotNull(KeFuConversationDO::getReceiverId)
                        .eq(KeFuConversationDO::getUserId, userId)
                        .eq(KeFuConversationDO::getReceiverId, receiverId)
        );
    }
    default KeFuConversationDO selectByUserId(Long userId) {
        return selectOne(KeFuConversationDO::getUserId, userId);
    }
    default List<KeFuConversationDO> selectListByUserId(Long userId) {
        return selectList(KeFuConversationDO::getUserId, userId);
    }

    default List<KeFuConversationDO> getKefuConversationListTopId(Long topIcId,String nickName) {
       /* return selectList(new LambdaQueryWrapperX<KeFuConversationDO>()
                .eq(KeFuConversationDO::getTopicId, topIcId)
                .orderByDesc(KeFuConversationDO::getLastMessageTime));*/
        MPJLambdaWrapperX<KeFuConversationDO> query = new MPJLambdaWrapperX<KeFuConversationDO>();
        query.selectAll(KeFuConversationDO.class)
                .leftJoin(MemberUserDO.class, on -> on
                        .eq(KeFuConversationDO::getUserId, MemberUserDO::getId))
                .orderByDesc(KeFuConversationDO::getLastMessageTime);
        if (topIcId!=null) {
            query.eq(KeFuConversationDO::getTopicId, topIcId);
        }
        if (StringUtils.isNotBlank(nickName)) {
            query.and(wrapper -> wrapper
                    .eq(MemberUserDO::getNickname, nickName)
                    .or()
                    .eq(MemberUserDO::getName, nickName)
            );
        }
        return selectList(query);
    }
}
