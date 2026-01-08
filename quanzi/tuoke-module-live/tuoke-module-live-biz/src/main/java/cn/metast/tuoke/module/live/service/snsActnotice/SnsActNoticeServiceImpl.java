package cn.metast.tuoke.module.live.service.snsActnotice;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnotice.SnsActNoticeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActnotice.SnsActNoticeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动公告 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActNoticeServiceImpl implements SnsActNoticeService {

    @Resource
    private SnsActNoticeMapper snsActNoticeMapper;

    @Override
    public Long createSnsActNotice(SnsActNoticeSaveReqVO createReqVO) {
        // 插入
        SnsActNoticeDO snsActNotice = BeanUtils.toBean(createReqVO, SnsActNoticeDO.class);
        snsActNoticeMapper.insert(snsActNotice);
        // 返回
        return snsActNotice.getId();
    }

    @Override
    public void updateSnsActNotice(SnsActNoticeSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActNoticeExists(updateReqVO.getId());
        // 更新
        SnsActNoticeDO updateObj = BeanUtils.toBean(updateReqVO, SnsActNoticeDO.class);
        snsActNoticeMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActNotice(Long id) {
        // 校验存在
        validateSnsActNoticeExists(id);
        // 删除
        snsActNoticeMapper.deleteById(id);
    }

    private void validateSnsActNoticeExists(Long id) {
        if (snsActNoticeMapper.selectById(id) == null) {
            throw exception(SNS_ACT_NOTICE_NOT_EXISTS);
        }
    }

    @Override
    public SnsActNoticeDO getSnsActNotice(Long id) {
        return snsActNoticeMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActNoticeDO> getSnsActNoticePage(SnsActNoticePageReqVO pageReqVO) {
        return snsActNoticeMapper.selectPage(pageReqVO);
    }

}