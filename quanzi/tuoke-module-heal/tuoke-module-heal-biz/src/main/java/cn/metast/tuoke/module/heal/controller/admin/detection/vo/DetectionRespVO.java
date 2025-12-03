package cn.metast.tuoke.module.heal.controller.admin.detection.vo;

import cn.hutool.json.JSONObject;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 检测记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DetectionRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23060")
    @ExcelProperty("id")
    private Long id;
    private Long[] ids;

    @Schema(description = "设备编号")
    @ExcelProperty("设备编号")
    private String deviceSn;

    @Schema(description = "用户ID", example = "891")
    private Long uid;
    @ExcelProperty("用户")
    private String uname;

    @Schema(description = "档案ID", example = "892")
    private Long aid;
    @ExcelProperty("档案")
    private String aname;

    private ArchivesDO archive;

    @Schema(description = "检测项目", example = "赵六")
    @ExcelProperty("检测项目")
    private String name;

    @Schema(description = "检测结果")
    @ExcelProperty("检测结果")
    private String report;

    @Schema(description = "结果单位")
    @ExcelProperty("结果单位")
    private String unit;

    @Schema(description = "参考范围")
    @ExcelProperty("参考范围")
    private String ckrange;

    @Schema(description = "结果状态")
    @ExcelProperty("结果状态")
    private String status;

    @Schema(description = "是否完成")
    @ExcelProperty("是否完成")
    private String iswc;

    @Schema(description = "样本类型", example = "2")
    @ExcelProperty("样本类型")
    private String type;

    @Schema(description = "添加时间")
    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

    private Map<String, Object> reportData;

}
