package cn.metast.tuoke.module.community.service.cmMessage;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmMessage.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmMessage.CmMessageDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmMessage.CmMessageMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 圈子消息 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmMessageServiceImpl implements CmMessageService {

    @Resource
    private CmMessageMapper cmMessageMapper;

    @Override
    public Long createCmMessage(CmMessageSaveReqVO createReqVO) {
        // 插入
        CmMessageDO cmMessage = BeanUtils.toBean(createReqVO, CmMessageDO.class);
        cmMessageMapper.insert(cmMessage);
        // 返回
        return cmMessage.getId();
    }

    @Override
    public void updateCmMessage(CmMessageSaveReqVO updateReqVO) {
        // 校验存在
        validateCmMessageExists(updateReqVO.getId());
        // 更新
        CmMessageDO updateObj = BeanUtils.toBean(updateReqVO, CmMessageDO.class);
        cmMessageMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmMessage(Long id) {
        // 校验存在
        validateCmMessageExists(id);
        // 删除
        cmMessageMapper.deleteById(id);
    }

    private void validateCmMessageExists(Long id) {
        if (cmMessageMapper.selectById(id) == null) {
            throw exception(CM_MESSAGE_NOT_EXISTS);
        }
    }

    @Override
    public CmMessageDO getCmMessage(Long id) {
        return cmMessageMapper.selectById(id);
    }

    @Override
    public PageResult<CmMessageDO> getCmMessagePage(CmMessagePageReqVO pageReqVO) {
        return cmMessageMapper.selectPage(pageReqVO);
    }

}