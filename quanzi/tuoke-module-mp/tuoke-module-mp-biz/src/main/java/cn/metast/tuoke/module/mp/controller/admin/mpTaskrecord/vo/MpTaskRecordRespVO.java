package cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公众号发送记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MpTaskRecordRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27544")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "任务Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8192")
    @ExcelProperty("任务Id")
    private String taskId;

    @Schema(description = "发送人id", example = "3238")
    @ExcelProperty("发送人id")
    private String sendUserId;

    @Schema(description = "发送人", example = "张三")
    @ExcelProperty("发送人")
    private String sendUserName;

    @Schema(description = "发送内容")
    @ExcelProperty("发送内容")
    private String content;

    @Schema(description = "附件，待定", example = "https://www.iocoder.cn")
    @ExcelProperty("附件，待定")
    private String url;

    @Schema(description = "已发送时间")
    @ExcelProperty("已发送时间")
    private String sendTime;

    @Schema(description = "接收人id", example = "32284")
    @ExcelProperty("接收人id")
    private String receiveUserId;

    @Schema(description = "接收人", example = "王五")
    @ExcelProperty("接收人")
    private String receiveUserName;

    @Schema(description = "状态(0-待发送 1-已发送 2-发送失败)", example = "2")
    @ExcelProperty("状态(0-待发送 1-已发送 2-发送失败)")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
