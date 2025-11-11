package cn.metast.tuoke.module.community.service.cmPost;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 用户发帖详情 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmPostService {

    /**
     * 创建用户发帖详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmPost(@Valid CmPostSaveReqVO createReqVO);

    /**
     * 更新用户发帖详情
     *
     * @param updateReqVO 更新信息
     */
    void updateCmPost(@Valid CmPostSaveReqVO updateReqVO);

    /**
     * 删除用户发帖详情
     *
     * @param id 编号
     */
    void deleteCmPost(Long id);

    /**
     * 获得用户发帖详情
     *
     * @param id 编号
     * @return 用户发帖详情
     */
    CmPostDO getCmPost(Long id);

    /**
     * 获得用户发帖详情分页
     *
     * @param pageReqVO 分页查询
     * @return 用户发帖详情分页
     */
    PageResult<CmPostDO> getCmPostPage(CmPostPageReqVO pageReqVO);

    PageResult<CmPostDO> getCmPostMediaPage(CmPostPageReqVO pageReqVO);

    PageResult<CmPostDO> getCmPostPageNew(CmPostPageReqVO pageReqVO);

    PageResult<CmPostDO> getCmPostPageTop(CmPostPageReqVO pageReqVO);
    /**
     * 我收藏的帖子
     * @param pageReqVO
     */
    PageResult<CmPostDO> getCmPostCollectPage(CmPostPageReqVO pageReqVO);
    /**
     * 我点赞的帖子
     * @param pageReqVO
     */
    PageResult<CmPostDO> getCmPostLikePage(CmPostPageReqVO pageReqVO);


    //发送服务号消息
    void sendWeixinMsg(CmPostSaveReqVO createReqVO);
}
