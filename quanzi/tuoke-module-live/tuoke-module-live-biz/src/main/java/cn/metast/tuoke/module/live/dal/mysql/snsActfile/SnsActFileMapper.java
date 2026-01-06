package cn.metast.tuoke.module.live.dal.mysql.snsActfile;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActfile.SnsActFileDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActfile.vo.*;

/**
 * 活动资料 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActFileMapper extends BaseMapperX<SnsActFileDO> {

    default PageResult<SnsActFileDO> selectPage(SnsActFilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActFileDO>()
                .eqIfPresent(SnsActFileDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActFileDO::getTitle, reqVO.getTitle())
                .betweenIfPresent(SnsActFileDO::getTime, reqVO.getTime())
                .eqIfPresent(SnsActFileDO::getFileId, reqVO.getFileId())
                .betweenIfPresent(SnsActFileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActFileDO::getId));
    }

}