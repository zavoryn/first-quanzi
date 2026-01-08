package cn.metast.tuoke.module.product.api.spu;

import cn.metast.tuoke.module.product.api.spu.dto.LiveShopGoodsRespDTO;
import cn.metast.tuoke.module.product.api.spu.dto.ProductSpuRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 商品 SPU API 接口
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface ProductSpuApi {

    /**
     * 批量查询 SPU 数组
     *
     * @param ids SPU 编号列表
     * @return SPU 数组
     */
    List<ProductSpuRespDTO> getSpuList(Collection<Long> ids);
    List<ProductSpuRespDTO> getSpuPage(Integer pageIndex, Integer pageSize);
    List<LiveShopGoodsRespDTO> getLiveShopGoodsPage(Integer pageIndex, Integer pageSize, Long uid);

    /**
     * 批量查询 SPU MAP
     *
     * @param ids SPU 编号列表
     * @return SPU MAP
     */
    default Map<Long, ProductSpuRespDTO> getSpusMap(Collection<Long> ids) {
        return convertMap(getSpuList(ids), ProductSpuRespDTO::getId);
    }

    /**
     * 批量查询 SPU 数组，并且校验是否 SPU 是否有效。
     *
     * 如下情况，视为无效：
     * 1. 商品编号不存在
     * 2. 商品被禁用
     *
     * @param ids SPU 编号列表
     * @return SPU 数组
     */
    List<ProductSpuRespDTO> validateSpuList(Collection<Long> ids);

    /**
     * 获得 SPU
     *
     * @return SPU
     */
    ProductSpuRespDTO getSpu(Long id);
    LiveShopGoodsRespDTO getLiveGoodsByGoodsId(Long id);

    int saveLiveGoods(long goodsId, int sort, int optType);

    int setExplainStatus(long id);

    int updateLiveGoodsSort(long goodsId, int sort);

    ProductSpuRespDTO getSpuByName(String name);
    ProductSpuRespDTO getResourceId(String resourceId);
}
