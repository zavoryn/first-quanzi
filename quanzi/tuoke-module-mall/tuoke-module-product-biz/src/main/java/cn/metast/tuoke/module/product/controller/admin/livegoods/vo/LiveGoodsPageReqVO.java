package cn.metast.tuoke.module.product.controller.admin.livegoods.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 直播商品列分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LiveGoodsPageReqVO extends PageParam {

    @Schema(description = " 主播Id", example = "25015")
    private Long anchorId;

    @Schema(description = " 商品渠道id", example = "16100")
    private Long channelId;

    @Schema(description = "商品价格", example = "24780")
    private Double favorablePrice;

    @Schema(description = " 商品id", example = "4376")
    private Long goodsId;

    @Schema(description = " 商品图片地址")
    private String goodsPicture;

    @Schema(description = " 商品价格", example = "213")
    private Double goodsPrice;

    @Schema(description = " 是否讲解")
    private Integer ifExplain;

    @Schema(description = " 商品名称", example = "王五")
    private String name;

    @Schema(description = "商品链接 ")
    private String productLinks;

    @Schema(description = " 排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
