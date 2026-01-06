package cn.metast.tuoke.module.live.dal.dataobject.snsAblum;

import cn.metast.tuoke.module.live.controller.admin.vo.PicVo;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 相册信息 DO
 *
 * @author 夏兆金
 */
@TableName("sns_ablum")
@KeySequence("sns_ablum_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsAblumDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * post_id
     */
    private Long postId;
    /**
     * url
     */
    private String url;
    /**
     * identifier
     */
    private String identifier;
    /**
     * meeting会务图集
     */
    private String type;
    /**
     * user_id
     */
    private Long userId;
    /**
     * user_name
     */
    private String userName;


    @TableField(exist = false)
    private Long fileSize;
    @TableField(exist = false)
    private String nickName;
    @TableField(exist = false)
    private List<PicVo> picInfo;
}
