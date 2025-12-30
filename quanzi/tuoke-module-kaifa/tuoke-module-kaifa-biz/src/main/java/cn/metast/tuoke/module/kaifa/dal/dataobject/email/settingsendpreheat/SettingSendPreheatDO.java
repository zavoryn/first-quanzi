package cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingsendpreheat;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 邮箱预热 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_setting_send_preheat")
@KeySequence("jw_setting_send_preheat_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingSendPreheatDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 收/发
     */
    private String type;
    /**
     * 邮箱类型
     */
    private String emailType;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱密码/授权码
     */
    private String password;
    /**
     * 收邮箱服务器HOST
     */
    private String host;
    /**
     * 收邮箱服务器端口
     */
    private Integer port;
    /**
     * 收邮箱服务器SSL
     */
    private String inSsl;
    /**
     * 发邮件HOST
     */
    private String outHost;
    /**
     * 发邮件端口
     */
    private Integer outPort;
    /**
     * 发邮件SSL
     */
    private String outSsl;
    /**
     * 预热天数
     */
    private Long days;
    /**
     * 预热总天数
     */
    private Long dayNum;
    /**
     * 发送数
     */
    private Long sendNum;
    /**
     * 回复数量
     */
    private Long replyNum;
    /**
     * 回复比例
     */
    private Long replyRatio;
    /**
     * 每日上限
     */
    private Long maxNum;
    /**
     * 预热状态(runing/stoping/computer)
     */
    private String preheat;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态(0-启用 1-停用)
     */
    private String status;

}
