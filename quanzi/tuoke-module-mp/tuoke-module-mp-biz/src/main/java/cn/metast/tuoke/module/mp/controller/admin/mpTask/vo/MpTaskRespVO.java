package cn.metast.tuoke.module.mp.controller.admin.mpTask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 自动开发公众号信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MpTaskRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5918")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "任务id", example = "19820")
    @ExcelProperty("任务id")
    private String taskId;

    @Schema(description = "模板id==待定", example = "22776")
    @ExcelProperty("模板id==待定")
    private Long templateId;

    @Schema(description = "任务名称", example = "赵六")
    @ExcelProperty("任务名称")
    private String taskName;

    @Schema(description = "主题，目标")
    @ExcelProperty("主题，目标")
    private String title;

    @Schema(description = "群发任务的类，1：主题，2：目标，3：随意", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("群发任务的类，1：主题，2：目标，3：随意")
    private Integer chatType;

    @Schema(description = "是否定时任务 0 立即发送 1 定时发送2 每天发送")
    @ExcelProperty("是否定时任务 0 立即发送 1 定时发送2 每天发送")
    private Integer isTask;

    @Schema(description = "发送规则1草稿2审核0发送")
    @ExcelProperty("发送规则1草稿2审核0发送")
    private Integer isRules;

    @Schema(description = "发送人昵称")
    @ExcelProperty("发送人昵称")
    private String sender;

    @Schema(description = "发送人", example = "23414")
    @ExcelProperty("发送人")
    private Long sendUserId;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private String sendTime;

    @Schema(description = "状态0进行中1结束任务2任务暂停", example = "1")
    @ExcelProperty("状态0进行中1结束任务2任务暂停")
    private String status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "指定时间")
    @ExcelProperty("指定时间")
    private String fromDuration;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}