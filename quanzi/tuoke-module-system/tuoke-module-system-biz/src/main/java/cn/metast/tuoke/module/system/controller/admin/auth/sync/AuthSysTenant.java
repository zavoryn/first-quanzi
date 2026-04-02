package cn.metast.tuoke.module.system.controller.admin.auth.sync;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSysTenant {

    /** Id */
    protected Long id;

    /** 策略Id */
    protected Long strategyId;

    /** 名称 */
    protected String name;

    /** 系统名称 */
    protected String systemName;

    /** 企业名称 */
    protected String nick;

    /** 企业logo */
    protected String logo;

    /** 企业账号修改次数 */
    protected Long nameFrequency;

    /** 超管租户（Y是 N否） */
    protected String isLessor;

    /** 默认企业（Y是 N否） */
    protected String isDefault;

    /** 状态（0 启用 1 禁用） */
    protected String status;

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

    protected String phone;

    /** 账号数量 */
    protected Integer zhNum;

    /** 试用结束时间 */
    protected Date expirationTime;
}
