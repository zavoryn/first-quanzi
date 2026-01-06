package cn.metast.tuoke.module.live.dal.dataobject.snsActuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动报名人员 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_user")
@KeySequence("sns_act_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActUserDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * user_id
     */
    private Long userId;
    /**
     * act_id
     */
    private Long actId;
    /**
     * 计划要收报名费
     */
    private Double planFee;
    /**
     * 0 报名成功  2待审核 3 审核不通过 -1 退报名
     */
    private Integer status;
    /**
     * 实际收到的报名费
     */
    private Double realFee;
    /**
     * 实际收费流水号
     */
    private String orderNo;
    /**
     * 实际缴费时间
     */
    private LocalDateTime feeTime;
    /**
     * 报名所填姓名
     */
    private String sName;
    /**
     * 报名所填电话
     */
    private String sPhone;
    /**
     * 报名所填年龄
     */
    private Integer sAge;
    /**
     * 报名所填性别
     */
    private String sGender;
    /**
     * 报名所填单位
     */
    private String sUnit;
    /**
     * 报名所填微信
     */
    private String sWx;
    /**
     * 报名所填邮箱
     */
    private String sEmail;
    /**
     * 预计到达时间
     */
    private LocalDateTime planArriveTime;
    /**
     * 签到时间
     */
    private LocalDateTime registerTime;
    /**
     * 友谊赛里程(km)
     */
    private String mileage;
    /**
     * 备注
     */
    private String remark;

}