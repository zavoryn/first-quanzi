package cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论回复 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsCommentReplyRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26304")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1089")
    @ExcelProperty("ID")
    private Long replyId;

    @Schema(description = "评论ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8397")
    @ExcelProperty("评论ID")
    private Long commentId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "972")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "点赞个数")
    @ExcelProperty("点赞个数")
    private Integer likeNum;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}