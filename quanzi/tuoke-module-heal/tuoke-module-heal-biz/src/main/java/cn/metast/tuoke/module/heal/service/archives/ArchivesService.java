package cn.metast.tuoke.module.heal.service.archives;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.archives.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 档案信息 Service 接口
 *
 * @author 超级管理员
 */
public interface ArchivesService {

    /**
     * 创建档案信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createArchives(@Valid ArchivesSaveReqVO createReqVO);

    /**
     * 更新档案信息
     *
     * @param updateReqVO 更新信息
     */
    void updateArchives(@Valid ArchivesSaveReqVO updateReqVO);

    /**
     * 删除档案信息
     *
     * @param id 编号
     */
    void deleteArchives(Long id);

    /**
     * 获得档案信息
     *
     * @param id 编号
     * @return 档案信息
     */
    ArchivesDO getArchives(Long id);

    /**
     * 获得档案信息分页
     *
     * @param pageReqVO 分页查询
     * @return 档案信息分页
     */
    PageResult<ArchivesDO> getArchivesPage(ArchivesPageReqVO pageReqVO);

}
