package cn.metast.tuoke.module.live.dal.mysql.snsAct;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsAct.SnsActDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 活动详情 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActMapper extends BaseMapperX<SnsActDO> {

    IPage<SnsActDO> selectSnsActList(IPage<SnsActDO> page, @Param("reqvo") SnsActPageReqVO reqvo);
    public SnsActDO selectSnsActById(@Param("id") Long id, @Param("userId")Long userId);
    public int deleteSnsActById(Long id);
    public int updateSnsAct(SnsActSaveReqVO snsAct);
    public int insertSnsAct(SnsActSaveReqVO snsAct);
    public SnsActDO selectSnsActByActId(Long id);
    default PageResult<SnsActDO> selectPage(SnsActPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActDO>()
                .eqIfPresent(SnsActDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsActDO::getActType, reqVO.getActType())
                .eqIfPresent(SnsActDO::getActForm, reqVO.getActForm())
                .eqIfPresent(SnsActDO::getFee, reqVO.getFee())
                .eqIfPresent(SnsActDO::getLongi, reqVO.getLongi())
                .eqIfPresent(SnsActDO::getLati, reqVO.getLati())
                .eqIfPresent(SnsActDO::getLocation, reqVO.getLocation())
                /*.betweenIfPresent(SnsActDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(SnsActDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(SnsActDO::getEndDate, reqVO.getEndDate())
                .betweenIfPresent(SnsActDO::getEndTime, reqVO.getEndTime())*/
                .eqIfPresent(SnsActDO::getRegSetting, reqVO.getRegSetting())
                .eqIfPresent(SnsActDO::getContactphone, reqVO.getContactphone())
                .eqIfPresent(SnsActDO::getSignNum, reqVO.getSignNum())
                .eqIfPresent(SnsActDO::getCreateUserId, reqVO.getCreateUserId())
                .likeIfPresent(SnsActDO::getCreateUserName, reqVO.getCreateUserName())
                .eqIfPresent(SnsActDO::getMStatus, reqVO.getMStatus())
                .eqIfPresent(SnsActDO::getQrcode, reqVO.getQrcode())
                .eqIfPresent(SnsActDO::getCheckFlag, reqVO.getCheckFlag())
               /* .betweenIfPresent(SnsActDO::getAppyStartDate, reqVO.getAppyStartDate())
                .betweenIfPresent(SnsActDO::getAppyEndDate, reqVO.getAppyEndDate())*/
                .eqIfPresent(SnsActDO::getSort, reqVO.getSort())
                .eqIfPresent(SnsActDO::getMemberLimit, reqVO.getMemberLimit())
                .eqIfPresent(SnsActDO::getJoinLimit, reqVO.getJoinLimit())
                .eqIfPresent(SnsActDO::getFeeLimit, reqVO.getFeeLimit())
                .eqIfPresent(SnsActDO::getJoinMemberView, reqVO.getJoinMemberView())
                .eqIfPresent(SnsActDO::getBtnView, reqVO.getBtnView())
                .eqIfPresent(SnsActDO::getShareCfg, reqVO.getShareCfg())
                .eqIfPresent(SnsActDO::getAudtCfg, reqVO.getAudtCfg())
                .eqIfPresent(SnsActDO::getActPut, reqVO.getActPut())
                .eqIfPresent(SnsActDO::getIsRecom, reqVO.getIsRecom())
                .eqIfPresent(SnsActDO::getCrowdQr, reqVO.getCrowdQr())
                .betweenIfPresent(SnsActDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActDO::getId));
    }

}
