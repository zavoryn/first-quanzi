package cn.metast.tuoke.module.live.controller.admin.snsActnote.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActNoteRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23947")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "user_id", example = "15047")
    @ExcelProperty("user_id")
    private Long userId;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String addr;

    @Schema(description = "create_time")
    @ExcelProperty("create_time")
    private LocalDateTime createTime;

}