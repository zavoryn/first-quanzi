package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 邮箱域名MX值新增/修改 Request VO")
@Data
public class EmailMxSaveReqVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10450")
    private Long id;

    @Schema(description = "邮箱类型", example = "1")
    private String type;

    @Schema(description = "邮箱后缀")
    private String domain;

    @Schema(description = "域名MX值,多个用逗号分割")
    private String mx;

    @Schema(description = "收邮件服务器")
    private String inHost;

    @Schema(description = "收邮件端口")
    private Integer inPort;

    @Schema(description = "发邮件服务器")
    private String outHost;

    @Schema(description = "发邮件端口")
    private Integer outPort;

    @Schema(description = "代理状态")
    private String proxyStatu;

    @Schema(description = "代理服务器")
    private String porxyHost;

    @Schema(description = "代理服务器")
    private String proxyPort;

    @Schema(description = "代理用户名")
    private String proxyUser;

    @Schema(description = "代理密码")
    private String proxyPass;

    @Schema(description = "状态", example = "2")
    private String status;

}
