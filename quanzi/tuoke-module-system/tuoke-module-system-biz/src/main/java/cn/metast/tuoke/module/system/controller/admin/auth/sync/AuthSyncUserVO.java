package cn.metast.tuoke.module.system.controller.admin.auth.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSyncUserVO {

    private AuthSysUser sysUser;

    private AuthSysTenant sysTenant;

    private AuthZbUser zbUser;
}
