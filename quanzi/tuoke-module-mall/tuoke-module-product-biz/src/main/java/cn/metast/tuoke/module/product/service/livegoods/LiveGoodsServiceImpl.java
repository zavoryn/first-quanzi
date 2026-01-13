package cn.metast.tuoke.module.product.service.livegoods;

import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.tenant.core.aop.TenantIgnore;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsPageReqVO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsSaveReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.livegoods.LiveGoodsDO;
import cn.metast.tuoke.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.metast.tuoke.module.product.dal.mysql.livegoods.LiveGoodsMapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;


import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.product.enums.ErrorCodeConstants.LIVE_GOODS_NOT_EXISTS;

/**
 * 直播商品列 Service 实现类
 *
 * @author admin
 */
@Service
@Validated
public class LiveGoodsServiceImpl implements LiveGoodsService {

    @Resource
    private LiveGoodsMapper liveGoodsMapper;

    @Override
    public Long createLiveGoods(LiveGoodsSaveReqVO createReqVO) {
        // 插入
        LiveGoodsDO liveGoods = BeanUtils.toBean(createReqVO, LiveGoodsDO.class);
        liveGoodsMapper.insert(liveGoods);
        // 返回
        return liveGoods.getId();
    }

    @Override
    public int updateLiveGoods(LiveGoodsSaveReqVO updateReqVO) {
        // 校验存在
        validateLiveGoodsExists(updateReqVO.getId());
        // 更新
        LiveGoodsDO updateObj = BeanUtils.toBean(updateReqVO, LiveGoodsDO.class);
        return liveGoodsMapper.updateById(updateObj);
    }

    @Override
    public void deleteLiveGoods(Long id) {
        // 校验存在
        validateLiveGoodsExists(id);
        // 删除
        liveGoodsMapper.deleteById(id);
    }

    @Override
    public int deleteByGoodsId(Long id) {
        // 删除
        return liveGoodsMapper.deleteByGoodsId(id);
    }

    private void validateLiveGoodsExists(Long id) {
        if (liveGoodsMapper.selectById(id) == null) {
            throw exception(LIVE_GOODS_NOT_EXISTS);
        }
    }

    @Override
    public LiveGoodsDO getLiveGoods(Long id) {
        return liveGoodsMapper.selectById(id);
    }

    @Override
    public LiveGoodsDO getLiveGoods(Long id, Long uid) {
        return liveGoodsMapper.selectByIdAndUid(id, uid);
    }

    @Override
    public LiveGoodsDO getLiveGoodsByGoodsId(Long goodsId) {
        LambdaQueryWrapperX<LiveGoodsDO> queryWrapperX = new LambdaQueryWrapperX<LiveGoodsDO>()
                .eq(LiveGoodsDO::getGoodsId, goodsId);
        return liveGoodsMapper.selectOne(queryWrapperX);
    }

    @Override
    public PageResult<LiveGoodsDO> getLiveGoodsPage(LiveGoodsPageReqVO pageReqVO) {
        return liveGoodsMapper.selectPage(pageReqVO);
    }

    @Override
    @TenantIgnore
    public int setSpusExplainStatus(){
        LambdaUpdateWrapper<LiveGoodsDO> updateWrapper = new LambdaUpdateWrapper<LiveGoodsDO>()
                .setSql(" if_explain = 0 " );
        int i = liveGoodsMapper.update(null, updateWrapper);
        return i;
    }
    @Override
    @TenantIgnore
    public int setSpuExplainStatus(Long id, int jjStatus){
        LambdaUpdateWrapper<LiveGoodsDO> updateWWrapper = new LambdaUpdateWrapper<LiveGoodsDO>()
                .eq(LiveGoodsDO::getGoodsId, id)
                .set(LiveGoodsDO::getIfExplain, jjStatus);
        int i = liveGoodsMapper.update(updateWWrapper);
        return i;
    }
    @Override
    @TenantIgnore
    public int updateLiveGoodsSort(Long goodsId, int sort){
        LambdaUpdateWrapper<LiveGoodsDO> updateWWrapper = new LambdaUpdateWrapper<LiveGoodsDO>()
                .eq(LiveGoodsDO::getGoodsId, goodsId)
                .set(LiveGoodsDO::getSort, sort);
        int i = liveGoodsMapper.update(updateWWrapper);
        return i;
    }

}
