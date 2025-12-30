package cn.metast.tuoke.module.live.controller.admin.snsActfile.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 活动资料新增/修改 Request VO")
@Data
public class SnsActFileSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12943")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1817")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "文件标题")
    private String title;

    @Schema(description = "时间")
    private LocalDateTime time;

    @Schema(description = "文件存储id", example = "6493")
    private Long fileId;

}