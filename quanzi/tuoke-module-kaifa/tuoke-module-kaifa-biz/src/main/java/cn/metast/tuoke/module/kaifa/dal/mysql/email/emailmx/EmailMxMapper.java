package cn.metast.tuoke.module.kaifa.dal.mysql.email.emailmx;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.micrometer.common.util.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo.*;

/**
 * 邮箱域名MX值 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface EmailMxMapper extends BaseMapperX<EmailMxDO> {

    default PageResult<EmailMxDO> selectPage(EmailMxPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EmailMxDO>()
                .eqIfPresent(EmailMxDO::getType, reqVO.getType())
                .eqIfPresent(EmailMxDO::getDomain, reqVO.getDomain())
                .eqIfPresent(EmailMxDO::getMx, reqVO.getMx())
                .eqIfPresent(EmailMxDO::getInHost, reqVO.getInHost())
                .eqIfPresent(EmailMxDO::getInPort, reqVO.getInPort())
                .eqIfPresent(EmailMxDO::getOutHost, reqVO.getOutHost())
                .eqIfPresent(EmailMxDO::getOutPort, reqVO.getOutPort())
                .eqIfPresent(EmailMxDO::getProxyStatu, reqVO.getProxyStatu())
                .eqIfPresent(EmailMxDO::getPorxyHost, reqVO.getPorxyHost())
                .eqIfPresent(EmailMxDO::getProxyPort, reqVO.getProxyPort())
                .eqIfPresent(EmailMxDO::getProxyUser, reqVO.getProxyUser())
                .eqIfPresent(EmailMxDO::getProxyPass, reqVO.getProxyPass())
                .eqIfPresent(EmailMxDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(EmailMxDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EmailMxDO::getId));
    }
    default EmailMxDO selectOneNew(String mainDomain){
        return selectOne( new LambdaQueryWrapper<EmailMxDO>().eq(StringUtils.isNotEmpty(mainDomain), EmailMxDO::getDomain, mainDomain)
                .last("limit 1"));
    }
    default EmailMxDO selectOneMX(String mx){
        return selectOne( new LambdaQueryWrapper<EmailMxDO>().eq(StringUtils.isNotEmpty(mx), EmailMxDO::getMx, mx)
                .last("limit 1"));
    }
}
