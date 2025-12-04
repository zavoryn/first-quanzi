package cn.metast.tuoke.module.heal.dal.mysql.healBomeconfig;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig.HealHomeConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo.*;

/**
 * 首页模块配置 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface HealHomeConfigMapper extends BaseMapperX<HealHomeConfigDO> {

    default PageResult<HealHomeConfigDO> selectPage(HealHomeConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HealHomeConfigDO>()
                .likeIfPresent(HealHomeConfigDO::getName, reqVO.getName())
                .eqIfPresent(HealHomeConfigDO::getIcon, reqVO.getIcon())
                .eqIfPresent(HealHomeConfigDO::getLinkUrl, reqVO.getLinkUrl())
                .eqIfPresent(HealHomeConfigDO::getSort, reqVO.getSort())
                .eqIfPresent(HealHomeConfigDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HealHomeConfigDO::getRemark, reqVO.getRemark())
                .eqIfPresent(HealHomeConfigDO::getParam, reqVO.getParam())
                .betweenIfPresent(HealHomeConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HealHomeConfigDO::getSort));
    }

}
