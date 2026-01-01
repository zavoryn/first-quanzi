package cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论回复点赞人数 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsCommentReplyLikeRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23655")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "reply_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28419")
    @ExcelProperty("reply_id")
    private Long replyId;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20028")
    @ExcelProperty("user_id")
    private Long userId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}