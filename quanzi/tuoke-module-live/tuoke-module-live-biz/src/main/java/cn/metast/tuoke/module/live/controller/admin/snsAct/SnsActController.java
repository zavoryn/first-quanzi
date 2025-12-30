package cn.metast.tuoke.module.live.controller.admin.snsAct;
import cn.metast.tuoke.framework.security.core.LoginUser;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.SnsActPlayerUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListDTO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListVo;
import cn.metast.tuoke.module.live.controller.admin.vo.ActPostInfo;
import cn.metast.tuoke.module.live.dal.mysql.snsActuser.SnsActUserMapper;
import cn.metast.tuoke.module.live.service.snsActplayeruser.SnsActPlayerUserService;
import cn.metast.tuoke.module.live.service.snsActuser.SnsActUserService;
import cn.metast.tuoke.module.live.service.snsPost.SnsPostService;
import cn.metast.tuoke.module.live.utils.BeanMapUtils;
import cn.metast.tuoke.module.live.utils.DateUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.security.PermitAll;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAct.SnsActDO;
import cn.metast.tuoke.module.live.service.snsAct.SnsActService;
@Tag(name = "管理后台 - 活动详情")
@RestController
@RequestMapping("/live/sns-act")
@Validated
public class SnsActController {

    @Resource
    private SnsActService snsActService;
    @Autowired
    private SnsActUserService snsActUserService;
    @Autowired
    private SnsActUserMapper snsActUserMapper;
    @Autowired
    private SnsActPlayerUserService snsActPlayerUserService;

    @Autowired
    private SnsPostService snsPostService;
    @PostMapping("/create")
    @Operation(summary = "创建活动详情")
    public CommonResult<Long> createSnsAct(@Valid @RequestBody SnsActSaveReqVO createReqVO) {
        return success(snsActService.createSnsAct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动详情")
    public CommonResult<Boolean> updateSnsAct(@Valid @RequestBody SnsActSaveReqVO updateReqVO) {
        snsActService.updateSnsAct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsAct(@RequestParam("id") Long id) {
        snsActService.deleteSnsActAppById(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsActRespVO> getSnsAct(@RequestParam("id") Long id) {
        SnsActDO snsAct = snsActService.getSnsAct(id);
        return success(BeanUtils.toBean(snsAct, SnsActRespVO.class));
    }
    @SneakyThrows
    @PermitAll
    @GetMapping("/list")
    @Operation(summary = "获得活动详情分页")
    public CommonResult<PageResult<SnsActRespVO>> getSnsActPage(@Valid SnsActPageReqVO pageReqVO) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df_2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SnsActPageReqVO snsAct = new SnsActPageReqVO();
        snsAct.setSpType(pageReqVO.getSpType());
        snsAct.setLoginId(SecurityFrameworkUtils.getLoginUserId());
        if("owner".equals(pageReqVO.getReqType()) || "join".equals(pageReqVO.getReqType())){
            snsAct.setCreateUserId(String.valueOf(SecurityFrameworkUtils.getLoginUserId()));
        }
        snsAct.setReqType(pageReqVO.getReqType());
        snsAct.setPostFlag(pageReqVO.getPostFlag());
        if(pageReqVO.getFlag()==null){//
            if(pageReqVO.getMStatus()==null){
                snsAct.setMStatus(0);
            }else{
                snsAct.setMStatus(pageReqVO.getMStatus());
            }
        }else{
            snsAct.setMStatus(pageReqVO.getMStatus());
        }
        PageResult<SnsActDO> pageResult = snsActService.selectSnsActListPage(pageReqVO);
        String date= DateUtils.getTime();
        Date d1= null;
        try {
            d1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(pageResult.getList().size()>0) {
            int i = 0;
            for (SnsActDO act : pageResult.getList()) {
                String et1 = sdf.format(act.getEndDate());
                String et2 = sdf1.format(act.getEndTime());
                String et = et1 + " " + et2;
                Date d2 = df.parse(et);
                if (StringUtils.isNotEmpty(pageReqVO.getParam())) {//1进行中0已结束
                    if ("1".equals(pageReqVO.getParam())) {
                        if (d1.getTime() > d2.getTime()) {
                            continue;
                        }
                    } else if ("0".equals(pageReqVO.getParam())) {
                        if (d1.getTime() < d2.getTime()) {
                            continue;
                        }
                    }
                }

                SnsPostSaveReqVO snsPost = snsPostService.selectSnsPostById(act.getPostId());
                SnsActDO vo = new SnsActDO();
                BeanMapUtils.copyBeanProp(vo, snsPost);
                if(snsPost!=null) {
                    vo.setCoverUrl(snsPost.getCoverUrl());
                }
                vo.setActId(act.getActId());
                vo.setActType(act.getActType());
                vo.setActForm(act.getActForm());
                vo.setFee(act.getFee());

                ActListVo vp = getActNum(act.getActId());
                vo.setIsJoin(vp.getIsJoin());//判断是否参加
                vo.setBNum(vp.getBNum());//报名次数
                vo.setFstatus(vp.getFstatus());//是否缴费
                vo.setMemberLimit(act.getMemberLimit());
                vo.setJoinLimit(act.getJoinLimit());
                vo.setFeeLimit(act.getFeeLimit());
                vo.setJoinMemberView(act.getJoinMemberView());
                vo.setBtnView(act.getBtnView());
                vo.setShareCfg(act.getShareCfg());
                vo.setAudtCfg(act.getAudtCfg());

                vo.setSort(act.getSort());
                vo.setQrcode(act.getQrcode());
                vo.setCheckFlag(act.getCheckFlag());

                //报名人数
                SnsActUserRespVO user = new SnsActUserRespVO();
                user.setActId(act.getActId());
                List<SnsActUserRespVO> userList = snsActUserMapper.selectSnsActUserAppList(user);
                vo.setSignNum(userList.size());
                //参与人数
                SnsActPlayerUserRespVO snsActPlayerUser = new SnsActPlayerUserRespVO();
                snsActPlayerUser.setActId(act.getActId());
                List<SnsActPlayerUserRespVO> ulist = snsActPlayerUserService.selectSnsActPlayerUserList(snsActPlayerUser);
                vo.setCanyuNum(ulist.size());


               // vo.setLati(String.valueOf(act.getLati()));
               // vo.setLongi(String.valueOf(act.getLongi()));
                vo.setLocation(act.getLocation());
                vo.setRegSetting(act.getRegSetting());
                vo.setMStatus(act.getMStatus());
                vo.setContactphone(act.getContactphone());

                vo.setStartDate(act.getStartDate() != null ? act.getStartDate() : null);
                vo.setEndDate(act.getEndDate() != null ? act.getEndDate() : null);
                vo.setStartTime(act.getStartTime() != null ? act.getStartTime() : null);
                vo.setEndTime(act.getEndTime() != null ? act.getEndTime() : null);

                //vo.setCreateTime(DateUtils.datetime(snsPost.getCreateTime()));
                vo.setAppyStartDate(act.getAppyStartDate() != null ? act.getAppyStartDate() : null);
                vo.setAppyEndDate(act.getAppyEndDate() != null ? act.getAppyEndDate() : null);

                vo.setSTime(sdf.format(act.getStartDate()) + " " + sdf1.format(act.getStartTime()));
                vo.setETime(sdf.format(act.getEndDate()) + " " + sdf1.format(act.getEndTime()));
                vo.setSpType(act.getSpType());
                vo.setNumber(i);
                BeanMapUtils.copyBeanProp(act, vo);
            }
        }
        return success(BeanUtils.toBean(pageResult, SnsActRespVO.class));
    }
    /**
     * 获取参数
     */
    public ActListVo getActNum(Long act){
        ActListVo vo = new ActListVo();
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        Long uid = null;
        if(loginUser!=null){
            uid = loginUser.getId();
        }
        if(uid!=null){
            SnsActUserRespVO snsActUser=new SnsActUserRespVO();
            snsActUser.setActId(act);
            snsActUser.setUserId(uid);
            int count=snsActUserMapper.selectActUserCount(snsActUser);
            vo.setIsJoin(String.valueOf(count));//判断是否参加
            vo.setBNum(count);//报名次数

            snsActUser.setFeeTime(new Date());
            int counts=snsActUserMapper.selectActUserCount(snsActUser);
            if(counts==0){//是否缴费
                vo.setFstatus(0);
            }else{
                vo.setFstatus(1);
            }
        }
        return vo;
    }
    @GetMapping("/getTotal")
    public Integer getTotal(ActListDTO actListDTO) throws ParseException {
        List<ActListVo> list = snsActService.selectSnsActList(actListDTO);
        return list.size()>0? list.size(): 0;
    }
    /**
     * 状态修改
     */
    @PutMapping("/changeActFlag")
    public CommonResult<Boolean> changeActFlag(@RequestBody SnsActSaveReqVO snsAct)
    {
        snsAct.setMStatus(snsAct.getMsFlag().intValue());
        snsActService.updateSnsAct(snsAct);
        return success(true);
    }
    /**
     * 获取活动详情详细信息
     */
    @GetMapping(value = "/getAcInfo/{id}")
    public CommonResult<SnsActDO> getInfo(@PathVariable("id") Long id)
    {
        return success(snsActService.selectSnsActByIdPc(id));
    }
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@PathVariable Long[] ids)
    {
        if(ids!=null){
            for(Long actId:ids){
                snsActService.deleteSnsActAppById(actId);
            }
        }
        return success(true);
    }


    @PostMapping("/updateActInfo")
    public CommonResult<Boolean> updateActInfo(@RequestBody ActPostInfo actPostInfo) throws ParseException {
        actPostInfo.setMStatus(2);
        snsActService.insertSnsActPost(actPostInfo);
        return success(true);
    }
    @PostMapping("/signAct")
    public CommonResult<Boolean> signAct(@RequestBody SnsActUserRespVO snsActUser)
    {
        ActListVo actListVo = snsActService.selectSnsActById(snsActUser.getActId());
        if(actListVo!=null){
            SnsActSaveReqVO snsAct = new SnsActSaveReqVO();
            snsAct.setActId(actListVo.getActId());
            snsAct.setSignNum(actListVo.getSignNum()+1);
           snsActService.updateSnsAct(snsAct);
        }
        snsActUserService.insertSnsActUser(snsActUser);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动详情 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActExcel(@Valid SnsActPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActDO> list = snsActService.getSnsActPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动详情.xls", "数据", SnsActRespVO.class,
                        BeanUtils.toBean(list, SnsActRespVO.class));
    }

}
