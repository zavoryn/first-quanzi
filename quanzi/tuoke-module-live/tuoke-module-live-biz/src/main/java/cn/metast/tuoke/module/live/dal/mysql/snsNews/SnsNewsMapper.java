package cn.metast.tuoke.module.live.dal.mysql.snsNews;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.SnsAblumRespVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsNews.SnsNewsDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 新闻信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsNewsMapper extends BaseMapperX<SnsNewsDO> {

    IPage<SnsNewsRespVO> selectSnsNewsLists(IPage<SnsNewsRespVO> page, @Param("reqVO") SnsNewsPageReqVO reqVO);
    SnsNewsRespVO selectSnsNewsById(Long newId);
    int updateSnsNewsId(SnsNewsSaveReqVO snsNewsSaveReqVO);
    public int deleteSnsNewsByIds(Long newsId);
    default PageResult<SnsNewsDO> selectPage(SnsNewsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsNewsDO>()
                .eqIfPresent(SnsNewsDO::getNewsId, reqVO.getNewsId())
                .eqIfPresent(SnsNewsDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsNewsDO::getTypeSeting, reqVO.getTypeSeting())
                .eqIfPresent(SnsNewsDO::getNewsConfig, reqVO.getNewsConfig())
                .eqIfPresent(SnsNewsDO::getIsFlag, reqVO.getIsFlag())
                .betweenIfPresent(SnsNewsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsNewsDO::getId));

    }

}
