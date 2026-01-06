package cn.metast.tuoke.module.live.dal.mysql.snsActuser;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.SnsActRespVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsActuser.SnsActUserDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 活动报名人员 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActUserMapper extends BaseMapperX<SnsActUserDO> {

    public int selectActUserCount(SnsActUserRespVO snsActUser);

    public List<SnsActUserRespVO> selectSnsActUserAppList(SnsActUserRespVO snsActUser);

    public int deleteSnsActUserActById(Long id);

    public List<SnsActUserRespVO> selectSnsActUserNewList(Long id);

    IPage<SnsActUserRespVO> selectSnsActUserList(IPage<SnsActUserRespVO> page,@Param("reqvo") SnsActUserPageReqVO reqvo);

    int insertSnsActUser(SnsActUserRespVO snsActUser);

    default PageResult<SnsActUserDO> selectPage(SnsActUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActUserDO>()
                .eqIfPresent(SnsActUserDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsActUserDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActUserDO::getPlanFee, reqVO.getPlanFee())
                .eqIfPresent(SnsActUserDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SnsActUserDO::getRealFee, reqVO.getRealFee())
                .eqIfPresent(SnsActUserDO::getOrderNo, reqVO.getOrderNo())
                .betweenIfPresent(SnsActUserDO::getFeeTime, reqVO.getFeeTime())
                .likeIfPresent(SnsActUserDO::getSName, reqVO.getSName())
                .eqIfPresent(SnsActUserDO::getSPhone, reqVO.getSPhone())
                .eqIfPresent(SnsActUserDO::getSAge, reqVO.getSAge())
                .eqIfPresent(SnsActUserDO::getSGender, reqVO.getSGender())
                .eqIfPresent(SnsActUserDO::getSUnit, reqVO.getSUnit())
                .eqIfPresent(SnsActUserDO::getSWx, reqVO.getSWx())
                .eqIfPresent(SnsActUserDO::getSEmail, reqVO.getSEmail())
                .betweenIfPresent(SnsActUserDO::getPlanArriveTime, reqVO.getPlanArriveTime())
                .betweenIfPresent(SnsActUserDO::getRegisterTime, reqVO.getRegisterTime())
                .eqIfPresent(SnsActUserDO::getMileage, reqVO.getMileage())
                .eqIfPresent(SnsActUserDO::getRemark, reqVO.getRemark())
                .orderByDesc(SnsActUserDO::getId));
    }

}
