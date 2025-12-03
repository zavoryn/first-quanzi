package cn.metast.tuoke.module.heal.controller.admin.detection.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检测记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DetectionPageReqVO extends PageParam {

    @Schema(description = "设备编号")
    private String deviceSn;

    @Schema(description = "用户ID", example = "891")
    private Long uid;

    @Schema(description = "用户ID", example = "892")
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

    @Schema(description = "添加时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
