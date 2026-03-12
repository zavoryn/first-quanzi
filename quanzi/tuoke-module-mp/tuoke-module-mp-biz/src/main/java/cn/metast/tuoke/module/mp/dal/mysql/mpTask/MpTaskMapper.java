package cn.metast.tuoke.module.mp.dal.mysql.mpTask;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTask.MpTaskDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.*;

/**
 * 自动开发公众号信息 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface MpTaskMapper extends BaseMapperX<MpTaskDO> {

    default List<MpTaskDO> selectListTaskName(String taskName) {
        return selectList(new LambdaQueryWrapperX<MpTaskDO>()
                .likeIfPresent(MpTaskDO::getTaskName, taskName));
    }
    default List<MpTaskDO> selectTaskAll(MpTaskDO reqVO){
        return selectList(new LambdaQueryWrapperX<MpTaskDO>()
                .eqIfPresent(MpTaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(MpTaskDO::getIsRules, reqVO.getIsRules()));
    };

    default PageResult<MpTaskDO> selectPage(MpTaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MpTaskDO>()
                .eqIfPresent(MpTaskDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(MpTaskDO::getTemplateId, reqVO.getTemplateId())
                .likeIfPresent(MpTaskDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(MpTaskDO::getTitle, reqVO.getTitle())
                .eqIfPresent(MpTaskDO::getChatType, reqVO.getChatType())
                .eqIfPresent(MpTaskDO::getIsTask, reqVO.getIsTask())
                .eqIfPresent(MpTaskDO::getIsRules, reqVO.getIsRules())
                .eqIfPresent(MpTaskDO::getSender, reqVO.getSender())
                .eqIfPresent(MpTaskDO::getSendUserId, reqVO.getSendUserId())
                .betweenIfPresent(MpTaskDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(MpTaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(MpTaskDO::getRemark, reqVO.getRemark())
                .eqIfPresent(MpTaskDO::getFromDuration, reqVO.getFromDuration())
                .betweenIfPresent(MpTaskDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MpTaskDO::getId));
    }

}
