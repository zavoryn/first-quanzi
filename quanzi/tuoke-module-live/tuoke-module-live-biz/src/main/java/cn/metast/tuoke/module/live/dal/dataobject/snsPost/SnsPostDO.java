package cn.metast.tuoke.module.live.dal.dataobject.snsPost;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 动态信息 DO
 *
 * @author 夏兆金
 */
@TableName("sns_post")
@KeySequence("sns_post_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsPostDO extends BaseDO {

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
     * 创建用户ID
     */
    private Long userId;
    /**
     * 类型(dyna 动态 act  活动 bus 商机 ent 企业家 spe 智库 srv  服务 news新闻,community社区,magazine 内刊)
     */
    private String postType;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面url
     */
    private String coverUrl;
    /**
     * 标签，多个用分号分割
     */
    private String tag;
    /**
     * 内容类型 纯文字:text  图文 weibo  纯图 pic  视频video 链接：外部link
     */
    private String contentType;
    /**
     * 内容
     */
    private String content;
    /**
     * 网页链接
     */
    private String linkUrl;
    /**
     * 分享链接
     */
    private String shareUrl;
    /**
     * 音视频的路径/x相对路径
     */
    private String mediaUrl;
    /**
     * 文件的路径/x相对路径
     */
    private String fileUrl;
    /**
     * 图片个数
     */
    private Integer picNum;
    /**
     * 分享个数
     */
    private Integer shareNum;
    /**
     * 点赞个数
     */
    private Integer likeNum;
    /**
     * 评论个数
     */
    private Integer commentNum;
    /**
     * 访问次数
     */
    private Integer visitNum;
    /**
     * 隐私设置(0 所有人 2 关注我的人  4仅自己 )
     */
    private Integer privacy;
    /**
     * 来源(INNER 内部 WXMP 微信公众号 WEIBO 微博 OTHER其他)
     */
    private String soureFrom;
    /**
     * 是否允许评论(Y 是 N否)
     */
    private String commentFlag;
    /**
     * 位置
     */
    private String postMap;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 增加方式 0 前端 1 后端手动 2 后端自动
     */
    private String addType;
    /**
     * 帐号状态（0正常 1删除)
     */
    private String status;
    /**
     * 联系人id
     */
    private String imUserId;
    /**
     * dyna_config
     */
    private Long dynaConfig;
    /**
     * 商机轮播图置顶
     */
    private String boType;
    /**
     * 联盟服务底部控制，是否显示0显示，1不显示
     */
    private String bottomType;
    /**
     * 模块类型，智库1和校友2，，服务1和创投2
     */
    private String spType;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 职位
     */
    private String position;
    /**
     * 标签
     */
    private String label;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否推荐：0 否 ，1：是
     */
    private Integer isRecom;
    /**
     * 简介
     */
    private String coment;
    /**
     * 访问次数
     */
    private Integer accessCount;
    /**
     * 智库等级(字典sns_spe_zkdj)
     */
    private String spelevel;
    /**
     * 导师类型1：学业深造导师、2：企业发展导师	、3：创新创业导师	、4生活美满导师
     */
    private String dslx;

}