package cn.metast.tuoke.module.product.service.livegoods;

import java.util.*;

import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsPageReqVO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsSaveReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.livegoods.LiveGoodsDO;
import jakarta.validation.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 直播商品列 Service 接口
 *
 * @author admin
 */
public interface LiveGoodsService {

    /**
     * 创建直播商品列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLiveGoods(@Valid LiveGoodsSaveReqVO createReqVO);

    /**
     * 更新直播商品列
     *
     * @param updateReqVO 更新信息
     */
    int updateLiveGoods(@Valid LiveGoodsSaveReqVO updateReqVO);

    /**
     * 删除直播商品列
     *
     * @param id 编号
     */
    void deleteLiveGoods(Long id);
    int deleteByGoodsId(Long id);

    /**
     * 获得直播商品列
     *
     * @param id 编号
     * @return 直播商品列
     */
    LiveGoodsDO getLiveGoods(Long id);
    LiveGoodsDO getLiveGoods(Long id, Long uid);
    LiveGoodsDO getLiveGoodsByGoodsId(Long goodsId);

    /**
     * 获得直播商品列分页
     *
     * @param pageReqVO 分页查询
     * @return 直播商品列分页
     */
    PageResult<LiveGoodsDO> getLiveGoodsPage(LiveGoodsPageReqVO pageReqVO);

    int setSpusExplainStatus();
    int setSpuExplainStatus(Long id, int jjStatus);
    int updateLiveGoodsSort(Long goodsId, int sort);

}
