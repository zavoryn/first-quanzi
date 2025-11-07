package cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户帖子中间 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmPostCollectionRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6169")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "帖子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30152")
    @ExcelProperty("帖子id")
    private Long postId;

}