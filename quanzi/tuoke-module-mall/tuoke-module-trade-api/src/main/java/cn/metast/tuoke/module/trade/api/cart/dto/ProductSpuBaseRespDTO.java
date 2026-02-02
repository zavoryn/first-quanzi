package cn.metast.tuoke.module.trade.api.cart.dto;

import lombok.Data;

/**
 * 订单信息 Response DTO
 *
 * @author HUIHUI
 */
@Data
public class ProductSpuBaseRespDTO {

    private Long id;

    private String name;

    private String picUrl;

    private Long categoryId;

}
