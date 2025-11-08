package cn.metast.tuoke.module.community.controller.admin.cmTopic.vo;

import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 圈子详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmTopicRespVO {

    @Schema(description = "圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22021")
    @ExcelProperty("圈子ID")
    private Long id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("姓名")
    private String realName;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("身份证号")
    private String idCard;

    @Schema(description = "微信号/QQ号", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("微信号/QQ号")
    private String chatName;

    @Schema(description = "其他账号名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    @ExcelProperty("其他账号名称")
    private String otherName;

    @Schema(description = "星球类型(0收费星球 1不收费)", example = "1")
    @ExcelProperty("星球类型(0收费星球 1不收费)")
    private Integer isType;

    @Schema(description = "身份证图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.metast.cn")
    @ExcelProperty("身份证图片")
    private String idCardUrl;

    @Schema(description = "投资顾问从业资格证书号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("投资顾问从业资格证书号")
    private String certificateNo;

    @Schema(description = "月度价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "19905")
    @ExcelProperty("月度价格")
    private Integer monthlyPrice;

    @Schema(description = "两月价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "23816")
    @ExcelProperty("两月价格")
    private Integer bimonthlyPrice;

    @Schema(description = "季度价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "22890")
    @ExcelProperty("季度价格")
    private Integer quarterlyPrice;

    @Schema(description = "四月价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "29339")
    @ExcelProperty("四月价格")
    private Integer aprilPrice;

    @Schema(description = "半年价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "26480")
    @ExcelProperty("半年价格")
    private Integer halfYearlyPrice;

    @Schema(description = "圈子名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    @ExcelProperty("圈子名称")
    private String topicName;

    @Schema(description = "封面图片")
    @ExcelProperty("封面图片")
    private String coverImage;

    @Schema(description = "圈子描述", example = "你说的对")
    @ExcelProperty("圈子描述")
    private String description;

    @Schema(description = "创建者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2967")
    @ExcelProperty("创建者ID")
    private Long userId;

    @Schema(description = "成员数量", example = "23376")
    @ExcelProperty("成员数量")
    private Integer memberCount;

    @Schema(description = "帖子数量", example = "241")
    @ExcelProperty("帖子数量")
    private Integer postCount;

    @Schema(description = "浏览量", example = "4385")
    @ExcelProperty("浏览量")
    private Integer viewCount;

    @Schema(description = "是否推荐(0否 1是)")
    @ExcelProperty("是否推荐(0否 1是)")
    private Integer isRecommend;

    @Schema(description = "是否热门(0否 1是)")
    @ExcelProperty("是否热门(0否 1是)")
    private Integer isHot;

    @Schema(description = "可见范围,0周1一个月2两个月3三个月4半年")
    @ExcelProperty("可见范围,0周1一个月2两个月3三个月4半年")
    private Integer isVisibile;

    @Schema(description = "评论审核(0自由评论 1审核)")
    @ExcelProperty("评论审核(0自由评论 1审核)")
    private Integer isComment;

    @Schema(description = "禁止评论(0否 1是)")
    @ExcelProperty("禁止评论(0否 1是)")
    private Integer isNo;

    @Schema(description = "续费加入(0自由加入 1审核)")
    @ExcelProperty("续费加入(0自由加入 1审核)")
    private Integer isRenew;

    @Schema(description = "加入方式(0自由加入 1需要审核)")
    @ExcelProperty("加入方式(0自由加入 1需要审核)")
    private Integer joinMode;

    @Schema(description = "展示会员数(0否 1是)")
    @ExcelProperty("展示会员数(0否 1是)")
    private Integer isMember;

    @Schema(description = "允许提问(0否 1是)")
    @ExcelProperty("允许提问(0否 1是)")
    private Integer isQuestion;

    @Schema(description = "禁止复制星球内容(0否 1是)")
    @ExcelProperty("禁止复制星球内容(0否 1是)")
    private Integer isCopy;

    @Schema(description = "状态(0正常 1审核中 2禁用)", example = "2")
    @ExcelProperty("状态(0正常 1审核中 2禁用)")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    List<Map<String,Object>> prices;
    Integer payStatus;
    CmTopicMemberDO cmTopicMember;

    //更新到聊天室(1未同步 0已同步)
    private Long isSyncChat;
    //更新到微信服务号(1未同步 0已同步)
    private Long isSyncWx;
    //发送次数
    private Long sendNum;
    //发送时间
    private LocalDateTime sendTime;
}
