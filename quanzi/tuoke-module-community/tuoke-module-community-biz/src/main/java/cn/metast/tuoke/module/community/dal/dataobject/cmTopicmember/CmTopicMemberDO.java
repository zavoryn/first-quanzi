package cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 圈子成员 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_topic_member")
@KeySequence("cm_topic_member_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmTopicMemberDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 圈子ID
     */
    private Long topicId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色(0普通成员 1管理员 2创建者)
     */
    private Integer role;
    /**
     * 购买次数
     */
    private Integer orderNum;
    /**
     * 状态(0正常 1审核中 2被拒绝3拉黑)
     */
    private Integer status;
    /**
     * 禁言结束时间
     */
    private LocalDateTime muteEndTime;
    /**
     * 加入时间
     */
    private LocalDateTime joinTime;
    /**
     * 购买时间
     */
    private LocalDateTime startTime;
    /**
     * 到期时间
     */
    private LocalDateTime endTime;
    /**
     * 付费时长1一月2两月3个月4四个月5半年
     */
    private Integer type;
    /**
     * 互动次数
     */
    private Integer interNum;
    /**
     * 拉黑原因
     */
    private String blockRemark;
    /**
     * 会员备注
     */
    private String remark;

    /**
     * 延长服务备注
     */
    private String extendRemark;

    /**
     * 是否合约首次: 1-首次 2-续费
     * 注意：需要在数据库执行 ALTER TABLE cm_topic_member ADD COLUMN is_contract INT DEFAULT NULL COMMENT '是否合约首次: 1-首次 2-续费';
     */
    @TableField(exist = false) 
    private Integer isContract;


    /**
     * 免费会员状态(0普通 1免费)
     */
    private Integer freeStatus;

    @TableField(exist = false)
    private String topicName;
    @TableField(exist = false)
    private String coverImage;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private String avatar;
    //购买时长
    @TableField(exist = false)
    private Long remainingDays;
    //剩余时长
    @TableField(exist = false)
    private Long endDays;

    //支付金额
    @TableField(exist = false)
    private Integer price;
    //退款金额
    @TableField(exist = false)
    private Integer refundPrice;

}
