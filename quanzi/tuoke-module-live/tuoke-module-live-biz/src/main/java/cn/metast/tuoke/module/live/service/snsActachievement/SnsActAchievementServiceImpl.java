package cn.metast.tuoke.module.live.service.snsActachievement;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActachievement.SnsActAchievementDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActachievement.SnsActAchievementMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动-成绩 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActAchievementServiceImpl implements SnsActAchievementService {

    @Resource
    private SnsActAchievementMapper snsActAchievementMapper;

    @Override
    public Integer createSnsActAchievement(SnsActAchievementSaveReqVO createReqVO) {
        // 插入
        SnsActAchievementDO snsActAchievement = BeanUtils.toBean(createReqVO, SnsActAchievementDO.class);
        snsActAchievementMapper.insert(snsActAchievement);
        // 返回
        return snsActAchievement.getId();
    }

    @Override
    public void updateSnsActAchievement(SnsActAchievementSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActAchievementExists(updateReqVO.getId());
        // 更新
        SnsActAchievementDO updateObj = BeanUtils.toBean(updateReqVO, SnsActAchievementDO.class);
        snsActAchievementMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActAchievement(Integer id) {
        // 校验存在
        validateSnsActAchievementExists(id);
        // 删除
        snsActAchievementMapper.deleteById(id);
    }

    private void validateSnsActAchievementExists(Integer id) {
        if (snsActAchievementMapper.selectById(id) == null) {
            throw exception(SNS_ACT_ACHIEVEMENT_NOT_EXISTS);
        }
    }

    @Override
    public SnsActAchievementDO getSnsActAchievement(Integer id) {
        return snsActAchievementMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActAchievementDO> getSnsActAchievementPage(SnsActAchievementPageReqVO pageReqVO) {
        return snsActAchievementMapper.selectPage(pageReqVO);
    }

}