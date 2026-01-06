package cn.metast.tuoke.module.live.dal.mysql.snsActinfotemplate;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfotemplate.SnsActInfoTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo.*;

/**
 * 活动模板 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActInfoTemplateMapper extends BaseMapperX<SnsActInfoTemplateDO> {

    default PageResult<SnsActInfoTemplateDO> selectPage(SnsActInfoTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActInfoTemplateDO>()
                .eqIfPresent(SnsActInfoTemplateDO::getColumnId, reqVO.getColumnId())
                .likeIfPresent(SnsActInfoTemplateDO::getFieldName, reqVO.getFieldName())
                .eqIfPresent(SnsActInfoTemplateDO::getFieldType, reqVO.getFieldType())
                .eqIfPresent(SnsActInfoTemplateDO::getFieldInput, reqVO.getFieldInput())
                .eqIfPresent(SnsActInfoTemplateDO::getFieldValue, reqVO.getFieldValue())
                .betweenIfPresent(SnsActInfoTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActInfoTemplateDO::getId));
    }

}