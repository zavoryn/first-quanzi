package cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 邮件内容 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EmailInfoRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20807")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户id", example = "28004")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "类型(1-发送 2-接收 3-草稿 4-垃圾邮件 9-自动发送的)", example = "1")
    @ExcelProperty("类型(1-发送 2-接收 3-草稿 4-垃圾邮件 9-自动发送的)")
    private String type;

    @Schema(description = "主题")
    @ExcelProperty("主题")
    private String title;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "文本内容（摘要）")
    @ExcelProperty("文本内容（摘要）")
    private String contentText;

    @Schema(description = "附件（多个）", example = "https://www.metast.cn")
    @ExcelProperty("附件（多个）")
    private String fileUrl;

    @Schema(description = "发送人", example = "元圈")
    @ExcelProperty("发送人")
    private String sendName;

    @Schema(description = "发送邮箱")
    @ExcelProperty("发送邮箱")
    private String sendEmail;

    @Schema(description = "接收人", example = "王五")
    @ExcelProperty("接收人")
    private String receiveName;

    @Schema(description = "接收邮箱")
    @ExcelProperty("接收邮箱")
    private String receiveEmail;

    @Schema(description = "抄送邮箱")
    @ExcelProperty("抄送邮箱")
    private String copyEmail;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "接收时间")
    @ExcelProperty("接收时间")
    private LocalDateTime receiveTime;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handTime;

    @Schema(description = "处理状态", example = "2")
    @ExcelProperty("处理状态")
    private String handStatus;

    @Schema(description = "1-已读")
    @ExcelProperty("1-已读")
    private String isSee;

    @Schema(description = "是否置顶")
    @ExcelProperty("是否置顶")
    private String isTop;

    @Schema(description = "置顶时间")
    @ExcelProperty("置顶时间")
    private LocalDateTime topTime;

    @Schema(description = "文件夹")
    @ExcelProperty("文件夹")
    private Long folder;

    @Schema(description = "来源时区")
    @ExcelProperty("来源时区")
    private String source;

    @Schema(description = "来源更新时间")
    @ExcelProperty("来源更新时间")
    private LocalDateTime sourceTime;

    @Schema(description = "1-已点击")
    @ExcelProperty("1-已点击")
    private String isClick;

    @Schema(description = "1-追踪")
    @ExcelProperty("1-追踪")
    private String isTrack;

    @Schema(description = "打开次数", example = "21879")
    @ExcelProperty("打开次数")
    private Long openCount;

    @Schema(description = "已打开时间")
    @ExcelProperty("已打开时间")
    private LocalDateTime openTime;

    @Schema(description = "打开国家")
    @ExcelProperty("打开国家")
    private String openCountry;

    @Schema(description = "点击次数", example = "6375")
    @ExcelProperty("点击次数")
    private Long clickCount;

    @Schema(description = "已点击时间")
    @ExcelProperty("已点击时间")
    private LocalDateTime clickTime;

    @Schema(description = "点击国家")
    @ExcelProperty("点击国家")
    private String clickCountry;

    @Schema(description = "自动回复邮件")
    @ExcelProperty("自动回复邮件")
    private String isRepaly;

    @Schema(description = "收到的客户回复内容")
    @ExcelProperty("收到的客户回复内容")
    private String replayContent;

    @Schema(description = "上一步绑定的id,或者回复的id", example = "26065")
    @ExcelProperty("上一步绑定的id,或者回复的id")
    private Long replayId;

    @Schema(description = "原生邮件id", example = "26940")
    @ExcelProperty("原生邮件id")
    private Long emailId;

    @Schema(description = "标签")
    @ExcelProperty("标签")
    private String tags;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
