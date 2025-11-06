package cn.metast.tuoke.module.community.controller.admin.cmFollow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户关注中间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmFollowRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14283")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7473")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "关注的用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23199")
    @ExcelProperty("关注的用户id")
    private Long followUserId;

    @Schema(description = "状态(0取消,1关注)")
    @ExcelProperty("状态(0取消,1关注)")
    private Boolean state;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}