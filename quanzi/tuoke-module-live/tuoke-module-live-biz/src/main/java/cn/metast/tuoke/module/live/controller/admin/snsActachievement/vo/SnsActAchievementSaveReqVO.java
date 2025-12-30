package cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 活动-成绩新增/修改 Request VO")
@Data
public class SnsActAchievementSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21304")
    private Integer id;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "图片")
    private String logo;

    @Schema(description = "分数")
    private Integer fraction;

    @Schema(description = "对手名称", example = "王五")
    private String toname;

    @Schema(description = "对手图片")
    private String tologo;

    @Schema(description = "对手分数")
    private Integer tofraction;

    @Schema(description = "创建人", example = "23472")
    private Integer createUserId;

}