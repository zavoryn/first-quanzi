package cn.metast.tuoke.module.community.service.cmBlock;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.cmBlockPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.cmBlockSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBlock.cmBlockDO;
import cn.metast.tuoke.module.community.dal.mysql.cmBlock.cmBlockMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.CM_BLOCK_NOT_EXISTS;

/**
 * 拉黑记录 Service 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class CmBlockServiceImpl implements CmBlockService {

    @Resource
    private cmBlockMapper cmBlockMapper;

    @Override
    public Long createcmBlock(cmBlockSaveReqVO createReqVO) {
        // 插入
        cmBlockDO cmBlock = BeanUtils.toBean(createReqVO, cmBlockDO.class);
        cmBlockMapper.insert(cmBlock);
        // 返回
        return cmBlock.getId();
    }

    @Override
    public void updatecmBlock(cmBlockSaveReqVO updateReqVO) {
        // 校验存在
        validatecmBlockExists(updateReqVO.getId());
        // 更新
        cmBlockDO updateObj = BeanUtils.toBean(updateReqVO, cmBlockDO.class);
        cmBlockMapper.updateById(updateObj);
    }

    @Override
    public void deletecmBlock(Long id) {
        // 校验存在
        validatecmBlockExists(id);
        // 删除
        cmBlockMapper.deleteById(id);
    }

    private void validatecmBlockExists(Long id) {
        if (cmBlockMapper.selectById(id) == null) {
            throw exception(CM_BLOCK_NOT_EXISTS);
        }
    }

    @Override
    public cmBlockDO getcmBlock(Long id) {
        return cmBlockMapper.selectById(id);
    }

    @Override
    public PageResult<cmBlockDO> getcmBlockPage(cmBlockPageReqVO pageReqVO) {
        return cmBlockMapper.selectPage(pageReqVO);
    }

    @Override
    public List<Long> getBlockUserIds(Long userId, Long topicId) {
        return cmBlockMapper.selectBlockUserIds(userId, topicId);
    }

    @Override
    public boolean existsBlock(Long userId, Long blockUserId, Long topicId) {
        return cmBlockMapper.existsBlock(userId, blockUserId, topicId);
    }

}
