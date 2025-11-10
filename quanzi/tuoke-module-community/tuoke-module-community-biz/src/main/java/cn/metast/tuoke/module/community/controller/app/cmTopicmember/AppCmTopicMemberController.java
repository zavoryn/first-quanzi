package cn.metast.tuoke.module.community.controller.app.cmTopicmember;

import cn.metast.tuoke.framework.common.util.collection.CollectionUtils;
import cn.metast.tuoke.framework.common.util.collection.MapUtils;
import cn.metast.tuoke.framework.common.util.number.NumberUtils;
import cn.metast.tuoke.framework.common.util.string.StrUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogRespVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.service.cmBuylog.CmBuyLogService;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
import cn.metast.tuoke.module.community.util.TopUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.metast.tuoke.module.pay.api.order.PayOrderApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderRespDTO;
import com.google.common.collect.Maps;
import io.micrometer.common.util.StringUtils;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.*;
import static cn.metast.tuoke.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.metast.tuoke.framework.web.core.util.WebFrameworkUtils.getLoginUserType;

import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;

@Tag(name = "管理后台 - 圈子成员")
@RestController
@Slf4j
@RequestMapping("/community/cm-topic-member")
@Validated
public class AppCmTopicMemberController {

    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Resource
    private CmTopicService cmTopicService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private CmBuyLogService cmBuyLogService;
    @Resource
    private PayOrderApi payOrderApi;

    @PostMapping("/create")
    @Operation(summary = "创建圈子成员")
    public CommonResult<Long> createCmTopicMember(@Valid @RequestBody CmTopicMemberSaveReqVO createReqVO) {
        return success(cmTopicMemberService.createCmTopicMember(createReqVO));
    }

    @PostMapping("/join")
    @Operation(summary = "加入圈子")
    public CommonResult<Long> joinCmTopicMember(@Valid @RequestBody CmTopicMemberSaveReqVO createReqVO) {
        return success(cmTopicMemberService.joinCmTopicMember(createReqVO));
    }

    @GetMapping("/joinStatus")
    @Operation(summary = "判断加入圈子的状态")
    public CommonResult<Long> joinStatus(@RequestParam("id") Long id) {
        CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),id);
        if(cmTopicMember.getStatus()==1){
            //正在审核
            return success(1L);
        }
        //收费
        Long status=0L;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = cmTopicMember.getEndTime();
        if (now.isAfter(endTime)) {//到期
            //到期了续费
            status=2L;
        }
        if(cmTopicMember.getStatus()==3){
            //拉黑
            status=3L;
        }
        if(cmTopicMember.getStatus()==5){
            //暂停服务后，需要重新交钱
            status=5L;
        }
        return success(status);
    }

    /**
     * 私信管理用户===不用了
     * @param pageReqVO
     * @return
     */
    @GetMapping("/privateMemberList")
    @Operation(summary = "私信管理用户")
    public CommonResult<PageResult<CmTopicMemberRespVO>> privateMemberList(@Valid CmTopicMemberPageReqVO pageReqVO) {
        //必传
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getCmTopicMemberConversationList(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmTopicMemberDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(itemRespVO -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(itemRespVO.getUserId());
            if (memberUserRespDTO != null) {
                itemRespVO.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                itemRespVO.setAvatar(memberUserRespDTO.getAvatar());
                //最后聊天时间
                //LocalDateTime lastMessageTime = itemRespVO.getLastMessageTime();
                //Date date = Date.from(lastMessageTime.atZone(ZoneId.systemDefault()).toInstant());
                //itemRespVO.setLastTime(TopUtils.datetime(date));
                //最后聊天内容
                //未读消息数
            }
        });
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 审核会员列表
     * @param pageReqVO
     * @return
     */
    @GetMapping("/checkMemberList")
    @Operation(summary = "审核会员列表")
    public CommonResult<PageResult<CmTopicMemberRespVO>> checkMemberList(@Valid CmTopicMemberPageReqVO pageReqVO) {
        //必传
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getCmTopicMemberPage(pageReqVO);
        pageResult.getList().forEach(itemRespVO -> {
            // 计算购买
            LocalDateTime createTime = itemRespVO.getCreateTime();
            LocalDateTime endTime = itemRespVO.getEndTime();
            long daysBetween = ChronoUnit.DAYS.between(createTime, endTime);
            itemRespVO.setRemainingDays(Math.max(0, daysBetween));
            //剩余时长
            LocalDateTime systemTime = LocalDateTime.now();
            long endDaysBetween = ChronoUnit.DAYS.between(systemTime, endTime);
            itemRespVO.setEndDays(Math.max(0, endDaysBetween));
            //支付金额
            CmTopicDO cmTopic = cmTopicService.getCmTopic(itemRespVO.getTopicId());
            if(cmTopic!=null){
                if(itemRespVO.getType()==1){
                    itemRespVO.setPrice(cmTopic.getMonthlyPrice());
                }else if(itemRespVO.getType()==2){
                    itemRespVO.setPrice(cmTopic.getBimonthlyPrice());
                }else if(itemRespVO.getType()==3){
                    itemRespVO.setPrice(cmTopic.getQuarterlyPrice());
                }else if(itemRespVO.getType()==4){
                    itemRespVO.setPrice(cmTopic.getAprilPrice());
                }else if(itemRespVO.getType()==5){
                    itemRespVO.setPrice(cmTopic.getHalfYearlyPrice());
                }
            }
        });
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 审核加入星球的会员
     * @param createReqVO
     * @return
     */
    @GetMapping("/checkMember")
    @Operation(summary = "审核加入星球的会员")
    public CommonResult<Long> checkMember(@Valid CmTopicMemberSaveReqVO createReqVO) {
        return success(cmTopicMemberService.checkMember(createReqVO));
    }
    /**
     * 会员列表
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getMemberList")
    @Operation(summary = "会员列表")
    public CommonResult<PageResult<CmTopicMemberRespVO>> getMemberList(@Valid CmTopicMemberPageReqVO pageReqVO) {
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getCmTopicMemberList(pageReqVO);

        // 获取用户信息列表
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmTopicMemberDO::getUserId));
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);

        pageResult.getList().forEach(itemRespVO -> {
            // 计算付费时长
            LocalDateTime createTime = itemRespVO.getCreateTime();
            LocalDateTime endTime = itemRespVO.getEndTime();
            long daysBetween = ChronoUnit.DAYS.between(createTime, endTime);
            itemRespVO.setRemainingDays(Math.max(0, daysBetween));
            //剩余时长
            LocalDateTime systemTime = LocalDateTime.now();
            long endDaysBetween = ChronoUnit.DAYS.between(systemTime, endTime);
            itemRespVO.setEndDays(Math.max(0, endDaysBetween));
            //互动次数
            itemRespVO.setInterNum(0);

            // 设置用户信息并隐藏手机号
            MemberUserRespDTO memberUserRespDTO = userMap.get(itemRespVO.getUserId());
            if (memberUserRespDTO != null) {
                itemRespVO.setNickname(memberUserRespDTO.getNickname());
                //itemRespVO.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                itemRespVO.setAvatar(memberUserRespDTO.getAvatar());
                // 隐藏手机号中间四位
                itemRespVO.setMobile(StrUtils.desensitizeMobile(memberUserRespDTO.getMobile()));
            }
        });
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 收入明细
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getIncomeDetailList")
    @Operation(summary = "收入明细")
    public CommonResult<PageResult<CmTopicMemberRespVO>> getIncomeDetailList(@Valid CmTopicMemberPageReqVO pageReqVO) {
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getIncomeDetailList(pageReqVO);
        pageResult.getList().forEach(itemRespVO -> {
            //支付金额
            //List<CmBuyLogDO> buyLog=cmBuyLogService.getCmBuyLogList(itemRespVO.getUserId(),itemRespVO.getTopicId());
            //支付金额 - 传递日期范围参数
            List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogListWithDateRange(
                    itemRespVO.getUserId(),
                    itemRespVO.getTopicId(),
                    pageReqVO.getBeginTime(),
                    pageReqVO.getFinishTime()
            );
            if(!org.springframework.util.CollectionUtils.isEmpty(buyLog)){
                Integer price=0;
                Integer refundPrice=0;
                for (CmBuyLogDO cmBuyLogDO : buyLog) {
                    if(cmBuyLogDO.getStatus()==1) {
                        //计算支付成功的金额
                        price += cmBuyLogDO.getPayPrice();
                    }else if(cmBuyLogDO.getStatus()==2) {
                        //计算退款的金额
                        refundPrice += cmBuyLogDO.getRefundPrice();
                    }
                }
                itemRespVO.setPrice(price);
                itemRespVO.setRefundPrice(refundPrice);
            }
        });
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 退款
     * @param notifyReqDTO
     * @return
     */
    @PermitAll
    @GetMapping("/refund-price")
    @Operation(summary = "退款")
    public CommonResult<Boolean> refundPrice(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        log.info("【支付回调】参数：{}", notifyReqDTO);
        cmBuyLogService.updateOrderPaidPrice(notifyReqDTO);
        return success(true);
    }

    @GetMapping("/getTotalPrice")
    @Operation(summary = "总收入明细")
    public CommonResult<Integer> getTotalPrice(@Valid CmTopicMemberPageReqVO pageReqVO) {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmTopicMemberDO> pageResult = cmTopicMemberService.getIncomeDetailList(pageReqVO).getList();
        // 使用 AtomicInteger 替代基本类型
        AtomicInteger price = new AtomicInteger(0);
        AtomicInteger refundPrice = new AtomicInteger(0);
        pageResult.forEach(itemRespVO -> {
            // 支付金额
            //List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogList(itemRespVO.getUserId(), itemRespVO.getTopicId());
            // 支付金额 - 传递日期范围参数
            List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogListWithDateRange(
                    itemRespVO.getUserId(),
                    itemRespVO.getTopicId(),
                    pageReqVO.getBeginTime(),
                    pageReqVO.getFinishTime()
            );
            if (!org.springframework.util.CollectionUtils.isEmpty(buyLog)) {
                for (CmBuyLogDO cmBuyLogDO : buyLog) {
                    if (cmBuyLogDO.getStatus() == 1) {
                        // 计算支付成功的金额 - 使用原子操作
                        price.addAndGet(cmBuyLogDO.getPayPrice());
                    }else if(cmBuyLogDO.getStatus() == 2){//退款
                        refundPrice.addAndGet(cmBuyLogDO.getRefundPrice());
                    }
                }
            }
        });
        // 获取最终结果
        int totalPrice = price.get();
        int refundTotalPrice = refundPrice.get();
        return success(totalPrice);
    }

    /**
     * 收入明细详情
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getIncome")
    @Operation(summary = "收入明细详情")
    public CommonResult<PageResult<CmBuyLogRespVO>> getIncome(@Valid CmBuyLogPageReqVO pageReqVO) {
        PageResult<CmBuyLogDO> pageResult=cmBuyLogService.getcmBuyLogPage(pageReqVO);
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), item -> Long.parseLong(item.getCreator())));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(itemRespVO -> {
            //圈子名称
            itemRespVO.setTopicName(cmTopicService.getCmTopic(itemRespVO.getLevelId()).getTopicName());

            // 获取用户信息
            MemberUserRespDTO memberUserRespDTO = userMap.get(Long.parseLong(itemRespVO.getCreator()));
            if(memberUserRespDTO!=null) {
                itemRespVO.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                itemRespVO.setAvatar(memberUserRespDTO.getAvatar());
                // 隐藏手机号中间四位
                itemRespVO.setMobile(StrUtils.desensitizeMobile(memberUserRespDTO.getMobile()));
            }
        });
        return success(BeanUtils.toBean(pageResult, CmBuyLogRespVO.class));
    }


    /**
     * 我的购买
     * @param pageReqVO
     * @return
     */
    @GetMapping("/getMyShopList")
    @Operation(summary = "我的购买")
    public CommonResult<PageResult<CmTopicMemberRespVO>> getMyShopList(@Valid CmTopicMemberPageReqVO pageReqVO) {
        pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getMyShopList(pageReqVO);
        pageResult.getList().forEach(itemRespVO -> {
            // 计算购买时长
            LocalDateTime createTime = itemRespVO.getCreateTime();
            LocalDateTime endTime = itemRespVO.getEndTime();
            long daysBetween = ChronoUnit.DAYS.between(createTime, endTime);
            itemRespVO.setRemainingDays(Math.max(0, daysBetween));
            //剩余时长
            LocalDateTime systemTime = LocalDateTime.now();
            long endDaysBetween = ChronoUnit.DAYS.between(systemTime, endTime);
            itemRespVO.setEndDays(Math.max(0, endDaysBetween));
            //支付金额
            CmTopicDO cmTopic = cmTopicService.getCmTopic(itemRespVO.getTopicId());
            if(cmTopic!=null){
                if(itemRespVO.getType()==1){
                    itemRespVO.setPrice(cmTopic.getMonthlyPrice());
                }else if(itemRespVO.getType()==2){
                    itemRespVO.setPrice(cmTopic.getBimonthlyPrice());
                }else if(itemRespVO.getType()==3){
                    itemRespVO.setPrice(cmTopic.getQuarterlyPrice());
                }else if(itemRespVO.getType()==4){
                    itemRespVO.setPrice(cmTopic.getAprilPrice());
                }else if(itemRespVO.getType()==5){
                    itemRespVO.setPrice(cmTopic.getHalfYearlyPrice());
                }
            }
        });
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 导出收入明细
     * @param pageReqVO 查询参数
     * @param response HTTP响应
     */
    @GetMapping("/exportIncomeDetail")
    @Operation(summary = "导出收入明细")
    public void exportIncomeDetail(@Valid CmTopicMemberPageReqVO pageReqVO, HttpServletResponse response) throws IOException {
        //查询圈子
        CmTopicDO cmTopic = cmTopicService.getCmTopic(pageReqVO.getTopicId());
        if(cmTopic==null){
            return;
        }
        // 设置不分页，获取全部数据
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getIncomeDetailList(pageReqVO);

        // 构建导出数据
        List<ExportIncomeDetailVO> exportList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        pageResult.getList().forEach(item -> {
            // 计算收入金额
            List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogListWithDateRange(
                    item.getUserId(),
                    item.getTopicId(),
                    pageReqVO.getBeginTime(),
                    pageReqVO.getFinishTime()
            );
            List<Long> blockIds=new ArrayList<>();
            Integer price = 0;
            if (!org.springframework.util.CollectionUtils.isEmpty(buyLog)) {
                for (CmBuyLogDO cmBuyLogDO : buyLog) {
                    if (cmBuyLogDO.getStatus() == 1) {
                        price += cmBuyLogDO.getPayPrice();
                        blockIds.add(cmBuyLogDO.getId());
                    }
                }
            }

            // 构建导出VO
            ExportIncomeDetailVO vo = new ExportIncomeDetailVO();
            vo.setStartTime(item.getStartTime() != null ? item.getStartTime().format(formatter) : "");
            vo.setEndTime(item.getEndTime() != null ? item.getEndTime().format(formatter) : "");
            vo.setOrderNum(item.getOrderNum());
            // 微信昵称格式：nickname-name-userId
            String nickname = (item.getNickname() != null ? item.getNickname() : "") + "-"
                    + (item.getName() != null ? item.getName() : "") + "-" + item.getUserId();
            vo.setNickname(nickname);
            vo.setPrice(price);
            //查询订单信息
            List<PayOrderRespDTO> payOrderRespDTOS=payOrderApi.getOrderIds(blockIds);
            String no = "";
            String channelCode = "";
            if(!org.springframework.util.CollectionUtils.isEmpty(payOrderRespDTOS)){
               for(PayOrderRespDTO payOrderRespDTO:payOrderRespDTOS){
                   if (StringUtils.isNotBlank(payOrderRespDTO.getNo())) {
                       no = payOrderRespDTO.getNo()+",";
                   }
                   if(StringUtils.isNotBlank(payOrderRespDTO.getChannelCode())) {
                       if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_pub")) {
                           channelCode = "微信 JSAPI 支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_lite")) {
                           channelCode = "微信小程序支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_app")) {
                           channelCode = "微信 App 支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_native")) {
                           channelCode = "微信 Native 支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_wap")) {
                           channelCode = "微信 Wap 网站支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "wx_bar")) {
                           channelCode = "微信付款码支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "alipay_pc")) {
                           channelCode = "支付宝 PC 网站支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "alipay_wap")) {
                           channelCode = "支付宝 Wap 网站支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "alipay_app")) {
                           channelCode = "支付宝App 支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "alipay_qr")) {
                           channelCode = "支付宝扫码支付,";
                       } else if (Objects.equals(payOrderRespDTO.getChannelCode(), "alipay_bar")) {
                           channelCode = "支付宝条码支付,";
                       }
                   }
               }
            }
            if(StringUtils.isNotBlank(no)) {
                vo.setNo(no.substring(0, no.length()-1));
            }
            if(StringUtils.isNotBlank(channelCode)){
                vo.setChannelCode(channelCode.substring(0, channelCode.length()-1));
            }
            vo.setName(cmTopic.getTopicName());
            exportList.add(vo);
        });

        // 导出Excel
        ExcelUtils.write(response, "收入明细.xls", "收入明细", ExportIncomeDetailVO.class, exportList);
    }


    /**
     * 同步订单支付信息
     * @param pageReqVO 查询参数
     */
    @GetMapping("/syncPayOrder")
    @Operation(summary = "导出收入明细")
    public CommonResult<Boolean> exportIncomeDetail(@Valid CmTopicMemberPageReqVO pageReqVO){
        //查询全部未支付的订单记录
        List<CmBuyLogDO> buyLog = cmBuyLogService.getCmBuyLogListWithDateRangeNoPay(pageReqVO.getTopicId());
        buyLog.forEach(item -> {
            // 调用同步按钮
            payOrderApi.syncOrder(item.getPayOrderId());

        });
        return success(true);
    }


    @PutMapping("/update")
    @Operation(summary = "更新圈子成员")
    public CommonResult<Boolean> updateCmTopicMember(@Valid @RequestBody CmTopicMemberSaveReqVO updateReqVO) {
        if(updateReqVO.getSendTime()!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            updateReqVO.setEndTime(LocalDateTime.parse(updateReqVO.getSendTime(), formatter));
        }
        cmTopicMemberService.updateCmTopicMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子成员")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteCmTopicMember(@RequestParam("id") Long id) {
        cmTopicMemberService.deleteCmTopicMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子成员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CmTopicMemberRespVO> getCmTopicMember(@RequestParam("id") Long id) {
        CmTopicMemberDO cmTopicMember = cmTopicMemberService.getCmTopicMember(id);
        return success(BeanUtils.toBean(cmTopicMember, CmTopicMemberRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子成员分页")
    public CommonResult<PageResult<CmTopicMemberRespVO>> getCmTopicMemberPage(@Valid CmTopicMemberPageReqVO pageReqVO) {
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getCmTopicMemberPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    /**
     * 会员用户注销
     * @return 操作结果
     */
    @DeleteMapping("/cancel")
    @Operation(summary = "会员用户注销")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public CommonResult<Boolean> cancelMember() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        cmTopicMemberService.cancelMemberByUserId(userId);
        return success(true);
    }
}
