package cn.metast.tuoke.module.kaifa.service.email.settingemail;

import com.alibaba.fastjson.JSONObject;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail.SettingEmailDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 邮件配置 Service 接口
 *
 * @author 精卫拓客
 */
public interface SettingEmailService {

    /**
     * 创建邮件配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSettingEmail(@Valid SettingEmailSaveReqVO createReqVO);

    /**
     * 更新邮件配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSettingEmail(@Valid SettingEmailSaveReqVO updateReqVO);

    /**
     * 删除邮件配置
     *
     * @param id 编号
     */
    void deleteSettingEmail(Long id);

    /**
     * 获得邮件配置
     *
     * @param id 编号
     * @return 邮件配置
     */
    SettingEmailDO getSettingEmail(Long id);

    /**
     * 获得邮件配置分页
     *
     * @param pageReqVO 分页查询
     * @return 邮件配置分页
     */
    PageResult<SettingEmailDO> getSettingEmailPage(SettingEmailPageReqVO pageReqVO);

    JSONObject checkEmailStatus(SettingEmailRespVO reqVO);
}
