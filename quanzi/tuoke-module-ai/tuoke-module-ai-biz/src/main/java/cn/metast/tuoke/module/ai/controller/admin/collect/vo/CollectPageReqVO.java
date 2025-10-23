package cn.metast.tuoke.module.ai.controller.admin.collect.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - AI 功能 收藏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CollectPageReqVO extends PageParam {

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "收藏用户", example = "16522")
    private Long uid;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}