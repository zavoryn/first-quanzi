package cn.metast.tuoke.module.system.controller.admin.auth.sync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSysUser {

    /** Id */
    protected Long id;

    /** 用户编码 */
    protected String code;

    /** 用户账号 */
    protected String userName;

    /** 用户昵称 */
    protected String nickName;

    /** 用户标识（00超级管理员） */
    protected String userType;

    /** 手机号码 */
    protected String phone;

    /** 用户邮箱 */
    protected String email;

    /** 用户性别 */
    protected String sex;

    protected String status;

    /** 用户头像 */
    protected String avatar;

    /** 密码 */
    protected String password;

    /**
     * 地址
     */
    private String address;

    private String tenantId;

    /** 显示顺序 */
    protected Integer sort;

    /** 备注 */
    protected String remark;

    /** 创建者Id */
    protected Long createBy;

    /** 创建时间 */
    protected LocalDateTime createTime;

    /** 更新者Id */
    protected Long updateBy;

    /** 更新时间 */
    protected LocalDateTime updateTime;

    /** 删除标志 */
    protected Long delFlag;

    /** 操作命令*/
    private String optCode;
    private List<Long> idList;
}
