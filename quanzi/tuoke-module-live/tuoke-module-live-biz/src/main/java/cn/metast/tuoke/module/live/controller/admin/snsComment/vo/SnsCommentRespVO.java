package cn.metast.tuoke.module.live.controller.admin.snsComment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsCommentRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23693")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "comment_id", example = "681")
    @ExcelProperty("comment_id")
    private Long commentId;

    @Schema(description = "动态ID", example = "27012")
    @ExcelProperty("动态ID")
    private Long postId;

    @Schema(description = "用户ID", example = "26641")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "点赞个数")
    @ExcelProperty("点赞个数")
    private Integer likeNum;

    @Schema(description = "tail")
    @ExcelProperty("tail")
    private String tail;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("type")
    private String type;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}