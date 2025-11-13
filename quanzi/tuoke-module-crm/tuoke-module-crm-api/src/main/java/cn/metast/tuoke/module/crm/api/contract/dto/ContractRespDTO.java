package cn.metast.tuoke.module.crm.api.contract.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同响应 DTO
 */
@Data
public class ContractRespDTO {

    /**
     * 合同ID
     */
    private Long id;

    /**
     * 合同编号
     */
    private String no;

    /**
     * 合同金额（单位：元）
     */
    private BigDecimal totalPrice;
}
