package cn.metast.tuoke.module.live.controller.admin.snsAct.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActRespVO {

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7182")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14122")
    @ExcelProperty("post_id")
    private Long postId;

    @Schema(description = "活动类型，字典：活动act/课程:course/会务:meeting", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("活动类型，字典：活动act/课程:course/会务:meeting")
    private String actType;

    @Schema(description = "活动形式（线下 0 线上1 模板2）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("活动形式（线下 0 线上1 模板2）")
    private String actForm;

    @Schema(description = "费用")
    @ExcelProperty("费用")
    private BigDecimal fee;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private BigDecimal longi;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private BigDecimal lati;

    @Schema(description = "地址")
    @ExcelProperty("地址")
    private String location;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Schema(description = "start_time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("start_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Schema(description = "end_time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date endTime;

    @Schema(description = "报名设置")
    @ExcelProperty("报名设置")
    private String regSetting;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String contactphone;

    @Schema(description = "报名人数")
    @ExcelProperty("报名人数")
    private Integer signNum;

    @Schema(description = "create_user_id", example = "30")
    @ExcelProperty("create_user_id")
    private String createUserId;

    @Schema(description = "create_user_name", example = "张三")
    @ExcelProperty("create_user_name")
    private String createUserName;

    @Schema(description = "0正常活动1模板活动", example = "1")
    @ExcelProperty("0正常活动1模板活动")
    private Integer mStatus;

    @Schema(description = "签到二维码")
    @ExcelProperty("签到二维码")
    private String qrcode;

    @Schema(description = "报名是否需要审核(N 否 Y是）")
    @ExcelProperty("报名是否需要审核(N 否 Y是）")
    private String checkFlag;

    @Schema(description = "报名开始时间")
    @ExcelProperty("报名开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appyStartDate;

    @Schema(description = "报名结束时间")
    @ExcelProperty("报名结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appyEndDate;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Long sort;

    @Schema(description = "总人数限制")
    @ExcelProperty("总人数限制")
    private Integer memberLimit;

    @Schema(description = "每人提交数限制")
    @ExcelProperty("每人提交数限制")
    private Integer joinLimit;

    @Schema(description = "收不收款 0 不收  1收")
    @ExcelProperty("收不收款 0 不收  1收")
    private Integer feeLimit;

    @Schema(description = "已提交列表显示 0 显示 1不显示")
    @ExcelProperty("已提交列表显示 0 显示 1不显示")
    private Integer joinMemberView;

    @Schema(description = "按钮显示的文本：立即报名、立即预约、立即申请、立即购买、立即填写")
    @ExcelProperty("按钮显示的文本：立即报名、立即预约、立即申请、立即购买、立即填写")
    private String btnView;

    @Schema(description = "是否可以分享 Y 是 N否")
    @ExcelProperty("是否可以分享 Y 是 N否")
    private String shareCfg;

    @Schema(description = "是否弹出添加机器人Y 是 N否")
    @ExcelProperty("是否弹出添加机器人Y 是 N否")
    private String audtCfg;

    @Schema(description = "费用支出，1固定费用，2参与人*人数")
    @ExcelProperty("费用支出，1固定费用，2参与人*人数")
    private String actPut;

    @Schema(description = "是否推荐：0 否 ，1：是")
    @ExcelProperty("是否推荐：0 否 ，1：是")
    private Integer isRecom;

    @Schema(description = "群聊二维码")
    @ExcelProperty("群聊二维码")
    private String crowdQr;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private String title;
    private String content;
    private String coverUrl;
    private String tag;
    private String isJoin;
    private String reqType;
    private Long loginId;
    private Long fileSize;
    private Date feeTime;//实际缴费时间
    private Date registerTime;
    private Long msFlag;
    private String actInfoCfg;//PC端详情显示
    private int fNum;
    private int bNum;
    private String spType; // 模块类型 智库1和校友2, 服务1和创投2,  5:兰杉学院banner  6:叁号路基金banner  7:兰杉企业研究会banner  8:紫金助企banner
    private Long postFlag;
    private Map<String, Object> params;
}
