package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 邮箱域名MX值 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EmailMxRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10450")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "邮箱类型", example = "1")
    @ExcelProperty("邮箱类型")
    private String type;

    @Schema(description = "邮箱后缀")
    @ExcelProperty("邮箱后缀")
    private String domain;

    @Schema(description = "域名MX值,多个用逗号分割")
    @ExcelProperty("域名MX值,多个用逗号分割")
    private String mx;

    @Schema(description = "收邮件服务器")
    @ExcelProperty("收邮件服务器")
    private String inHost;

    @Schema(description = "收邮件端口")
    @ExcelProperty("收邮件端口")
    private Integer inPort;

    @Schema(description = "发邮件服务器")
    @ExcelProperty("发邮件服务器")
    private String outHost;

    @Schema(description = "发邮件端口")
    @ExcelProperty("发邮件端口")
    private Integer outPort;

    @Schema(description = "代理状态")
    @ExcelProperty("代理状态")
    private String proxyStatu;

    @Schema(description = "代理服务器")
    @ExcelProperty("代理服务器")
    private String porxyHost;

    @Schema(description = "代理服务器")
    @ExcelProperty("代理服务器")
    private String proxyPort;

    @Schema(description = "代理用户名")
    @ExcelProperty("代理用户名")
    private String proxyUser;

    @Schema(description = "代理密码")
    @ExcelProperty("代理密码")
    private String proxyPass;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
