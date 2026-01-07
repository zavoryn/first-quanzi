package cn.metast.tuoke.module.live.service.snsNews;

import java.util.*;

import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.SnsAblumRespVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsNews.SnsNewsDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 新闻信息 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsNewsService {

    /**
     * 创建新闻信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsNews(@Valid SnsNewsSaveReqVO createReqVO);

    /**
     * 更新新闻信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsNews(@Valid SnsNewsSaveReqVO updateReqVO);

    /**
     * 删除新闻信息
     *
     * @param id 编号
     */
    void deleteSnsNews(Long id);

    /**
     * 获得新闻信息
     *
     * @param id 编号
     * @return 新闻信息
     */
    SnsNewsDO getSnsNews(Long id);

    /**
     * 获得新闻信息分页
     *
     * @param pageReqVO 分页查询
     * @return 新闻信息分页
     */
    PageResult<SnsNewsRespVO> getSnsNewsPage(SnsNewsPageReqVO pageReqVO);

    SnsNewsRespVO selectSnsNewsById(Long newId);

    public int updateSnsNewsId(SnsNewsSaveReqVO snsNewsSaveReqVO);

    int deleteSnsNewsByIds(Long newsId);

}
