package cn.metast.tuoke.module.kaifa.service.email.emailmodel;

import java.util.*;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmodel.EmailModelDO;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 模板-快速文本 Service 接口
 *
 * @author 精卫拓客
 */
public interface EmailModelService {

    /**
     * 创建模板-快速文本
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEmailModel(@Valid EmailModelSaveReqVO createReqVO);

    /**
     * 更新模板-快速文本
     *
     * @param updateReqVO 更新信息
     */
    void updateEmailModel(@Valid EmailModelSaveReqVO updateReqVO);

    /**
     * 删除模板-快速文本
     *
     * @param id 编号
     */
    void deleteEmailModel(Long id);

    /**
     * 获得模板-快速文本
     *
     * @param id 编号
     * @return 模板-快速文本
     */
    EmailModelDO getEmailModel(Long id);

    /**
     * 获得模板-快速文本分页
     *
     * @param pageReqVO 分页查询
     * @return 模板-快速文本分页
     */
    PageResult<EmailModelDO> getEmailModelPage(EmailModelPageReqVO pageReqVO);


    List<EmailModelDO> selectEmailModelPage(EmailModelRespVO modelRespVO);
}
