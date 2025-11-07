package cn.metast.tuoke.module.community.controller.admin.cmPost.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户发帖详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmPostPageReqVO extends PageParam {

    @Schema(description = "用户ID", example = "9086")
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

    @Schema(description = "状态0正常1审核2草稿3定时发布", example = "2")
    private Integer status;

    @Schema(description = "发布时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "分类", example = "30204")
    private Long cateId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private String keyWord;
    private String beginTime;
    private String finishTime;
    private String sort;
    private String deleted;

}
