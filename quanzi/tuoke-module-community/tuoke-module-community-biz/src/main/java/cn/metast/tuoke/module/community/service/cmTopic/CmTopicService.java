package cn.metast.tuoke.module.community.service.cmTopic;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 圈子详情 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmTopicService {

    /**
     * 创建圈子详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmTopic(@Valid CmTopicSaveReqVO createReqVO);
    /**
     * 更新圈子详情
     *
     * @param updateReqVO 更新信息
     */
    void updateCmTopic(@Valid CmTopicSaveReqVO updateReqVO);

    /**
     * 删除圈子详情
     *
     * @param id 编号
     */
    void deleteCmTopic(Long id);

    /**
     * 获得圈子详情
     *
     * @param id 编号
     * @return 圈子详情
     */
    CmTopicDO getCmTopic(Long id);

    /**
     * 获得圈子详情分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子详情分页
     */
    PageResult<CmTopicDO> getCmTopicPage(CmTopicPageReqVO pageReqVO);

    /**
     * 查询我加入的和我创建的星球
     */
    Map<String, Object> getTopicList(CmTopicRespVO cmTopicRespVO);

    /**
     * 判断圈子是否签署
     *
     * @param topicId 圈子编号
     * @return 圈子详情
     */
    Map<String,Object> paytypeoy(Long topicId,Long type);
    /**
     * 生成小程序二维码
     *
     * @param topicId 编号
     * @return 圈子详情列表
     */
    String getQrdUrl(Long topicId);

    /**
     * 生成小程序二维码链接
     *
     * @param topicId 编号
     * @return 圈子详情列表
     */
    String getShortUrl(Long topicId);

    /**
     * 生成小程序h5链接
     *
     * @param topicId 编号
     * @return 圈子详情列表
     */
    String getLinkUrl(Long topicId);
}

