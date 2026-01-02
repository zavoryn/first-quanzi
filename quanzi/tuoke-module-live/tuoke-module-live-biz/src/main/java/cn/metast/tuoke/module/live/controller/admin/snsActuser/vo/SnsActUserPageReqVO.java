package cn.metast.tuoke.module.live.controller.admin.snsActuser.vo;

import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.SnsActInfoUserRespVO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动报名人员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActUserPageReqVO extends PageParam {

    @Schema(description = "user_id", example = "14199")
    private Long userId;

    @Schema(description = "act_id", example = "10390")
    private Long actId;

    @Schema(description = "计划要收报名费")
    private Double planFee;

    @Schema(description = "报名时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报名时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "0 报名成功  2待审核 3 审核不通过 -1 退报名", example = "1")
    private Integer status;

    @Schema(description = "实际收到的报名费")
    private Double realFee;

    @Schema(description = "实际收费流水号")
    private String orderNo;

    @Schema(description = "实际缴费时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] feeTime;

    @Schema(description = "报名所填姓名", example = "李四")
    private String sName;

    @Schema(description = "报名所填电话")
    private String sPhone;

    @Schema(description = "报名所填年龄")
    private Integer sAge;

    @Schema(description = "报名所填性别")
    private String sGender;

    @Schema(description = "报名所填单位")
    private String sUnit;

    @Schema(description = "报名所填微信")
    private String sWx;

    @Schema(description = "报名所填邮箱")
    private String sEmail;

    @Schema(description = "预计到达时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] planArriveTime;

    @Schema(description = "签到时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] registerTime;

    @Schema(description = "友谊赛里程(km)")
    private String mileage;

    @Schema(description = "备注", example = "随便")
    private String remark;

    private String title;
    private String tName;
    private String avatar;
    private String valueDate;
    private Long type;
    private String nickName;
    private List<SnsActInfoUserRespVO> actInfoUsers;
    private String actPlayerUserUsers;//报名字段
}
