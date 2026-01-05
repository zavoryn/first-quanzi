package cn.metast.tuoke.module.live.controller.app.goods.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopGoodsAttrDTO", description = "商品属性返回")
public class ShopGoodsAttrDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品属性id", name = "attrId")
    public long attrId;

    @ApiModelProperty(value = "商品属性名称", name = "attrName")
    public String attrName;

    @ApiModelProperty(value = "商品属性值", name = "attrValueList")
    public List<ShopAttrValue> attrValueList;
}
