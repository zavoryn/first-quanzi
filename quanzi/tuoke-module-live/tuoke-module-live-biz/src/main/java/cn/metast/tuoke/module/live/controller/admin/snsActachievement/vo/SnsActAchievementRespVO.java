package cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动-成绩 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActAchievementRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21304")
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String logo;

    @Schema(description = "分数")
    @ExcelProperty("分数")
    private Integer fraction;

    @Schema(description = "对手名称", example = "王五")
    @ExcelProperty("对手名称")
    private String toname;

    @Schema(description = "对手图片")
    @ExcelProperty("对手图片")
    private String tologo;

    @Schema(description = "对手分数")
    @ExcelProperty("对手分数")
    private Integer tofraction;

    @Schema(description = "创建人", example = "23472")
    @ExcelProperty("创建人")
    private Integer createUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}