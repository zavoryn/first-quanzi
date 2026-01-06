package cn.metast.tuoke.module.live.dal.mysql.snsAblum;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.*;

/**
 * 相册信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsAblumMapper extends BaseMapperX<SnsAblumDO> {

    public int deleteSnsAblumById(Long postId);
    public int deleteSnsAblumByPostId(Long postId);
    List<SnsAblumDO>  selectSnsAblumPostId(Long postId);

    public List<SnsAblumDO> selectSnsAblumList(SnsAblumDO snsAblum);

    default PageResult<SnsAblumDO> selectPage(SnsAblumPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsAblumDO>()
                .eqIfPresent(SnsAblumDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsAblumDO::getUrl, reqVO.getUrl())
                .eqIfPresent(SnsAblumDO::getIdentifier, reqVO.getIdentifier())
                .eqIfPresent(SnsAblumDO::getType, reqVO.getType())
                .eqIfPresent(SnsAblumDO::getUserId, reqVO.getUserId())
                .likeIfPresent(SnsAblumDO::getUserName, reqVO.getUserName())
                .betweenIfPresent(SnsAblumDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsAblumDO::getId));
    }

}
