package cn.metast.tuoke.module.trade.api.order.dto;

import lombok.Data;

@Data
public class ProductPropertyValueDetailRespDTO {

    private Long propertyId;

    private String propertyName;

    private Long valueId;

    private String valueName;

}
