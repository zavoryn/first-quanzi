package cn.metast.tuoke.module.live.service.snsAct;
import java.text.ParseException;
import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListDTO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListVo;
import cn.metast.tuoke.module.live.controller.admin.vo.ActPostInfo;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAct.SnsActDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 活动详情 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActService {

    /**
     * 创建活动详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsAct(@Valid SnsActSaveReqVO createReqVO);

    /**
     * 更新活动详情
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsAct(@Valid SnsActSaveReqVO updateReqVO);

    /**
     * 删除活动详情
     *
     * @param id 编号
     */
    void deleteSnsAct(Long id);

    /**
     * 获得活动详情
     *
     * @param id 编号
     * @return 活动详情
     */
    SnsActDO getSnsAct(Long id);

    /**
     * 获得活动详情分页
     *
     * @param pageReqVO 分页查询
     * @return 活动详情分页
     */
    PageResult<SnsActDO> getSnsActPage(SnsActPageReqVO pageReqVO);

    List<ActListVo> selectSnsActList(ActListDTO actListDTO) throws ParseException;

    PageResult<SnsActDO> selectSnsActListPage(SnsActPageReqVO pageReqVO);

    public int deleteSnsActAppById(Long id);

    public ActListVo selectSnsActById(Long id);

    public SnsActDO selectSnsActByActId(Long id);

    public int insertSnsActPost(ActPostInfo actPostInfo) throws ParseException;

    public SnsActDO selectSnsActByIdPc(Long id);

}
