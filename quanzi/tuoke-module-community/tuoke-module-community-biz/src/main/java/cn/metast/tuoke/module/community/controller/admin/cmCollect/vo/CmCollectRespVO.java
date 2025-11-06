package cn.metast.tuoke.module.community.controller.admin.cmCollect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 收藏记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmCollectRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12103")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24529")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "收藏的帖子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14267")
    @ExcelProperty("收藏的帖子id")
    private Long postId;

    @Schema(description = "状态(0取消,1收藏)")
    @ExcelProperty("状态(0取消,1收藏)")
    private Boolean state;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}