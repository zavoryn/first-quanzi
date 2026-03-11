package cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 公众号发送记录 DO
 *
 * @author 苏丹家园
 */
@TableName("mp_task_record")
@KeySequence("mp_task_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpTaskRecordDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 任务Id
     */
    private String taskId;
    /**
     * 发送人id
     */
    private String sendUserId;
    /**
     * 发送人
     */
    private String sendUserName;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 附件，待定
     */
    private String url;
    /**
     * 已发送时间
     */
    private String sendTime;
    /**
     * 接收人id
     */
    private String receiveUserId;
    /**
     * 接收人
     */
    private String receiveUserName;
    /**
     * 状态(0-待发送 1-已发送 2-发送失败)
     */
    private String status;

}
