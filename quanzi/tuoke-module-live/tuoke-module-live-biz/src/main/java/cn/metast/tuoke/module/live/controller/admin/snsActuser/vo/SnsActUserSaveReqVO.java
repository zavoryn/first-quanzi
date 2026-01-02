package cn.metast.tuoke.module.live.controller.admin.snsActuser.vo;

import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.SnsActInfoUserRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 活动报名人员新增/修改 Request VO")
@Data
public class SnsActUserSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22768")
    private Long id;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14199")
    @NotNull(message = "user_id不能为空")
    private Long userId;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10390")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "计划要收报名费")
    private Double planFee;

    @Schema(description = "0 报名成功  2待审核 3 审核不通过 -1 退报名", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "0 报名成功  2待审核 3 审核不通过 -1 退报名不能为空")
    private Integer status;

    @Schema(description = "实际收到的报名费")
    private Double realFee;

    @Schema(description = "实际收费流水号")
    private String orderNo;

    @Schema(description = "实际缴费时间")
    private LocalDateTime feeTime;

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
    private LocalDateTime planArriveTime;

    @Schema(description = "签到时间")
    private LocalDateTime registerTime;

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
