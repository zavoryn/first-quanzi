package cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 邮箱域名MX值 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_email_mx")
@KeySequence("jw_email_mx_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMxDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 邮箱类型
     */
    private String type;
    /**
     * 邮箱后缀
     */
    private String domain;
    /**
     * 域名MX值,多个用逗号分割
     */
    private String mx;
    /**
     * 收邮件服务器
     */
    private String inHost;
    /**
     * 收邮件端口
     */
    private Integer inPort;
    /**
     * 发邮件服务器
     */
    private String outHost;
    /**
     * 发邮件端口
     */
    private Integer outPort;
    /**
     * 代理状态
     */
    private String proxyStatu;
    /**
     * 代理服务器
     */
    private String porxyHost;
    /**
     * 代理服务器
     */
    private String proxyPort;
    /**
     * 代理用户名
     */
    private String proxyUser;
    /**
     * 代理密码
     */
    private String proxyPass;
    /**
     * 状态
     */
    private String status;

    /** 如果是1，说明邮箱已经存在，否则不存在 */
    @TableField(exist = false)
    protected String emailType;
    @TableField(exist = false)
    protected String sendEmailType;

}
