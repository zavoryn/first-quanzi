package cn.metast.tuoke.module.live.controller.admin.snsNews.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 新闻信息新增/修改 Request VO")
@Data
public class SnsNewsSaveReqVO {
    @Schema(description = "news_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3122")
    private Long newsId;

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11824")
    private Long postId;

    @Schema(description = "type_seting")
    private Long typeSeting;

    @Schema(description = "新闻标识")
    private Long newsConfig;

    @Schema(description = "0上线1下线")
    private String isFlag;

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
