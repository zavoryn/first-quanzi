package cn.metast.tuoke.module.ai.service.dify;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsPageReqVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsRespVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsSaveReqVO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiPresetsDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * AI API 密钥 Service 接口
 *
 * @author metast.cn
 */
public interface AiPresetsService {

    /**
     * 创建 API 密钥
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPresets(@Valid AiPresetsSaveReqVO createReqVO);

    /**
     * 更新 API 密钥
     *
     * @param updateReqVO 更新信息
     */
    void updatePresets(@Valid AiPresetsSaveReqVO updateReqVO);

    /**
     * 删除 API 密钥
     *
     * @param id 编号
     */
    void deletePresets(Long id);

    /**
     * 获得 API 密钥
     *
     * @param id 编号
     * @return API 密钥
     */
    AiPresetsDO getPresets(Long id);

    /**
     * 获得 API 密钥分页
     *
     * @param pageReqVO 分页查询
     * @return API 密钥分页
     */
    PageResult<AiPresetsDO> getPresetsPage(AiPresetsPageReqVO pageReqVO);

    /**
     * 获得 API 密钥列表
     *
     * @return API 密钥列表
     */
    List<AiPresetsDO> getPresetsList(AiPresetsRespVO resqVO);

}
