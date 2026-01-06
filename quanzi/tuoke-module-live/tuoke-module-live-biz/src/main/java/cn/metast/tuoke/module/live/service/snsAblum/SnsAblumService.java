package cn.metast.tuoke.module.live.service.snsAblum;

import java.util.*;

import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 相册信息 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsAblumService {

    /**
     * 创建相册信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsAblum(@Valid SnsAblumSaveReqVO createReqVO);


    Boolean insertSnsAblum(SnsPostRespVO snsPost);
    /**
     * 更新相册信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsAblum(@Valid SnsAblumSaveReqVO updateReqVO);

    /**
     * 删除相册信息
     *
     * @param id 编号
     */
    void deleteSnsAblum(Long id);

    /**
     * 获得相册信息
     *
     * @param id 编号
     * @return 相册信息
     */
    SnsAblumDO getSnsAblum(Long id);

    /**
     * 获得相册信息分页
     *
     * @param pageReqVO 分页查询
     * @return 相册信息分页
     */
    PageResult<SnsAblumDO> getSnsAblumPage(SnsAblumPageReqVO pageReqVO);

    /**
     * 删除相册信息信息
     *
     * @param id 相册信息ID
     * @return 结果
     */
    public int deleteSnsAblumById(Long id);

    public Boolean insertSnsAblumList(SnsPostSaveReqVO snsPost);

    List<SnsAblumDO> selectSnsAblumPostId(Long postId);

}
