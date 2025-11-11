package cn.metast.tuoke.module.community.dal.mysql.cmTopic;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicRespVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 圈子详情 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmTopicMapper extends BaseMapperX<CmTopicDO> {

    default List<CmTopicDO> getTopicList(CmTopicRespVO reqVO) {
        return selectList(new LambdaQueryWrapper<CmTopicDO>()
                .eq(CmTopicDO::getUserId, reqVO.getUserId()).eq(CmTopicDO::getStatus, 0));
    }

    /**
     * 分页查询圈子（无过滤）
     */
    default PageResult<CmTopicDO> selectPage(CmTopicPageReqVO reqVO) {
        return selectPageByTopicIds(reqVO, null);
    }

    /**
     * 分页查询圈子，支持部门数据隔离
     *
     * @param reqVO 查询参数
     * @param topicIds 允许查询的圈子ID列表，null表示不过滤
     * @return 分页结果
     */
    default PageResult<CmTopicDO> selectPageByTopicIds(CmTopicPageReqVO reqVO, List<Long> topicIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmTopicDO>()
                .inIfPresent(CmTopicDO::getId, topicIds)
                .likeIfPresent(CmTopicDO::getRealName, reqVO.getRealName())
                .eqIfPresent(CmTopicDO::getMobile, reqVO.getMobile())
                .eqIfPresent(CmTopicDO::getIdCard, reqVO.getIdCard())
                .likeIfPresent(CmTopicDO::getChatName, reqVO.getChatName())
                .likeIfPresent(CmTopicDO::getOtherName, reqVO.getOtherName())
                .eqIfPresent(CmTopicDO::getIsType, reqVO.getIsType())
                .eqIfPresent(CmTopicDO::getIdCardUrl, reqVO.getIdCardUrl())
                .eqIfPresent(CmTopicDO::getCertificateNo, reqVO.getCertificateNo())
                .eqIfPresent(CmTopicDO::getMonthlyPrice, reqVO.getMonthlyPrice())
                .eqIfPresent(CmTopicDO::getBimonthlyPrice, reqVO.getBimonthlyPrice())
                .eqIfPresent(CmTopicDO::getQuarterlyPrice, reqVO.getQuarterlyPrice())
                .eqIfPresent(CmTopicDO::getAprilPrice, reqVO.getAprilPrice())
                .eqIfPresent(CmTopicDO::getHalfYearlyPrice, reqVO.getHalfYearlyPrice())
                .eqIfPresent(CmTopicDO::getTopicName, reqVO.getTopicName())
                .eqIfPresent(CmTopicDO::getCoverImage, reqVO.getCoverImage())
                .eqIfPresent(CmTopicDO::getDescription, reqVO.getDescription())
                .eqIfPresent(CmTopicDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmTopicDO::getMemberCount, reqVO.getMemberCount())
                .eqIfPresent(CmTopicDO::getPostCount, reqVO.getPostCount())
                .eqIfPresent(CmTopicDO::getViewCount, reqVO.getViewCount())
                .eqIfPresent(CmTopicDO::getIsRecommend, reqVO.getIsRecommend())
                .eqIfPresent(CmTopicDO::getIsHot, reqVO.getIsHot())
                .eqIfPresent(CmTopicDO::getIsVisibile, reqVO.getIsVisibile())
                .eqIfPresent(CmTopicDO::getIsComment, reqVO.getIsComment())
                .eqIfPresent(CmTopicDO::getIsNo, reqVO.getIsNo())
                .eqIfPresent(CmTopicDO::getIsRenew, reqVO.getIsRenew())
                .eqIfPresent(CmTopicDO::getJoinMode, reqVO.getJoinMode())
                .eqIfPresent(CmTopicDO::getIsMember, reqVO.getIsMember())
                .eqIfPresent(CmTopicDO::getIsQuestion, reqVO.getIsQuestion())
                .eqIfPresent(CmTopicDO::getIsCopy, reqVO.getIsCopy())
                .eqIfPresent(CmTopicDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmTopicDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmTopicDO::getId));
    }

}
