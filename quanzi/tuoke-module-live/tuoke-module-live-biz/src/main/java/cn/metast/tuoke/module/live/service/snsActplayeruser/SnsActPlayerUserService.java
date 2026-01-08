package cn.metast.tuoke.module.live.service.snsActplayeruser;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActplayeruser.SnsActPlayerUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 参与人信息 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActPlayerUserService {

    /**
     * 创建参与人信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActPlayerUser(@Valid SnsActPlayerUserSaveReqVO createReqVO);

    /**
     * 更新参与人信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActPlayerUser(@Valid SnsActPlayerUserSaveReqVO updateReqVO);

    /**
     * 删除参与人信息
     *
     * @param id 编号
     */
    void deleteSnsActPlayerUser(Long id);

    /**
     * 获得参与人信息
     *
     * @param id 编号
     * @return 参与人信息
     */
    SnsActPlayerUserDO getSnsActPlayerUser(Long id);

    /**
     * 获得参与人信息分页
     *
     * @param pageReqVO 分页查询
     * @return 参与人信息分页
     */
    PageResult<SnsActPlayerUserDO> getSnsActPlayerUserPage(SnsActPlayerUserPageReqVO pageReqVO);

    public List<SnsActPlayerUserRespVO> selectSnsActPlayerUserList(SnsActPlayerUserRespVO snsActPlayerUser);

    int insertSnsActPlayerUserList(List<SnsActPlayerUserRespVO> snsActPlayerUsers);
}
