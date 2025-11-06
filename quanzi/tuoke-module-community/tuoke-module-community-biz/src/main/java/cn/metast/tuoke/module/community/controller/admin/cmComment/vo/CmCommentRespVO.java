package cn.metast.tuoke.module.community.controller.admin.cmComment.vo;

import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 圈子评论 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmCommentRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "881")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "父级id", example = "26634")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "评论类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("评论类型")
    private Integer type;

    @Schema(description = "评论作者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15743")
    @ExcelProperty("评论作者ID")
    private Long userId;

    @Schema(description = "被回复用户ID", example = "20226")
    @ExcelProperty("被回复用户ID")
    private Long toUserId;

    @Schema(description = "评论帖子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11472")
    @ExcelProperty("评论帖子ID")
    private Long postId;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("评论内容")
    private String content;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //圈子名称
    private String topicName;
    /** 回复列表 */
    private List<CmCommentDO> replies;
    /** 回复人 */
    private String replyTo;
    /** 帖子 */
    private CmPostDO post;


}
