package cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 档案信息关联用户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ArchivesUserRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22335")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28722")
    @ExcelProperty("用户ID")
    private Long uid;

    @Schema(description = "档案ID", example = "20560")
    @ExcelProperty("档案ID")
    private Long archivesId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
