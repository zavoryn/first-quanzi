package cn.metast.tuoke.module.community.service.cmConfig;

import cn.metast.tuoke.framework.common.exception.ServiceException;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 圈子配置 Service 接口
 *
 * @author adminXq
 */
public interface CmConfigService {

    /**
     * 创建圈子配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmConfig(@Valid CmConfigSaveReqVO createReqVO);

    /**
     * 更新圈子配置
     *
     * @param updateReqVO 更新信息
     */
    void updateCmConfig(@Valid CmConfigSaveReqVO updateReqVO);

    /**
     * 删除圈子配置
     *
     * @param id 编号
     */
    void deleteCmConfig(Long id);

    /**
     * 获得圈子配置
     *
     * @param id 编号
     * @return 圈子配置
     */
    CmConfigDO getCmConfig(Long id);

    CmConfigDO getCmConfigTopicId(Long id);
    /**
     * 获得圈子配置分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子配置分页
     */
    PageResult<CmConfigDO> getCmConfigPage(CmConfigPageReqVO pageReqVO);

    /**
     * 获取所有圈子
     *
     * @return 圈子列表
     */
    List<CmTopicDO> getAllTopics();

    /**
     * 验证部门-圈子配置是否已存在
     *
     * @param deptId 部门ID
     * @param topicId 圈子ID
     * @param excludeId 排除的配置ID（更新时使用）
     * @throws ServiceException 如果配置已存在
     */
    void validateConfigNotExists(Long deptId, Long topicId, Long excludeId);

}
