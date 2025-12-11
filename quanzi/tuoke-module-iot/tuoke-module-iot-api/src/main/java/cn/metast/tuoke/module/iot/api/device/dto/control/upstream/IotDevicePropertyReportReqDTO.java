package cn.metast.tuoke.module.iot.api.device.dto.control.upstream;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

/**
 * IoT 设备【属性】上报 Request DTO
 *
 * @author metast.cn
 */
@Data
public class IotDevicePropertyReportReqDTO extends IotDeviceUpstreamAbstractReqDTO {

    /**
     * 属性参数
     */
    @NotEmpty(message = "属性参数不能为空")
    private Map<String, Object> properties;

}