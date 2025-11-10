package cn.metast.tuoke.module.community.service.cmConfig;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.mysql.cmConfig.CmConfigMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.CM_CONFIG_DUPLICATE;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.CM_CONFIG_NOT_EXISTS;

/**
 * 圈子配置 Service 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class CmConfigServiceImpl implements CmConfigService {

    @Resource
    private CmConfigMapper cmConfigMapper;

    @Resource
    private CmTopicMapper cmTopicMapper;

    @Override
    public Long createCmConfig(CmConfigSaveReqVO createReqVO) {
        // 验证配置是否已存在
        validateConfigNotExists(createReqVO.getDeptId(), createReqVO.getTopicId(), null);
        // 插入
        CmConfigDO cmConfig = BeanUtils.toBean(createReqVO, CmConfigDO.class);
        cmConfigMapper.insert(cmConfig);
        // 返回
        return cmConfig.getId();
    }

    @Override
    public void updateCmConfig(CmConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateCmConfigExists(updateReqVO.getId());
        // 验证配置是否已存在
        validateConfigNotExists(updateReqVO.getDeptId(), updateReqVO.getTopicId(), updateReqVO.getId());
        // 更新
        CmConfigDO updateObj = BeanUtils.toBean(updateReqVO, CmConfigDO.class);
        cmConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmConfig(Long id) {
        // 校验存在
        validateCmConfigExists(id);
        // 删除
        cmConfigMapper.deleteById(id);
    }

    private void validateCmConfigExists(Long id) {
        if (cmConfigMapper.selectById(id) == null) {
            throw exception(CM_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public CmConfigDO getCmConfig(Long id) {
        return cmConfigMapper.selectById(id);
    }

    @Override
    public CmConfigDO getCmConfigTopicId(Long topicId) {
        return cmConfigMapper.selectOne( new LambdaQueryWrapper<CmConfigDO>()
                .eq(CmConfigDO::getTopicId, topicId)
                .orderByDesc(CmConfigDO::getId)
                .last("LIMIT 1"));
    }

    @Override
    public PageResult<CmConfigDO> getCmConfigPage(CmConfigPageReqVO pageReqVO) {
        return cmConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CmTopicDO> getAllTopics() {
        return cmTopicMapper.selectList();
    }

    @Override
    public void validateConfigNotExists(Long deptId, Long topicId, Long excludeId) {
        CmConfigDO config = cmConfigMapper.selectOne(
            new LambdaQueryWrapperX<CmConfigDO>()
                .eq(CmConfigDO::getDeptId, deptId)
                .eq(CmConfigDO::getTopicId, topicId)
                .neIfPresent(CmConfigDO::getId, excludeId)
        );
        if (config != null) {
            throw exception(CM_CONFIG_DUPLICATE);
        }
    }

}
