package cn.metast.tuoke.module.kaifa.dal.mysql.email.emailmodel;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmodel.EmailModelDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo.*;

/**
 * 模板-快速文本 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface EmailModelMapper extends BaseMapperX<EmailModelDO> {

    default PageResult<EmailModelDO> selectPage(EmailModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmailModelDO>()
                .eqIfPresent(EmailModelDO::getType, reqVO.getType())
                .eqIfPresent(EmailModelDO::getTitle, reqVO.getTitle())
                .eqIfPresent(EmailModelDO::getContent, reqVO.getContent())
                .eqIfPresent(EmailModelDO::getConteText, reqVO.getConteText())
                .eqIfPresent(EmailModelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(EmailModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EmailModelDO::getId));
    }

    default List<EmailModelDO> selectEmailModelPage(EmailModelRespVO modelRespVO){
        return selectList(new LambdaQueryWrapperX<EmailModelDO>()
                .eq(EmailModelDO::getTitle, modelRespVO.getTitle()));
    }
}
