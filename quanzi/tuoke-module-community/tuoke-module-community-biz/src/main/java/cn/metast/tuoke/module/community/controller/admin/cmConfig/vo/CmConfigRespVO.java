package cn.metast.tuoke.module.community.controller.admin.cmConfig.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 圈子配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmConfigRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25595")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "部门id", example = "4803")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "圈子id", example = "14062")
    @ExcelProperty("圈子id")
    private Long topicId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}