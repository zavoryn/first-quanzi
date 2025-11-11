package cn.metast.tuoke.module.community.dal.dataobject.cmReport;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 举报记录 DO
 *
 * @author adminXq
 */
@TableName("cm_report")
@KeySequence("cm_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class cmReportDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 举报用户id
     */
    private Long reportUserId;
    /**
     * 举报原因
     */
    private String reason;
    /**
     * 圈子id
     */
    private Long topicId;

}
