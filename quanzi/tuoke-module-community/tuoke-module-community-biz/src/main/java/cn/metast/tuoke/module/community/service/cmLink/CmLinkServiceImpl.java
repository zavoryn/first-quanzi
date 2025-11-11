package cn.metast.tuoke.module.community.service.cmLink;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmLink.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmLink.CmLinkDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmLink.CmLinkMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 首页轮播图 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmLinkServiceImpl implements CmLinkService {

    @Resource
    private CmLinkMapper cmLinkMapper;

    @Override
    public Long createCmLink(CmLinkSaveReqVO createReqVO) {
        // 插入
        CmLinkDO cmLink = BeanUtils.toBean(createReqVO, CmLinkDO.class);
        cmLinkMapper.insert(cmLink);
        // 返回
        return cmLink.getId();
    }

    @Override
    public void updateCmLink(CmLinkSaveReqVO updateReqVO) {
        // 校验存在
        validateCmLinkExists(updateReqVO.getId());
        // 更新
        CmLinkDO updateObj = BeanUtils.toBean(updateReqVO, CmLinkDO.class);
        cmLinkMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmLink(Long id) {
        // 校验存在
        validateCmLinkExists(id);
        // 删除
        cmLinkMapper.deleteById(id);
    }

    private void validateCmLinkExists(Long id) {
        if (cmLinkMapper.selectById(id) == null) {
            throw exception(CM_LINK_NOT_EXISTS);
        }
    }

    @Override
    public CmLinkDO getCmLink(Long id) {
        return cmLinkMapper.selectById(id);
    }

    @Override
    public PageResult<CmLinkDO> getCmLinkPage(CmLinkPageReqVO pageReqVO) {
        return cmLinkMapper.selectPage(pageReqVO);
    }

}