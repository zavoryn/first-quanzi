package cn.metast.tuoke.module.pay.dal.mysql.order;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.pay.controller.admin.order.vo.PayOrderExportReqVO;
import cn.metast.tuoke.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.metast.tuoke.module.pay.dal.dataobject.order.PayOrderDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PayOrderMapper extends BaseMapperX<PayOrderDO> {

    default PageResult<PayOrderDO> selectPage(PayOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayOrderDO>()
                .eqIfPresent(PayOrderDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayOrderDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayOrderDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayOrderDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayOrderDO::getNo, reqVO.getNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayOrderDO::getId));
    }

    /**
     * 分页查询支付订单，支持部门数据隔离
     *
     * @param reqVO 查询参数
     * @param orderIds 允许查询的订单ID列表
     * @return 分页结果
     */
    default PageResult<PayOrderDO> selectPageByOrderIds(PayOrderPageReqVO reqVO, List<Long> orderIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayOrderDO>()
                .inIfPresent(PayOrderDO::getId, orderIds)
                .eqIfPresent(PayOrderDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayOrderDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayOrderDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayOrderDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayOrderDO::getNo, reqVO.getNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayOrderDO::getId));
    }

    default List<PayOrderDO> selectList(PayOrderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PayOrderDO>()
                .eqIfPresent(PayOrderDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayOrderDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayOrderDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayOrderDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayOrderDO::getNo, reqVO.getNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayOrderDO::getId));
    }

    /**
     * 查询支付订单列表，支持部门数据隔离
     *
     * @param reqVO 查询参数
     * @param orderIds 允许查询的订单ID列表
     * @return 订单列表
     */
    default List<PayOrderDO> selectListByOrderIds(PayOrderExportReqVO reqVO, List<Long> orderIds) {
        return selectList(new LambdaQueryWrapperX<PayOrderDO>()
                .inIfPresent(PayOrderDO::getId, orderIds)
                .eqIfPresent(PayOrderDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayOrderDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayOrderDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayOrderDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayOrderDO::getNo, reqVO.getNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayOrderDO::getId));
    }

    default Long selectCountByAppId(Long appId) {
        return selectCount(PayOrderDO::getAppId, appId);
    }

    default PayOrderDO selectByAppIdAndMerchantOrderId(Long appId, String merchantOrderId) {
        return selectOne(PayOrderDO::getAppId, appId,
                PayOrderDO::getMerchantOrderId, merchantOrderId);
    }

    default int updateByIdAndStatus(Long id, Integer status, PayOrderDO update) {
        return update(update, new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getId, id).eq(PayOrderDO::getStatus, status));
    }

    default List<PayOrderDO> selectListByStatusAndExpireTimeLt(Integer status, LocalDateTime expireTime) {
        return selectList(new LambdaQueryWrapper<PayOrderDO>()
                .eq(PayOrderDO::getStatus, status)
                .lt(PayOrderDO::getExpireTime, expireTime));
    }

}
