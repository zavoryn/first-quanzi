package cn.metast.tuoke.module.mp.service.mpTasktemplate;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTasktemplate.MpTaskTemplateDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.mp.dal.mysql.mpTasktemplate.MpTaskTemplateMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.mp.enums.ErrorCodeConstants.*;

/**
 * 公众号模板 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class MpTaskTemplateServiceImpl implements MpTaskTemplateService {

    @Resource
    private MpTaskTemplateMapper taskTemplateMapper;

    @Override
    public Long createTaskTemplate(MpTaskTemplateSaveReqVO createReqVO) {
        // 插入
        MpTaskTemplateDO taskTemplate = BeanUtils.toBean(createReqVO, MpTaskTemplateDO.class);
        taskTemplateMapper.insert(taskTemplate);
        // 返回
        return taskTemplate.getId();
    }

    @Override
    public void updateTaskTemplate(MpTaskTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateTaskTemplateExists(updateReqVO.getId());
        // 更新
        MpTaskTemplateDO updateObj = BeanUtils.toBean(updateReqVO, MpTaskTemplateDO.class);
        taskTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteTaskTemplate(Long id) {
        // 校验存在
        validateTaskTemplateExists(id);
        // 删除
        taskTemplateMapper.deleteById(id);
    }

    private void validateTaskTemplateExists(Long id) {
        if (taskTemplateMapper.selectById(id) == null) {
            throw exception(TASK_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public MpTaskTemplateDO getTaskTemplate(Long id) {
        return taskTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<MpTaskTemplateDO> getTaskTemplatePage(MpTaskTemplatePageReqVO pageReqVO) {
        return taskTemplateMapper.selectPage(pageReqVO);
    }

}