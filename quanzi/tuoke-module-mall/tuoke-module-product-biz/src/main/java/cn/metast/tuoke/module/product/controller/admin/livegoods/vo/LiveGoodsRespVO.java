package cn.metast.tuoke.module.product.controller.admin.livegoods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 直播商品列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LiveGoodsRespVO {

    @Schema(description = "主键ID自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "4815")
    @ExcelProperty("主键ID自增")
    private Long id;

    @Schema(description = " 主播Id", example = "25015")
    @ExcelProperty(" 主播Id")
    private Long anchorId;

    @Schema(description = " 商品渠道id", example = "16100")
    @ExcelProperty(" 商品渠道id")
    private Long channelId;

    @Schema(description = "商品价格", example = "24780")
    @ExcelProperty("商品价格")
    private Double favorablePrice;

    @Schema(description = " 商品id", example = "4376")
    @ExcelProperty(" 商品id")
    private Long goodsId;

    @Schema(description = " 商品图片地址")
    @ExcelProperty(" 商品图片地址")
    private String goodsPicture;

    @Schema(description = " 商品价格", example = "213")
    @ExcelProperty(" 商品价格")
    private Double goodsPrice;

    @Schema(description = " 是否讲解")
    @ExcelProperty(" 是否讲解")
    private Integer ifExplain;

    @Schema(description = " 商品名称", example = "王五")
    @ExcelProperty(" 商品名称")
    private String name;

    @Schema(description = "商品链接 ")
    @ExcelProperty("商品链接 ")
    private String productLinks;

    @Schema(description = " 排序")
    @ExcelProperty(" 排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
