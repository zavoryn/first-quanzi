package cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 邮箱预热 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SettingSendPreheatRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13231")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "收/发", example = "1")
    @ExcelProperty("收/发")
    private String type;

    @Schema(description = "邮箱类型", example = "1")
    @ExcelProperty("邮箱类型")
    private String emailType;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "邮箱密码/授权码")
    @ExcelProperty("邮箱密码/授权码")
    private String password;

    @Schema(description = "收邮箱服务器HOST")
    @ExcelProperty("收邮箱服务器HOST")
    private String host;

    @Schema(description = "收邮箱服务器端口")
    @ExcelProperty("收邮箱服务器端口")
    private Integer port;

    @Schema(description = "收邮箱服务器SSL")
    @ExcelProperty("收邮箱服务器SSL")
    private String inSsl;

    @Schema(description = "发邮件HOST")
    @ExcelProperty("发邮件HOST")
    private String outHost;

    @Schema(description = "发邮件端口")
    @ExcelProperty("发邮件端口")
    private Integer outPort;

    @Schema(description = "发邮件SSL")
    @ExcelProperty("发邮件SSL")
    private String outSsl;

    @Schema(description = "预热天数")
    @ExcelProperty("预热天数")
    private Long days;

    @Schema(description = "预热总天数")
    @ExcelProperty("预热总天数")
    private Long dayNum;

    @Schema(description = "发送数")
    @ExcelProperty("发送数")
    private Long sendNum;

    @Schema(description = "回复数量")
    @ExcelProperty("回复数量")
    private Long replyNum;

    @Schema(description = "回复比例")
    @ExcelProperty("回复比例")
    private Long replyRatio;

    @Schema(description = "每日上限")
    @ExcelProperty("每日上限")
    private Long maxNum;

    @Schema(description = "预热状态(runing/stoping/computer)")
    @ExcelProperty("预热状态(runing/stoping/computer)")
    private String preheat;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态(0-启用 1-停用)", example = "2")
    @ExcelProperty("状态(0-启用 1-停用)")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
