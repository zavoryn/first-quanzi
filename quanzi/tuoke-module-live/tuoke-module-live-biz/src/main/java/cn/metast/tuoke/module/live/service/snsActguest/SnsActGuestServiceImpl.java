package cn.metast.tuoke.module.live.service.snsActguest;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActguest.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActguest.SnsActGuestDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActguest.SnsActGuestMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动嘉宾 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActGuestServiceImpl implements SnsActGuestService {

    @Resource
    private SnsActGuestMapper snsActGuestMapper;

    @Override
    public Long createSnsActGuest(SnsActGuestSaveReqVO createReqVO) {
        // 插入
        SnsActGuestDO snsActGuest = BeanUtils.toBean(createReqVO, SnsActGuestDO.class);
        snsActGuestMapper.insert(snsActGuest);
        // 返回
        return snsActGuest.getId();
    }

    @Override
    public void updateSnsActGuest(SnsActGuestSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActGuestExists(updateReqVO.getId());
        // 更新
        SnsActGuestDO updateObj = BeanUtils.toBean(updateReqVO, SnsActGuestDO.class);
        snsActGuestMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActGuest(Long id) {
        // 校验存在
        validateSnsActGuestExists(id);
        // 删除
        snsActGuestMapper.deleteById(id);
    }

    private void validateSnsActGuestExists(Long id) {
        if (snsActGuestMapper.selectById(id) == null) {
            throw exception(SNS_ACT_GUEST_NOT_EXISTS);
        }
    }

    @Override
    public SnsActGuestDO getSnsActGuest(Long id) {
        return snsActGuestMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActGuestDO> getSnsActGuestPage(SnsActGuestPageReqVO pageReqVO) {
        return snsActGuestMapper.selectPage(pageReqVO);
    }

}