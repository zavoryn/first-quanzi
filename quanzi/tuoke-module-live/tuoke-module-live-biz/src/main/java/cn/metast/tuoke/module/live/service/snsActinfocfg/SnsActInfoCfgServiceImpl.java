package cn.metast.tuoke.module.live.service.snsActinfocfg;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActinfocfg.SnsActInfoCfgMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 报名填写信息设置 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActInfoCfgServiceImpl implements SnsActInfoCfgService {

    @Resource
    private SnsActInfoCfgMapper snsActInfoCfgMapper;

    @Override
    public Long createSnsActInfoCfg(SnsActInfoCfgSaveReqVO createReqVO) {
        // 插入
        SnsActInfoCfgDO snsActInfoCfg = BeanUtils.toBean(createReqVO, SnsActInfoCfgDO.class);
        snsActInfoCfgMapper.insert(snsActInfoCfg);
        // 返回
        return snsActInfoCfg.getId();
    }

    @Override
    public void updateSnsActInfoCfg(SnsActInfoCfgSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActInfoCfgExists(updateReqVO.getId());
        // 更新
        SnsActInfoCfgDO updateObj = BeanUtils.toBean(updateReqVO, SnsActInfoCfgDO.class);
        snsActInfoCfgMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActInfoCfg(Long id) {
        // 校验存在
        validateSnsActInfoCfgExists(id);
        // 删除
        snsActInfoCfgMapper.deleteById(id);
    }

    private void validateSnsActInfoCfgExists(Long id) {
        if (snsActInfoCfgMapper.selectById(id) == null) {
            throw exception(SNS_ACT_INFO_CFG_NOT_EXISTS);
        }
    }

    @Override
    public SnsActInfoCfgDO getSnsActInfoCfg(Long id) {
        return snsActInfoCfgMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActInfoCfgDO> getSnsActInfoCfgPage(SnsActInfoCfgPageReqVO pageReqVO) {
        return snsActInfoCfgMapper.selectPage(pageReqVO);
    }

    @Override
    public int deleteSnsActInfoCfgById(Long id) {
        return snsActInfoCfgMapper.deleteSnsActInfoCfgById(id);
    }

    @Override
    public int insertSnsActUserInfoCfg(List<SnsActInfoCfgRespVO> snsActInfoCfg) {
        return snsActInfoCfgMapper.insertSnsActUserInfoCfg(snsActInfoCfg);
    }

    @Override
    public int updateSnsActInfoCfg(SnsActInfoCfgRespVO snsActInfoCfg) {
        return snsActInfoCfgMapper.updateSnsActInfoCfg(snsActInfoCfg);
    }

    @Override
    public List<SnsActInfoCfgRespVO> selectSnsActInfoCfgList(SnsActInfoCfgRespVO snsActInfoCfg) {
        return snsActInfoCfgMapper.selectSnsActInfoCfgList(snsActInfoCfg);
    }

}
