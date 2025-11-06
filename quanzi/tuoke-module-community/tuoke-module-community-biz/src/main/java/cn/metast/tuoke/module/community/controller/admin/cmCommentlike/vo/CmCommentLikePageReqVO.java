package cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论点赞分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmCommentLikePageReqVO extends PageParam {

    @Schema(description = "评论id", example = "32535")
    private Long commentId;

    @Schema(description = "评论点赞用户id", example = "8061")
    private Long userId;

    @Schema(description = "状态(0取消,1点赞)")
    private Boolean state;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}