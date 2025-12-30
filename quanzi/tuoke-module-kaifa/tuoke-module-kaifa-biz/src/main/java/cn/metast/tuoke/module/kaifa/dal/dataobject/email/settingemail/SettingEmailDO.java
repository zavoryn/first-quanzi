package cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 邮件配置 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_setting_email")
@KeySequence("jw_setting_email_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingEmailDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 邮箱类型(1-腾讯qq 2-网易163 3-阿里个人版)
     */
    private String emailType;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 邮件昵称
     */
    private String name;
    /**
     * 邮件密码
     */
    private String password;
    /**
     * 收邮件HOST
     */
    private String host;
    /**
     * 收邮件端口
     */
    private Integer port;
    /**
     * 收邮件SSL
     */
    private String inSsl;
    /**
     * 写邮件HOST
     */
    private String outHost;
    /**
     * 写邮件端口
     */
    private Integer outPort;
    /**
     * 写邮件SSL
     */
    private String outSsl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 1-业务邮箱
     */
    private String isYw;
    /**
     * 是否启用
     */
    private Integer isUser;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 状态(0-正常 1-异常)
     */
    private String status;

}
