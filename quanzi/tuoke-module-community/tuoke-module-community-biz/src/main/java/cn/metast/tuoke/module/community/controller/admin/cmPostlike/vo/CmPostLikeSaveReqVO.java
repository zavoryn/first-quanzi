package cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 帖子点赞新增/修改 Request VO")
@Data
public class CmPostLikeSaveReqVO {

    @Schema(description = "帖子点赞编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29916")
    private Integer id;

    @Schema(description = "帖子id", example = "32316")
    private Long postId;

    @Schema(description = "评论点赞用户id", example = "1834")
    private Long userId;

    @Schema(description = "状态(0取消,1点赞)")
    private Boolean state;

}