package cn.metast.tuoke.module.kaifa.service.email.settingsendpreheat;

import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.SettingEmailRespVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingsendpreheat.SettingSendPreheatDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.kaifa.dal.mysql.email.settingsendpreheat.SettingSendPreheatMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;

/**
 * 邮箱预热 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class SettingSendPreheatServiceImpl implements SettingSendPreheatService {

    @Resource
    private SettingSendPreheatMapper settingSendPreheatMapper;

    @Override
    public Long createSettingSendPreheat(SettingSendPreheatSaveReqVO createReqVO) {
        // 插入
        SettingSendPreheatDO settingSendPreheat = BeanUtils.toBean(createReqVO, SettingSendPreheatDO.class);
        settingSendPreheatMapper.insert(settingSendPreheat);
        // 返回
        return settingSendPreheat.getId();
    }

    @Override
    public void updateSettingSendPreheat(SettingSendPreheatSaveReqVO updateReqVO) {
        // 校验存在
        validateSettingSendPreheatExists(updateReqVO.getId());
        // 更新
        SettingSendPreheatDO updateObj = BeanUtils.toBean(updateReqVO, SettingSendPreheatDO.class);
        settingSendPreheatMapper.updateById(updateObj);
    }

    @Override
    public void deleteSettingSendPreheat(Long id) {
        // 校验存在
        validateSettingSendPreheatExists(id);
        // 删除
        settingSendPreheatMapper.deleteById(id);
    }

    private void validateSettingSendPreheatExists(Long id) {
        if (settingSendPreheatMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public SettingSendPreheatDO getSettingSendPreheat(Long id) {
        return settingSendPreheatMapper.selectById(id);
    }

    @Override
    public PageResult<SettingSendPreheatDO> getSettingSendPreheatPage(SettingSendPreheatPageReqVO pageReqVO) {
        return settingSendPreheatMapper.selectPage(pageReqVO);
    }

    @Override
    public Boolean emailPreheat(List<SettingEmailRespVO> perheatList, Long tenantId) {
        return null;
    }

    @Override
    public Map<String, Object> statPreheat() {
        return settingSendPreheatMapper.statPreheat(SecurityFrameworkUtils.getLoginUserId());
    }

    @Override
    public Boolean cancelTask(String mail) {
        return null;
    }

}
