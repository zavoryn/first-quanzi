package cn.metast.tuoke.module.community.dal.dataobject.cmBuylog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会员购买记录 DO
 *
 * @author adminXq
 */
@TableName("member_buy_log")
@KeySequence("member_buy_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmBuyLogDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 会员等级编号
     */
    private Long levelId;
    /**
     * 会员名称
     */
    private String name;
    /**
     * 会员等级
     */
    private Integer level;
    /**
     * 会员经验(价格)
     */
    private Integer experience;
    /**
     * 状态(0-待支付 1-购买成功)
     */
    private Integer status;
    /**
     * 支付订单编号
     */
    private Long payOrderId;
    /**
     * 是否已支付：[0:未支付 1:已经支付过]
     */
    private Boolean payStatus;
    /**
     * 订单支付时间
     */
    private LocalDateTime payTime;
    /**
     * 应付金额（总），单位：分
     */
    private Integer payPrice;
    /**
     * 会员原价（总），单位：分
     */
    private Integer totalPrice;
    /**
     * 已购买会员金额（总），单位：分
     */
    private Integer yzfPrice;
    /**
     * 一级返佣比例
     */
    private Integer brokerageFirstPercent;
    /**
     * 二级返佣比例
     */
    private Integer brokerageSecondPercent;

    /**
     * 退款金额
     */
    private Integer refundPrice;
    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String topicName;

}
