package cn.metast.tuoke.module.live.controller.admin.vo;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.SnsActInfoCfgRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserRespVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
//  "活动人Vo")
public class ActListVo extends PostListVo{


    private String startDate;
    private String endDate;

    private Long actId;
    private String startTime;
    private String endTime;

    private BigDecimal fee;
    private Integer signNum;
    private Integer canyuNum;//参与人
    private String actPut;//金额费用-
    private String actForm;
    private String actType;
    private String location;
    private String longi;
    private String lati;
    private String regSetting;
    private String contactphone;
    private String createUserId;
    private String createUserName;
    private List<CommentVo> CommentInfo;
    private String isJoin;

    private String title;
    private String content;
    private String coverUrl;
    private String tag;

    private List<SnsActUserRespVO> actUserList;

    private Integer mStatus;

    private Integer fstatus;//缴费状态0未交费1已缴费
    //报名时间
    private String appyStartDate;
    private String appyEndDate;

    private String qrcode;//二维码
    private String checkFlag;//是否需要审核N 否 Y是

    private int status;//0 报名成功  2待审核 3 审核不通过 -1 退报名

    private int shNum;//待审核数
    private int qdNum;//签到数

    private String type;//推荐用来定义是哪个类型，目前只有活动（act，活动）
    private Long params;//用来定义数据的Id

    private Long sort;

    private Integer registerType;//是否签到，0未签到，1已签到

    private List<SnsActInfoCfgDO> actInfoCfgs;

    private Integer memberLimit;//总人数限制
    private Integer joinLimit;//没人提交数限制
    private Integer feeLimit;//收不收款，0不收1收
    private Integer joinMemberView;//已提交列表显示，0显示1不显示
    private String btnView;//按钮显示的文本，立即报名，立即预约，立即申请，立即购买，立即填写
    private String shareCfg;//是否可以分享Y是N否
    private String audtCfg;//是否弹出机器人选项0是1否
    private String userCfg;//判断活动报名是否已满

    private int bNum;//已报名次数

    private Integer number;//pc端id计数
    private String sTime;//PC端只要时分
    private String eTime;
    private String crowdQr;
}
