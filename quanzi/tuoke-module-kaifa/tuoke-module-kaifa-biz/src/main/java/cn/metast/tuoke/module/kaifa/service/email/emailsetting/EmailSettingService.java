package cn.metast.tuoke.module.kaifa.service.email.emailsetting;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailsetting.EmailSettingDO;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 公共配置 Service 接口
 *
 * @author 精卫拓客
 */
public interface EmailSettingService {

    /**
     * 创建公共配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmailSetting(@Valid EmailSettingSaveReqVO createReqVO);

    /**
     * 更新公共配置
     *
     * @param updateReqVO 更新信息
     */
    void updateEmailSetting(@Valid EmailSettingSaveReqVO updateReqVO);

    /**
     * 删除公共配置
     *
     * @param id 编号
     */
    void deleteEmailSetting(Long id);

    /**
     * 获得公共配置
     *
     * @param id 编号
     * @return 公共配置
     */
    EmailSettingDO getEmailSetting(Long id);

    /**
     * 获得公共配置分页
     *
     * @param pageReqVO 分页查询
     * @return 公共配置分页
     */
    PageResult<EmailSettingDO> getEmailSettingPage(EmailSettingPageReqVO pageReqVO);

}
