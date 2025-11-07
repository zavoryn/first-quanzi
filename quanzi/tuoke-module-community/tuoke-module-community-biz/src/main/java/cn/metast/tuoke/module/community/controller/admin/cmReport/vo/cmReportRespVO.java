package cn.metast.tuoke.module.community.controller.admin.cmReport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 举报记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class cmReportRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18036")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13770")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "举报用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3798")
    @ExcelProperty("举报用户id")
    private Long reportUserId;

    @Schema(description = "举报原因", example = "不好")
    @ExcelProperty("举报原因")
    private String reason;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "圈子id", example = "1024")
    @ExcelProperty("圈子id")
    private Long topicId;

}
