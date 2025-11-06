package cn.metast.tuoke.module.community.controller.admin.cmCategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 圈子分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmCategoryPageReqVO extends PageParam {

    @Schema(description = "分类名称", example = "元圈")
    private String cateName;

    @Schema(description = "是否推荐")
    private Integer isTop;

    @Schema(description = "图片地址")
    private String coverImage;

    @Schema(description = "排序")
    private String sort;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}