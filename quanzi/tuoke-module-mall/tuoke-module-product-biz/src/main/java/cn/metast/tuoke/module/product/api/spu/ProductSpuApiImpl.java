package cn.metast.tuoke.module.product.api.spu;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.product.api.spu.dto.LiveShopGoodsRespDTO;
import cn.metast.tuoke.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsPageReqVO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsSaveReqVO;
import cn.metast.tuoke.module.product.controller.admin.spu.vo.ProductSpuPageReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.livegoods.LiveGoodsDO;
import cn.metast.tuoke.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.metast.tuoke.module.product.service.livegoods.LiveGoodsService;
import cn.metast.tuoke.module.product.service.spu.ProductSpuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static cn.metast.tuoke.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * 商品 SPU API 接口实现类
 *
 * @author LeeYan9
 * @since 2022-09-06
 */
@Service
@Validated
public class ProductSpuApiImpl implements ProductSpuApi {

    @Resource
    private ProductSpuService spuService;

    @Resource
    private LiveGoodsService liveGoodsService;

    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public List<ProductSpuRespDTO> getSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.getSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public List<ProductSpuRespDTO> getSpuPage(Integer pageIndex, Integer pageSize) {
        ProductSpuPageReqVO pageReqVO = new ProductSpuPageReqVO();
        pageReqVO.setPageNo(pageIndex);
        pageReqVO.setPageSize(pageSize);
        PageResult<ProductSpuDO> spuPage = spuService.getSpuPage(pageReqVO);
        return BeanUtils.toBean(spuPage.getList(), ProductSpuRespDTO.class);
    }

    @Override
    public List<LiveShopGoodsRespDTO> getLiveShopGoodsPage(Integer pageIndex, Integer pageSize, Long uid) {
        LiveGoodsPageReqVO pageReqVO = new LiveGoodsPageReqVO();
        pageReqVO.setPageNo(pageIndex);
        pageReqVO.setPageSize(pageSize);
        pageReqVO.setAnchorId(uid);
        PageResult<LiveGoodsDO> liveGoodsPage = liveGoodsService.getLiveGoodsPage(pageReqVO);
        return BeanUtils.toBean(liveGoodsPage.getList(), LiveShopGoodsRespDTO.class);
    }

    @Override
    public List<ProductSpuRespDTO> validateSpuList(Collection<Long> ids) {
        List<ProductSpuDO> spus = spuService.validateSpuList(ids);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public ProductSpuRespDTO getSpu(Long id) {
        ProductSpuDO spu = spuService.getSpu(id);
        return BeanUtils.toBean(spu, ProductSpuRespDTO.class);
    }

    @Override
    public LiveShopGoodsRespDTO getLiveGoodsByGoodsId(Long goodsId) {
        LiveGoodsDO liveGoods = liveGoodsService.getLiveGoodsByGoodsId(goodsId);
        return BeanUtils.toBean(liveGoods, LiveShopGoodsRespDTO.class);
    }

    /**
     * 添加直播间商品
     *
     * @param goodsId  商品id
     * @param sort     排序
     * @param optType  操作类型 1：增加 2：删除
     *
     */
    @Override
    public int saveLiveGoods(long goodsId, int sort, int optType){

        Long loginUserId = getLoginUserId();

        int i = 0;

        if (optType == 1) {
            ProductSpuDO spu = spuService.getSpu(goodsId);
            LiveGoodsDO liveGoods = liveGoodsService.getLiveGoods(goodsId, loginUserId);
            LiveGoodsSaveReqVO saveReqVO = new LiveGoodsSaveReqVO();
            if(liveGoods == null){
                saveReqVO.setAnchorId(loginUserId);
                saveReqVO.setGoodsId(goodsId);
                saveReqVO.setChannelId(1L);
                saveReqVO.setFavorablePrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
                saveReqVO.setGoodsPrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
                saveReqVO.setGoodsPicture(spu.getPicUrl());
                saveReqVO.setName(spu.getName());
                Long liveGoodsId = liveGoodsService.createLiveGoods(saveReqVO);
                if(liveGoodsId.intValue() > 0){
                    i = 1;
                }
            }else{
                saveReqVO.setAnchorId(loginUserId);
                saveReqVO.setId(liveGoods.getId());
                saveReqVO.setGoodsId(goodsId);
                saveReqVO.setChannelId(1L);
                saveReqVO.setFavorablePrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
                saveReqVO.setGoodsPrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
                saveReqVO.setGoodsPicture(spu.getPicUrl());
                saveReqVO.setName(spu.getName());
                i = liveGoodsService.updateLiveGoods(saveReqVO);
            }

        }else{
            i = liveGoodsService.deleteByGoodsId(goodsId);
        }

        return i;
    }

    @Override
    public int setExplainStatus(long id){

        int i = 0;

        // 将所有商品设置为未讲解
//        i = spuService.setSpusExplainStatus();
        i = liveGoodsService.setSpusExplainStatus();

        // 切换目标商品的讲解状态
//        i = spuService.setSpuExplainStatus(id, 1);
        i = liveGoodsService.setSpuExplainStatus(id, 1);

        return 1;
    }

    @Override
    public int updateLiveGoodsSort(long goodsId, int sort){

        int i = 0;
        LiveGoodsDO liveGoodsByGoodsId = liveGoodsService.getLiveGoodsByGoodsId(goodsId);
        if (liveGoodsByGoodsId != null){
            i = liveGoodsService.updateLiveGoodsSort(goodsId, sort);
            if(i != 1){
                i = -1;
            }
        }

        return i;
    }

    @Override
    public ProductSpuRespDTO getSpuByName(String name) {
        ProductSpuDO spus = spuService.getSpuByName(name);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

    @Override
    public ProductSpuRespDTO getResourceId(String resourceId) {
        ProductSpuDO spus = spuService.getResourceId(resourceId);
        return BeanUtils.toBean(spus, ProductSpuRespDTO.class);
    }

}
