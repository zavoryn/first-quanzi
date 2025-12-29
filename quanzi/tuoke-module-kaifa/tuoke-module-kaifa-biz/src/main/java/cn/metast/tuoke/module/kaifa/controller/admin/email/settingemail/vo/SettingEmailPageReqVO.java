package cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 邮件配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SettingEmailPageReqVO extends PageParam {

    @Schema(description = "邮箱类型(1-腾讯qq 2-网易163 3-阿里个人版)", example = "2")
    private String emailType;

    @Schema(description = "邮件地址")
    private String email;

    @Schema(description = "邮件昵称", example = "李四")
    private String name;

    @Schema(description = "邮件密码")
    private String password;

    @Schema(description = "收邮件HOST")
    private String host;

    @Schema(description = "收邮件端口")
    private Integer port;

    @Schema(description = "收邮件SSL")
    private String inSsl;

    @Schema(description = "写邮件HOST")
    private String outHost;

    @Schema(description = "写邮件端口")
    private Integer outPort;

    @Schema(description = "写邮件SSL")
    private String outSsl;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "1-业务邮箱")
    private String isYw;

    @Schema(description = "是否启用")
    private Integer isUser;

    @Schema(description = "用户ID", example = "18493")
    private Long userId;

    @Schema(description = "状态(0-正常 1-异常)", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
