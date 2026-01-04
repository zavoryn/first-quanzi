package cn.metast.tuoke.module.live.controller.admin.snsNews.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 新闻信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsNewsRespVO {

    @Schema(description = "news_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3122")
    @ExcelProperty("news_id")
    private Long newsId;

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11824")
    @ExcelProperty("post_id")
    private Long postId;

    @Schema(description = "type_seting")
    @ExcelProperty("type_seting")
    private Long typeSeting;

    @Schema(description = "新闻标识")
    @ExcelProperty("新闻标识")
    private Long newsConfig;

    @Schema(description = "0上线1下线")
    @ExcelProperty("0上线1下线")
    private String isFlag;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

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
