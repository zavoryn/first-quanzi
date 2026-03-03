package cn.metast.tuoke.module.member.api.user.dto;

import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息 Response DTO
 *
 * @author metast.cn
 */
@Data
public class MemberUserRespDTO {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String name;
    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 创建时间（注册时间）
     */
    private LocalDateTime createTime;

    /**
     * areaId  关联地区id
     */
    private Long areaId;


    /**
     * 会员备注
     */
    private String mark;

    // ========== 其它信息 ==========

    /**
     * 会员级别编号
     */
    private Long levelId;

    /**
     * 积分
     */
    private Integer point;

    /**
     * 分配客服Id
     */
    private String kefuId;

    private String tjDate;
    private String bsNum;
    private String xlNum;
    private String smNum;
}
