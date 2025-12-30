package cn.metast.tuoke.module.kaifa.service.email.emailsetting;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailsetting.EmailSettingDO;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.emailsetting.EmailSettingMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;

/**
 * 公共配置 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class EmailSettingServiceImpl implements EmailSettingService {

    @Resource
    private EmailSettingMapper emailSettingMapper;

    @Override
    public Long createEmailSetting(EmailSettingSaveReqVO createReqVO) {
        // 插入
        EmailSettingDO emailSetting = BeanUtils.toBean(createReqVO, EmailSettingDO.class);
        emailSettingMapper.insert(emailSetting);
        // 返回
        return emailSetting.getId();
    }

    @Override
    public void updateEmailSetting(EmailSettingSaveReqVO updateReqVO) {
        // 校验存在
        validateEmailSettingExists(updateReqVO.getId());
        // 更新
        EmailSettingDO updateObj = BeanUtils.toBean(updateReqVO, EmailSettingDO.class);
        emailSettingMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmailSetting(Long id) {
        // 校验存在
        validateEmailSettingExists(id);
        // 删除
        emailSettingMapper.deleteById(id);
    }

    private void validateEmailSettingExists(Long id) {
        if (emailSettingMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public EmailSettingDO getEmailSetting(Long id) {
        return emailSettingMapper.selectById(id);
    }

    @Override
    public PageResult<EmailSettingDO> getEmailSettingPage(EmailSettingPageReqVO pageReqVO) {
        return emailSettingMapper.selectPage(pageReqVO);
    }

}
