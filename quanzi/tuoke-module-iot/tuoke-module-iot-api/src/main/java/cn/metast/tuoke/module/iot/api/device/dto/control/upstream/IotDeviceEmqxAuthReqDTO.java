package cn.metast.tuoke.module.iot.api.device.dto.control.upstream;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

// TODO @元圈：要不要继承 IotDeviceUpstreamAbstractReqDTO
// TODO @元圈：@haohao：后续其它认证的设计
/**
 * IoT 认证 Emqx 连接 Request DTO
 *
 * @author metast.cn
 */
@Data
public class IotDeviceEmqxAuthReqDTO {

    /**
     * 客户端 ID
     */
    @NotEmpty(message = "客户端 ID 不能为空")
    private String clientId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

}
