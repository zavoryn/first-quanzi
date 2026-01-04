package cn.metast.tuoke.module.live.controller.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(value= JsonInclude.Include.ALWAYS )
@Data
//  "发布列表Vo")
public class PostListVo {
    //
    private Long postId;

    //来源
    private String sourceForm;
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
    /** 经度 */
    private String longitude;
    /** 纬度 */
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
    private Boolean isFollow;
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
    //商机id
    private Long boId;
    //行业Id
    private Long industryId;
    //地区Id
    private Long areaId;
    //行业名称
    private String industryName;
    //地区名称
//    private Long area;
    private Long subFlag;//供需圈1上线，2下线

    //企业家,智库信息
    private Long spId;
    private String contactphone;
    private String intro;
    private String website;
    //服务信息
    private Long typeSeting;
    //服务底部信息显示
    private String bottomType;

    //介绍人头像
    private String avatarUrl;

    private String filterParam;

    private Long newsId;
    /**
     * 职位
     */
    private String position;
    /**
     * 标签
     */
    private String label;
    /**
     * 地址
     */
    private String address;

    /**
     * 简介
     */
    private String coment;
    /**
     * 访问次数
     */
    private Integer accessCount;

    private String spType; // 模块类型 智库1和校友2, 服务1和创投2,  5:兰杉学院banner  6:叁号路基金banner  7:兰杉企业研究会banner  8:紫金助企banner

    private String dslx;

    /** 职称 */
    private String zc;
    /** 领域 */
    private String area;
}
