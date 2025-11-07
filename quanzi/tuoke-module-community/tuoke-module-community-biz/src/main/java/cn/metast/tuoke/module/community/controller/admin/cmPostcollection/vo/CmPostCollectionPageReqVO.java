package cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 用户帖子中间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmPostCollectionPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "6169")
    private Long userId;

    @Schema(description = "帖子id", example = "30152")
    private Long postId;

}