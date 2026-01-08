package cn.metast.tuoke.module.live.service.snsActnote;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActnote.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnote.SnsActNoteDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActnote.SnsActNoteMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动记录 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActNoteServiceImpl implements SnsActNoteService {

    @Resource
    private SnsActNoteMapper snsActNoteMapper;

    @Override
    public Long createSnsActNote(SnsActNoteSaveReqVO createReqVO) {
        // 插入
        SnsActNoteDO snsActNote = BeanUtils.toBean(createReqVO, SnsActNoteDO.class);
        snsActNoteMapper.insert(snsActNote);
        // 返回
        return snsActNote.getId();
    }

    @Override
    public void updateSnsActNote(SnsActNoteSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActNoteExists(updateReqVO.getId());
        // 更新
        SnsActNoteDO updateObj = BeanUtils.toBean(updateReqVO, SnsActNoteDO.class);
        snsActNoteMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActNote(Long id) {
        // 校验存在
        validateSnsActNoteExists(id);
        // 删除
        snsActNoteMapper.deleteById(id);
    }

    private void validateSnsActNoteExists(Long id) {
        if (snsActNoteMapper.selectById(id) == null) {
            throw exception(SNS_ACT_NOTE_NOT_EXISTS);
        }
    }

    @Override
    public SnsActNoteDO getSnsActNote(Long id) {
        return snsActNoteMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActNoteDO> getSnsActNotePage(SnsActNotePageReqVO pageReqVO) {
        return snsActNoteMapper.selectPage(pageReqVO);
    }

}