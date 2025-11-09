package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 圈子成员新增/修改 Request VO")
@Data
public class CmTopicMemberSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22514")
    private Long id;

    @Schema(description = "圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16198")
    @NotNull(message = "圈子ID不能为空")
    private Long topicId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31473")
    private Long userId;

    @Schema(description = "角色(0普通成员 1管理员 2创建者)")
    private Integer role;

    @Schema(description = "购买次数")
    private Integer orderNum;

    @Schema(description = "状态(0正常 1审核中 2被拒绝3拉黑)", example = "2")
    private Integer status;

    @Schema(description = "禁言结束时间")
    private LocalDateTime muteEndTime;

    @Schema(description = "加入时间")
    private LocalDateTime joinTime;

    @Schema(description = "购买时间")
    private LocalDateTime startTime;

    @Schema(description = "到期时间")
    private LocalDateTime endTime;

    @Schema(description = "付费时长1一月2两月3个月4四个月5半年", example = "2")
    private Integer type;

    @Schema(description = "互动次数")
    private Integer interNum;

    @Schema(description = "拉黑原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    private String blockRemark;

    @Schema(description = "会员备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    private String remark;

    //新传一个参数，0正常 1审核中 2被拒绝3拉黑，拿到老的状态
    private Integer newStatus;

    //是否合约首次1首次2续费
    private Integer isContract;
    /**
     * 延长服务备注
     */
    private String extendRemark;
    private String sendTime;

    private Long[] ids;
}
