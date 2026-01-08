package cn.metast.tuoke.module.live.service.snsActschedule;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActschedule.SnsActScheduleDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActschedule.SnsActScheduleMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动日程 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActScheduleServiceImpl implements SnsActScheduleService {

    @Resource
    private SnsActScheduleMapper snsActScheduleMapper;

    @Override
    public Long createSnsActSchedule(SnsActScheduleSaveReqVO createReqVO) {
        // 插入
        SnsActScheduleDO snsActSchedule = BeanUtils.toBean(createReqVO, SnsActScheduleDO.class);
        snsActScheduleMapper.insert(snsActSchedule);
        // 返回
        return snsActSchedule.getId();
    }

    @Override
    public void updateSnsActSchedule(SnsActScheduleSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActScheduleExists(updateReqVO.getId());
        // 更新
        SnsActScheduleDO updateObj = BeanUtils.toBean(updateReqVO, SnsActScheduleDO.class);
        snsActScheduleMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActSchedule(Long id) {
        // 校验存在
        validateSnsActScheduleExists(id);
        // 删除
        snsActScheduleMapper.deleteById(id);
    }

    private void validateSnsActScheduleExists(Long id) {
        if (snsActScheduleMapper.selectById(id) == null) {
            throw exception(SNS_ACT_SCHEDULE_NOT_EXISTS);
        }
    }

    @Override
    public SnsActScheduleDO getSnsActSchedule(Long id) {
        return snsActScheduleMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActScheduleDO> getSnsActSchedulePage(SnsActSchedulePageReqVO pageReqVO) {
        return snsActScheduleMapper.selectPage(pageReqVO);
    }

}