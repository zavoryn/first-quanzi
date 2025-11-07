package cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 帖子点赞 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmPostLikeRespVO {

    @Schema(description = "帖子点赞编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29916")
    @ExcelProperty("帖子点赞编号")
    private Integer id;

    @Schema(description = "帖子id", example = "32316")
    @ExcelProperty("帖子id")
    private Long postId;

    @Schema(description = "评论点赞用户id", example = "1834")
    @ExcelProperty("评论点赞用户id")
    private Long userId;

    @Schema(description = "状态(0取消,1点赞)")
    @ExcelProperty("状态(0取消,1点赞)")
    private Boolean state;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}