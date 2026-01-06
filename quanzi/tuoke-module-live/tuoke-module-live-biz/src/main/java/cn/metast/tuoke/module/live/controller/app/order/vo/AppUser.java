package cn.metast.tuoke.module.live.controller.app.order.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.crud.DBTreeEntity;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties
@DBTreeEntity(fieldPID = "pid", fieldLevel = "level", fieldTitle = "username")
// @DBRandomID()//加此注解，ID后面会产生几位随机数
@ApiModel(value = "com.kalacheng.libuser.model.AppUser", description = "用户基础信息表")
public class AppUser implements Serializable {

    public static final long serialVersionUID = 1L;

    public long userid;

    @ApiModelProperty(value = "用户是否开通svip 1:是 0:否")
    public int isSvip;

    @ApiModelProperty(value = "注册类型")
    public int regType;

    @ApiModelProperty(value = "微信企业ID", name = "openid")
    public String openid;

    @ApiModelProperty(value = "微信唯一ID", name = "unionid")
    public String unionid;

    @ApiModelProperty(value = "用户名", name = "username")
    public String username;

    @ApiModelProperty(value = "真实姓名", name = "nickname")
    public String nickname;

    @ApiModelProperty(value = "手机号", name = "mobile")
    public String mobile;

    @ApiModelProperty(value = "手机号区域 例如:86", name = "smsRegion")
    public String smsRegion;

    @ApiModelProperty(value = "分成方案")
    public long dealScalePlan;

    @ApiModelProperty(value = "微信号", name = "wechat")
    public String wechat;

    @ApiModelProperty(value = "密码", name = "password")
    public String password;

    @ApiModelProperty(value = "盐", name = "salt")
    public String salt;

    @ApiModelProperty(value = "用户状态 1:禁用 0:正常", name = "status")
    public int status;

    @ApiModelProperty(value = "操作用户名,如禁用操作/解禁操作等", name = "optUserName")
    public String optUserName;

    @ApiModelProperty(value = "禁用时间", name = "lockTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date lockTime;

    @ApiModelProperty(value = "禁用原因", name = "lockReason")
    public String lockReason;

    @ApiModelProperty(value = "用户当前设备信息ID", name = "deviceId")
    public String deviceId;

    @ApiModelProperty(value = "用户当前的位置信息ID", name = "ipaddr")
    public String ipaddr;

    @ApiModelProperty(value = "删除状态 0:未删除(默认) 1:已删除", name = "delFlag")
    public int delFlag;

    @ApiModelProperty(value = "注册时间", name = "createTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    @ApiModelProperty(value = "上次登录时间", name = "lastLoginTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date lastLoginTime;

    @ApiModelProperty(value = "离线时间", name = "lastOffLineTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date lastOffLineTime;

    @ApiModelProperty(value = "登录邮箱")
    public String userEmail;

    @ApiModelProperty(value = "用户个人网站")
    public String userUrl;

    @ApiModelProperty(value = "用户头像")
    public String avatar;

    @ApiModelProperty(value = "性别 0:未设置 1:男 2:女 ")
    public int sex;

    @ApiModelProperty(value = "是否开启勿扰 0:未开启 1:开启")
    public int isNotDisturb;

    @ApiModelProperty(value = "隐藏位置 0:未开启 1:开启")
    public int whetherEnablePositioningShow;

    @ApiModelProperty(value = "隐藏距离 0:不隐藏 1:隐藏")
    public int hideDistance;

    @ApiModelProperty(value = "生日")
    public String birthday;

    @ApiModelProperty(value = "个性签名")
    public String signature;

    @ApiModelProperty(value = "最后登录ip")
    public String lastLoginIp;

    @ApiModelProperty(value = "注册时ip")
    public String registerIp;

    @ApiModelProperty(value = "激活码")
    public String userActivationKey;

    @ApiModelProperty(value = "用户积分")
    public int score;

    @ApiModelProperty(value = "用户类型 1:-- 2:会员  3：游客")
    public int userType;

    @ApiModelProperty(value = "金币 /充值金额")
    public double coin;

    @ApiModelProperty(value = "映票余额/可提现金额")
    public double votes;

    @ApiModelProperty(value = "剩余佣金/可提现佣金")
    public double amount;

    @ApiModelProperty(value = "总收益佣金")
    public double totalAmount;

    @ApiModelProperty(value = "映票总额")
    public double votestotal;

    @ApiModelProperty(value = "累计充值金额")
    public double totalCharge;

    @ApiModelProperty(value = "累计提现佣金")
    public double totalAmountCash;

    @ApiModelProperty(value = "累计提现映票")
    public double totalCash;

    @ApiModelProperty(value = "消费总额(财富积分)")
    public double consumption;

    @ApiModelProperty(value = "用户积分")
    public int userPoint;

    @ApiModelProperty(value = "主播积分")
    public int anchorPoint;

    @ApiModelProperty(value = "魅力积分")
    public int charmPoint;

    @ApiModelProperty(value = "用户等级")
    public int userGrade;

    @ApiModelProperty(value = "主播等级")
    public int anchorGrade;

    @ApiModelProperty(value = "财富等级")
    public int wealthGrade;

    @ApiModelProperty(value = "贵族等级")
    public int nobleGrade;

    @ApiModelProperty(value = "魅力等级")
    public int charmGrade;

    @ApiModelProperty(value = "省份")
    public String province;

    @ApiModelProperty(value = "城市")
    public String city;

    @ApiModelProperty(value = "地址", name = "address")
    public String address;

    @ApiModelProperty(value = "经度", name = "lng")
    public double lng;

    @ApiModelProperty(value = "纬度", name = "lat")
    public double lat;

    @ApiModelProperty(value = "身高（CM）", name = "height")
    public int height;

    @ApiModelProperty(value = "体重（KG）", name = "height")
    public double weight;

    @ApiModelProperty(value = "职业")
    public String vocation;

    @ApiModelProperty(value = "三围")
    public String sanwei;

    @ApiModelProperty(value = "星座")
    public String constellation;

    @ApiModelProperty(value = "注册方式 1:手机注册 2:微信注册 3:QQ注册")
    public String loginType;

    @ApiModelProperty(value = "是否开启僵尸粉 1:未开启(默认) 0:已开启")
    public int iszombie;

    @ApiModelProperty(value = "开播随机僵尸粉数量")
    public int openLiveZombieNum;

    @ApiModelProperty(value = "僵尸粉比例")
    public double zombieRatio;

    @ApiModelProperty(value = "最大僵尸粉数量")
    public int zombieMaxNum;

    @ApiModelProperty(value = "是否开起回放 1:未开启 0:已开启")
    public int isrecord;

    @ApiModelProperty(value = "是否真人 0:不是 1:是")
    public int iszombiep;

    @ApiModelProperty(value = "是否超管 1:否 0:是")
    public int issuper;

    @ApiModelProperty(value = "是否热门显示 1:否 0:是")
    public int ishot;

    @ApiModelProperty(value = "当前装备靓号")
    public String goodnum;

    @ApiModelProperty(value = "注册来源")
    public String source;

    @ApiModelProperty(value = "直属上级")
    public long pid;

    @ApiModelProperty(value = "主播粉丝团群聊id")
    public long groupId;

    @ApiModelProperty(value = "是否加入极光 0:未加入 1:已加入")
    public int isJoinJg;

    @ApiModelProperty(value = "层级")
    public int level;

    @ApiModelProperty(value = "封面")
    public String liveThumb;

    @ApiModelProperty(value = "0:未编辑过 1:编辑过了")
    public int cityEdit;

    @ApiModelProperty(value = "角色 0:普通用户 1:主播")
    public int role;

    @ApiModelProperty(value = "手机厂商")
    public String phoneFirm;

    @ApiModelProperty(value = "手机型号")
    public String phoneModel;

    @ApiModelProperty(value = "手机系统")
    public String phoneSystem;

    @ApiModelProperty(value = "当前版本号")
    public String appVersion;

    @ApiModelProperty(value = "当前版本code")
    public String appVersionCode;

    @ApiModelProperty(value = "手机唯一标识")
    public String phoneUuid;

    @ApiModelProperty(value = "直属上级代理（后台管理员）")
    public long agentId;

    @ApiModelProperty(value = "所属公会ID")
    public long guildId;

    @ApiModelProperty(value = "上级经纪人公会ID，对应AdminUser表。Co=company公司")
    public long managerCoId;

    @ApiModelProperty(value = "上级经纪人ID，对应AdminUser表")
    public long managerId;

    @ApiModelProperty(value = "是否自动填充主播分成比例  0:自动  1:人工调整")
    public int isAutomatic;

    @ApiModelProperty(value = "主播是否认证 0:未认证 1:已认证  后台添加主播时,如果是认证状态, 需要添加认证记录")
    public int isAnchorAuth;

    @ApiModelProperty(value = "邀请码")
    public String inviteCode;

    @ApiModelProperty(value = "是否推荐 0:不推荐 1:推荐中", name = "isRecommend")
    public int isRecommend;

    @ApiModelProperty(value = "连续登录天数", name = "awardLoginDay")
    public int awardLoginDay;

    @ApiModelProperty(value = "上次登录时间(连续登录用)", name = "lastLoginDay")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date lastLoginDay;

    @ApiModelProperty(value = "短视频剩余可观看私密视频次数", name = "readShortVideoNumber")
    public int readShortVideoNumber;

    @ApiModelProperty(value = "连续签到次数", name = "signCount")
    public int signCount;

    @ApiModelProperty(value = "上次签到时间", name = "signTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date signTime;

    @ApiModelProperty(value = "最大连续签到天数", name = "signCount")
    public int maxSignCount;

    @ApiModelProperty(value = "推送平台 1:小米 2:华为 3:vivo 4:oppo 5:苹果 6:极光 7:apns 8:miApns", name = "signCount")
    public int pushPlatform;

    @ApiModelProperty(value = "推送平台对应的id")
    public String pushRegisterId;

    @ApiModelProperty(value = "苹果voip")
    public String voipToken;

    /************** 演示账号配置字段 *********************************************/
    @ApiModelProperty(value = "是否是一对一演示账号 1:是 0:否", name = "isOOOAccount")
    public int isOOOAccount;

    @ApiModelProperty(value = "是否是多人直播演示账号 1:多人视频直播演示账号 2:多人语音直播演示账号 0:否", name = "isLiveAccount")
    public int isLiveAccount;

    @ApiModelProperty(value = "房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间", name = "roomType")
    public int roomType;

    @ApiModelProperty(value = "房间类型值")
    public String roomTypeVal;

    @ApiModelProperty(value = "房间标题")
    public String roomTitle;

    @ApiModelProperty(value = "直播频道", name = "channelId")
    public int channelId;

    @ApiModelProperty(value = "是否显示在首页 1:展示在首页 0:不展示在首页", name = "isShowHomePage")
    public int isShowHomePage;

    @ApiModelProperty(value = "ooo导航分类关联app_live_channel表")
    public long headNo;

    @ApiModelProperty(value = "ooo二级分类id")
    public long oooTwoClassifyId;

    @ApiModelProperty(value = "首页一对一排序编号(附近使用)", name = "oooHomePageSortNo")
    public int oooHomePageSortNo;

    @ApiModelProperty(value = "视频通话时间金币 /min")
    public double videoCoin;

    @ApiModelProperty(value = "语音通话时间金币 /min")
    public double voiceCoin;

    @ApiModelProperty(value = "海报", name = "poster")
    public String poster;

    @ApiModelProperty(value = "展示视频", name = "video")
    public String video;

    @ApiModelProperty(value = "展示视频封面", name = "videoImg")
    public String videoImg;

    @ApiModelProperty(value = "展示声音")
    public String voice;

    @ApiModelProperty(value = "直播封面图", name = "thumb")
    public String liveThumbs;

    @ApiModelProperty(value = "修改密码时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date updatePwdTime;

    //////////////////////////////////

    /*** 状态相关信息，放在此 下面 (同步到 UserBasicInfo、ApiUserInfo、AppUser) *******************************************************/

    @ApiModelProperty(value = "用户设置的在线状态 0:在线 1:忙碌 2:离开 3:通话中")
    public int userSetOnlineStatus;

    @ApiModelProperty(value = "用户在线状态 0:离线 1:在线")
    public int onlineStatus;

    @ApiModelProperty(value = "视频直播状态:0:未进行直播 1:直播中的主播 2:直播中的观众")
    public int liveStatus;

    @ApiModelProperty(value = "一对一直播状态:0:未进行直播 1:通话中 2:邀请他人通话 3:正在被邀请")
    public int oooLiveStatus;

    // -------------------------------多人语音直播间相关-------------------------------------------------------
    @ApiModelProperty(value = "多人语音直播状态 0:不在语音房间中 2:上麦标识 3:被邀上麦中 4:被踢下麦 5:下麦标识 6:申请上麦中 8:被踢出房间")
    public int voiceStatus;

    @ApiModelProperty(value = "被踢到期时间戳")
    public long kickTime;

    @ApiModelProperty(value = "上次抽奖进度")
    public int lastGameNum;

    @ApiModelProperty(value = "房间号(跟随用)")
    public long gsRoomId;

    @ApiModelProperty(value = "房间号")
    public long roomId;

    @ApiModelProperty(value = "跟随房间类型 1:视频 2:语音")
    public int gsRoomType;

    /*** 状态相关信息，放在此 上面 ***********************************************/
    @ApiModelProperty(value = "是否有直播购 0:没有直播购 1:有直播购", name = "liveFunction")
    public int liveFunction;

    @ApiModelProperty(value = "是否开启青少年模式 1:开启 2:未开启", name = "isYouthModel")
    public int isYouthModel;

    @ApiModelProperty(value = "青少年密码", name = "youthPassword")
    public String youthPassword;

    @ApiModelProperty(value = "是否开启消息推送 0:开启 1:关闭", name = "isPush")
    public int isPush;

    @ApiModelProperty(value = "是否关闭提示音 0:开启 1:关闭", name = "isTone")
    public int isTone;

    @ApiModelProperty(value = "充值隐身 0:不隐身 1:隐身")
    public int chargeShow;

    @ApiModelProperty(value = "贡献榜排行隐身 0:不隐身 1:隐身")
    public int devoteShow;

    @ApiModelProperty(value = "加入房间隐身 0:不隐身 1:隐身")
    public int joinRoomShow;

    @ApiModelProperty(value = "全站广播功能 0:关闭功能 1:开启功能")
    public int broadCast;

    @ApiModelProperty(value = "礼物全局广播 0:关闭 1:开启")
    public int giftGlobalBroadcast;

    @ApiModelProperty(value = "主播星级id", name = "starId")
    public long starId;

    // --------------------------------- 下面的字段是一些零时字段 ----------------------------------------
    @ApiModelProperty(value = "用户资料图片,英文逗号隔开")
    public String portrait;
}
