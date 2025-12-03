package cn.metast.tuoke.module.heal.controller.admin.detection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 检测记录新增/修改 Request VO")
@Data
public class DetectionSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23060")
    private Long id;

    @Schema(description = "设备编号")
    private String deviceSn;

    @Schema(description = "用户ID", example = "891")
    private Long uid;

    @Schema(description = "档案ID", example = "892")
    private Long aid;

    @Schema(description = "检测项目", example = "赵六")
    private String name;

    @Schema(description = "检测结果")
    private String report;

    @Schema(description = "结果单位")
    private String unit;

    @Schema(description = "参考范围")
    private String ckrange;

    @Schema(description = "结果状态")
    private String status;

    @Schema(description = "是否完成")
    private String iswc;

    @Schema(description = "样本类型", example = "2")
    private String type;

}
