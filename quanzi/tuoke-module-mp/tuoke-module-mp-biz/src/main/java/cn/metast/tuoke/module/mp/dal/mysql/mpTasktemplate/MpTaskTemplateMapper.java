package cn.metast.tuoke.module.mp.dal.mysql.mpTasktemplate;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTasktemplate.MpTaskTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.*;

/**
 * 公众号模板 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface MpTaskTemplateMapper extends BaseMapperX<MpTaskTemplateDO> {

    default PageResult<MpTaskTemplateDO> selectPage(MpTaskTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MpTaskTemplateDO>()
                .eqIfPresent(MpTaskTemplateDO::getUrl, reqVO.getUrl())
                .eqIfPresent(MpTaskTemplateDO::getContent, reqVO.getContent())
                .betweenIfPresent(MpTaskTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MpTaskTemplateDO::getId));
    }

}