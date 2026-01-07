package cn.metast.tuoke.module.live.service.snsActfile;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActfile.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActfile.SnsActFileDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActfile.SnsActFileMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动资料 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActFileServiceImpl implements SnsActFileService {

    @Resource
    private SnsActFileMapper snsActFileMapper;

    @Override
    public Long createSnsActFile(SnsActFileSaveReqVO createReqVO) {
        // 插入
        SnsActFileDO snsActFile = BeanUtils.toBean(createReqVO, SnsActFileDO.class);
        snsActFileMapper.insert(snsActFile);
        // 返回
        return snsActFile.getId();
    }

    @Override
    public void updateSnsActFile(SnsActFileSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActFileExists(updateReqVO.getId());
        // 更新
        SnsActFileDO updateObj = BeanUtils.toBean(updateReqVO, SnsActFileDO.class);
        snsActFileMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActFile(Long id) {
        // 校验存在
        validateSnsActFileExists(id);
        // 删除
        snsActFileMapper.deleteById(id);
    }

    private void validateSnsActFileExists(Long id) {
        if (snsActFileMapper.selectById(id) == null) {
            throw exception(SNS_ACT_FILE_NOT_EXISTS);
        }
    }

    @Override
    public SnsActFileDO getSnsActFile(Long id) {
        return snsActFileMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActFileDO> getSnsActFilePage(SnsActFilePageReqVO pageReqVO) {
        return snsActFileMapper.selectPage(pageReqVO);
    }

}