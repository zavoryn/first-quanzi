package cn.metast.tuoke.module.community.service.cmFollow;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmFollow.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmFollow.CmFollowDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmFollow.CmFollowMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 用户关注中间 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmFollowServiceImpl implements CmFollowService {

    @Resource
    private CmFollowMapper cmFollowMapper;

    @Override
    public Long createCmFollow(CmFollowSaveReqVO createReqVO) {
        // 插入
        CmFollowDO cmFollow = BeanUtils.toBean(createReqVO, CmFollowDO.class);
        cmFollowMapper.insert(cmFollow);
        // 返回
        return cmFollow.getId();
    }

    @Override
    public void updateCmFollow(CmFollowSaveReqVO updateReqVO) {
        // 校验存在
        validateCmFollowExists(updateReqVO.getId());
        // 更新
        CmFollowDO updateObj = BeanUtils.toBean(updateReqVO, CmFollowDO.class);
        cmFollowMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmFollow(Long id) {
        // 校验存在
        validateCmFollowExists(id);
        // 删除
        cmFollowMapper.deleteById(id);
    }

    private void validateCmFollowExists(Long id) {
        if (cmFollowMapper.selectById(id) == null) {
            throw exception(CM_FOLLOW_NOT_EXISTS);
        }
    }

    @Override
    public CmFollowDO getCmFollow(Long id) {
        return cmFollowMapper.selectById(id);
    }

    @Override
    public PageResult<CmFollowDO> getCmFollowPage(CmFollowPageReqVO pageReqVO) {
        return cmFollowMapper.selectPage(pageReqVO);
    }

}