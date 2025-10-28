package cn.metast.tuoke.module.ai.service.dify;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsPageReqVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsRespVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsSaveReqVO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiPresetsDO;
import cn.metast.tuoke.module.ai.dal.mysql.dify.AiPresetsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.ai.enums.ErrorCodeConstants.AI_PRESETS_NOT_EXISTS;

/**
 * AI API 密钥 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
public class AiPresetsServiceImpl implements AiPresetsService {

    @Resource
    private AiPresetsMapper apiKeyMapper;

    @Override
    public Long createPresets(AiPresetsSaveReqVO createReqVO) {
        // 插入
        AiPresetsDO apiKey = BeanUtils.toBean(createReqVO, AiPresetsDO.class);
        apiKeyMapper.insert(apiKey);
        // 返回
        return apiKey.getId();
    }

    @Override
    public void updatePresets(AiPresetsSaveReqVO updateReqVO) {
        // 校验存在
        validateApiKeyExists(updateReqVO.getId());
        // 更新
        AiPresetsDO updateObj = BeanUtils.toBean(updateReqVO, AiPresetsDO.class);
        apiKeyMapper.updateById(updateObj);
    }

    @Override
    public void deletePresets(Long id) {
        // 校验存在
        validateApiKeyExists(id);
        // 删除
        apiKeyMapper.deleteById(id);
    }

    private AiPresetsDO validateApiKeyExists(Long id) {
        AiPresetsDO apiKey = apiKeyMapper.selectById(id);
        if (apiKey == null) {
            throw exception(AI_PRESETS_NOT_EXISTS);
        }
        return apiKey;
    }

    @Override
    public AiPresetsDO getPresets(Long id) {
        return apiKeyMapper.selectById(id);
    }

    @Override
    public PageResult<AiPresetsDO> getPresetsPage(AiPresetsPageReqVO pageReqVO) {
        return apiKeyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AiPresetsDO> getPresetsList(AiPresetsRespVO resqVO) {
        return apiKeyMapper.selectList(resqVO);
    }

}
