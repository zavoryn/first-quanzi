package cn.metast.tuoke.module.community.service.cmComment;

import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmComment.CmCommentMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 圈子评论 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmCommentServiceImpl implements CmCommentService {

    @Resource
    private CmCommentMapper cmCommentMapper;

    @Override
    public Long createCmComment(CmCommentSaveReqVO createReqVO) {
        // 插入
        CmCommentDO cmComment = BeanUtils.toBean(createReqVO, CmCommentDO.class);
        cmCommentMapper.insert(cmComment);
        // 返回
        return cmComment.getId();
    }

    @Override
    public void updateCmComment(CmCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateCmCommentExists(updateReqVO.getId());
        // 更新
        CmCommentDO updateObj = BeanUtils.toBean(updateReqVO, CmCommentDO.class);
        cmCommentMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmComment(Long id) {
        // 校验存在
        validateCmCommentExists(id);
        // 删除
        cmCommentMapper.deleteById(id);
    }

    private void validateCmCommentExists(Long id) {
        if (cmCommentMapper.selectById(id) == null) {
            throw exception(CM_COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public CmCommentDO getCmComment(Long id) {
        return cmCommentMapper.selectById(id);
    }

    @Override
    public PageResult<CmCommentDO> getCmCommentPage(CmCommentPageReqVO pageReqVO) {
        return cmCommentMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CmCommentDO> getCmCommentPageNew(CmCommentPageReqVO pageReqVO) {
        IPage<CmCommentDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmCommentMapper.getCmCommentPageNew(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public Long selectCmCommentCount(Long postId,Long userId) {
        return cmCommentMapper.selectCmCommentCount(postId,userId);
    }

    @Override
    public List<CmCommentDO> getCmCommentPageShow(CmCommentPageReqVO pageReqVO) {
        return cmCommentMapper.getCmCommentPageShow(pageReqVO);
    }

}
