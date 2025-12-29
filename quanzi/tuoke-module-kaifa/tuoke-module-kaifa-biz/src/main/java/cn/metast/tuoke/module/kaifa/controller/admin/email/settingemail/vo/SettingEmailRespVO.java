package cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 邮件配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SettingEmailRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "166")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "邮箱类型(1-腾讯qq 2-网易163 3-阿里个人版)", example = "2")
    @ExcelProperty("邮箱类型(1-腾讯qq 2-网易163 3-阿里个人版)")
    private String emailType;

    @Schema(description = "邮件地址")
    @ExcelProperty("邮件地址")
    private String email;

    @Schema(description = "邮件昵称", example = "李四")
    @ExcelProperty("邮件昵称")
    private String name;

    @Schema(description = "邮件密码")
    @ExcelProperty("邮件密码")
    private String password;

    @Schema(description = "收邮件HOST")
    @ExcelProperty("收邮件HOST")
    private String host;

    @Schema(description = "收邮件端口")
    @ExcelProperty("收邮件端口")
    private Integer port;

    @Schema(description = "收邮件SSL")
    @ExcelProperty("收邮件SSL")
    private String inSsl;

    @Schema(description = "写邮件HOST")
    @ExcelProperty("写邮件HOST")
    private String outHost;

    @Schema(description = "写邮件端口")
    @ExcelProperty("写邮件端口")
    private Integer outPort;

    @Schema(description = "写邮件SSL")
    @ExcelProperty("写邮件SSL")
    private String outSsl;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "1-业务邮箱")
    @ExcelProperty("1-业务邮箱")
    private String isYw;

    @Schema(description = "是否启用")
    @ExcelProperty("是否启用")
    private Integer isUser;

    @Schema(description = "用户ID", example = "18493")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "状态(0-正常 1-异常)", example = "2")
    @ExcelProperty("状态(0-正常 1-异常)")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
