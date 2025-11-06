package cn.metast.tuoke.module.community.api.cmConfig;

import java.util.List;

/**
 * 圈子配置 API 接口
 * 提供跨模块数据查询，避免循环依赖
 *
 * @author adminXq
 */
public interface CmConfigApi {

    /**
     * 根据部门ID获取用户ID列表
     * 查询逻辑：deptId -> topicIds(cm_config) -> userIds(cm_topic_member)
     *
     * @param deptId 部门ID
     * @return 用户ID列表
     */
    List<Long> getUserIdsByDeptId(Long deptId);
}
