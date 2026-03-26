package cn.metast.tuoke.module.pay.dal.mysql.app;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.pay.controller.admin.app.vo.PayAppPageReqVO;
import cn.metast.tuoke.module.pay.dal.dataobject.app.PayAppDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayAppMapper extends BaseMapperX<PayAppDO> {

    default PageResult<PayAppDO> selectPage(PayAppPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayAppDO>()
                .likeIfPresent(PayAppDO::getName, reqVO.getName())
                .likeIfPresent(PayAppDO::getAppKey, reqVO.getAppKey())
                .eqIfPresent(PayAppDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayAppDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayAppDO::getId));
    }

    /**
     * 分页查询，支持部门过滤
     */
    default PageResult<PayAppDO> selectPageByDeptId(PayAppPageReqVO reqVO, Long deptId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayAppDO>()
                .eq(PayAppDO::getDeptId, deptId)
                .likeIfPresent(PayAppDO::getName, reqVO.getName())
                .likeIfPresent(PayAppDO::getAppKey, reqVO.getAppKey())
                .eqIfPresent(PayAppDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayAppDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayAppDO::getId));
    }

    /**
     * 查询指定部门的应用列表
     */
    default List<PayAppDO> selectListByDeptId(Long deptId) {
        return selectList(PayAppDO::getDeptId, deptId);
    }

    default PayAppDO selectByAppKey(String appKey) {
        return selectOne(PayAppDO::getAppKey, appKey);
    }

}
