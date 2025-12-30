package cn.metast.tuoke.module.kaifa.dal.mysql.email.emailsetting;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailsetting.EmailSettingDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting.vo.*;

/**
 * 公共配置 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface EmailSettingMapper extends BaseMapperX<EmailSettingDO> {

    default PageResult<EmailSettingDO> selectPage(EmailSettingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmailSettingDO>()
                .eqIfPresent(EmailSettingDO::getSendType, reqVO.getSendType())
                .betweenIfPresent(EmailSettingDO::getRegularTime, reqVO.getRegularTime())
                .eqIfPresent(EmailSettingDO::getSendRestDay, reqVO.getSendRestDay())
                .eqIfPresent(EmailSettingDO::getFilterateEmail, reqVO.getFilterateEmail())
                .eqIfPresent(EmailSettingDO::getFirstSend, reqVO.getFirstSend())
                .eqIfPresent(EmailSettingDO::getSingleUpperLimit, reqVO.getSingleUpperLimit())
                .eqIfPresent(EmailSettingDO::getFilterateMyClub, reqVO.getFilterateMyClub())
                .eqIfPresent(EmailSettingDO::getFilterateColleagueClub, reqVO.getFilterateColleagueClub())
                .eqIfPresent(EmailSettingDO::getFilterateMyCustomer, reqVO.getFilterateMyCustomer())
                .eqIfPresent(EmailSettingDO::getFilterateTsCustomer, reqVO.getFilterateTsCustomer())
                .eqIfPresent(EmailSettingDO::getFilteratePublicCustomer, reqVO.getFilteratePublicCustomer())
                .eqIfPresent(EmailSettingDO::getFilterateBeenSent, reqVO.getFilterateBeenSent())
                .eqIfPresent(EmailSettingDO::getScreenCompany, reqVO.getScreenCompany())
                .eqIfPresent(EmailSettingDO::getScreenPositionKp, reqVO.getScreenPositionKp())
                .eqIfPresent(EmailSettingDO::getScreenPositionOther, reqVO.getScreenPositionOther())
                .eqIfPresent(EmailSettingDO::getScreenEmail, reqVO.getScreenEmail())
                .eqIfPresent(EmailSettingDO::getSaveCompany, reqVO.getSaveCompany())
                .eqIfPresent(EmailSettingDO::getSavePositionKp, reqVO.getSavePositionKp())
                .eqIfPresent(EmailSettingDO::getSavePositionOther, reqVO.getSavePositionOther())
                .eqIfPresent(EmailSettingDO::getSaveEmail, reqVO.getSaveEmail())
                .eqIfPresent(EmailSettingDO::getSaveTags, reqVO.getSaveTags())
                .eqIfPresent(EmailSettingDO::getSopNo, reqVO.getSopNo())
                .betweenIfPresent(EmailSettingDO::getPlanTime, reqVO.getPlanTime())
                .eqIfPresent(EmailSettingDO::getPlanNum, reqVO.getPlanNum())
                .eqIfPresent(EmailSettingDO::getPlanDay, reqVO.getPlanDay())
                .betweenIfPresent(EmailSettingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EmailSettingDO::getId));
    }

}
