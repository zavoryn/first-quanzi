package cn.metast.tuoke.module.live.service.snsPost;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsPost.SnsPostDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 动态信息 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsPostService {

    /**
     * 创建动态信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsPost(@Valid SnsPostSaveReqVO createReqVO);

    /**
     * 更新动态信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsPost(@Valid SnsPostSaveReqVO updateReqVO);

    /**
     * 删除动态信息
     *
     * @param id 编号
     */
    void deleteSnsPost(Long id);

    /**
     * 获得动态信息
     *
     * @param id 编号
     * @return 动态信息
     */
    SnsPostDO getSnsPost(Long id);

    /**
     * 获得动态信息分页
     *
     * @param pageReqVO 分页查询
     * @return 动态信息分页
     */
    PageResult<SnsPostDO> getSnsPostPage(SnsPostPageReqVO pageReqVO);

    public int updateSnsPostPc(SnsPostSaveReqVO snsPost);

    public int deleteSnsPostById(Long postId);

    PageResult<SnsPostSaveReqVO>selectSnsNewsListApp(SnsPostPageReqVO snsPost);

    public SnsPostSaveReqVO selectSnsPostById(Long postId);
    int insertSnsPost(SnsPostRespVO snsPost);
}
