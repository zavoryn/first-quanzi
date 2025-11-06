package cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 评论用户关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmCommentThumbsRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12153")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "评论id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17057")
    @ExcelProperty("评论id")
    private Long commentId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26441")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}