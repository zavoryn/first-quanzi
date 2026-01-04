package cn.metast.tuoke.module.live.controller.admin.snsNews.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 新闻信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsNewsPageReqVO extends PageParam {

    @Schema(description = "news_id", example = "3122")
    private Long newsId;

    @Schema(description = "post_id", example = "11824")
    private Long postId;

    @Schema(description = "type_seting")
    private Long typeSeting;

    @Schema(description = "新闻标识")
    private Long newsConfig;

    @Schema(description = "0上线1下线")
    private String isFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;



    private Long userId;
    private String module;
    private String postType;
    private String tag;
    private String title;
    private String content;
    private String contentType;
    private String coverUrl;
    private String linkUrl;
    private String shareUrl;
    private String mediaUrl;
    private String fileUrl;
    private Integer picNum;
    private Integer shareNum;
    private Integer likeNum;
    private Integer commentNum;
    private Integer visitNum;
    private Integer privacy;
    private String soureFrom;
    private String commentFlag;
    private String postMap;
    private String longitude;
    private String latitude;
    private String addType;
    private String status;
    private String imUserId;
    private Long sort;
    private String spType;
}
