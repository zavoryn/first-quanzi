package cn.metast.tuoke.module.system.service.logger;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.metast.tuoke.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.metast.tuoke.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.metast.tuoke.module.system.dal.dataobject.logger.LoginLogDO;

import jakarta.validation.Valid;

/**
 * 登录日志 Service 接口
 */
public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param pageReqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO);

    /**
     * 获得登录日志分页（包含用户昵称）
     *
     * @param pageReqVO 分页条件
     * @return 登录日志分页（用户名已替换为昵称）
     */
    PageResult<LoginLogRespVO> getLoginLogPageWithNickname(LoginLogPageReqVO pageReqVO);

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
