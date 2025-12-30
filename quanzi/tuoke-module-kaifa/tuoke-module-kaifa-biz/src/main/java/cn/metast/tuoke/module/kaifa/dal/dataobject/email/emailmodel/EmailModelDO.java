package cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmodel;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 模板-快速文本 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_email_model")
@KeySequence("jw_email_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailModelDO extends BaseDO {

    /**
     * 系统主键
     */
    @TableId
    private Long id;
    /**
     * 类型（model个人模板、text快速文本)
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 文本内容(摘要)
     */
    private String conteText;
    /**
     * 状态
     */
    private String status;

}
