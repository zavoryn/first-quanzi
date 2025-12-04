package cn.metast.tuoke.module.heal.service.healKnowledge;

import java.util.*;

import cn.metast.tuoke.module.heal.controller.app.vo.HealCourseDo;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCoursePageReqVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.healKnowledge.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healKnowledge.HealKnowledgeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 健康知识 Service 接口
 *
 * @author 苏丹家园
 */
public interface HealKnowledgeService {

    /**
     * 创建健康知识
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createKnowledge(@Valid HealKnowledgeSaveReqVO createReqVO);

    /**
     * 更新健康知识
     *
     * @param updateReqVO 更新信息
     */
    void updateKnowledge(@Valid HealKnowledgeSaveReqVO updateReqVO);

    /**
     * 删除健康知识
     *
     * @param id 编号
     */
    void deleteKnowledge(Long id);

    /**
     * 获得健康知识
     *
     * @param id 编号
     * @return 健康知识
     */
    HealKnowledgeDO getKnowledge(Long id);

    /**
     * 获得健康知识分页
     *
     * @param pageReqVO 分页查询
     * @return 健康知识分页
     */
    PageResult<HealKnowledgeDO> getKnowledgePage(HealKnowledgePageReqVO pageReqVO);

    PageResult<HealCourseDo> getCoursePage(HealCoursePageReqVO pageReqVO);
}
