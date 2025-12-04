package cn.metast.tuoke.module.heal.service.archivesuser;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archivesuser.ArchivesUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 档案信息关联用户 Service 接口
 *
 * @author 超级管理员
 */
public interface ArchivesUserService {

    /**
     * 创建档案信息关联用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createArchivesUser(@Valid ArchivesUserSaveReqVO createReqVO);

    /**
     * 更新档案信息关联用户
     *
     * @param updateReqVO 更新信息
     */
    void updateArchivesUser(@Valid ArchivesUserSaveReqVO updateReqVO);

    /**
     * 删除档案信息关联用户
     *
     * @param id 编号
     */
    void deleteArchivesUser(Long id);

    /**
     * 获得档案信息关联用户
     *
     * @param id 编号
     * @return 档案信息关联用户
     */
    ArchivesUserDO getArchivesUser(Long id);

    /**
     * 获得档案信息关联用户分页
     *
     * @param pageReqVO 分页查询
     * @return 档案信息关联用户分页
     */
    PageResult<ArchivesUserDO> getArchivesUserPage(ArchivesUserPageReqVO pageReqVO);

}
