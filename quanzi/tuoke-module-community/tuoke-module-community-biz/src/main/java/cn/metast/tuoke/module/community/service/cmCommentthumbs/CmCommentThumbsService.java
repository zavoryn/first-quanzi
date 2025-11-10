package cn.metast.tuoke.module.community.service.cmCommentthumbs;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentthumbs.CmCommentThumbsDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论用户关联 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmCommentThumbsService {

    /**
     * 创建评论用户关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmCommentThumbs(@Valid CmCommentThumbsSaveReqVO createReqVO);

    /**
     * 更新评论用户关联
     *
     * @param updateReqVO 更新信息
     */
    void updateCmCommentThumbs(@Valid CmCommentThumbsSaveReqVO updateReqVO);

    /**
     * 删除评论用户关联
     *
     * @param id 编号
     */
    void deleteCmCommentThumbs(Long id);

    /**
     * 获得评论用户关联
     *
     * @param id 编号
     * @return 评论用户关联
     */
    CmCommentThumbsDO getCmCommentThumbs(Long id);

    /**
     * 获得评论用户关联分页
     *
     * @param pageReqVO 分页查询
     * @return 评论用户关联分页
     */
    PageResult<CmCommentThumbsDO> getCmCommentThumbsPage(CmCommentThumbsPageReqVO pageReqVO);

}