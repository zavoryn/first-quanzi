package cn.metast.tuoke.module.kaifa.service.email.emailinfo;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailinfo.EmailInfoDO;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.EmailMxDO;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
/**
 * 邮件内容 Service 接口
 *
 * @author 精卫拓客
 */
public interface EmailInfoService {

    /**
     * 创建邮件内容
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmailInfo(@Valid EmailInfoSaveReqVO createReqVO);

    /**
     * 更新邮件内容
     *
     * @param updateReqVO 更新信息
     */
    void updateEmailInfo(@Valid EmailInfoSaveReqVO updateReqVO);

    /**
     * 删除邮件内容
     *
     * @param id 编号
     */
    void deleteEmailInfo(Long id);

    /**
     * 获得邮件内容
     *
     * @param id 编号
     * @return 邮件内容
     */
    EmailInfoDO getEmailInfo(Long id);

    /**
     * 获得邮件内容分页
     *
     * @param pageReqVO 分页查询
     * @return 邮件内容分页
     */
    PageResult<EmailInfoDO> getEmailInfoPage(EmailInfoPageReqVO pageReqVO);

    EmailMxDO chenkBindEmail(String email);
}
