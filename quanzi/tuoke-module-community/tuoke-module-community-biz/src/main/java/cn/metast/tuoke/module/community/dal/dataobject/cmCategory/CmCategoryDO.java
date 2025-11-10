package cn.metast.tuoke.module.community.dal.dataobject.cmCategory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 圈子分类 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_category")
@KeySequence("cm_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmCategoryDO extends BaseDO {

    /**
     * 分类ID
     */
    @TableId
    private Long id;
    /**
     * 分类名称
     */
    private String cateName;
    /**
     * 是否推荐
     */
    private Integer isTop;
    /**
     * 图片地址
     */
    private String coverImage;
    /**
     * 排序
     */
    private String sort;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Integer status;

}