package cn.metast.tuoke.module.heal.dal.mysql.healKnowledge;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCourseDo;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCoursePageReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.healKnowledge.HealKnowledgeDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.healKnowledge.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 健康知识 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface HealKnowledgeMapper extends BaseMapperX<HealKnowledgeDO> {

    IPage<HealCourseDo> getCourseList(IPage<HealCourseDo> page, @Param("reqvo") HealCoursePageReqVO reqvo);
    default PageResult<HealKnowledgeDO> selectPage(HealKnowledgePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HealKnowledgeDO>()
                .eqIfPresent(HealKnowledgeDO::getAuthor, reqVO.getAuthor())
                .eqIfPresent(HealKnowledgeDO::getTitle, reqVO.getTitle())
                .eqIfPresent(HealKnowledgeDO::getCoverUrl, reqVO.getCoverUrl())
                .eqIfPresent(HealKnowledgeDO::getContent, reqVO.getContent())
                .eqIfPresent(HealKnowledgeDO::getLinkUrl, reqVO.getLinkUrl())
                .eqIfPresent(HealKnowledgeDO::getSort, reqVO.getSort())
                .eqIfPresent(HealKnowledgeDO::getShareNum, reqVO.getShareNum())
                .eqIfPresent(HealKnowledgeDO::getLikeNum, reqVO.getLikeNum())
                .eqIfPresent(HealKnowledgeDO::getCommentNum, reqVO.getCommentNum())
                .eqIfPresent(HealKnowledgeDO::getVisitNum, reqVO.getVisitNum())
                .betweenIfPresent(HealKnowledgeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HealKnowledgeDO::getSort));
    }

}
