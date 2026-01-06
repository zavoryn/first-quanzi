package cn.metast.tuoke.module.live.dal.mysql.snsActguest;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActguest.SnsActGuestDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActguest.vo.*;

/**
 * 活动嘉宾 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActGuestMapper extends BaseMapperX<SnsActGuestDO> {

    default PageResult<SnsActGuestDO> selectPage(SnsActGuestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActGuestDO>()
                .eqIfPresent(SnsActGuestDO::getActId, reqVO.getActId())
                .likeIfPresent(SnsActGuestDO::getName, reqVO.getName())
                .eqIfPresent(SnsActGuestDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(SnsActGuestDO::getIntroduce, reqVO.getIntroduce())
                .betweenIfPresent(SnsActGuestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActGuestDO::getId));
    }

}