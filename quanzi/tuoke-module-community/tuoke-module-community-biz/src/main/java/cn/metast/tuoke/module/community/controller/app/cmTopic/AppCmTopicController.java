package cn.metast.tuoke.module.community.controller.app.cmTopic;

import cn.metast.tuoke.framework.common.util.string.StrUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.CmTopicMemberSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.dal.mysql.cmBuylog.CmBuyLogMapper;
import cn.metast.tuoke.module.community.service.cmBuylog.CmBuyLogService;
import cn.metast.tuoke.module.community.service.cmConfig.CmConfigService;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.metast.tuoke.module.pay.api.order.PayOrderApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.metast.tuoke.module.system.api.dict.DictDataApi;
import cn.metast.tuoke.module.system.api.dict.dto.DictDataRespDTO;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import static cn.metast.tuoke.framework.common.util.servlet.ServletUtils.getClientIP;

import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
@Slf4j
@Tag(name = "管理后台 - 圈子详情")
@RestController
@RequestMapping("/community/cm-topic")
@Validated
public class AppCmTopicController {

    @Resource
    private CmTopicService cmTopicService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private CmBuyLogMapper cmBuyLogMapper;
    @Resource
    private CmBuyLogService cmBuyLogService;
    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private DictDataApi dictDataApi;
    @Resource
    private CmConfigService cmConfigService;
    @GetMapping("/getBuy")
    @Operation(summary = "获得圈子详情")
    public CommonResult<CmTopicRespVO> getBuy(@RequestParam("id") Long id) {
        CmTopicDO cmTopic = cmTopicService.getCmTopic(id);
        List<Map<String,Object>> prices=new ArrayList<>();
        if(cmTopic!=null){
            //返回金额集合
            if(cmTopic.getMonthlyPrice()!=null && cmTopic.getMonthlyPrice()>0){
                Map<String,Object> mm=new HashMap<>();
                mm.put("price",cmTopic.getMonthlyPrice());
                mm.put("type","1");
                mm.put("name","月度");
                prices.add(mm);
            }
            if(cmTopic.getBimonthlyPrice()!=null && cmTopic.getBimonthlyPrice()>0){
                Map<String,Object> mm=new HashMap<>();
                mm.put("price",cmTopic.getBimonthlyPrice());
                mm.put("type","2");
                mm.put("name","两月");
                prices.add(mm);
            }
            if(cmTopic.getQuarterlyPrice()!=null && cmTopic.getQuarterlyPrice()>0){
                Map<String,Object> mm=new HashMap<>();
                mm.put("price",cmTopic.getQuarterlyPrice());
                mm.put("type","3");
                mm.put("name","季度");
                prices.add(mm);
            }
            if(cmTopic.getAprilPrice()!=null && cmTopic.getAprilPrice()>0){
                Map<String,Object> mm=new HashMap<>();
                mm.put("price",cmTopic.getAprilPrice());
                mm.put("type","4");
                mm.put("name","四月");
                prices.add(mm);
            }
            if(cmTopic.getHalfYearlyPrice()!=null && cmTopic.getHalfYearlyPrice()>0){
                Map<String,Object> mm=new HashMap<>();
                mm.put("price",cmTopic.getHalfYearlyPrice());
                mm.put("type","5");
                mm.put("name","半年");
                prices.add(mm);
            }
            cmTopic.setPrices(prices);
            //判断是否已加入
            CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),id);
            if(cmTopicMember==null){
                //查询全部未支付的订单记录
                List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogListWithDateRangeNoPay(SecurityFrameworkUtils.getLoginUserId(),id);
                buyLog.forEach(item -> {
                    // 调用同步按钮
                    log.info("同步订单：{}",item.getPayOrderId());
                    payOrderApi.syncOrder(item.getPayOrderId());
                });
                cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),id);
                log.info("cmTopicMember:{}",cmTopicMember);
            }
            if(cmTopicMember==null && cmTopic.getIsType()==0){
                //1未加入
                cmTopic.setPayStatus(1);
            }else if(cmTopicMember==null && cmTopic.getIsType()==1){//现在全是收费的了
                //不收费
                cmTopic.setPayStatus(0);
                //不收费，直接插入
                //创建圈子成员
                CmTopicMemberSaveReqVO reqVO=new CmTopicMemberSaveReqVO();
                reqVO.setTopicId(id);
                reqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
                reqVO.setRole(0);
                reqVO.setStatus(0);
                reqVO.setJoinTime(LocalDateTime.now());
                reqVO.setStartTime(LocalDateTime.now());
                reqVO.setEndTime(LocalDateTime.now().plusYears(100));
                cmTopicMemberService.createCmTopicMember(reqVO);
                cmTopic.setCmTopicMember(BeanUtils.toBean(reqVO, CmTopicMemberDO.class));
            }else if(cmTopicMember!=null){
                //收费
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime endTime = cmTopicMember.getEndTime();
                if (now.isAfter(endTime)) {//到期
                    //到期了续费
                    cmTopic.setPayStatus(1);
                }else{
                    cmTopic.setPayStatus(0);
                }
                if(cmTopicMember.getStatus()==5){
                    //暂停服务后，需要重新交钱
                    cmTopic.setPayStatus(5);
                }
                if(cmTopicMember.getStatus()==3){
                    //拉黑
                    cmTopic.setPayStatus(3);
                }
                cmTopic.setCmTopicMember(cmTopicMember);
            }
            //更新微信发送次数
            Long num=getRemainingCountOptimized(cmTopic);
            cmTopic.setSendNum(num);
        }
        return success(BeanUtils.toBean(cmTopic, CmTopicRespVO.class));
    }
    /**
     * 获取当天剩余次数
     */
    public Long getRemainingCountOptimized(CmTopicDO cmTopicDO) {
        LocalDateTime sendTime = cmTopicDO.getSendTime();
        LocalDate today = LocalDate.now();
        // 如果不是今天，返回20次；如果是今天，返回剩余次数
        if (sendTime == null || !sendTime.toLocalDate().equals(today)) {
            cmTopicDO.setSendTime(LocalDateTime.now());
            cmTopicDO.setSendNum(20L);
            cmTopicService.updateCmTopic(BeanUtils.toBean(cmTopicDO, CmTopicSaveReqVO.class));
            return 20L;
        } else {
            return cmTopicDO.getSendNum();
        }
    }
    /**
     * 支付接口
     * @param
     * @return
     */
    @GetMapping("/createBuyLog")
    @Operation(summary = "创建会员购买订单")
    public CommonResult<CmBuyLogDO> createBuyLog(@RequestParam("topicId")Long topicId,@RequestParam("type") Long type) {
        Long userId= SecurityFrameworkUtils.getLoginUserId();
       // memberApiService.memberApi.createBuyLog(topicId,type);
        // 插入
        CmBuyLogDO buyLog = new CmBuyLogDO();
        CmTopicDO cmTopic=cmTopicService.getCmTopic(topicId);
        buyLog.setName("加入圈子");
        buyLog.setLevelId(topicId);
        buyLog.setLevel(type.intValue());
        buyLog.setStatus(0);
        buyLog.setCreator(userId.toString());
        buyLog.setYzfPrice(0);
        if(type==1){
            buyLog.setExperience(cmTopic.getMonthlyPrice());
            buyLog.setTotalPrice(cmTopic.getMonthlyPrice());
            buyLog.setPayPrice(cmTopic.getMonthlyPrice());
        }else if(type==2){
            buyLog.setExperience(cmTopic.getBimonthlyPrice());
            buyLog.setTotalPrice(cmTopic.getBimonthlyPrice());
            buyLog.setPayPrice(cmTopic.getBimonthlyPrice());
        }else if(type==3){

            buyLog.setExperience(cmTopic.getQuarterlyPrice());
            buyLog.setTotalPrice(cmTopic.getQuarterlyPrice());
            buyLog.setPayPrice(cmTopic.getQuarterlyPrice());
        }else if(type==4){
            buyLog.setExperience(cmTopic.getAprilPrice());
            buyLog.setTotalPrice(cmTopic.getAprilPrice());
            buyLog.setPayPrice(cmTopic.getAprilPrice());

        }else if(type==5){
            buyLog.setExperience(cmTopic.getHalfYearlyPrice());
            buyLog.setTotalPrice(cmTopic.getHalfYearlyPrice());
            buyLog.setPayPrice(cmTopic.getHalfYearlyPrice());
        }
        cmBuyLogMapper.insert(buyLog);
        String mType="member";
        CmConfigDO config =cmConfigService.getCmConfigTopicId(topicId);
        if(config!=null){
            if(config.getDeptId()==116){
                //天鼎
                mType="member";
            }else if(config.getDeptId()==117){
                //长沙
                mType="memberCS";
            }
        }

        // 创建支付单，用于后续的支付
        PayOrderCreateReqDTO payOrderCreateReqDTO = new PayOrderCreateReqDTO()
                .setAppKey(mType).setUserIp(getClientIP());
        payOrderCreateReqDTO.setMerchantOrderId(String.valueOf(buyLog.getId()));
        String subject = buyLog.getName();
        subject = StrUtils.maxLength(subject, PayOrderCreateReqDTO.SUBJECT_MAX_LENGTH); // 避免超过 32 位
        payOrderCreateReqDTO.setSubject(subject);
        payOrderCreateReqDTO.setBody(subject);
        // 订单相关字段
        payOrderCreateReqDTO.setPrice(buyLog.getPayPrice()*100);
        //payOrderCreateReqDTO.setPrice(1);//1分钱
        payOrderCreateReqDTO.setExpireTime(LocalDateTime.now().plus(Duration.ofHours(2L)));
        Long payOrderId = payOrderApi.createOrder(payOrderCreateReqDTO);

        buyLog.setPayOrderId(payOrderId);
        cmBuyLogMapper.updateById(new CmBuyLogDO().setId(buyLog.getId()).setPayOrderId(payOrderId));
        return success(buyLog);
    }

    /**
     * 支付完成接口回调接口
     * @param notifyReqDTO
     * @return
     */
    @PostMapping("/update-paid")
    @Operation(summary = "会员购买成功") // 会员购买服务，进行回调
    @PermitAll
    public CommonResult<Boolean> updateMembetInfo(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        // 4. 更新 用户会员信息
        CmBuyLogDO cmBuyLogDO=cmBuyLogService.getcmBuyLog(Long.valueOf(notifyReqDTO.getMerchantOrderId()));
        if(cmBuyLogDO!=null) {
            cmBuyLogService.updateOrderPaid(
                    Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                    notifyReqDTO.getPayOrderId()
            );
            CmTopicMemberSaveReqVO createReqVO = new CmTopicMemberSaveReqVO();
            createReqVO.setUserId(Long.parseLong(cmBuyLogDO.getCreator()));
            createReqVO.setTopicId(cmBuyLogDO.getLevelId());
            createReqVO.setType(cmBuyLogDO.getLevel());
            cmTopicMemberService.joinCmTopicMember(createReqVO);
        }
        return success(true);
    }

    /**
     *  查询用户是否已签署合同
     * @return
     */
    @GetMapping("/paytypeoy")
    @Operation(summary = "生成网签合同数据")
    public CommonResult<Map<String,Object>> paytypeoy(@RequestParam("topicId")Long topicId,@RequestParam("type") Long type) {
        return success(cmTopicService.paytypeoy(topicId,type));
    }

    /**
     *  生成二维码
     * @return
     */
    @GetMapping("/getQrdUrl")
    @Operation(summary = "生成二维码链接")
    public CommonResult<String> getQrdUrl(@RequestParam("topicId")Long topicId) {
        return success(cmTopicService.getQrdUrl(topicId));
    }

    /**
     *  生成二维码链接
     * @return
     */
    @GetMapping("/getShortUrl")
    @Operation(summary = "生成二维码链接")
    public CommonResult<String> getShortUrl(@RequestParam("topicId")Long topicId) {
        return success(cmTopicService.getShortUrl(topicId));
    }
    /**
     *  生成二维码H5Url链接
     * @return
     */
    @GetMapping("/getLinkUrl")
    @Operation(summary = "生成二维码链接")
    public CommonResult<String> getLinkUrl(@RequestParam("topicId")Long topicId) {
        return success(cmTopicService.getLinkUrl(topicId));
    }


    @PostMapping("/create")
    @Operation(summary = "创建圈子详情")
    public CommonResult<Long> createCmTopic(@Valid @RequestBody CmTopicSaveReqVO createReqVO) {
        createReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        createReqVO.setStatus(1);//审核中
        Long id =cmTopicService.createCmTopic(createReqVO);
        return success(id);
    }

    /**
     * 查询我加入的星球和创建的新球
     * @return
     */
    @PermitAll
    @GetMapping("/getTopicList")
    @Operation(summary = "查询我加入的星球和创建的新球")
    public CommonResult<Map<String, Object>> getTopicList(@Valid CmTopicRespVO cmTopicRespVO) {
        cmTopicRespVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        return success(cmTopicService.getTopicList(cmTopicRespVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子详情")
    public CommonResult<Boolean> updateCmTopic(@Valid @RequestBody CmTopicSaveReqVO updateReqVO) {
        cmTopicService.updateCmTopic(updateReqVO);
        return success(true);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteCmTopic(@RequestParam("id") Long id) {
        cmTopicService.deleteCmTopic(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CmTopicRespVO> getCmTopic(@RequestParam("id") Long id) {
        CmTopicDO cmTopic = cmTopicService.getCmTopic(id);
        return success(BeanUtils.toBean(cmTopic, CmTopicRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子详情分页")
    public CommonResult<PageResult<CmTopicRespVO>> getCmTopicPage(@Valid CmTopicPageReqVO pageReqVO) {
        pageReqVO.setStatus(0);
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<CmTopicDO> pageResult = cmTopicService.getCmTopicPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmTopicRespVO.class));
    }

    //0 显示 1不显示
    @Operation(summary = "获得状态显示隐藏")
    @GetMapping("/getAppStatus")
    @PermitAll
    public CommonResult<String> getLiveStatus() {
        DictDataRespDTO dictDataRespDTO=dictDataApi.parseDictData("cm_status","cm_status");
        if(dictDataRespDTO!=null){
            return success(dictDataRespDTO.getValue());
        }
        return success("0");
    }
}
