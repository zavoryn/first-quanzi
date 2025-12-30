package cn.metast.tuoke.module.kaifa.service.email.settingsendpreheat;

import java.util.*;

import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.SettingEmailRespVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingsendpreheat.SettingSendPreheatDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 邮箱预热 Service 接口
 *
 * @author 精卫拓客
 */
public interface SettingSendPreheatService {

    /**
     * 创建邮箱预热
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSettingSendPreheat(@Valid SettingSendPreheatSaveReqVO createReqVO);

    /**
     * 更新邮箱预热
     *
     * @param updateReqVO 更新信息
     */
    void updateSettingSendPreheat(@Valid SettingSendPreheatSaveReqVO updateReqVO);

    /**
     * 删除邮箱预热
     *
     * @param id 编号
     */
    void deleteSettingSendPreheat(Long id);

    /**
     * 获得邮箱预热
     *
     * @param id 编号
     * @return 邮箱预热
     */
    SettingSendPreheatDO getSettingSendPreheat(Long id);

    /**
     * 获得邮箱预热分页
     *
     * @param pageReqVO 分页查询
     * @return 邮箱预热分页
     */
    PageResult<SettingSendPreheatDO> getSettingSendPreheatPage(SettingSendPreheatPageReqVO pageReqVO);

    Boolean emailPreheat(List<SettingEmailRespVO> perheatList, Long tenantId);

    Map<String,Object> statPreheat();

    public Boolean cancelTask(String mail);
}
