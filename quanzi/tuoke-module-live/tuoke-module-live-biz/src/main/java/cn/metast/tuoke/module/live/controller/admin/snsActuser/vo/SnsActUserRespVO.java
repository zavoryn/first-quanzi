package cn.metast.tuoke.module.live.controller.admin.snsActuser.vo;

import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.SnsActInfoUserRespVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动报名人员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActUserRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22768")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14199")
    @ExcelProperty("user_id")
    private Long userId;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10390")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "计划要收报名费")
    @ExcelProperty("计划要收报名费")
    private Double planFee;

    @Schema(description = "报名时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报名时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "0 报名成功  2待审核 3 审核不通过 -1 退报名", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("0 报名成功  2待审核 3 审核不通过 -1 退报名")
    private Integer status;

    @Schema(description = "实际收到的报名费")
    @ExcelProperty("实际收到的报名费")
    private Double realFee;

    @Schema(description = "实际收费流水号")
    @ExcelProperty("实际收费流水号")
    private String orderNo;

    @Schema(description = "实际缴费时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("实际缴费时间")
    private Date feeTime;

    @Schema(description = "报名所填姓名", example = "李四")
    @ExcelProperty("报名所填姓名")
    private String sName;

    @Schema(description = "报名所填电话")
    @ExcelProperty("报名所填电话")
    private String sPhone;

    @Schema(description = "报名所填年龄")
    @ExcelProperty("报名所填年龄")
    private Integer sAge;

    @Schema(description = "报名所填性别")
    @ExcelProperty("报名所填性别")
    private String sGender;

    @Schema(description = "报名所填单位")
    @ExcelProperty("报名所填单位")
    private String sUnit;

    @Schema(description = "报名所填微信")
    @ExcelProperty("报名所填微信")
    private String sWx;

    @Schema(description = "报名所填邮箱")
    @ExcelProperty("报名所填邮箱")
    private String sEmail;

    @Schema(description = "预计到达时间")
    @ExcelProperty("预计到达时间")
    private Date planArriveTime;

    @Schema(description = "签到时间")
    @ExcelProperty("签到时间")
    private Date registerTime;

    @Schema(description = "友谊赛里程(km)")
    @ExcelProperty("友谊赛里程(km)")
    private String mileage;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    //@Excel(name = "报名信息")
    private String title;
    private String tName;
    private String avatar;
    private String valueDate;
    private Long type;
    private String nickName;

    private List<SnsActInfoUserRespVO> actInfoUsers;
    private String actPlayerUserUsers;//报名字段
}
