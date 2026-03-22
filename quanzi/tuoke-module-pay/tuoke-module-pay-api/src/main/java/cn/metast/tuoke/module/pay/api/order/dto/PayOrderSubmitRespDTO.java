package cn.metast.tuoke.module.pay.api.order.dto;

import lombok.Data;

@Data
public class PayOrderSubmitRespDTO {

    private Integer status;

    private String displayMode;
    private String displayContent;

    private String merchantOrderId;

}
