package cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论点赞 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmCommentLikeRespVO {

    @Schema(description = "评论点赞编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31350")
    @ExcelProperty("评论点赞编号")
    private Integer id;

    @Schema(description = "评论id", example = "32535")
    @ExcelProperty("评论id")
    private Long commentId;

    @Schema(description = "评论点赞用户id", example = "8061")
    @ExcelProperty("评论点赞用户id")
    private Long userId;

    @Schema(description = "状态(0取消,1点赞)")
    @ExcelProperty("状态(0取消,1点赞)")
    private Boolean state;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}