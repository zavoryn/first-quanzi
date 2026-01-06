package cn.metast.tuoke.module.live.dal.mysql.snsActinfouser;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.SnsActRespVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfouser.SnsActInfoUserDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 报名填写用户信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActInfoUserMapper extends BaseMapperX<SnsActInfoUserDO> {

    int insertSnsActInfoUserList(List<SnsActInfoUserRespVO> actInfoUsers);

    List<SnsActRespVO> selectSnsActInfoUserList(IPage<SnsActInfoUserDO> page, @Param("reqvo") SnsActInfoUserPageReqVO reqvo);

    default PageResult<SnsActInfoUserDO> selectPage(SnsActInfoUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActInfoUserDO>()
                .eqIfPresent(SnsActInfoUserDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActInfoUserDO::getUserId, reqVO.getUserId())
                .likeIfPresent(SnsActInfoUserDO::getFieldName, reqVO.getFieldName())
                .eqIfPresent(SnsActInfoUserDO::getFieldValue, reqVO.getFieldValue())
                .eqIfPresent(SnsActInfoUserDO::getActUserId, reqVO.getActUserId())
                .betweenIfPresent(SnsActInfoUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActInfoUserDO::getId));
    }

}
