package cn.metast.tuoke.module.heal.dal.mysql.archives;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.archives.vo.*;

/**
 * 档案信息 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ArchivesMapper extends BaseMapperX<ArchivesDO> {

    default PageResult<ArchivesDO> selectPage(ArchivesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ArchivesDO>()
                .eqIfPresent(ArchivesDO::getUid, reqVO.getUid())
                .likeIfPresent(ArchivesDO::getName, reqVO.getName())
                .eqIfPresent(ArchivesDO::getAge, reqVO.getAge())
                .eqIfPresent(ArchivesDO::getSex, reqVO.getSex())
                .eqIfPresent(ArchivesDO::getBirthday, reqVO.getBirthday())
                .eqIfPresent(ArchivesDO::getWeight, reqVO.getWeight())
                .eqIfPresent(ArchivesDO::getHeight, reqVO.getHeight())
                .eqIfPresent(ArchivesDO::getBlood, reqVO.getBlood())
                .eqIfPresent(ArchivesDO::getGmfy, reqVO.getGmfy())
                .eqIfPresent(ArchivesDO::getJzbs, reqVO.getJzbs())
                .eqIfPresent(ArchivesDO::getJjlxr, reqVO.getJjlxr())
                .eqIfPresent(ArchivesDO::getReportTj, reqVO.getReportTj())
                .eqIfPresent(ArchivesDO::getReportBl, reqVO.getReportBl())
                .betweenIfPresent(ArchivesDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ArchivesDO::getImgid, reqVO.getImgid())
                .orderByDesc(ArchivesDO::getId));
    }

}
