package cn.metast.tuoke.module.heal.dal.dataobject.detection;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 检测记录 DO
 *
 * @author 超级管理员
 */
@TableName("wn_detection")
@KeySequence("wn_detection_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetectionDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 设备编号
     */
    private String deviceSn;
    /**
     * 用户ID
     */
    private Long uid;
    @TableField(exist = false)
    private String uname;
    /**
     * 档案ID
     */
    private Long aid;
    @TableField(exist = false)
    private String aname;
    /**
     * 检测项目
     */
    private String name;
    /**
     * 检测结果
     */
    private String report;
    /**
     * 结果单位
     */
    private String unit;
    /**
     * 参考范围
     */
    private String ckrange;
    /**
     * 结果状态
     */
    private String status;
    /**
     * 是否完成
     */
    private String iswc;
    /**
     * 样本类型
     */
    private String type;

}
