package cn.metast.tuoke.module.ai.dal.dataobject.dify;

import cn.metast.tuoke.framework.ai.core.enums.AiPlatformEnum;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI API 秘钥 DO
 *
 * @author metast.cn
 */
@TableName("ai_presets")
@KeySequence("ai_presets_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiPresetsDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     *
     * 枚举 {@link AiPlatformEnum}
     */
    private String type;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
