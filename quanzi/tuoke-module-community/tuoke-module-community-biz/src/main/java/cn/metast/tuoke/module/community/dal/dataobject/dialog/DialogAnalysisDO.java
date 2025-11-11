package cn.metast.tuoke.module.community.dal.dataobject.dialog;

import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 对话分析 DO
 *
 * @author adminXq
 */
@TableName("cm_dialog_analysis")
@KeySequence("cm_dialog_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogAnalysisDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 关联圈子ID
     */
    private Long topicId;
    /**
     * 对话方1姓名
     */
    private String userName1;
    /**
     * 对话方2姓名
     */
    private String userName2;
    /**
     * 对话内容(纯文本)
     */
    private String dialogContent;
    /**
     * 原始HTML内容
     */
    private String originalHtml;
    /**
     * 音频文件URL列表(JSON)
     */
    private String audioUrls;
    /**
     * 分析结果(Markdown)
     */
    private String analysisResult;
    /**
     * 状态(0待处理 1进行中 2已完成 3失败)
     */
    private Integer status;
    /**
     * 错误信息
     */
    private String errorMsg;

}
