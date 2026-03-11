package cn.metast.tuoke.module.mp.dal.dataobject.mpTask;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 自动开发公众号信息 DO
 *
 * @author 苏丹家园
 */
@TableName("mp_task")
@KeySequence("mp_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpTaskDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 模板id==待定
     */
    private Long templateId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 主题，目标
     */
    private String title;
    /**
     * 群发任务的类，1：主题，2：目标，3：随意
     */
    private Integer chatType;
    /**
     * 是否定时任务 0 立即发送 1 定时发送2 每天发送
     */
    private Integer isTask;
    /**
     * 发送规则1草稿2审核0发送
     */
    private Integer isRules;
    /**
     * 发送人昵称
     */
    private String sender;
    /**
     * 发送人
     */
    private Long sendUserId;
    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * 状态0进行中1结束任务2任务暂停
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 指定时间
     */
    private String fromDuration;

}