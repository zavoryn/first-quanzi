package cn.metast.tuoke.module.live.controller.admin.snsPost.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动态信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsPostPageReqVO extends PageParam {

    @Schema(description = "post_id", example = "12195")
    private Long postId;

    @Schema(description = "创建用户ID", example = "20716")
    private Long userId;

    @Schema(description = "类型(dyna 动态 act  活动 bus 商机 ent 企业家 spe 智库 srv  服务 news新闻,community社区,magazine 内刊)", example = "2")
    private String postType;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "封面url", example = "https://www.iocoder.cn")
    private String coverUrl;

    @Schema(description = "标签，多个用分号分割")
    private String tag;

    @Schema(description = "内容类型 纯文字:text  图文 weibo  纯图 pic  视频video 链接：外部link", example = "1")
    private String contentType;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "网页链接", example = "https://www.iocoder.cn")
    private String linkUrl;

    @Schema(description = "分享链接", example = "https://www.iocoder.cn")
    private String shareUrl;

    @Schema(description = "音视频的路径/x相对路径", example = "https://www.iocoder.cn")
    private String mediaUrl;

    @Schema(description = "文件的路径/x相对路径", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "图片个数")
    private Integer picNum;

    @Schema(description = "分享个数")
    private Integer shareNum;

    @Schema(description = "点赞个数")
    private Integer likeNum;

    @Schema(description = "评论个数")
    private Integer commentNum;

    @Schema(description = "访问次数")
    private Integer visitNum;

    @Schema(description = "隐私设置(0 所有人 2 关注我的人  4仅自己 )")
    private Integer privacy;

    @Schema(description = "来源(INNER 内部 WXMP 微信公众号 WEIBO 微博 OTHER其他)")
    private String soureFrom;

    @Schema(description = "是否允许评论(Y 是 N否)")
    private String commentFlag;

    @Schema(description = "位置")
    private String postMap;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "增加方式 0 前端 1 后端手动 2 后端自动", example = "2")
    private String addType;

    @Schema(description = "帐号状态（0正常 1删除)", example = "2")
    private String status;

    @Schema(description = "联系人id", example = "10888")
    private String imUserId;

    @Schema(description = "dyna_config")
    private Long dynaConfig;

    @Schema(description = "商机轮播图置顶", example = "2")
    private String boType;

    @Schema(description = "联盟服务底部控制，是否显示0显示，1不显示", example = "1")
    private String bottomType;

    @Schema(description = "模块类型，智库1和校友2，，服务1和创投2", example = "1")
    private String spType;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "职位")
    private String position;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "是否推荐：0 否 ，1：是")
    private Integer isRecom;

    @Schema(description = "简介")
    private String coment;

    @Schema(description = "访问次数", example = "32165")
    private Integer accessCount;

    @Schema(description = "智库等级(字典sns_spe_zkdj)")
    private String spelevel;

    @Schema(description = "导师类型1：学业深造导师、2：企业发展导师	、3：创新创业导师	、4生活美满导师")
    private String dslx;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;


    private Long boId;
    private Long industryId;
    private Long areaId;
    private String industryIds;
    private String areaIds;
    private String reqType;
    private String industryName;
    private String areaName;
    private Long spId;
    private String avatar;
    private String contactphone;
    private String intro;
    private String website;
    private Long serviceId;
    private Long typeSeting;
    private Long serviceConfig;
    private Long newsId;
    private Long newsConfig;
    private Long fileSize;
    private Long favUserId;
    private Long subFlag;
    private Long postFlag;
    private String userName;
    private String filterParam;
    private String tutor;
    private String param;
    private List<Map<String,Object>> picUrl;
    /** 职称 */
    private String zc;
    /** 领域 */
    private String area;
    /**
     * 查询权限范围 1：全部 2:相互关注的 3：我关注的
     */
    private Integer range;
    private Long uid;
    private Long servicesId;
}
