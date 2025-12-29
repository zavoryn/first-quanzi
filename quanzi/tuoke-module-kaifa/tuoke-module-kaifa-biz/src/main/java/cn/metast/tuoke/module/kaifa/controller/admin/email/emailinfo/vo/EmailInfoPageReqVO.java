package cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 邮件内容分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmailInfoPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "28004")
    private Long userId;

    @Schema(description = "类型(1-发送 2-接收 3-草稿 4-垃圾邮件 9-自动发送的)", example = "1")
    private String type;

    @Schema(description = "主题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "文本内容（摘要）")
    private String contentText;

    @Schema(description = "附件（多个）", example = "https://www.metast.cn")
    private String fileUrl;

    @Schema(description = "发送人", example = "元圈")
    private String sendName;

    @Schema(description = "发送邮箱")
    private String sendEmail;

    @Schema(description = "接收人", example = "王五")
    private String receiveName;

    @Schema(description = "接收邮箱")
    private String receiveEmail;

    @Schema(description = "抄送邮箱")
    private String copyEmail;

    @Schema(description = "发送时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

    @Schema(description = "接收时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] receiveTime;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] handTime;

    @Schema(description = "处理状态", example = "2")
    private String handStatus;

    @Schema(description = "1-已读")
    private String isSee;

    @Schema(description = "是否置顶")
    private String isTop;

    @Schema(description = "置顶时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] topTime;

    @Schema(description = "文件夹")
    private Long folder;

    @Schema(description = "来源时区")
    private String source;

    @Schema(description = "来源更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sourceTime;

    @Schema(description = "1-已点击")
    private String isClick;

    @Schema(description = "1-追踪")
    private String isTrack;

    @Schema(description = "打开次数", example = "21879")
    private Long openCount;

    @Schema(description = "已打开时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] openTime;

    @Schema(description = "打开国家")
    private String openCountry;

    @Schema(description = "点击次数", example = "6375")
    private Long clickCount;

    @Schema(description = "已点击时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] clickTime;

    @Schema(description = "点击国家")
    private String clickCountry;

    @Schema(description = "自动回复邮件")
    private String isRepaly;

    @Schema(description = "收到的客户回复内容")
    private String replayContent;

    @Schema(description = "上一步绑定的id,或者回复的id", example = "26065")
    private Long replayId;

    @Schema(description = "原生邮件id", example = "26940")
    private Long emailId;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
