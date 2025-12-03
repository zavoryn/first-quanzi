package cn.metast.tuoke.module.heal.dal.dataobject.archivesuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 档案信息关联用户 DO
 *
 * @author 超级管理员
 */
@TableName("wn_archives_user")
@KeySequence("wn_archives_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivesUserDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 档案ID
     */
    private Long archivesId;

}
