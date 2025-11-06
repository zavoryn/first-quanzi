package cn.metast.tuoke.module.community.controller.admin.cmBlock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 拉黑记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class cmBlockRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27516")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16962")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "拉黑用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9914")
    @ExcelProperty("拉黑用户id")
    private Long blockUserId;

    @Schema(description = "圈子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    @ExcelProperty("圈子id")
    private Long topicId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
