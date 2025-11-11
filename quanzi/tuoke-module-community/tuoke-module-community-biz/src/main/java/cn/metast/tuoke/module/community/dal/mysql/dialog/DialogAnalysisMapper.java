package cn.metast.tuoke.module.community.dal.mysql.dialog;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.dialog.DialogAnalysisDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对话分析 Mapper
 *
 * @author adminXq
 */
@Mapper
public interface DialogAnalysisMapper extends BaseMapperX<DialogAnalysisDO> {

    default PageResult<DialogAnalysisDO> selectPage(DialogAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DialogAnalysisDO>()
                .eqIfPresent(DialogAnalysisDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(DialogAnalysisDO::getUserName1, reqVO.getUserName1())
                .eqIfPresent(DialogAnalysisDO::getUserName2, reqVO.getUserName2())
                .eqIfPresent(DialogAnalysisDO::getDialogContent, reqVO.getDialogContent())
                .eqIfPresent(DialogAnalysisDO::getOriginalHtml, reqVO.getOriginalHtml())
                .eqIfPresent(DialogAnalysisDO::getAudioUrls, reqVO.getAudioUrls())
                .eqIfPresent(DialogAnalysisDO::getAnalysisResult, reqVO.getAnalysisResult())
                .eqIfPresent(DialogAnalysisDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DialogAnalysisDO::getErrorMsg, reqVO.getErrorMsg())
                .betweenIfPresent(DialogAnalysisDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DialogAnalysisDO::getId));
    }

}
