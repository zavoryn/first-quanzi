package cn.metast.tuoke.module.live.service.snsActinfocfg;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 报名填写信息设置 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActInfoCfgService {

    /**
     * 创建报名填写信息设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActInfoCfg(@Valid SnsActInfoCfgSaveReqVO createReqVO);

    /**
     * 更新报名填写信息设置
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActInfoCfg(@Valid SnsActInfoCfgSaveReqVO updateReqVO);

    /**
     * 删除报名填写信息设置
     *
     * @param id 编号
     */
    void deleteSnsActInfoCfg(Long id);

    /**
     * 获得报名填写信息设置
     *
     * @param id 编号
     * @return 报名填写信息设置
     */
    SnsActInfoCfgDO getSnsActInfoCfg(Long id);

    /**
     * 获得报名填写信息设置分页
     *
     * @param pageReqVO 分页查询
     * @return 报名填写信息设置分页
     */
    PageResult<SnsActInfoCfgDO> getSnsActInfoCfgPage(SnsActInfoCfgPageReqVO pageReqVO);

    public int deleteSnsActInfoCfgById(Long id);

    public int insertSnsActUserInfoCfg(List<SnsActInfoCfgRespVO> snsActInfoCfg);

    public int updateSnsActInfoCfg(SnsActInfoCfgRespVO snsActInfoCfg);

    List<SnsActInfoCfgRespVO> selectSnsActInfoCfgList(SnsActInfoCfgRespVO snsActInfoCfg);

}
