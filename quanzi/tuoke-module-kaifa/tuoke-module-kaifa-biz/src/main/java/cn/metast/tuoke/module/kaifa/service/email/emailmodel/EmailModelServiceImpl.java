package cn.metast.tuoke.module.kaifa.service.email.emailmodel;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmodel.EmailModelDO;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.emailmodel.EmailModelMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;

/**
 * 模板-快速文本 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class EmailModelServiceImpl implements EmailModelService {

    @Resource
    private EmailModelMapper emailModelMapper;

    @Override
    public Long createEmailModel(EmailModelSaveReqVO createReqVO) {
        // 插入
        EmailModelDO emailModel = BeanUtils.toBean(createReqVO, EmailModelDO.class);
        emailModelMapper.insert(emailModel);
        // 返回
        return emailModel.getId();
    }

    @Override
    public void updateEmailModel(EmailModelSaveReqVO updateReqVO) {
        // 校验存在
        validateEmailModelExists(updateReqVO.getId());
        // 更新
        EmailModelDO updateObj = BeanUtils.toBean(updateReqVO, EmailModelDO.class);
        emailModelMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmailModel(Long id) {
        // 校验存在
        validateEmailModelExists(id);
        // 删除
        emailModelMapper.deleteById(id);
    }

    private void validateEmailModelExists(Long id) {
        if (emailModelMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public EmailModelDO getEmailModel(Long id) {
        return emailModelMapper.selectById(id);
    }

    @Override
    public PageResult<EmailModelDO> getEmailModelPage(EmailModelPageReqVO pageReqVO) {
        return emailModelMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EmailModelDO> selectEmailModelPage(EmailModelRespVO modelRespVO) {
        return emailModelMapper.selectEmailModelPage(modelRespVO);
    }

}
