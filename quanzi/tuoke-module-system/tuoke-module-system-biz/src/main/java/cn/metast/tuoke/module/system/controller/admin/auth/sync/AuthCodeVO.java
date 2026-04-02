package cn.metast.tuoke.module.system.controller.admin.auth.sync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCodeVO {

    /** 租户名称 */
    protected String tenantId;

    /** 用户账号 */
    protected String username;
}
