package cn.metast.tuoke.module.mp.controller.admin.mpTask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 自动开发公众号信息新增/修改 Request VO")
@Data
public class MpTaskSaveReqVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5918")
    private Long id;

    @Schema(description = "任务id", example = "19820")
    private String taskId;

    @Schema(description = "模板id==待定", example = "22776")
    private Long templateId;

    @Schema(description = "任务名称", example = "赵六")
    private String taskName;

    @Schema(description = "主题，目标")
    private String title;

    @Schema(description = "群发任务的类，1：主题，2：目标，3：随意", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer chatType;

    @Schema(description = "是否定时任务 0 立即发送 1 定时发送2 每天发送")
    private Integer isTask;

    @Schema(description = "发送规则1草稿2审核0发送")
    private Integer isRules;

    @Schema(description = "发送人昵称")
    private String sender;

    @Schema(description = "发送人", example = "23414")
    private Long sendUserId;

    @Schema(description = "发送时间")
    private String sendTime;

    @Schema(description = "状态0进行中1结束任务2任务暂停", example = "1")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "指定时间")
    private String fromDuration;

    private String templateValue;

}
