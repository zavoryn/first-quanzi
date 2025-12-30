package cn.metast.tuoke.module.kaifa.dal.mysql.email.settingemail;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail.SettingEmailDO;
/**
 * 邮件配置 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface SettingEmailMapper extends BaseMapperX<SettingEmailDO> {

    default PageResult<SettingEmailDO> selectPage(SettingEmailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SettingEmailDO>()
                .eqIfPresent(SettingEmailDO::getEmailType, reqVO.getEmailType())
                .eqIfPresent(SettingEmailDO::getEmail, reqVO.getEmail())
                .likeIfPresent(SettingEmailDO::getName, reqVO.getName())
                .eqIfPresent(SettingEmailDO::getPassword, reqVO.getPassword())
                .eqIfPresent(SettingEmailDO::getHost, reqVO.getHost())
                .eqIfPresent(SettingEmailDO::getPort, reqVO.getPort())
                .eqIfPresent(SettingEmailDO::getInSsl, reqVO.getInSsl())
                .eqIfPresent(SettingEmailDO::getOutHost, reqVO.getOutHost())
                .eqIfPresent(SettingEmailDO::getOutPort, reqVO.getOutPort())
                .eqIfPresent(SettingEmailDO::getOutSsl, reqVO.getOutSsl())
                .eqIfPresent(SettingEmailDO::getRemark, reqVO.getRemark())
                .eqIfPresent(SettingEmailDO::getIsYw, reqVO.getIsYw())
                .eqIfPresent(SettingEmailDO::getIsUser, reqVO.getIsUser())
                .eqIfPresent(SettingEmailDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SettingEmailDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SettingEmailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SettingEmailDO::getId));
    }
    default JSONObject checkEmailStatus(SettingEmailRespVO reqVO){
        return null;
    }
}
