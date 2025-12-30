package cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 邮箱预热分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SettingSendPreheatPageReqVO extends PageParam {

    @Schema(description = "收/发", example = "1")
    private String type;

    @Schema(description = "邮箱类型", example = "1")
    private String emailType;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "邮箱密码/授权码")
    private String password;

    @Schema(description = "收邮箱服务器HOST")
    private String host;

    @Schema(description = "收邮箱服务器端口")
    private Integer port;

    @Schema(description = "收邮箱服务器SSL")
    private String inSsl;

    @Schema(description = "发邮件HOST")
    private String outHost;

    @Schema(description = "发邮件端口")
    private Integer outPort;

    @Schema(description = "发邮件SSL")
    private String outSsl;

    @Schema(description = "预热天数")
    private Long days;

    @Schema(description = "预热总天数")
    private Long dayNum;

    @Schema(description = "发送数")
    private Long sendNum;

    @Schema(description = "回复数量")
    private Long replyNum;

    @Schema(description = "回复比例")
    private Long replyRatio;

    @Schema(description = "每日上限")
    private Long maxNum;

    @Schema(description = "预热状态(runing/stoping/computer)")
    private String preheat;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "状态(0-启用 1-停用)", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
