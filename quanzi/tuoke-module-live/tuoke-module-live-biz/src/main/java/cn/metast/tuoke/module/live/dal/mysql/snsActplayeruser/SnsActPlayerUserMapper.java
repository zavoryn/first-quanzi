package cn.metast.tuoke.module.live.dal.mysql.snsActplayeruser;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActplayeruser.SnsActPlayerUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.*;

/**
 * 参与人信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActPlayerUserMapper extends BaseMapperX<SnsActPlayerUserDO> {

    int insertSnsActPlayerUserList(List<SnsActPlayerUserRespVO> snsActPlayerUsers);
    List<SnsActPlayerUserRespVO> selectSnsActPlayerUserList(SnsActPlayerUserRespVO snsActPlayerUser);
    default PageResult<SnsActPlayerUserDO> selectPage(SnsActPlayerUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActPlayerUserDO>()
                .eqIfPresent(SnsActPlayerUserDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActPlayerUserDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsActPlayerUserDO::getFieldValue, reqVO.getFieldValue())
                .eqIfPresent(SnsActPlayerUserDO::getActUserId, reqVO.getActUserId())
                .betweenIfPresent(SnsActPlayerUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActPlayerUserDO::getId));
    }

}
