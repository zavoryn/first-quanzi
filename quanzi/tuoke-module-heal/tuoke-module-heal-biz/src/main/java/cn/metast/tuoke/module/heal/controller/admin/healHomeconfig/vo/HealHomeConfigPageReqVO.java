package cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 首页模块配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HealHomeConfigPageReqVO extends PageParam {

    @Schema(description = "标题", example = "王五")
    private String name;

    @Schema(description = "图片地址")
    private String icon;

    @Schema(description = "链接地址", example = "https://www.metast.cn")
    private String linkUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "上下线 1（true）上线， 0（false）下线", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
