package cn.metast.tuoke.module.live.controller.app.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties
@ApiModel(value = "com.kalacheng.shop.entity.ShopAttrCompose", description = "商品详情表")
public class ShopAttrCompose implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "商品id", name = "businessId")
    public long goodsId;

    @ApiModelProperty(value = "商品属性值1id", name = "attribute1Id")
    public long attribute1Id;

    @ApiModelProperty(value = "属性值1名称", name = "name1")
    public String name1;

    @ApiModelProperty(value = "商品属性值2id", name = "attribute2Id")
    public long attribute2Id;

    @ApiModelProperty(value = "属性值2名称", name = "name2")
    public String name2;

    @ApiModelProperty(value = "商品价格", name = "price")
    public double price;

    @ApiModelProperty(value = "优惠价格", name = "favorablePrice")
    public double favorablePrice;

    @ApiModelProperty(value = "库存", name = "stock")
    public int stock;

    @ApiModelProperty(value = "冻结库存", name = "stock")
    public int frozenStock;

}
