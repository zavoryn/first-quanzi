package cn.metast.tuoke.module.live.service.snsActinfouser;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfouser.SnsActInfoUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 报名填写用户信息 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActInfoUserService {

    /**
     * 创建报名填写用户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActInfoUser(@Valid SnsActInfoUserSaveReqVO createReqVO);

    /**
     * 更新报名填写用户信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActInfoUser(@Valid SnsActInfoUserSaveReqVO updateReqVO);

    /**
     * 删除报名填写用户信息
     *
     * @param id 编号
     */
    void deleteSnsActInfoUser(Long id);

    /**
     * 获得报名填写用户信息
     *
     * @param id 编号
     * @return 报名填写用户信息
     */
    SnsActInfoUserDO getSnsActInfoUser(Long id);

    /**
     * 获得报名填写用户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 报名填写用户信息分页
     */
    PageResult<SnsActInfoUserDO> getSnsActInfoUserPage(SnsActInfoUserPageReqVO pageReqVO);

    public int insertSnsActInfoUserList(List<SnsActInfoUserRespVO> actInfoUsers);

    PageResult<SnsActInfoUserDO> selectSnsActInfoUserList(SnsActInfoUserPageReqVO snsActInfoUser);

}
