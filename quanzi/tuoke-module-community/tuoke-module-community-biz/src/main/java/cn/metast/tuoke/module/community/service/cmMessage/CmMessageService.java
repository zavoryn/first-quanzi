package cn.metast.tuoke.module.community.service.cmMessage;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmMessage.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmMessage.CmMessageDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 圈子消息 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmMessageService {

    /**
     * 创建圈子消息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmMessage(@Valid CmMessageSaveReqVO createReqVO);

    /**
     * 更新圈子消息
     *
     * @param updateReqVO 更新信息
     */
    void updateCmMessage(@Valid CmMessageSaveReqVO updateReqVO);

    /**
     * 删除圈子消息
     *
     * @param id 编号
     */
    void deleteCmMessage(Long id);

    /**
     * 获得圈子消息
     *
     * @param id 编号
     * @return 圈子消息
     */
    CmMessageDO getCmMessage(Long id);

    /**
     * 获得圈子消息分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子消息分页
     */
    PageResult<CmMessageDO> getCmMessagePage(CmMessagePageReqVO pageReqVO);

}