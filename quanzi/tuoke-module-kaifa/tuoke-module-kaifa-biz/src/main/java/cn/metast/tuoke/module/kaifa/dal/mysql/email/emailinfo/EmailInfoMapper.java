package cn.metast.tuoke.module.kaifa.dal.mysql.email.emailinfo;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailinfo.EmailInfoDO;
/**
 * 邮件内容 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface EmailInfoMapper extends BaseMapperX<EmailInfoDO> {

    default PageResult<EmailInfoDO> selectPage(EmailInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmailInfoDO>()
                .eqIfPresent(EmailInfoDO::getUserId, reqVO.getUserId())
                .eqIfPresent(EmailInfoDO::getType, reqVO.getType())
                .eqIfPresent(EmailInfoDO::getTitle, reqVO.getTitle())
                .eqIfPresent(EmailInfoDO::getContent, reqVO.getContent())
                .eqIfPresent(EmailInfoDO::getContentText, reqVO.getContentText())
                .eqIfPresent(EmailInfoDO::getFileUrl, reqVO.getFileUrl())
                .likeIfPresent(EmailInfoDO::getSendName, reqVO.getSendName())
                .eqIfPresent(EmailInfoDO::getSendEmail, reqVO.getSendEmail())
                .likeIfPresent(EmailInfoDO::getReceiveName, reqVO.getReceiveName())
                .eqIfPresent(EmailInfoDO::getReceiveEmail, reqVO.getReceiveEmail())
                .eqIfPresent(EmailInfoDO::getCopyEmail, reqVO.getCopyEmail())
                .betweenIfPresent(EmailInfoDO::getSendTime, reqVO.getSendTime())
                .betweenIfPresent(EmailInfoDO::getReceiveTime, reqVO.getReceiveTime())
                .betweenIfPresent(EmailInfoDO::getHandTime, reqVO.getHandTime())
                .eqIfPresent(EmailInfoDO::getHandStatus, reqVO.getHandStatus())
                .eqIfPresent(EmailInfoDO::getIsSee, reqVO.getIsSee())
                .eqIfPresent(EmailInfoDO::getIsTop, reqVO.getIsTop())
                .betweenIfPresent(EmailInfoDO::getTopTime, reqVO.getTopTime())
                .eqIfPresent(EmailInfoDO::getFolder, reqVO.getFolder())
                .eqIfPresent(EmailInfoDO::getSource, reqVO.getSource())
                .betweenIfPresent(EmailInfoDO::getSourceTime, reqVO.getSourceTime())
                .eqIfPresent(EmailInfoDO::getIsClick, reqVO.getIsClick())
                .eqIfPresent(EmailInfoDO::getIsTrack, reqVO.getIsTrack())
                .eqIfPresent(EmailInfoDO::getOpenCount, reqVO.getOpenCount())
                .betweenIfPresent(EmailInfoDO::getOpenTime, reqVO.getOpenTime())
                .eqIfPresent(EmailInfoDO::getOpenCountry, reqVO.getOpenCountry())
                .eqIfPresent(EmailInfoDO::getClickCount, reqVO.getClickCount())
                .betweenIfPresent(EmailInfoDO::getClickTime, reqVO.getClickTime())
                .eqIfPresent(EmailInfoDO::getClickCountry, reqVO.getClickCountry())
                .eqIfPresent(EmailInfoDO::getIsRepaly, reqVO.getIsRepaly())
                .eqIfPresent(EmailInfoDO::getReplayContent, reqVO.getReplayContent())
                .eqIfPresent(EmailInfoDO::getReplayId, reqVO.getReplayId())
                .eqIfPresent(EmailInfoDO::getEmailId, reqVO.getEmailId())
                .eqIfPresent(EmailInfoDO::getTags, reqVO.getTags())
                .eqIfPresent(EmailInfoDO::getRemark, reqVO.getRemark())
                .eqIfPresent(EmailInfoDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(EmailInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EmailInfoDO::getId));
    }

}
