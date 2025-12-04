package cn.metast.tuoke.module.heal.dal.mysql.archivesuser;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.archivesuser.ArchivesUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo.*;

/**
 * 档案信息关联用户 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ArchivesUserMapper extends BaseMapperX<ArchivesUserDO> {

    default PageResult<ArchivesUserDO> selectPage(ArchivesUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ArchivesUserDO>()
                .eqIfPresent(ArchivesUserDO::getUid, reqVO.getUid())
                .eqIfPresent(ArchivesUserDO::getArchivesId, reqVO.getArchivesId())
                .betweenIfPresent(ArchivesUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ArchivesUserDO::getId));
    }

}
