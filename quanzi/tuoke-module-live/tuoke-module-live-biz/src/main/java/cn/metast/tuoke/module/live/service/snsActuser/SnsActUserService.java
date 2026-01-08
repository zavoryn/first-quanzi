package cn.metast.tuoke.module.live.service.snsActuser;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActuser.SnsActUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动报名人员 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActUserService {

    /**
     * 创建活动报名人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActUser(@Valid SnsActUserSaveReqVO createReqVO);

    /**
     * 更新活动报名人员
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActUser(@Valid SnsActUserSaveReqVO updateReqVO);

    /**
     * 删除活动报名人员
     *
     * @param id 编号
     */
    void deleteSnsActUser(Long id);

    /**
     * 获得活动报名人员
     *
     * @param id 编号
     * @return 活动报名人员
     */
    SnsActUserDO getSnsActUser(Long id);

    /**
     * 获得活动报名人员分页
     *
     * @param pageReqVO 分页查询
     * @return 活动报名人员分页
     */
    PageResult<SnsActUserDO> getSnsActUserPage(SnsActUserPageReqVO pageReqVO);

    public List<SnsActUserRespVO> selectSnsActUserAppList(SnsActUserRespVO snsActUser);

    PageResult<SnsActUserRespVO> selectSnsActUserList(SnsActUserPageReqVO snsActUser);

    int insertSnsActUser(SnsActUserRespVO snsActUser);

}
