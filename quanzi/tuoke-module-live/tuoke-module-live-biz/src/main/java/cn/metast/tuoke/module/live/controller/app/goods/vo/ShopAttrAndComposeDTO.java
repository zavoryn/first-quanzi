package cn.metast.tuoke.module.live.controller.app.goods.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopAttrAndComposeDTO", description = "返回商品属性及属性值组合")
public class ShopAttrAndComposeDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性及属性值", name = "shopGoodsAttrDTOS")
    public List<ShopGoodsAttrDTO> shopGoodsAttrDTOS;

    @ApiModelProperty(value = "属性值组合", name = "attrComposeList")
    public List<ShopAttrCompose> attrComposeList;


}
