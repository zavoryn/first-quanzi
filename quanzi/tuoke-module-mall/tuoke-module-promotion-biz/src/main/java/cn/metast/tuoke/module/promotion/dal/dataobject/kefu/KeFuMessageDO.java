package cn.metast.tuoke.module.promotion.dal.dataobject.kefu;

import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;
import cn.metast.tuoke.module.promotion.enums.kefu.KeFuMessageContentTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 客服消息 DO
 *
 * @author HUIHUI
 */
@TableName("promotion_kefu_message")
@KeySequence("promotion_kefu_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeFuMessageDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 会话编号
     *
     * 关联 {@link KeFuConversationDO#getId()}
     */
    private Long conversationId;

    /**
     * 发送人编号
     *
     * 存储的是用户编号
     */
    private Long senderId;
    /**
     * 发送人类型
     *
     * 枚举，{@link UserTypeEnum}
     */
    private Integer senderType;
    /**
     * 接收人编号
     *
     * 存储的是用户编号
     */
    private Long receiverId;
    /**
     * 接收人类型
     *
     * 枚举，{@link UserTypeEnum}
     */
    private Integer receiverType;

    /**
     * 消息类型
     *
     * 枚举 {@link KeFuMessageContentTypeEnum}
     */
    private Integer contentType;
    /**
     * 消息
     */
    private String content;

    //======================= 消息相关状态 =======================

    /**
     * 是/否已读
     */
    private Boolean readStatus;

    /**
     * 状态0正常1待审核
     */
    private Integer status;

    /**
     * 聊天室状态1
     */
    @TableField(exist = false)
    private Long chatType;
    //圈子id
    private Long topicId;

    @TableField(exist = false)
    private String senderAvatar;
    @TableField(exist = false)
    private String senderName;
    @TableField(exist = false)
    private String receiverAvatar;
    @TableField(exist = false)
    private String receiverName;

    /**
     * 引用的消息内容（被引用消息的文本）
     */
    private String quoteContent;

    /**
     * 引用人ID（被引用消息的发送人ID）
     */
    private Long quoteUserId;

}
