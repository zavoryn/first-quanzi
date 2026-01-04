package cn.metast.tuoke.module.live.controller.admin.vo;

import lombok.Data;

import java.util.List;

@Data
//  "发布列表Vo")
public class PostInfoVo {
    //
    private Long postId;

    //来源
    private String source;
    //标题
    private String title;
    //封面链接
    private String coverUrl;
    //标签
    private String tag;
    //
    private String postType;
    //类型：图文 weibo， 图 pic， 视频video 链接：link
    private String contentType;
    //内容，富文本
    private String content;
    //链接地址
    private String linkUrl;
     //创建时间
    private String createTime;
    //地理位置
    private String postMap;
    private String longitude;
    private String latitude;
    //图片个数
    private Integer picNum;
    //图片信息
    private List<PicVo> picInfo;
    //视频信息
    private List<VideoVo> videoInfo;
    private String mediaUrl;
    //是否收藏 Y是
    private String isFavorite;
    //是否点赞 Y是
    private String isLike;
    //是否关注 Y是
    private String isFollow;
    //评论个数
    private Integer commentNum;
    //点赞个数
    private Integer likeNum;
    //转发个数
    private Integer shareNum;
    //浏览个数
    private Integer visitNum;
    //分享url
    private String shareUrl;
    //是否允许评论 Y是 其他否
    private String commentFlag;
    //评论信息
    private List<CommentVo> CommentInfo;

    //访问权限0 所有人 1 社群 2 关注我的人 3 朋友圈  4只有我
    private Integer privacy;

    private Long userId;
    //联系人聊天ID
    private String imuserId;
    //名称
    private String nickName;
    //联系人头像地址
    private String avatar;
    //电话
    private String phone;

    //服务底部信息显示
    private String bottomType;
    /**
     * 访问次数
     */
    private Integer accessCount;

}
