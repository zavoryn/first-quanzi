package cn.metast.tuoke.module.live.controller.admin.snsActguest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动嘉宾 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActGuestRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28014")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15421")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "name", example = "李四")
    @ExcelProperty("name")
    private String name;

    @Schema(description = "avatar")
    @ExcelProperty("avatar")
    private String avatar;

    @Schema(description = "introduce")
    @ExcelProperty("introduce")
    private String introduce;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}