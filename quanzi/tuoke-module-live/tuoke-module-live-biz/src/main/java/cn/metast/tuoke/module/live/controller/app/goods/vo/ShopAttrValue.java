package cn.metast.tuoke.module.live.controller.app.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties
@ApiModel(value = "com.kalacheng.shop.entity.ShopAttrValue", description = "商品属性值表")
public class ShopAttrValue implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "商品id", name = "goodsId")
    public long goodsId;


    @ApiModelProperty(value = "商品属性id", name = "attributeId")
    public long attributeId;

    @ApiModelProperty(value = "属性值名称", name = "name")
    public String name;
}
