package cn.metast.tuoke.module.live.controller.admin.snsAblum.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 相册信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsAblumPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}