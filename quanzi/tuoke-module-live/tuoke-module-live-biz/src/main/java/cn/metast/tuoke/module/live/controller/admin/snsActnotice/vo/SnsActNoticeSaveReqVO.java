package cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 活动公告新增/修改 Request VO")
@Data
public class SnsActNoticeSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23946")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7203")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "公共标题")
    private String title;

    @Schema(description = "时间")
    private LocalDateTime time;

    @Schema(description = "公告内容")
    private String context;

    @Schema(description = "公告类型（1通知 2公告）", example = "1")
    private String noticeType;

    @Schema(description = "公告状态（0正常 1关闭）", example = "1")
    private String status;

    @Schema(description = "re")
    private String re;

    @Schema(description = "发送用户的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26156")
    @NotEmpty(message = "发送用户的id不能为空")
    private String fromId;

    @Schema(description = "接收用户的id", example = "29471")
    private String toId;

    @Schema(description = "single 单个 some  特定人  all 全部")
    private String scope;

}