package cn.metast.tuoke.module.community.controller.admin.cmTopic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 圈子详情新增/修改 Request VO")
@Data
public class CmTopicSaveReqVO {

    @Schema(description = "圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22021")
    private Long id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String realName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Schema(description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idCard;

    @Schema(description = "微信号/QQ号", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String chatName;

    @Schema(description = "其他账号名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    private String otherName;

    @Schema(description = "星球类型(0收费星球 1不收费)", example = "1")
    private Integer isType;

    @Schema(description = "身份证图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.metast.cn")
    private String idCardUrl;

    @Schema(description = "投资顾问从业资格证书号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificateNo;

    @Schema(description = "月度价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "19905")
    private Integer monthlyPrice;

    @Schema(description = "两月价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "23816")
    private Integer bimonthlyPrice;

    @Schema(description = "季度价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "22890")
    private Integer quarterlyPrice;

    @Schema(description = "四月价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "29339")
    private Integer aprilPrice;

    @Schema(description = "半年价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "26480")
    private Integer halfYearlyPrice;

    @Schema(description = "圈子名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    private String topicName;

    @Schema(description = "封面图片")
    private String coverImage;

    @Schema(description = "圈子描述", example = "你说的对")
    private String description;

    @Schema(description = "创建者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2967")
    private Long userId;

    @Schema(description = "成员数量", example = "23376")
    private Integer memberCount;

    @Schema(description = "帖子数量", example = "241")
    private Integer postCount;

    @Schema(description = "浏览量", example = "4385")
    private Integer viewCount;

    @Schema(description = "是否推荐(0否 1是)")
    private Integer isRecommend;

    @Schema(description = "是否热门(0否 1是)")
    private Integer isHot;

    @Schema(description = "可见范围,0周1一个月2两个月3三个月4半年")
    private Integer isVisibile;

    @Schema(description = "评论审核(0自由评论 1审核)")
    private Integer isComment;

    @Schema(description = "禁止评论(0否 1是)")
    private Integer isNo;

    @Schema(description = "续费加入(0自由加入 1审核)")
    private Integer isRenew;

    @Schema(description = "加入方式(0自由加入 1需要审核)")
    private Integer joinMode;

    @Schema(description = "展示会员数(0否 1是)")
    private Integer isMember;

    @Schema(description = "允许提问(0否 1是)")
    private Integer isQuestion;

    @Schema(description = "禁止复制星球内容(0否 1是)")
    private Integer isCopy;

    @Schema(description = "状态(0正常 1审核中 2禁用)", example = "2")
    private Integer status;

    //更新到聊天室(1未同步 0已同步)
    private Long isSyncChat;
    //更新到微信服务号(1未同步 0已同步)
    private Long isSyncWx;
    //发送次数
    private Long sendNum;
    //发送时间
    private LocalDateTime sendTime;

}
