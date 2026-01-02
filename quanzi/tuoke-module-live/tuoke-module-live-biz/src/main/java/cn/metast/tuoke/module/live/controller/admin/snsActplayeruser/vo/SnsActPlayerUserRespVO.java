package cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 参与人信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActPlayerUserRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24989")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26083")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "报名用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18805")
    @ExcelProperty("报名用户ID")
    private Long userId;

    @Schema(description = "参与人员信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("参与人员信息")
    private String fieldValue;

    @Schema(description = "报名人员id，区分多个报名人员", example = "27910")
    @ExcelProperty("报名人员id，区分多个报名人员")
    private Long actUserId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private String title;

}
