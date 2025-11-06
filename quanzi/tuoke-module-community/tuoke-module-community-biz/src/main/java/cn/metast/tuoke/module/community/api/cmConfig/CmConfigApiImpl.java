package cn.metast.tuoke.module.community.api.cmConfig;

import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.dal.mysql.cmConfig.CmConfigMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopicmember.CmTopicMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 圈子配置 API 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class CmConfigApiImpl implements CmConfigApi {

    @Resource
    private CmConfigMapper cmConfigMapper;

    @Resource
    private CmTopicMemberMapper cmTopicMemberMapper;

    @Override
    public List<Long> getUserIdsByDeptId(Long deptId) {
        if (deptId == null) {
            return Collections.emptyList();
        }

        // 1. 从 cm_config 获取部门对应的 topic_id 列表
        List<Long> topicIds = cmConfigMapper.selectList(new LambdaQueryWrapperX<CmConfigDO>()
                .eq(CmConfigDO::getDeptId, deptId))
                .stream()
                .map(CmConfigDO::getTopicId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (topicIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 从 cm_topic_member 获取这些 topic_id 对应的 user_id 列表
        return cmTopicMemberMapper.selectList(new LambdaQueryWrapperX<CmTopicMemberDO>()
                .in(CmTopicMemberDO::getTopicId, topicIds))
                .stream()
                .map(CmTopicMemberDO::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}
