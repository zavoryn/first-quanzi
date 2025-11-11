package cn.metast.tuoke.module.community.service.cmBuylog;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import cn.metast.tuoke.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 会员购买记录 Service 接口
 *
 * @author adminXq
 */
public interface CmBuyLogService {

    /**
     * 创建会员购买记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createcmBuyLog(@Valid CmBuyLogSaveReqVO createReqVO);

    /**
     * 更新会员购买记录
     *
     * @param updateReqVO 更新信息
     */
    void updatecmBuyLog(@Valid CmBuyLogSaveReqVO updateReqVO);

    /**
     * 删除会员购买记录
     *
     * @param id 编号
     */
    void deletecmBuyLog(Long id);

    /**
     * 获得会员购买记录
     *
     * @param id 编号
     * @return 会员购买记录
     */
    CmBuyLogDO getcmBuyLog(Long id);

    /**
     * 获得会员购买记录分页
     *
     * @param pageReqVO 分页查询
     * @return 会员购买记录分页
     */
    PageResult<CmBuyLogDO> getcmBuyLogPage(CmBuyLogPageReqVO pageReqVO);

    void updateOrderPaid(Long orderId, Long payOrderId);

    /**
     * 更新退案金额
     * @param payOrder
     */
    void updateOrderPaidPrice(PayOrderNotifyReqDTO payOrder);

    List<CmBuyLogDO> getCmBuyLogList(Long userId, Long topicId);

    /**
     * 根据用户ID、圈子ID和日期范围查询购买记录
     *
     * @param userId 用户ID
     * @param topicId 圈子ID
     * @param beginTime 开始日期
     * @param finishTime 结束日期
     * @return 购买记录列表
     */
    List<CmBuyLogDO> getCmBuyLogListWithDateRange(Long userId, Long topicId, String beginTime, String finishTime);

    //查询全部未支付记录
    List<CmBuyLogDO> getCmBuyLogListWithDateRangeNoPay(Long topicId);

    List<CmBuyLogDO> getCmBuyLogListWithDateRangeNoPay(Long userId,Long topicId);

    /**
     * 根据部门ID获取支付订单ID列表
     * 用于部门数据隔离
     *
     * @param deptId 部门ID
     * @return 支付订单ID列表，null表示不过滤（超级管理员）
     */
    List<Long> getPayOrderIdsByDeptId(Long deptId);
}
