package cn.metast.tuoke.module.community.controller.admin.cmPost.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户发帖详情新增/修改 Request VO")
@Data
public class CmPostSaveReqVO {

    @Schema(description = "帖子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "479")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9086")
    private Long userId;

    @Schema(description = "圈子ID", example = "23695")
    private Long topicId;

    @Schema(description = "话题ID", example = "4634")
    private Long discussId;

    @Schema(description = "投票ID", example = "25518")
    private Long voteId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "文件")
    private String media;

    @Schema(description = "图片")
    private String pics;

    @Schema(description = "浏览量", example = "15710")
    private Integer readCount;

    @Schema(description = "置顶")
    private Integer postTop;

    @Schema(description = "帖子类型1图文2文章", example = "2")
    private Integer type;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "经度")
    private Double longitude;

    @Schema(description = "纬度")
    private Double latitude;

    @Schema(description = "状态0正常1审核2草稿3定时发布", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer status;

    @Schema(description = "发布时间")
    private LocalDateTime sendTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "30204")
    private Long cateId;

    //同步聊天室
    private Long isSyncChat;
    //同步微信服务号
    private Long isSyncWx;

    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 收藏数
     */
    private Integer collectCount;

    //置顶时间
    private LocalDateTime topTime;

}
