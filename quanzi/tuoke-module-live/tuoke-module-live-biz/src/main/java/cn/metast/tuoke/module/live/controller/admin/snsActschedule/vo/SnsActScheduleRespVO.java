package cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动日程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActScheduleRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14078")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "item_name", example = "王五")
    @ExcelProperty("item_name")
    private String itemName;

    @Schema(description = "sub_name", example = "赵六")
    @ExcelProperty("sub_name")
    private String subName;

    @Schema(description = "sub_stime")
    @ExcelProperty("sub_stime")
    private LocalDateTime subStime;

    @Schema(description = "sub_etime")
    @ExcelProperty("sub_etime")
    private LocalDateTime subEtime;

    @Schema(description = "sub_context")
    @ExcelProperty("sub_context")
    private String subContext;

    @Schema(description = "parent_id", example = "17712")
    @ExcelProperty("parent_id")
    private Long parentId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}