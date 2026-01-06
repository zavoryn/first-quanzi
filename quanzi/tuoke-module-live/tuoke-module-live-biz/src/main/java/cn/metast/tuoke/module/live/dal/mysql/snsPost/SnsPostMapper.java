package cn.metast.tuoke.module.live.dal.mysql.snsPost;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsPost.SnsPostDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 动态信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsPostMapper extends BaseMapperX<SnsPostDO> {

    public SnsPostSaveReqVO selectSnsPostById(Long postId);

    public int updateSnsPostPc(SnsPostSaveReqVO snsPost);

    public int deleteSnsPostById(Long postId);

    public IPage<SnsPostSaveReqVO> selectSnsNewsListApp(IPage<SnsPostSaveReqVO> page, @Param("reqVO") SnsPostPageReqVO reqVO);


    /**
     * 查询动态信息列表
     *
     * @param snsPost 动态信息
     * @return 动态信息集合
     */
    public List<SnsPostRespVO> selectSnsPostList(SnsPostRespVO snsPost);

    public List<SnsPostRespVO> selectSnsPostListByType(SnsPostRespVO snsPost);
    //相册
    public List<SnsPostRespVO> selectAlbumPostList(SnsPostRespVO snsPost);
    //我的收藏
    public List<SnsPostRespVO> selectFavList(SnsPostRespVO snsPost);
    /**
     * 新闻
     */
    public List<SnsPostRespVO> selectSnsNewsList(SnsPostRespVO snsPost);

    public List<SnsPostRespVO> selectSnsNewsListApp(SnsPostRespVO snsPost);
    /**
     * 新增动态信息
     *
     * @param snsPost 动态信息
     * @return 结果
     */
    public int insertSnsPost(SnsPostRespVO snsPost);

    /**
     * 修改动态信息
     *
     * @param snsPost 动态信息
     * @return 结果
     */
    public int updateSnsPost(SnsPostRespVO snsPost);

    public int updateSnsPostPc(SnsPostRespVO snsPost);
    /**
     * 批量删除动态信息
     *
     * @param postIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSnsPostByIds(Long[] postIds);

    default PageResult<SnsPostDO> selectPage(SnsPostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsPostDO>()
                .eqIfPresent(SnsPostDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsPostDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsPostDO::getPostType, reqVO.getPostType())
                .eqIfPresent(SnsPostDO::getTitle, reqVO.getTitle())
                .eqIfPresent(SnsPostDO::getCoverUrl, reqVO.getCoverUrl())
                .eqIfPresent(SnsPostDO::getTag, reqVO.getTag())
                .eqIfPresent(SnsPostDO::getContentType, reqVO.getContentType())
                .eqIfPresent(SnsPostDO::getContent, reqVO.getContent())
                .eqIfPresent(SnsPostDO::getLinkUrl, reqVO.getLinkUrl())
                .eqIfPresent(SnsPostDO::getShareUrl, reqVO.getShareUrl())
                .eqIfPresent(SnsPostDO::getMediaUrl, reqVO.getMediaUrl())
                .eqIfPresent(SnsPostDO::getFileUrl, reqVO.getFileUrl())
                .eqIfPresent(SnsPostDO::getPicNum, reqVO.getPicNum())
                .eqIfPresent(SnsPostDO::getShareNum, reqVO.getShareNum())
                .eqIfPresent(SnsPostDO::getLikeNum, reqVO.getLikeNum())
                .eqIfPresent(SnsPostDO::getCommentNum, reqVO.getCommentNum())
                .eqIfPresent(SnsPostDO::getVisitNum, reqVO.getVisitNum())
                .eqIfPresent(SnsPostDO::getPrivacy, reqVO.getPrivacy())
                .eqIfPresent(SnsPostDO::getSoureFrom, reqVO.getSoureFrom())
                .eqIfPresent(SnsPostDO::getCommentFlag, reqVO.getCommentFlag())
                .eqIfPresent(SnsPostDO::getPostMap, reqVO.getPostMap())
                .eqIfPresent(SnsPostDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(SnsPostDO::getLatitude, reqVO.getLatitude())
                .eqIfPresent(SnsPostDO::getAddType, reqVO.getAddType())
                .eqIfPresent(SnsPostDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SnsPostDO::getImUserId, reqVO.getImUserId())
                .eqIfPresent(SnsPostDO::getDynaConfig, reqVO.getDynaConfig())
                .eqIfPresent(SnsPostDO::getBoType, reqVO.getBoType())
                .eqIfPresent(SnsPostDO::getBottomType, reqVO.getBottomType())
                .eqIfPresent(SnsPostDO::getSpType, reqVO.getSpType())
                .eqIfPresent(SnsPostDO::getSort, reqVO.getSort())
                .eqIfPresent(SnsPostDO::getPosition, reqVO.getPosition())
                .eqIfPresent(SnsPostDO::getLabel, reqVO.getLabel())
                .eqIfPresent(SnsPostDO::getAddress, reqVO.getAddress())
                .eqIfPresent(SnsPostDO::getIsRecom, reqVO.getIsRecom())
                .eqIfPresent(SnsPostDO::getComent, reqVO.getComent())
                .eqIfPresent(SnsPostDO::getAccessCount, reqVO.getAccessCount())
                .eqIfPresent(SnsPostDO::getSpelevel, reqVO.getSpelevel())
                .eqIfPresent(SnsPostDO::getDslx, reqVO.getDslx())
                .betweenIfPresent(SnsPostDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsPostDO::getId));
    }

}
