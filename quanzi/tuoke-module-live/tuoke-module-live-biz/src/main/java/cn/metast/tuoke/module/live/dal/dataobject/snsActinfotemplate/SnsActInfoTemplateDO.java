package cn.metast.tuoke.module.live.dal.dataobject.snsActinfotemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动模板 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_info_template")
@KeySequence("sns_act_info_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActInfoTemplateDO extends BaseDO {

    /**
     * 编号
     */
    private Long columnId;
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选
     */
    private Integer fieldType;
    /**
     * 输入 0 必填  1 选填
     */
    private String fieldInput;
    /**
     * 选择的时候，选项 ，多个空格分隔
     */
    private String fieldValue;

}