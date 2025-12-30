package cn.metast.tuoke.module.kaifa.service.email.emailmx;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.EmailMxDO;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 邮箱域名MX值 Service 接口
 *
 * @author 精卫拓客
 */
public interface EmailMxService {

    /**
     * 创建邮箱域名MX值
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmailMx(@Valid EmailMxSaveReqVO createReqVO);

    /**
     * 更新邮箱域名MX值
     *
     * @param updateReqVO 更新信息
     */
    void updateEmailMx(@Valid EmailMxSaveReqVO updateReqVO);

    /**
     * 删除邮箱域名MX值
     *
     * @param id 编号
     */
    void deleteEmailMx(Long id);

    /**
     * 获得邮箱域名MX值
     *
     * @param id 编号
     * @return 邮箱域名MX值
     */
    EmailMxDO getEmailMx(Long id);

    /**
     * 获得邮箱域名MX值分页
     *
     * @param pageReqVO 分页查询
     * @return 邮箱域名MX值分页
     */
    PageResult<EmailMxDO> getEmailMxPage(EmailMxPageReqVO pageReqVO);

    EmailMxDO selectOneNew(String mainDomain);

    EmailMxDO selectOneMX(String mx);

}
