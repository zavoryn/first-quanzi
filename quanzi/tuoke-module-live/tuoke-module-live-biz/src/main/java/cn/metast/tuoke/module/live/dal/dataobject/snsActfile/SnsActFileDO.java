package cn.metast.tuoke.module.live.dal.dataobject.snsActfile;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动资料 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_file")
@KeySequence("sns_act_file_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActFileDO extends BaseDO {

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
     * 文件标题
     */
    private String title;
    /**
     * 时间
     */
    private LocalDateTime time;
    /**
     * 文件存储id
     */
    private Long fileId;

}