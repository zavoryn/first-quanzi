package cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公众号发送记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpTaskRecordPageReqVO extends PageParam {

    @Schema(description = "任务Id", example = "8192")
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
    private String sendTime;

    @Schema(description = "接收人id", example = "32284")
    private String receiveUserId;

    @Schema(description = "接收人", example = "王五")
    private String receiveUserName;

    @Schema(description = "状态(0-待发送 1-已发送 2-发送失败)", example = "2")
    private String status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
