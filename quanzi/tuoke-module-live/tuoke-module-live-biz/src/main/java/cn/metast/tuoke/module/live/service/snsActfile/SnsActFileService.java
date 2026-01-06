package cn.metast.tuoke.module.live.service.snsActfile;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActfile.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActfile.SnsActFileDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动资料 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActFileService {

    /**
     * 创建活动资料
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActFile(@Valid SnsActFileSaveReqVO createReqVO);

    /**
     * 更新活动资料
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActFile(@Valid SnsActFileSaveReqVO updateReqVO);

    /**
     * 删除活动资料
     *
     * @param id 编号
     */
    void deleteSnsActFile(Long id);

    /**
     * 获得活动资料
     *
     * @param id 编号
     * @return 活动资料
     */
    SnsActFileDO getSnsActFile(Long id);

    /**
     * 获得活动资料分页
     *
     * @param pageReqVO 分页查询
     * @return 活动资料分页
     */
    PageResult<SnsActFileDO> getSnsActFilePage(SnsActFilePageReqVO pageReqVO);

}