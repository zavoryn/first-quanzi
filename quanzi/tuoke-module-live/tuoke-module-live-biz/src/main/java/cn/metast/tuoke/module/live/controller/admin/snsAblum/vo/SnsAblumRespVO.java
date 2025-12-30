package cn.metast.tuoke.module.live.controller.admin.snsAblum.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 相册信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsAblumRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7905")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "post_id", example = "12392")
    @ExcelProperty("post_id")
    private Long postId;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;

    @Schema(description = "identifier")
    @ExcelProperty("identifier")
    private String identifier;

    @Schema(description = "meeting会务图集", example = "1")
    @ExcelProperty("meeting会务图集")
    private String type;

    @Schema(description = "user_id", example = "14432")
    @ExcelProperty("user_id")
    private Long userId;

    @Schema(description = "user_name", example = "张三")
    @ExcelProperty("user_name")
    private String userName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}
