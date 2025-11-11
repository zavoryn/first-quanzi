package cn.metast.tuoke.module.community.dal.mysql.cmConfig;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigPageReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 圈子配置 Mapper
 *
 * @author adminXq
 */
@Mapper
public interface CmConfigMapper extends BaseMapperX<CmConfigDO> {

    default PageResult<CmConfigDO> selectPage(CmConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmConfigDO>()
                .eqIfPresent(CmConfigDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(CmConfigDO::getTopicId, reqVO.getTopicId())
                .betweenIfPresent(CmConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmConfigDO::getId));
    }

    /**
     * 根据部门ID获取圈子ID列表
     *
     * @param deptId 部门ID
     * @param cmTopicMapper 圈子Mapper（用于超级管理员查询所有圈子）
     * @return 圈子ID列表
     */
    default List<Long> selectTopicIdsByDeptId(Long deptId, CmTopicMapper cmTopicMapper) {
        if (deptId != null && deptId == 115L) {
            // 超级管理员，返回所有圈子ID
            return cmTopicMapper.selectList(new LambdaQueryWrapperX<CmTopicDO>())
                    .stream()
                    .map(CmTopicDO::getId)
                    .collect(Collectors.toList());
        }
        return selectList(new LambdaQueryWrapperX<CmConfigDO>()
                .eq(CmConfigDO::getDeptId, deptId))
                .stream()
                .map(CmConfigDO::getTopicId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID获取配置列表
     *
     * @param deptId 部门ID
     * @return 配置列表
     */
    default List<CmConfigDO> selectListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<CmConfigDO>()
                .eqIfPresent(CmConfigDO::getDeptId, deptId));
    }

}