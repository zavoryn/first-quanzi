package cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 App - 客服消息 Request VO")
@Data
public class AppKeFuMessagePageReqVO extends PageParam {

    private static final Integer LIMIT = 10;

    @Schema(description = "会话编号", example = "12580")
    private Long conversationId;

    @Schema(description = "接收人编号", example = "29124")
    private Long receiverId;

    @Schema(description = "发送时间", example = "2024-03-27 12:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "每次查询条数，最大值为 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每次查询条数不能为空")
    @Min(value = 1, message = "每次查询条数最小值为 1")
    @Max(value = 100, message = "每次查询最大值为 100")
    private Integer limit = LIMIT;

    //发送人
    private Long sendId;
    //发送人
    private String userName;
    //聊天室topicId
    private Long topicId;
    //聊天室审核状态
    private Integer status;
    //被拉黑用户ID列表（用于排除）
    private List<Long> blockUserIds;
    //关键词
    private String keyword;
}
