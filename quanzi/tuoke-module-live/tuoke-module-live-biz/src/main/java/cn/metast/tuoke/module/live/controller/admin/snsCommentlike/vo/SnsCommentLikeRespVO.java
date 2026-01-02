package cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论点赞人 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsCommentLikeRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3868")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19662")
    @ExcelProperty("post_id")
    private Long postId;

    @Schema(description = "comment_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1886")
    @ExcelProperty("comment_id")
    private Long commentId;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28720")
    @ExcelProperty("user_id")
    private Long userId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}