package cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActInfoTemplatePageReqVO extends PageParam {

    @Schema(description = "编号", example = "7279")
    private Long columnId;

    @Schema(description = "字段名称", example = "芋艿")
    private String fieldName;

    @Schema(description = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选", example = "2")
    private Integer fieldType;

    @Schema(description = "输入 0 必填  1 选填")
    private String fieldInput;

    @Schema(description = "选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}