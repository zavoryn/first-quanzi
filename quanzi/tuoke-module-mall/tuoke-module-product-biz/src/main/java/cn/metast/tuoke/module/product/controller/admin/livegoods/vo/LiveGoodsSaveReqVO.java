package cn.metast.tuoke.module.product.controller.admin.livegoods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 直播商品列新增/修改 Request VO")
@Data
public class LiveGoodsSaveReqVO {

    @Schema(description = "主键ID自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "4815")
    private Long id;

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

}
