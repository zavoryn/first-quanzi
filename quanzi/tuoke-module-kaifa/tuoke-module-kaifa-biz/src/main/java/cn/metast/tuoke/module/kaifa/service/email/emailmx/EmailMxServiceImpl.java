package cn.metast.tuoke.module.kaifa.service.email.emailmx;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.EmailMxDO;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.emailmx.EmailMxMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;

/**
 * 邮箱域名MX值 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class EmailMxServiceImpl implements EmailMxService {

    @Resource
    private EmailMxMapper emailMxMapper;

    @Override
    public Long createEmailMx(EmailMxSaveReqVO createReqVO) {
        // 插入
        EmailMxDO emailMx = BeanUtils.toBean(createReqVO, EmailMxDO.class);
        emailMxMapper.insert(emailMx);
        // 返回
        return emailMx.getId();
    }

    @Override
    public void updateEmailMx(EmailMxSaveReqVO updateReqVO) {
        // 校验存在
        validateEmailMxExists(updateReqVO.getId());
        // 更新
        EmailMxDO updateObj = BeanUtils.toBean(updateReqVO, EmailMxDO.class);
        emailMxMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmailMx(Long id) {
        // 校验存在
        validateEmailMxExists(id);
        // 删除
        emailMxMapper.deleteById(id);
    }

    private void validateEmailMxExists(Long id) {
        if (emailMxMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public EmailMxDO getEmailMx(Long id) {
        return emailMxMapper.selectById(id);
    }

    @Override
    public PageResult<EmailMxDO> getEmailMxPage(EmailMxPageReqVO pageReqVO) {
        return emailMxMapper.selectPage(pageReqVO);
    }

    @Override
    public EmailMxDO selectOneNew(String mainDomain) {
        return emailMxMapper.selectOneNew(mainDomain);
    }

    @Override
    public EmailMxDO selectOneMX(String mx) {
        return emailMxMapper.selectOneMX(mx);
    }

}
