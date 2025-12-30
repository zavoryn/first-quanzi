package cn.metast.tuoke.module.kaifa.dal.mysql.email.settingsendpreheat;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingsendpreheat.SettingSendPreheatDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo.*;

/**
 * 邮箱预热 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface SettingSendPreheatMapper extends BaseMapperX<SettingSendPreheatDO> {

    default PageResult<SettingSendPreheatDO> selectPage(SettingSendPreheatPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SettingSendPreheatDO>()
                .eqIfPresent(SettingSendPreheatDO::getType, reqVO.getType())
                .eqIfPresent(SettingSendPreheatDO::getEmailType, reqVO.getEmailType())
                .eqIfPresent(SettingSendPreheatDO::getEmail, reqVO.getEmail())
                .eqIfPresent(SettingSendPreheatDO::getPassword, reqVO.getPassword())
                .eqIfPresent(SettingSendPreheatDO::getHost, reqVO.getHost())
                .eqIfPresent(SettingSendPreheatDO::getPort, reqVO.getPort())
                .eqIfPresent(SettingSendPreheatDO::getInSsl, reqVO.getInSsl())
                .eqIfPresent(SettingSendPreheatDO::getOutHost, reqVO.getOutHost())
                .eqIfPresent(SettingSendPreheatDO::getOutPort, reqVO.getOutPort())
                .eqIfPresent(SettingSendPreheatDO::getOutSsl, reqVO.getOutSsl())
                .eqIfPresent(SettingSendPreheatDO::getDays, reqVO.getDays())
                .eqIfPresent(SettingSendPreheatDO::getDayNum, reqVO.getDayNum())
                .eqIfPresent(SettingSendPreheatDO::getSendNum, reqVO.getSendNum())
                .eqIfPresent(SettingSendPreheatDO::getReplyNum, reqVO.getReplyNum())
                .eqIfPresent(SettingSendPreheatDO::getReplyRatio, reqVO.getReplyRatio())
                .eqIfPresent(SettingSendPreheatDO::getMaxNum, reqVO.getMaxNum())
                .eqIfPresent(SettingSendPreheatDO::getPreheat, reqVO.getPreheat())
                .eqIfPresent(SettingSendPreheatDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SettingSendPreheatDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SettingSendPreheatDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SettingSendPreheatDO::getId));
    }
    Map<String, Object> statPreheat(Long userId);

}
