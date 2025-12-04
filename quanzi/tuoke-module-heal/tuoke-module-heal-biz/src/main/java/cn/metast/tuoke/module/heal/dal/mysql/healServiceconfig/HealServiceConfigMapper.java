package cn.metast.tuoke.module.heal.dal.mysql.healServiceconfig;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.healServiceconfig.HealServiceConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo.*;

/**
 * 服务配置 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface HealServiceConfigMapper extends BaseMapperX<HealServiceConfigDO> {

    default PageResult<HealServiceConfigDO> selectPage(HealServiceConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HealServiceConfigDO>()
                .likeIfPresent(HealServiceConfigDO::getName, reqVO.getName())
                .eqIfPresent(HealServiceConfigDO::getType, reqVO.getType())
                .eqIfPresent(HealServiceConfigDO::getParam, reqVO.getParam())
                .betweenIfPresent(HealServiceConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HealServiceConfigDO::getId));
    }

}