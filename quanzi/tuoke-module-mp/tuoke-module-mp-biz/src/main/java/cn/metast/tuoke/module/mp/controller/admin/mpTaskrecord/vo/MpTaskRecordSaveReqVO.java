package cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公众号发送记录新增/修改 Request VO")
@Data
public class MpTaskRecordSaveReqVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27544")
    private Long id;

    @Schema(description = "任务Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8192")
    @NotEmpty(message = "任务Id不能为空")
    private String taskId;

    @Schema(description = "发送人id", example = "3238")
    private String sendUserId;

    @Schema(description = "发送人", example = "张三")
    private String sendUserName;

    @Schema(description = "发送内容")
    private String content;

    @Schema(description = "附件，待定", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "已发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String sendTime;

    @Schema(description = "接收人id", example = "32284")
    private String receiveUserId;

    @Schema(description = "接收人", example = "王五")
    private String receiveUserName;

    @Schema(description = "状态(0-待发送 1-已发送 2-发送失败)", example = "2")
    private String status;

}
