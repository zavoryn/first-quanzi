package cn.metast.tuoke.module.live.service.snsActinfotemplate;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfotemplate.SnsActInfoTemplateDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActinfotemplate.SnsActInfoTemplateMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动模板 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActInfoTemplateServiceImpl implements SnsActInfoTemplateService {

    @Resource
    private SnsActInfoTemplateMapper snsActInfoTemplateMapper;

    @Override
    public Long createSnsActInfoTemplate(SnsActInfoTemplateSaveReqVO createReqVO) {
        // 插入
        SnsActInfoTemplateDO snsActInfoTemplate = BeanUtils.toBean(createReqVO, SnsActInfoTemplateDO.class);
        snsActInfoTemplateMapper.insert(snsActInfoTemplate);
        // 返回
        return snsActInfoTemplate.getId();
    }

    @Override
    public void updateSnsActInfoTemplate(SnsActInfoTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActInfoTemplateExists(updateReqVO.getId());
        // 更新
        SnsActInfoTemplateDO updateObj = BeanUtils.toBean(updateReqVO, SnsActInfoTemplateDO.class);
        snsActInfoTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActInfoTemplate(Long id) {
        // 校验存在
        validateSnsActInfoTemplateExists(id);
        // 删除
        snsActInfoTemplateMapper.deleteById(id);
    }

    private void validateSnsActInfoTemplateExists(Long id) {
        if (snsActInfoTemplateMapper.selectById(id) == null) {
            throw exception(SNS_ACT_INFO_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public SnsActInfoTemplateDO getSnsActInfoTemplate(Long id) {
        return snsActInfoTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActInfoTemplateDO> getSnsActInfoTemplatePage(SnsActInfoTemplatePageReqVO pageReqVO) {
        return snsActInfoTemplateMapper.selectPage(pageReqVO);
    }

}