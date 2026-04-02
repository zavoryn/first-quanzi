package cn.metast.tuoke.module.system.controller.admin.auth.sync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthZbUser {

    public static final long serialVersionUID = 1L;

    public long userid;

    /*用户名称 == nickName*/
    public String userName;

    /*手机号 == userName*/
    public String mobile;

    /*用户头像*/
    public String avatar;

    /*性别 0:未设置 1:男 2:女 */
    public int sex;

    /** 操作命令*/
    private String optCode;
    private List<Long> idList;
}
