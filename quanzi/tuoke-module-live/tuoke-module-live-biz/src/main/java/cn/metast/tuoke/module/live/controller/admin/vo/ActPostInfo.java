package cn.metast.tuoke.module.live.controller.admin.vo;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.SnsActInfoCfgRespVO;
import lombok.Data;

import java.util.List;

@Data
public class ActPostInfo extends PostAddDTO {

    private Long	actId; 		  //活动id	携带 则表示修改，不携带则表示新增	long	是
    private String	content;      //内容，富文本	string	否	<a  href=\"https:\/\/m.weibo.cn\/search?containeu3010
    private Integer	picNum;       //图片个数	int	是	10
    private String	picIds;       //图片id字符串，多个用分号分隔；	string	是	1111;222;2333
    private String	mediaId;      //多媒体ID	string	是	1111111111
    private String	fileId;       //文件ID	string	是	1111111111
    private String	commentFlag;  //是否允许评论 Y是 其他否	string	是	Y
    private String	title;        //活动标题	string	否	投资人大会
    private String	address;      //地点	string	是	新街口
    private String	startDate;    //开始日期	string	是	2021-7-8/周三
    private String	startTime;    //开始时间	string	是
    private String	endDate;      //结束日期	string	是
    private String	endTime;      //结束时间	string	是
    private Double	fee;          //费用	double	是	0
    private String	coverUrl;     //封面id	string	是
    private String	actType;      //活动类型，字典：活动/课程/会务
    private String	location;     //活动地址	string	是	需要定位
    private String	longi;        //经度	string	是
    private String	lati;         //纬度	string	是
    private String	actForm;      //活动形式：0 线下 1 线上	string 是
    private String	regSetting;   //报名设置，目前只支持：姓名/电话/年龄/性别/单位/微信账号/邮箱name/phone/age/gender/unit/wx/email	string	是
    private String	contactphone; //联系电话	string	是
    private Integer mtatus;//0正常活动1是模板
    private String	appyStartDate;    //报名开始日期	string
    private String	appyEndDate;    //报名结束日期	string

    private String qrcode;
    private String checkFlag;

    private String [] regSettings;//后台,报名设置

    private Long sort;
    private Integer mStatus;

    private Integer memberLimit;//总人数限制
    private Integer joinLimit;//没人提交数限制
    private Integer feeLimit;//收不收款，0不收1收
    private Integer joinMemberView;//已提交列表显示，0显示1不显示
    private String btnView;//按钮显示的文本，立即报名，立即预约，立即申请，立即购买，立即填写
    private String shareCfg;//是否可以分享Y是N否
    private String audtCfg;//是否弹出机器人选项0是1否
    private String actPut;//费用支出

    private List<SnsActInfoCfgRespVO> snsActInfoCfgs;

    private String actInfoCfgList;//pc端传值

    private Integer isRecom;

    // 群聊二维码
    private String crowdQr;
}
