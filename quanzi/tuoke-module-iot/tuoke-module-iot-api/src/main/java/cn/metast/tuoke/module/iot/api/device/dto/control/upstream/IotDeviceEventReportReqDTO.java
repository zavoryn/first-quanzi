package cn.metast.tuoke.module.iot.api.device.dto.control.upstream;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

/**
 * IoT 设备【事件】上报 Request DTO
 *
 * @author metast.cn
 */
@Data
public class IotDeviceEventReportReqDTO extends IotDeviceUpstreamAbstractReqDTO {

    /**
     * 事件标识
     */
    @NotEmpty(message = "事件标识不能为空")
    private String identifier;
    /**
     * 事件参数
     */
    private Map<String, Object> params;

}
