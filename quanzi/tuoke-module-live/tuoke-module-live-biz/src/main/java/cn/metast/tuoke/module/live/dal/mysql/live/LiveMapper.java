package cn.metast.tuoke.module.live.dal.mysql.live;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.SnsAblumPageReqVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 相册信息 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface LiveMapper{
    Map<String, Object> appUserId(String mobile);
}
