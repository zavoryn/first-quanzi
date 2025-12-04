package cn.metast.tuoke.module.heal.service.healKnowledge;

import cn.metast.tuoke.module.heal.controller.app.vo.HealCourseDo;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCoursePageReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.healKnowledge.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healKnowledge.HealKnowledgeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.healKnowledge.HealKnowledgeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.*;

/**
 * 健康知识 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class HealKnowledgeServiceImpl implements HealKnowledgeService {

    @Resource
    private HealKnowledgeMapper knowledgeMapper;

    @Override
    public Long createKnowledge(HealKnowledgeSaveReqVO createReqVO) {
        // 插入
        HealKnowledgeDO knowledge = BeanUtils.toBean(createReqVO, HealKnowledgeDO.class);
        knowledgeMapper.insert(knowledge);
        // 返回
        return knowledge.getId();
    }

    @Override
    public void updateKnowledge(HealKnowledgeSaveReqVO updateReqVO) {
        // 校验存在
        validateKnowledgeExists(updateReqVO.getId());
        // 更新
        HealKnowledgeDO updateObj = BeanUtils.toBean(updateReqVO, HealKnowledgeDO.class);
        knowledgeMapper.updateById(updateObj);
    }

    @Override
    public void deleteKnowledge(Long id) {
        // 校验存在
        validateKnowledgeExists(id);
        // 删除
        knowledgeMapper.deleteById(id);
    }

    private void validateKnowledgeExists(Long id) {
        if (knowledgeMapper.selectById(id) == null) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
    }

    @Override
    public HealKnowledgeDO getKnowledge(Long id) {
        return knowledgeMapper.selectById(id);
    }

    @Override
    public PageResult<HealKnowledgeDO> getKnowledgePage(HealKnowledgePageReqVO pageReqVO) {
        return knowledgeMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<HealCourseDo> getCoursePage(HealCoursePageReqVO pageReqVO) {
        IPage<HealCourseDo> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        knowledgeMapper.getCourseList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

}
