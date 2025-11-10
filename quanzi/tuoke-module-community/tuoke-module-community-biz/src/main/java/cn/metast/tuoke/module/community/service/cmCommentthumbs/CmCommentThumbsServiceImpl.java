package cn.metast.tuoke.module.community.service.cmCommentthumbs;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentthumbs.CmCommentThumbsDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmCommentthumbs.CmCommentThumbsMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 评论用户关联 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmCommentThumbsServiceImpl implements CmCommentThumbsService {

    @Resource
    private CmCommentThumbsMapper cmCommentThumbsMapper;

    @Override
    public Long createCmCommentThumbs(CmCommentThumbsSaveReqVO createReqVO) {
        // 插入
        CmCommentThumbsDO cmCommentThumbs = BeanUtils.toBean(createReqVO, CmCommentThumbsDO.class);
        cmCommentThumbsMapper.insert(cmCommentThumbs);
        // 返回
        return cmCommentThumbs.getId();
    }

    @Override
    public void updateCmCommentThumbs(CmCommentThumbsSaveReqVO updateReqVO) {
        // 校验存在
        validateCmCommentThumbsExists(updateReqVO.getId());
        // 更新
        CmCommentThumbsDO updateObj = BeanUtils.toBean(updateReqVO, CmCommentThumbsDO.class);
        cmCommentThumbsMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmCommentThumbs(Long id) {
        // 校验存在
        validateCmCommentThumbsExists(id);
        // 删除
        cmCommentThumbsMapper.deleteById(id);
    }

    private void validateCmCommentThumbsExists(Long id) {
        if (cmCommentThumbsMapper.selectById(id) == null) {
            throw exception(CM_COMMENT_THUMBS_NOT_EXISTS);
        }
    }

    @Override
    public CmCommentThumbsDO getCmCommentThumbs(Long id) {
        return cmCommentThumbsMapper.selectById(id);
    }

    @Override
    public PageResult<CmCommentThumbsDO> getCmCommentThumbsPage(CmCommentThumbsPageReqVO pageReqVO) {
        return cmCommentThumbsMapper.selectPage(pageReqVO);
    }

}