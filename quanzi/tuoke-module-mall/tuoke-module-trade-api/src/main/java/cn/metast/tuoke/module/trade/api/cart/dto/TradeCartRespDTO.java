package cn.metast.tuoke.module.trade.api.cart.dto;

import cn.metast.tuoke.framework.common.enums.TerminalEnum;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderCancelTypeEnum;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderStatusEnum;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单信息 Response DTO
 *
 * @author HUIHUI
 */
@Data
public class TradeCartRespDTO {

    /**
     * 编号，唯一自增
     */
    private Long id;

    /**
     * 用户编号
     *
     * 关联 MemberUserDO 的 id 编号
     */
    private Long userId;

    // ========= 商品信息 =========

    /**
     * 商品 SPU 编号
     *
     * 关联 ProductSpuDO 的 id 编号
     */
    private Long spuId;
    /**
     * 商品 SKU 编号
     *
     * 关联 ProductSkuDO 的 id 编号
     */
    private Long skuId;
    /**
     * 商品购买数量
     */
    private Integer count;
    /**
     * 是否选中
     */
    private Boolean selected;

    private LocalDateTime createTime;

}
