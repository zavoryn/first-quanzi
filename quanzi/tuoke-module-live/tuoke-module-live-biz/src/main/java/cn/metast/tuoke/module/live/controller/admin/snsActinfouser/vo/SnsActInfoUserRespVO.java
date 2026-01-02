package cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 报名填写用户信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActInfoUserRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17086")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28406")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "报名用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12306")
    @ExcelProperty("报名用户ID")
    private Long userId;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "选择的时候，选项 ，多个空格分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

    @Schema(description = "报名id，区分多个报名人员", example = "24081")
    @ExcelProperty("报名id，区分多个报名人员")
    private Long actUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


}
