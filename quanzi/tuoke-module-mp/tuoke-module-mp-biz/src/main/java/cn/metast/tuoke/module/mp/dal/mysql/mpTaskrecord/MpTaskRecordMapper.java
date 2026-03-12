package cn.metast.tuoke.module.mp.dal.mysql.mpTaskrecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.MpTaskSaveReqVO;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord.MpTaskRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.*;

/**
 * 公众号发送记录 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface MpTaskRecordMapper extends BaseMapperX<MpTaskRecordDO> {

    default List<MpTaskRecordDO> getTaskRecordList(MpTaskSaveReqVO taskDto){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sEndTime  = formatter.format(now);
        return selectList(new LambdaQueryWrapperX<MpTaskRecordDO>()
                .eqIfPresent(MpTaskRecordDO::getTaskId, taskDto.getTaskId())
                .eqIfPresent(MpTaskRecordDO::getSendTime, sEndTime)
        );
    }

    default PageResult<MpTaskRecordDO> selectPage(MpTaskRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MpTaskRecordDO>()
                .eqIfPresent(MpTaskRecordDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(MpTaskRecordDO::getSendUserId, reqVO.getSendUserId())
                .likeIfPresent(MpTaskRecordDO::getSendUserName, reqVO.getSendUserName())
                .eqIfPresent(MpTaskRecordDO::getContent, reqVO.getContent())
                .eqIfPresent(MpTaskRecordDO::getUrl, reqVO.getUrl())
                .eqIfPresent(MpTaskRecordDO::getReceiveUserId, reqVO.getReceiveUserId())
                .likeIfPresent(MpTaskRecordDO::getReceiveUserName, reqVO.getReceiveUserName())
                .eqIfPresent(MpTaskRecordDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(MpTaskRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MpTaskRecordDO::getId));
    }

}
