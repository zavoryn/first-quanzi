package cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论点赞新增/修改 Request VO")
@Data
public class CmCommentLikeSaveReqVO {

    @Schema(description = "评论点赞编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31350")
    private Integer id;

    @Schema(description = "评论id", example = "32535")
    private Long commentId;

    @Schema(description = "评论点赞用户id", example = "8061")
    private Long userId;

    @Schema(description = "状态(0取消,1点赞)")
    private Boolean state;

}