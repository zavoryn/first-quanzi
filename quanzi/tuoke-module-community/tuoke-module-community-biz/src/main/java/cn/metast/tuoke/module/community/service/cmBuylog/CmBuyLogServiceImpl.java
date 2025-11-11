package cn.metast.tuoke.module.community.service.cmBuylog;

import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.json.JsonUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.dal.mysql.cmBuylog.CmBuyLogMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmConfig.CmConfigMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import cn.metast.tuoke.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.metast.tuoke.module.pay.api.order.PayOrderApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderRespDTO;
import cn.metast.tuoke.module.pay.enums.order.PayOrderStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 会员购买记录 Service 实现类
 *
 * @author adminXq
 */
@Slf4j
@Service
@Validated
public class CmBuyLogServiceImpl implements CmBuyLogService {

    @Resource
    private CmBuyLogMapper cmBuyLogMapper;
    @Resource
    private CmConfigMapper cmConfigMapper;
    @Resource
    private CmTopicMapper cmTopicMapper;
    @Resource
    private PayOrderApi payOrderApi;
    @Override
    public Long createcmBuyLog(CmBuyLogSaveReqVO createReqVO) {
        // 插入
        CmBuyLogDO cmBuyLog = BeanUtils.toBean(createReqVO, CmBuyLogDO.class);
        cmBuyLogMapper.insert(cmBuyLog);
        // 返回
        return cmBuyLog.getId();
    }

    @Override
    public void updatecmBuyLog(CmBuyLogSaveReqVO updateReqVO) {
        // 校验存在
        validatecmBuyLogExists(updateReqVO.getId());
        // 更新
        CmBuyLogDO updateObj = BeanUtils.toBean(updateReqVO, CmBuyLogDO.class);
        cmBuyLogMapper.updateById(updateObj);
    }

    @Override
    public void deletecmBuyLog(Long id) {
        // 校验存在
        validatecmBuyLogExists(id);
        // 删除
        cmBuyLogMapper.deleteById(id);
    }

    private void validatecmBuyLogExists(Long id) {
        if (cmBuyLogMapper.selectById(id) == null) {
            throw exception(CM_BUY_LOG_NOT_EXISTS);
        }
    }

    @Override
    public CmBuyLogDO getcmBuyLog(Long id) {
        return cmBuyLogMapper.selectById(id);
    }

    @Override
    public PageResult<CmBuyLogDO> getcmBuyLogPage(CmBuyLogPageReqVO pageReqVO) {
        return cmBuyLogMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderPaid(Long id, Long payOrderId) {
        // 1.1 校验订单是否存在
        //CmBuyLogDO memberBuyLogDO = validateOrderExists(id);
        // 3. 更新 MemberBuyLogDO 状态为已支付
            cmBuyLogMapper.updateById(
                    new CmBuyLogDO()
                            .setId(id)
                            .setStatus(1)
                            .setPayStatus(true)
                            .setPayTime(LocalDateTime.now())
            );
    }

    @Override
    public void updateOrderPaidPrice(PayOrderNotifyReqDTO payOrder) {
        cmBuyLogMapper.updateById(
                new CmBuyLogDO()
                        .setId(Long.valueOf(payOrder.getMerchantOrderId()))
                        .setStatus(2)//退款
                        .setRefundPrice(payOrder.getPrice())
                        .setRefundTime(LocalDateTime.now())
        );
    }

    @Override
    public List<CmBuyLogDO> getCmBuyLogList(Long userId, Long topicId) {
        return cmBuyLogMapper.selectList(new LambdaQueryWrapper<CmBuyLogDO>()
                .eq(CmBuyLogDO::getCreator, userId.toString())
                .eq(CmBuyLogDO::getLevelId, topicId.intValue())
        );
    }

    @Override
    public List<CmBuyLogDO> getCmBuyLogListWithDateRange(Long userId, Long topicId, String beginTime, String finishTime) {
        LambdaQueryWrapper<CmBuyLogDO> wrapper = new LambdaQueryWrapper<CmBuyLogDO>()
                .eq(CmBuyLogDO::getCreator, userId.toString())
                .eq(CmBuyLogDO::getLevelId, topicId.intValue());

        // 如果有日期范围，则添加支付时间筛选条件
        if (beginTime != null && finishTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(beginTime, formatter);
            LocalDate endDate = LocalDate.parse(finishTime, formatter);
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
            wrapper.between(CmBuyLogDO::getPayTime, startDateTime, endDateTime);
        }

        return cmBuyLogMapper.selectList(wrapper);
    }
    @Override
    public List<CmBuyLogDO> getCmBuyLogListWithDateRangeNoPay(Long topicId) {
        LambdaQueryWrapper<CmBuyLogDO> wrapper = new LambdaQueryWrapper<CmBuyLogDO>()
                .eq(CmBuyLogDO::getLevelId, topicId)
                .eq(CmBuyLogDO::getStatus, 0);
        // 如果有日期范围，则添加支付时间筛选条件
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(LocalDate.now().toString(), formatter);
            LocalDate endDate = LocalDate.parse(LocalDate.now().toString(), formatter);
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
            wrapper.between(CmBuyLogDO::getCreateTime, startDateTime, endDateTime);
        return cmBuyLogMapper.selectList(wrapper);
    }

    @Override
    public List<CmBuyLogDO> getCmBuyLogListWithDateRangeNoPay(Long userId, Long topicId) {
        LambdaQueryWrapper<CmBuyLogDO> wrapper = new LambdaQueryWrapper<CmBuyLogDO>()
                .eq(CmBuyLogDO::getLevelId, topicId)
                .eq(CmBuyLogDO::getCreator, userId.toString())
                .eq(CmBuyLogDO::getStatus, 0);
        return cmBuyLogMapper.selectList(wrapper);
    }

    /**
     * 校验支付订单的合法性
     *
     * @param buyLogDO 购买记录
     * @param payOrderId 支付订单编号
     * @return 支付订单
     */
    private PayOrderRespDTO validatePayOrderPaid(CmBuyLogDO buyLogDO, Long payOrderId) {
        // 1. 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId);
        if (payOrder == null) {
            log.error("[validatePayOrderPaid][order({}) payOrder({}) 不存在，请进行处理！]", buyLogDO.getId(), payOrderId);
            throw exception(BUY_LOG_NOT_FOUND);
        }

        // 2.1 校验支付单已支付
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validatePayOrderPaid][order({}) payOrder({}) 未支付，请进行处理！payOrder 数据是：{}]",
                    buyLogDO.getId(), payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(BUY_LOG_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // 2.2 校验支付金额一致
        if (ObjectUtil.notEqual(payOrder.getPrice(), buyLogDO.getPayPrice())) {
            log.error("[validatePayOrderPaid][order({}) payOrder({}) 支付金额不匹配，请进行处理！order 数据是：{}，payOrder 数据是：{}]",
                    buyLogDO.getId(), payOrderId, JsonUtils.toJsonString(buyLogDO), JsonUtils.toJsonString(payOrder));
            throw exception(BUY_LOG_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH);
        }
        // 2.2 校验支付订单匹配（二次）
        if (ObjectUtil.notEqual(payOrder.getMerchantOrderId(), buyLogDO.getId().toString())) {
            log.error("[validatePayOrderPaid][order({}) 支付单不匹配({})，请进行处理！payOrder 数据是：{}]",
                    buyLogDO.getId(), payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(BUY_LOG_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }
        return payOrder;
    }
    public CmBuyLogDO validateOrderExists(Long id) {
        // 校验支付记录是否存在
        CmBuyLogDO memberBuyLogDO = cmBuyLogMapper.selectById(id);
        if (memberBuyLogDO == null) {
            throw exception(CM_BUY_LOG_NOT_EXISTS);
        }
        return memberBuyLogDO;
    }

    @Override
    public List<Long> getPayOrderIdsByDeptId(Long deptId) {
        // 超级管理员(deptId=115)，返回null表示不过滤
        if (deptId != null && deptId == 115L) {
            return null;
        }

        // 1. 从 cm_config 获取部门对应的 topic_id 列表
        List<Long> topicIds = cmConfigMapper.selectList(new LambdaQueryWrapperX<CmConfigDO>()
                .eq(CmConfigDO::getDeptId, deptId))
                .stream()
                .map(CmConfigDO::getTopicId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (topicIds.isEmpty()) {
            return List.of(); // 返回空列表
        }

        // 2. 从 member_buy_log 获取这些 topic_id 对应的 pay_order_id 列表
        return cmBuyLogMapper.selectList(new LambdaQueryWrapperX<CmBuyLogDO>()
                .in(CmBuyLogDO::getLevelId, topicIds))
                .stream()
                .map(CmBuyLogDO::getPayOrderId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}
