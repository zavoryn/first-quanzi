package cn.metast.tuoke.module.live.dal.dataobject.snsActnotice;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动公告 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_notice")
@KeySequence("sns_act_notice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActNoticeDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * act_id
     */
    private Long actId;
    /**
     * 公共标题
     */
    private String title;
    /**
     * 时间
     */
    private LocalDateTime time;
    /**
     * 公告内容
     */
    private String context;
    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;
    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    /**
     * re
     */
    private String re;
    /**
     * 发送用户的id
     */
    private String fromId;
    /**
     * 接收用户的id
     */
    private String toId;
    /**
     * single 单个 some  特定人  all 全部
     */
    private String scope;

}