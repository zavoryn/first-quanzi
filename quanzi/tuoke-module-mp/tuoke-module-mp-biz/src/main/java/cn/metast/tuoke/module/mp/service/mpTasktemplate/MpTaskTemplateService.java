package cn.metast.tuoke.module.mp.service.mpTasktemplate;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTasktemplate.MpTaskTemplateDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 公众号模板 Service 接口
 *
 * @author 苏丹家园
 */
public interface MpTaskTemplateService {

    /**
     * 创建公众号模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskTemplate(@Valid MpTaskTemplateSaveReqVO createReqVO);

    /**
     * 更新公众号模板
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskTemplate(@Valid MpTaskTemplateSaveReqVO updateReqVO);

    /**
     * 删除公众号模板
     *
     * @param id 编号
     */
    void deleteTaskTemplate(Long id);

    /**
     * 获得公众号模板
     *
     * @param id 编号
     * @return 公众号模板
     */
    MpTaskTemplateDO getTaskTemplate(Long id);

    /**
     * 获得公众号模板分页
     *
     * @param pageReqVO 分页查询
     * @return 公众号模板分页
     */
    PageResult<MpTaskTemplateDO> getTaskTemplatePage(MpTaskTemplatePageReqVO pageReqVO);

}