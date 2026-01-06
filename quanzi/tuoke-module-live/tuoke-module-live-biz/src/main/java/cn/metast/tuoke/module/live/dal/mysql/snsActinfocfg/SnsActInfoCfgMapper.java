package cn.metast.tuoke.module.live.dal.mysql.snsActinfocfg;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.*;

/**
 * 报名填写信息设置 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActInfoCfgMapper extends BaseMapperX<SnsActInfoCfgDO> {
    int deleteSnsActInfoCfgById(Long id);
    int insertSnsActUserInfoCfg(List<SnsActInfoCfgRespVO> snsActInfoCfg);
    List<SnsActInfoCfgRespVO> selectSnsActInfoCfgList(SnsActInfoCfgRespVO snsActInfoCfg);


    int updateSnsActInfoCfg(SnsActInfoCfgRespVO snsActInfoCfg);
    default PageResult<SnsActInfoCfgDO> selectPage(SnsActInfoCfgPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActInfoCfgDO>()
                .eqIfPresent(SnsActInfoCfgDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActInfoCfgDO::getFieldKey, reqVO.getFieldKey())
                .likeIfPresent(SnsActInfoCfgDO::getFieldName, reqVO.getFieldName())
                .eqIfPresent(SnsActInfoCfgDO::getFieldType, reqVO.getFieldType())
                .eqIfPresent(SnsActInfoCfgDO::getFieldInput, reqVO.getFieldInput())
                .eqIfPresent(SnsActInfoCfgDO::getFieldValue, reqVO.getFieldValue())
                .betweenIfPresent(SnsActInfoCfgDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActInfoCfgDO::getId));
    }

}
