package cn.metast.tuoke.module.community.api.cmBlock;

import cn.metast.tuoke.module.community.service.cmBlock.CmBlockService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 拉黑记录 API 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class BlockApiImpl implements BlockApi {

    @Resource
    private CmBlockService cmBlockService;

    @Override
    public List<Long> getBlockUserIds(Long userId, Long topicId) {
        return cmBlockService.getBlockUserIds(userId, topicId);
    }

}
