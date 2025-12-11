package cn.metast.tuoke.module.iot.api.device.dto.control.upstream;

// TODO @元圈：待实现：/ota/${productKey}/${deviceName}/pull
/**
 * IoT 设备【OTA】升级下拉 Request DTO（拉取固件更新）
 *
 * @author metast.cn
 */
public class IotDeviceOtaPullReqDTO {

    /**
     * 固件编号
     */
    private Long firmwareId;

    /**
     * 固件版本
     */
    private String version;

}
