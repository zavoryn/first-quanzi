package cn.metast.tuoke.module.live.controller.app.live;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.idempotent.core.annotation.Idempotent;
import cn.metast.tuoke.framework.security.core.LoginUser;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.SnsActRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.SnsActInfoCfgPageReqVO;
import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.SnsActInfoUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.SnsActPlayerUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserPageReqVO;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListDTO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListVo;
import cn.metast.tuoke.module.live.controller.admin.vo.ActPostInfo;
import cn.metast.tuoke.module.live.dal.dataobject.snsAct.SnsActDO;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import cn.metast.tuoke.module.live.service.snsAct.SnsActService;
import cn.metast.tuoke.module.live.service.snsActinfocfg.SnsActInfoCfgService;
import cn.metast.tuoke.module.live.service.snsActinfouser.SnsActInfoUserService;
import cn.metast.tuoke.module.live.service.snsActplayeruser.SnsActPlayerUserService;
import cn.metast.tuoke.module.live.service.snsActuser.SnsActUserService;
import cn.metast.tuoke.module.live.utils.DateUtils;
import cn.metast.tuoke.module.live.utils.JsonUtil;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.error;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

/**
 * 活动管理 Controller
 */
@Api("活动管理")
@RestController
@RequestMapping(value = "/api/act",method = {RequestMethod.GET,RequestMethod.POST})
public class MActController {
    @Autowired
    private SnsActService snsActService;
    @Autowired
    private SnsActUserService snsActUserService;
    @Resource
    private MemberUserApi memberUserApi;
    @Autowired
    private SnsActInfoCfgService snsActInfoCfgService;
    @Autowired
    private SnsActInfoUserService snsActInfoUserService;
    @Autowired
    private SnsActPlayerUserService snsActPlayerUserService;
    /**
     * reqType    rec 推荐     fri 朋友圈     focus 关注    fav 收藏     user 某用户的ID
     * userId 用户ID  reqType=user 携带
     */
    @ApiOperation("获取活动列表")
    @GetMapping("/getActLst_uni")
    @PermitAll
    public CommonResult<List<ActListVo>> list_uni(ActListDTO actListDTO) throws ParseException {
        List<ActListVo> vo = snsActService.selectSnsActList(actListDTO);
        return success(vo);
    }

    @ApiOperation("获取我参与的活动列表")
    @GetMapping("/getMyActLst_uni")
    public CommonResult<List<ActListVo>> myList_uni(ActListDTO actListDTO) throws ParseException {
        List<ActListVo> vo = snsActService.selectSnsActList(actListDTO);
        return success(vo);
    }
    /**
     * 查询活动详情列表
     */
    @GetMapping("/getTotal_uni")
    public Integer getTotal(ActListDTO actListDTO) throws ParseException {
        List<ActListVo> list = snsActService.selectSnsActList(actListDTO);
        return list.size()>0? list.size(): 0;
    }
    @Idempotent(timeout = 10, timeUnit = TimeUnit.SECONDS, message = "请勿重复提交")
    @ApiOperation("删除活动")
    @GetMapping("/getDeleteAct_uni")
    public CommonResult<Integer> getDeleteAct_uni(Long actId) throws ParseException {
        snsActService.deleteSnsActAppById(actId);
        return success(1);
    }
    @ApiOperation("活动详情")
    @PermitAll
    @GetMapping("/getAcInfo_uni")
    public CommonResult<ActListVo> getInfo_uni(Long id,String actType) throws ParseException {
        ActListVo actListVo=snsActService.selectSnsActById(id);
        //报名总人数限制
        actListVo.setUserCfg("N");//默认没满
        SnsActUserRespVO users=new SnsActUserRespVO();
        users.setActId(id);
        List<SnsActUserRespVO> uList = snsActUserService.selectSnsActUserAppList(users);
        if(actListVo.getMemberLimit()!=null) {
            if (uList.size()>actListVo.getMemberLimit()) {
                actListVo.setUserCfg("Y");
            }
        }
        return success(actListVo);
    }
    @Idempotent(timeout = 10, timeUnit = TimeUnit.SECONDS, message = "请勿重复提交")
    @ApiOperation("编辑活动")
    @PostMapping("/updateActInfo_uni")
    @ResponseBody
    public CommonResult<Boolean> updateActInfo2(@RequestBody ActPostInfo actPostInfo) throws ParseException {
        snsActService.insertSnsActPost(actPostInfo);
        return success(true);
    }
    @ApiOperation("活动报名设置列表")
    @GetMapping("/getActInfoCfgList")
    public CommonResult<List<SnsActInfoCfgDO>> getActInfoCfgList(Long actId) throws ParseException {
        SnsActInfoCfgPageReqVO cfg=new SnsActInfoCfgPageReqVO();
        cfg.setActId(actId);
        cfg.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActInfoCfgDO> templates=snsActInfoCfgService.getSnsActInfoCfgPage(cfg).getList();
        return success(templates);
    }
    /**
     * 新增活动报名人员
     */
    @Idempotent(timeout = 10, timeUnit = TimeUnit.SECONDS, message = "请勿重复提交")
    @PostMapping("/signAct_uni")
    public CommonResult<Integer> signAct(@RequestBody SnsActUserRespVO snsActUser)
    {

        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        Long uid=null;
        if(loginUser!=null){
            uid=loginUser.getId();
        }
        snsActUser.setUserId(uid);
        SnsActDO actListVo = snsActService.selectSnsActByActId(snsActUser.getActId());
        if(actListVo!=null){
            //查看是否需要审核
            if(actListVo.getCheckFlag()!=null&&"Y".equals(actListVo.getCheckFlag())){
                snsActUser.setStatus(2);
            }else{
                snsActUser.setStatus(0);
            }

            SnsActUserRespVO use=new SnsActUserRespVO();
            use.setActId(snsActUser.getActId());
            List<SnsActUserRespVO> users1 = snsActUserService.selectSnsActUserAppList(use);
            actListVo.setSignNum(users1.size());
            SnsActUserPageReqVO snsActUsers=new SnsActUserPageReqVO();
            snsActUsers.setUserId(uid);
            snsActUsers.setStatus(0);//待审核不算报名
            snsActUsers.setActId(snsActUser.getActId());

            List<SnsActUserRespVO> users=snsActUserService.selectSnsActUserList(snsActUsers).getList();
            Integer memberLimit = actListVo.getMemberLimit();
            if (memberLimit == 0 ||  memberLimit == users.size()) {
                return error(300, "超过报名人数");
            }
            if(actListVo.getJoinLimit()!=null){
                if(users.size()>=actListVo.getJoinLimit()){
                    return error(300, "超过报名次数");
                }
            }
            if(!CollectionUtils.isEmpty(users)){
                return error(500, "该人员已经报名");
            }
            //判断报名人数是否满员
            if(actListVo.getMemberLimit()!=0 && actListVo.getMemberLimit()<=actListVo.getSignNum()){
                return error(500, "名额已满");
            }
        }
        snsActUser.setCreateTime(new Date());
        int i=snsActUserService.insertSnsActUser(snsActUser);
        if(i>0){
            //插入报名填入的字段
            List<SnsActInfoUserRespVO> actInfoUser=snsActUser.getActInfoUsers();
            if(!CollectionUtils.isEmpty(actInfoUser)){
                for(SnsActInfoUserRespVO act:actInfoUser){
                    act.setActId(snsActUser.getActId());
                    act.setUserId(uid);
                    act.setActUserId(snsActUser.getId());
                }
                snsActInfoUserService.insertSnsActInfoUserList(actInfoUser);
            }
            //插入报名填入的字段
            List<SnsActPlayerUserRespVO> ll=new ArrayList<SnsActPlayerUserRespVO>();
            List<Map<String,Object>> list= JsonUtil.jsonArray2List(snsActUser.getActPlayerUserUsers());
            if(!CollectionUtils.isEmpty(list)){
                for(Map<String,Object> mm:list){
                    SnsActPlayerUserRespVO act=new SnsActPlayerUserRespVO();
                    act.setActId(snsActUser.getActId());
                    act.setUserId(uid);
                    act.setActUserId(snsActUser.getId());
                    act.setFieldValue(JsonUtil.write2JsonStr(mm.get("fieldValue")));
                    ll.add(act);
                }
                snsActPlayerUserService.insertSnsActPlayerUserList(ll);
            }
            //创建发消息
            /*MemberUserRespDTO ue=memberUserApi.getUser(loginUser.getId());
            executor.execute(() -> {
                String context="报名成功";
                wxRobotService.sendRobotMsg(0,null,ue.getWxId(),"text",context,new String[0],1);
            });*/
        }
        return success(1);
    }
    @GetMapping("/getActUserList")
    public CommonResult<List<SnsActUserRespVO>> getActUserList(Long actId){
        if(actId==null){
            return error(500,"活动id不能为空");
        }
        SnsActUserRespVO user=new SnsActUserRespVO();
        user.setActId(actId);
        List<SnsActUserRespVO> userList = snsActUserService.selectSnsActUserAppList(user);
        if(!CollectionUtils.isEmpty(userList)){
            for(SnsActUserRespVO fs: userList){
                fs.setAvatar(fs.getAvatar());
                fs.setValueDate(DateUtils.datetime(fs.getCreateTime()));
                SnsActPlayerUserRespVO actPlayerUser = new SnsActPlayerUserRespVO();
                actPlayerUser.setActId(actId);
                actPlayerUser.setUserId(fs.getUserId());
                List<SnsActPlayerUserRespVO> sct = snsActPlayerUserService.selectSnsActPlayerUserList(actPlayerUser);
                if(sct.size() > 0 && !CollectionUtils.isEmpty(userList)){
                    SnsActPlayerUserRespVO actPlayerUser1 = sct.get(0);
                    Map<String, Object> map = JsonUtil.json2Map(actPlayerUser1.getFieldValue());
                    fs.setSName(map.get("xingming").toString());//报名的时候的姓名
                }
            }
        }
        return success(userList);
    }
/*    @RepeatSubmit
    @ApiOperation("查询计划报名费")
    @GetMapping("/selectPlanFee")
    @Log(title = "查询计划报名费", businessType = BusinessType.OTHER)
    public AjaxResult selectPlanFee(Long actId){
        if(actId == null){ return AjaxResult.error("参数 actId 错误"); }
        Map<String, Object> map = new HashedMap();
        map.put("actId", actId);
        map.put("userId", SecurityUtils.getUserId());
        SnsActUser snsActUser = snsActUserService.selectPlanFee(map);
        if(snsActUser == null){ return AjaxResult.error("尚未报名"); }
        Map<String, Object> res = new HashedMap();
        res.put("planFee", snsActUser.getPlanFee());

        return AjaxResult.success(res);
    }*/

}
