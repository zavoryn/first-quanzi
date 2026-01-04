package cn.metast.tuoke.module.live.controller.admin.snsPost.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 动态信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsPostRespVO {

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12195")
    @ExcelProperty("post_id")
    private Long postId;

    @Schema(description = "创建用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20716")
    @ExcelProperty("创建用户ID")
    private Long userId;

    @Schema(description = "类型(dyna 动态 act  活动 bus 商机 ent 企业家 spe 智库 srv  服务 news新闻,community社区,magazine 内刊)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("类型(dyna 动态 act  活动 bus 商机 ent 企业家 spe 智库 srv  服务 news新闻,community社区,magazine 内刊)")
    private String postType;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "封面url", example = "https://www.iocoder.cn")
    @ExcelProperty("封面url")
    private String coverUrl;

    @Schema(description = "标签，多个用分号分割")
    @ExcelProperty("标签，多个用分号分割")
    private String tag;

    @Schema(description = "内容类型 纯文字:text  图文 weibo  纯图 pic  视频video 链接：外部link", example = "1")
    @ExcelProperty("内容类型 纯文字:text  图文 weibo  纯图 pic  视频video 链接：外部link")
    private String contentType;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "网页链接", example = "https://www.iocoder.cn")
    @ExcelProperty("网页链接")
    private String linkUrl;

    @Schema(description = "分享链接", example = "https://www.iocoder.cn")
    @ExcelProperty("分享链接")
    private String shareUrl;

    @Schema(description = "音视频的路径/x相对路径", example = "https://www.iocoder.cn")
    @ExcelProperty("音视频的路径/x相对路径")
    private String mediaUrl;

    @Schema(description = "文件的路径/x相对路径", example = "https://www.iocoder.cn")
    @ExcelProperty("文件的路径/x相对路径")
    private String fileUrl;

    @Schema(description = "图片个数")
    @ExcelProperty("图片个数")
    private Integer picNum;

    @Schema(description = "分享个数")
    @ExcelProperty("分享个数")
    private Integer shareNum;

    @Schema(description = "点赞个数")
    @ExcelProperty("点赞个数")
    private Integer likeNum;

    @Schema(description = "评论个数")
    @ExcelProperty("评论个数")
    private Integer commentNum;

    @Schema(description = "访问次数")
    @ExcelProperty("访问次数")
    private Integer visitNum;

    @Schema(description = "隐私设置(0 所有人 2 关注我的人  4仅自己 )", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("隐私设置(0 所有人 2 关注我的人  4仅自己 )")
    private Integer privacy;

    @Schema(description = "来源(INNER 内部 WXMP 微信公众号 WEIBO 微博 OTHER其他)")
    @ExcelProperty("来源(INNER 内部 WXMP 微信公众号 WEIBO 微博 OTHER其他)")
    private String soureFrom;

    @Schema(description = "是否允许评论(Y 是 N否)")
    @ExcelProperty("是否允许评论(Y 是 N否)")
    private String commentFlag;

    @Schema(description = "位置")
    @ExcelProperty("位置")
    private String postMap;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private String longitude;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private String latitude;

    @Schema(description = "增加方式 0 前端 1 后端手动 2 后端自动", example = "2")
    @ExcelProperty("增加方式 0 前端 1 后端手动 2 后端自动")
    private String addType;

    @Schema(description = "帐号状态（0正常 1删除)", example = "2")
    @ExcelProperty("帐号状态（0正常 1删除)")
    private String status;

    @Schema(description = "联系人id", example = "10888")
    @ExcelProperty("联系人id")
    private String imUserId;

    @Schema(description = "dyna_config")
    @ExcelProperty("dyna_config")
    private Long dynaConfig;

    @Schema(description = "商机轮播图置顶", example = "2")
    @ExcelProperty("商机轮播图置顶")
    private String boType;

    @Schema(description = "联盟服务底部控制，是否显示0显示，1不显示", example = "1")
    @ExcelProperty("联盟服务底部控制，是否显示0显示，1不显示")
    private String bottomType;

    @Schema(description = "模块类型，智库1和校友2，，服务1和创投2", example = "1")
    @ExcelProperty("模块类型，智库1和校友2，，服务1和创投2")
    private String spType;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Long sort;

    @Schema(description = "职位")
    @ExcelProperty("职位")
    private String position;

    @Schema(description = "标签")
    @ExcelProperty("标签")
    private String label;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String address;

    @Schema(description = "是否推荐：0 否 ，1：是")
    @ExcelProperty("是否推荐：0 否 ，1：是")
    private Integer isRecom;

    @Schema(description = "简介")
    @ExcelProperty("简介")
    private String coment;

    @Schema(description = "访问次数", example = "32165")
    @ExcelProperty("访问次数")
    private Integer accessCount;

    @Schema(description = "智库等级(字典sns_spe_zkdj)")
    @ExcelProperty("智库等级(字典sns_spe_zkdj)")
    private String spelevel;

    @Schema(description = "导师类型1：学业深造导师、2：企业发展导师	、3：创新创业导师	、4生活美满导师")
    @ExcelProperty("导师类型1：学业深造导师、2：企业发展导师	、3：创新创业导师	、4生活美满导师")
    private String dslx;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private Date createTime;
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
