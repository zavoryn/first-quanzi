package cn.metast.tuoke.module.community.dal.dataobject.cmPost;

import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户发帖详情 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_post")
@KeySequence("cm_post_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmPostDO extends BaseDO {

    /**
     * 帖子ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 圈子ID
     */
    private Long topicId;
    /**
     * 话题ID
     */
    private Long discussId;
    /**
     * 投票ID
     */
    private Long voteId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 文件
     */
    private String media;

    /**
     * 图片
     */
    private String pics;

    /**
     * 置顶
     */
    private Integer postTop;
    /**
     * 帖子类型1图文2文章
     */
    private Integer type;
    /**
     * 地址
     */
    private String address;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 状态0正常1审核2草稿3定时发布
     */
    private Integer status;
    /**
     * 发布时间
     */
    private LocalDateTime sendTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 分类
     */
    private Long cateId;

    /**
     * 浏览量
     */
    private Integer readCount;
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

    /**
     * 置顶时间
     */
    private LocalDateTime topTime;


    //用户昵称
    @TableField(exist = false)
    private String nickname;
    //用户头像
    @TableField(exist = false)
    private String avatar;
    //圈子名称
    @TableField(exist = false)
    private String topicName;
    @TableField(exist = false)
    private String realName;
    @TableField(exist = false)
    private String certificateNo;

    //是否点赞
    @TableField(exist = false)
    private Boolean isLiked;
    //是否收藏
    @TableField(exist = false)
    private Boolean isCollected;
    /** 评论列表 */
    @TableField(exist = false)
    private List<CmCommentDO> comments;

}
