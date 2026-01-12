package cn.metast.tuoke.module.product.dal.mysql.livegoods;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsPageReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.livegoods.LiveGoodsDO;
import cn.metast.tuoke.module.product.dal.dataobject.spu.ProductSpuDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 直播商品列 Mapper
 *
 * @author admin
 */
@Mapper
public interface LiveGoodsMapper extends BaseMapperX<LiveGoodsDO> {

    default PageResult<LiveGoodsDO> selectPage(LiveGoodsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LiveGoodsDO>()
                .eqIfPresent(LiveGoodsDO::getAnchorId, reqVO.getAnchorId())
                .eqIfPresent(LiveGoodsDO::getChannelId, reqVO.getChannelId())
                .eqIfPresent(LiveGoodsDO::getFavorablePrice, reqVO.getFavorablePrice())
                .eqIfPresent(LiveGoodsDO::getGoodsId, reqVO.getGoodsId())
                .eqIfPresent(LiveGoodsDO::getGoodsPicture, reqVO.getGoodsPicture())
                .eqIfPresent(LiveGoodsDO::getGoodsPrice, reqVO.getGoodsPrice())
                .eqIfPresent(LiveGoodsDO::getIfExplain, reqVO.getIfExplain())
                .likeIfPresent(LiveGoodsDO::getName, reqVO.getName())
                .eqIfPresent(LiveGoodsDO::getProductLinks, reqVO.getProductLinks())
                .eqIfPresent(LiveGoodsDO::getSort, reqVO.getSort())
                .betweenIfPresent(LiveGoodsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(LiveGoodsDO::getId));
    }


    default LiveGoodsDO selectByIdAndUid(Long id, Long uid) {
        LambdaQueryWrapperX<LiveGoodsDO> queryWrapperX = new LambdaQueryWrapperX<LiveGoodsDO>()
                .eq(LiveGoodsDO::getAnchorId, uid)
                .eq(LiveGoodsDO::getGoodsId, id);
        return selectOne(queryWrapperX);
    }


    default int deleteByGoodsId(Long id) {
        LambdaUpdateWrapper<LiveGoodsDO> delWrapper = new LambdaUpdateWrapper<LiveGoodsDO>()
                .eq(LiveGoodsDO::getGoodsId, id);
        return delete(null, delWrapper);
    }

}
