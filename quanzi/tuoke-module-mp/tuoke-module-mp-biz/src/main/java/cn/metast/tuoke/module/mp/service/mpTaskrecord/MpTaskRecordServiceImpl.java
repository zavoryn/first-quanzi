package cn.metast.tuoke.module.mp.service.mpTaskrecord;

import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.MpTaskSaveReqVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord.MpTaskRecordDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.mp.dal.mysql.mpTaskrecord.MpTaskRecordMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.mp.enums.ErrorCodeConstants.*;

/**
 * 公众号发送记录 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class MpTaskRecordServiceImpl implements MpTaskRecordService {

    @Resource
    private MpTaskRecordMapper taskRecordMapper;

    @Override
    public Long createTaskRecord(MpTaskRecordSaveReqVO createReqVO) {
        // 插入
        MpTaskRecordDO taskRecord = BeanUtils.toBean(createReqVO, MpTaskRecordDO.class);
        taskRecordMapper.insert(taskRecord);
        // 返回
        return taskRecord.getId();
    }

    @Override
    public void updateTaskRecord(MpTaskRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateTaskRecordExists(updateReqVO.getId());
        // 更新
        MpTaskRecordDO updateObj = BeanUtils.toBean(updateReqVO, MpTaskRecordDO.class);
        taskRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteTaskRecord(Long id) {
        // 校验存在
        validateTaskRecordExists(id);
        // 删除
        taskRecordMapper.deleteById(id);
    }

    private void validateTaskRecordExists(Long id) {
        if (taskRecordMapper.selectById(id) == null) {
            throw exception(TASK_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public MpTaskRecordDO getTaskRecord(Long id) {
        return taskRecordMapper.selectById(id);
    }

    @Override
    public PageResult<MpTaskRecordDO> getTaskRecordPage(MpTaskRecordPageReqVO pageReqVO) {
        return taskRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MpTaskRecordDO> getTaskRecordList(MpTaskSaveReqVO taskDto) {
        return taskRecordMapper.getTaskRecordList(taskDto);
    }

}
