package cn.metast.tuoke.module.live.dal.mysql.snsAppconfig;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsAppconfig.SnsAppConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo.*;

/**
 * 配置 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsAppConfigMapper extends BaseMapperX<SnsAppConfigDO> {

    default PageResult<SnsAppConfigDO> selectPage(SnsAppConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsAppConfigDO>()
                .likeIfPresent(SnsAppConfigDO::getCfgName, reqVO.getCfgName())
                .eqIfPresent(SnsAppConfigDO::getCfgValue, reqVO.getCfgValue())
                .eqIfPresent(SnsAppConfigDO::getCfgRemark, reqVO.getCfgRemark())
                .betweenIfPresent(SnsAppConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsAppConfigDO::getId));
    }

}