package cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailsetting;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公共配置 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_email_setting")
@KeySequence("jw_email_setting_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSettingDO extends BaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 发送类型(1-立即发送 2-定时)
     */
    private String sendType;
    /**
     * 定时发送时间
     */
    private String regularTime;
    /**
     * 双休日是否发送(1-是)
     */
    private String sendRestDay;
    /**
     * 过滤邮箱
     */
    private String filterateEmail;
    /**
     * 优先发送对象
     */
    private String firstSend;
    /**
     * 单个公司邮箱开发上限 (0-不限制 1-限制)
     */
    private String singleUpperLimit;
    /**
     * 过滤我的线索 (1-有效)
     */
    private String filterateMyClub;
    /**
     * 过滤同事线索 (1-有效)
     */
    private String filterateColleagueClub;
    /**
     * 过滤我的客户 (1-有效)
     */
    private String filterateMyCustomer;
    /**
     * 过滤同事客户 (1-有效)
     */
    private String filterateTsCustomer;
    /**
     * 过滤公海客户 (1-有效)
     */
    private String filteratePublicCustomer;
    /**
     * 过滤已发送 (1-有效)
     */
    private String filterateBeenSent;
    /**
     * 筛选公司
     */
    private String screenCompany;
    /**
     * 筛选KP职位
     */
    private String screenPositionKp;
    /**
     * 筛选其他职位
     */
    private String screenPositionOther;
    /**
     * 筛选邮箱状态
     */
    private String screenEmail;
    /**
     * 存储公司
     */
    private String saveCompany;
    /**
     * 存储KP职位
     */
    private String savePositionKp;
    /**
     * 存储其他职位
     */
    private String savePositionOther;
    /**
     * 存储邮箱状态
     */
    private String saveEmail;
    /**
     * 存储标签
     */
    private String saveTags;
    /**
     * 默认循环
     */
    private String sopNo;
    /**
     * 循环开始日期-暂时不填，默认第二天的日期
     */
    private String planTime;
    /**
     * 循环次数
     */
    private Long planNum;
    /**
     * 间隔天数
     */
    private Long planDay;

}
