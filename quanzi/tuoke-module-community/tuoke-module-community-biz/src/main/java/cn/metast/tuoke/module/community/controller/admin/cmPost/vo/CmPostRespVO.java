package cn.metast.tuoke.module.community.controller.admin.cmPost.vo;

import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.CmCommentRespVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户发帖详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmPostRespVO {

    @Schema(description = "帖子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "479")
    @ExcelProperty("帖子ID")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9086")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "圈子ID", example = "23695")
    @ExcelProperty("圈子ID")
    private Long topicId;

    @Schema(description = "话题ID", example = "4634")
    @ExcelProperty("话题ID")
    private Long discussId;

    @Schema(description = "投票ID", example = "25518")
    @ExcelProperty("投票ID")
    private Long voteId;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "文件")
    @ExcelProperty("文件")
    private String media;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String pics;

    @Schema(description = "浏览量", example = "15710")
    @ExcelProperty("浏览量")
    private Integer readCount;

    @Schema(description = "置顶")
    @ExcelProperty("置顶")
    private Integer postTop;

    @Schema(description = "帖子类型1图文2文章", example = "2")
    @ExcelProperty("帖子类型1图文2文章")
    private Integer type;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private Double longitude;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private Double latitude;

    @Schema(description = "状态0正常1审核2草稿3定时发布", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态0正常1审核2草稿3定时发布")
    private Integer status;

    @Schema(description = "发布时间")
    @ExcelProperty("发布时间")
    private LocalDateTime sendTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "30204")
    @ExcelProperty("分类")
    private Long cateId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")

    private LocalDateTime createTime;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //圈子名称
    private String topicName;

    private String realName;
    private String certificateNo;

    //是否点赞
    private Boolean isLiked;
    //是否收藏
    private Boolean isCollected;
    //点赞数
    private Integer likeCount;
    //评论数
    private Integer commentCount;
    //收藏数
    private Integer collectCount;
    /** 评论列表 */
    private List<CmCommentDO> comments;
}
