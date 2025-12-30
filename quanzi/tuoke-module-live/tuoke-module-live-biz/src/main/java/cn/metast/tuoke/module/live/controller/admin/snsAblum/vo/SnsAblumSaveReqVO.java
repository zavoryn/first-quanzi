package cn.metast.tuoke.module.live.controller.admin.snsAblum.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 相册信息新增/修改 Request VO")
@Data
public class SnsAblumSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7905")
    private Long id;

    @Schema(description = "post_id", example = "12392")
    private Long postId;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "identifier")
    private String identifier;

    @Schema(description = "meeting会务图集", example = "1")
    private String type;

    @Schema(description = "user_id", example = "14432")
    private Long userId;

    @Schema(description = "user_name", example = "张三")
    private String userName;

}