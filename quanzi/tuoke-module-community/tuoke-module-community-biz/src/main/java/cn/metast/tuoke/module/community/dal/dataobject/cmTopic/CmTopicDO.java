package cn.metast.tuoke.module.community.dal.dataobject.cmTopic;

import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 圈子详情 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_topic")
@KeySequence("cm_topic_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmTopicDO extends BaseDO {

    /**
     * 圈子ID
     */
    @TableId
    private Long id;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 微信号/QQ号
     */
    private String chatName;
    /**
     * 其他账号名称
     */
    private String otherName;
    /**
     * 星球类型(0收费星球 1不收费)
     */
    private Integer isType;
    /**
     * 身份证图片
     */
    private String idCardUrl;
    /**
     * 投资顾问从业资格证书号
     */
    private String certificateNo;
    /**
     * 月度价格
     */
    private Integer monthlyPrice;
    /**
     * 两月价格
     */
    private Integer bimonthlyPrice;
    /**
     * 季度价格
     */
    private Integer quarterlyPrice;
    /**
     * 四月价格
     */
    private Integer aprilPrice;
    /**
     * 半年价格
     */
    private Integer halfYearlyPrice;
    /**
     * 圈子名称
     */
    private String topicName;
    /**
     * 封面图片
     */
    private String coverImage;
    /**
     * 圈子描述
     */
    private String description;
    /**
     * 创建者ID
     */
    private Long userId;
    /**
     * 成员数量
     */
    private Integer memberCount;
    /**
     * 帖子数量
     */
    private Integer postCount;
    /**
     * 浏览量
     */
    private Integer viewCount;
    /**
     * 是否推荐(0否 1是)
     */
    private Integer isRecommend;
    /**
     * 是否热门(0否 1是)
     */
    private Integer isHot;
    /**
     * 可见范围,0周1一个月2两个月3三个月4半年
     */
    private Integer isVisibile;
    /**
     * 评论审核(0自由评论 1审核)
     */
    private Integer isComment;
    /**
     * 禁止评论(0否 1是)
     */
    private Integer isNo;
    /**
     * 续费加入(0自由加入 1审核)
     */
    private Integer isRenew;
    /**
     * 加入方式(0自由加入 1需要审核)
     */
    private Integer joinMode;
    /**
     * 展示会员数(0否 1是)
     */
    private Integer isMember;
    /**
     * 允许提问(0否 1是)
     */
    private Integer isQuestion;
    /**
     * 禁止复制星球内容(0否 1是)
     */
    private Integer isCopy;
    /**
     * 状态(0正常 1审核中 2禁用)
     */
    private Integer status;

    @TableField(exist = false)
    List<Map<String,Object>> prices;
    //支付状态(1未支付 0已支付)
    @TableField(exist = false)
    Integer payStatus;
    @TableField(exist = false)
    CmTopicMemberDO cmTopicMember;
    //到期时间
    @TableField(exist = false)
    LocalDateTime endTime;


    //更新到聊天室(1未同步 0已同步)
    private Long isSyncChat;
    //更新到微信服务号(1未同步 0已同步)
    private Long isSyncWx;
    //发送次数
    private Long sendNum;
    //发送时间
    private LocalDateTime sendTime;
    //二维码
    private String qrcodeUrl;
}
