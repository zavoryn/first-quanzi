package cn.metast.tuoke.module.live.service.snsActachievement;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActachievement.SnsActAchievementDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动-成绩 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActAchievementService {

    /**
     * 创建活动-成绩
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createSnsActAchievement(@Valid SnsActAchievementSaveReqVO createReqVO);

    /**
     * 更新活动-成绩
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActAchievement(@Valid SnsActAchievementSaveReqVO updateReqVO);

    /**
     * 删除活动-成绩
     *
     * @param id 编号
     */
    void deleteSnsActAchievement(Integer id);

    /**
     * 获得活动-成绩
     *
     * @param id 编号
     * @return 活动-成绩
     */
    SnsActAchievementDO getSnsActAchievement(Integer id);

    /**
     * 获得活动-成绩分页
     *
     * @param pageReqVO 分页查询
     * @return 活动-成绩分页
     */
    PageResult<SnsActAchievementDO> getSnsActAchievementPage(SnsActAchievementPageReqVO pageReqVO);

}