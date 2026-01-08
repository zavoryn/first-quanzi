package cn.metast.tuoke.module.live.service.snsActinfotemplate;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfotemplate.SnsActInfoTemplateDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动模板 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActInfoTemplateService {

    /**
     * 创建活动模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActInfoTemplate(@Valid SnsActInfoTemplateSaveReqVO createReqVO);

    /**
     * 更新活动模板
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActInfoTemplate(@Valid SnsActInfoTemplateSaveReqVO updateReqVO);

    /**
     * 删除活动模板
     *
     * @param id 编号
     */
    void deleteSnsActInfoTemplate(Long id);

    /**
     * 获得活动模板
     *
     * @param id 编号
     * @return 活动模板
     */
    SnsActInfoTemplateDO getSnsActInfoTemplate(Long id);

    /**
     * 获得活动模板分页
     *
     * @param pageReqVO 分页查询
     * @return 活动模板分页
     */
    PageResult<SnsActInfoTemplateDO> getSnsActInfoTemplatePage(SnsActInfoTemplatePageReqVO pageReqVO);

}