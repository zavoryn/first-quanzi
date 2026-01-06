package cn.metast.tuoke.module.live.dal.dataobject.snsAct;

import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.vo.CommentVo;
import cn.metast.tuoke.module.live.controller.admin.vo.PicVo;
import cn.metast.tuoke.module.live.controller.admin.vo.VideoVo;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动详情 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act")
@KeySequence("sns_act_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * act_id
     */
    private Long actId;
    /**
     * post_id
     */
    private Long postId;
    /**
     * 活动类型，字典：活动act/课程:course/会务:meeting
     */
    private String actType;
    /**
     * 活动形式（线下 0 线上1 模板2）
     */
    private String actForm;
    /**
     * 费用
     */
    private BigDecimal fee;
    /**
     * 经度
     */
    private BigDecimal longi;
    /**
     * 纬度
     */
    private BigDecimal lati;
    /**
     * 地址
     */
    private String location;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * start_time
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * end_time
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date endTime;
    /**
     * 报名设置
     */
    private String regSetting;
    /**
     * 联系电话
     */
    private String contactphone;
    /**
     * 报名人数
     */
    private Integer signNum;
    /**
     * create_user_id
     */
    private String createUserId;
    /**
     * create_user_name
     */
    private String createUserName;
    /**
     * 0正常活动1模板活动
     */
    private Integer mStatus;
    /**
     * 签到二维码
     */
    private String qrcode;
    /**
     * 报名是否需要审核(N 否 Y是）
     */
    private String checkFlag;
    /**
     * 报名开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appyStartDate;
    /**
     * 报名结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appyEndDate;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 总人数限制
     */
    private Integer memberLimit;
    /**
     * 每人提交数限制
     */
    private Integer joinLimit;
    /**
     * 收不收款 0 不收  1收
     */
    private Integer feeLimit;
    /**
     * 已提交列表显示 0 显示 1不显示
     */
    private Integer joinMemberView;
    /**
     * 按钮显示的文本：立即报名、立即预约、立即申请、立即购买、立即填写
     */
    private String btnView;
    /**
     * 是否可以分享 Y 是 N否
     */
    private String shareCfg;
    /**
     * 是否弹出添加机器人Y 是 N否
     */
    private String audtCfg;
    /**
     * 费用支出，1固定费用，2参与人*人数
     */
    private String actPut;
    /**
     * 是否推荐：0 否 ，1：是
     */
    private Integer isRecom;
    /**
     * 群聊二维码
     */
    private String crowdQr;
    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private String coverUrl;
    @TableField(exist = false)
    private String tag;
    @TableField(exist = false)
    private String isJoin;
    @TableField(exist = false)
    private String reqType;
    @TableField(exist = false)
    private Long loginId;
    @TableField(exist = false)
    private Long fileSize;
    @TableField(exist = false)
    private Date feeTime;//实际缴费时间
    @TableField(exist = false)
    private Date registerTime;
    @TableField(exist = false)
    private Long msFlag;
    @TableField(exist = false)
    private String actInfoCfg;//PC端详情显示
    @TableField(exist = false)
    private int fNum;
    @TableField(exist = false)
    private int bNum;
    @TableField(exist = false)
    private String spType; // 模块类型 智库1和校友2, 服务1和创投2,  5:兰杉学院banner  6:叁号路基金banner  7:兰杉企业研究会banner  8:紫金助企banner
    @TableField(exist = false)
    private Long postFlag;
    @TableField(exist = false)
    private Map<String, Object> params;
    @TableField(exist = false)
    private Integer fstatus;
    @TableField(exist = false)
    private Integer canyuNum;//参与人
    @TableField(exist = false)
    private List<CommentVo> CommentInfo;
    @TableField(exist = false)
    private List<SnsActUserRespVO> actUserList;
    @TableField(exist = false)
    private int status;//0 报名成功  2待审核 3 审核不通过 -1 退报名
    @TableField(exist = false)
    private int shNum;//待审核数
    @TableField(exist = false)
    private int qdNum;//签到数
    @TableField(exist = false)
    private String type;//推荐用来定义是哪个类型，目前只有活动（act，活动）
    @TableField(exist = false)
    private Integer registerType;//是否签到，0未签到，1已签到
    @TableField(exist = false)
    private List<SnsActInfoCfgDO> actInfoCfgs;
    @TableField(exist = false)
    private String userCfg;//判断活动报名是否已满
    @TableField(exist = false)
    private Integer number;//pc端id计数
    @TableField(exist = false)
    private String sTime;//PC端只要时分
    @TableField(exist = false)
    private String eTime;
    //来源
    @TableField(exist = false)
    private String sourceForm;
    //标题
    @TableField(exist = false)
    private String postType;
    //类型：图文 weibo， 图 pic， 视频video 链接：link
    @TableField(exist = false)
    private String contentType;
    //链接地址
    @TableField(exist = false)
    private String linkUrl;
    //地理位置
    @TableField(exist = false)
    private String postMap;
    /** 经度 */
    @TableField(exist = false)
    private String longitude;
    /** 纬度 */
    @TableField(exist = false)
    private String latitude;
    //图片个数
    @TableField(exist = false)
    private Integer picNum;
    //图片信息
    @TableField(exist = false)
    private List<PicVo> picInfo;
    //视频信息
    @TableField(exist = false)
    private List<VideoVo> videoInfo;
    @TableField(exist = false)
    private String mediaUrl;
    //是否收藏 Y是
    @TableField(exist = false)
    private String isFavorite;
    //是否点赞 Y是
    @TableField(exist = false)
    private String isLike;
    //是否关注 Y是
    @TableField(exist = false)
    private Boolean isFollow;
    //评论个数
    @TableField(exist = false)
    private Integer commentNum;
    //点赞个数
    @TableField(exist = false)
    private Integer likeNum;
    //转发个数
    @TableField(exist = false)
    private Integer shareNum;
    //浏览个数
    @TableField(exist = false)
    private Integer visitNum;
    //分享url
    @TableField(exist = false)
    private String shareUrl;
    //是否允许评论 Y是 其他否
    @TableField(exist = false)
    private String commentFlag;
    //访问权限0 所有人 1 社群 2 关注我的人 3 朋友圈  4只有我
    @TableField(exist = false)
    private Integer privacy;
    @TableField(exist = false)
    private Long userId;
    //联系人聊天ID
    @TableField(exist = false)
    private String imuserId;
    //名称
    @TableField(exist = false)
    private String nickName;
    //联系人头像地址
    @TableField(exist = false)
    private String avatar;
    //电话
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private Long newsId;
    /**
     * 职位
     */
    @TableField(exist = false)
    private String position;
    /**
     * 标签
     */
    @TableField(exist = false)
    private String label;
    /**
     * 地址
     */
    @TableField(exist = false)
    private String address;
    /**
     * 简介
     */
    @TableField(exist = false)
    private String coment;
    /**
     * 访问次数
     */
    @TableField(exist = false)
    private Integer accessCount;
    @TableField(exist = false)
    private String dslx;
    /** 职称 */
    @TableField(exist = false)
    private String zc;
    /** 领域 */
    @TableField(exist = false)
    private String area;
}
