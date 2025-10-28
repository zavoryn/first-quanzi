package cn.metast.tuoke.module.ai.dal.mysql.dify;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.query.QueryWrapperX;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiApiKeyDifyPageReqVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsPageReqVO;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsRespVO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiApiKeyDifyDO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiPresetsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI API 密钥 Mapper
 *
 * @author metast.cn
 */
@Mapper
public interface AiPresetsMapper extends BaseMapperX<AiPresetsDO> {

    default PageResult<AiPresetsDO> selectPage(AiPresetsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiPresetsDO>()
                .likeIfPresent(AiPresetsDO::getName, reqVO.getName())
                .eqIfPresent(AiPresetsDO::getType, reqVO.getType())
                .eqIfPresent(AiPresetsDO::getStatus, reqVO.getStatus())
                .orderByDesc(AiPresetsDO::getId));
    }

    default List<AiPresetsDO> selectList(AiPresetsRespVO reqVO) {
        return selectList(
                new LambdaQueryWrapperX<AiPresetsDO>()
                .likeIfPresent(AiPresetsDO::getName, reqVO.getName())
                .eqIfPresent(AiPresetsDO::getType, reqVO.getType())
                .eqIfPresent(AiPresetsDO::getStatus, reqVO.getStatus())
                .orderByDesc(AiPresetsDO::getId)
        );
    }

}
