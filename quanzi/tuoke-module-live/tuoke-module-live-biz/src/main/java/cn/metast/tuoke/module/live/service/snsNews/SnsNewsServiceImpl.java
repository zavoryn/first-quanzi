package cn.metast.tuoke.module.live.service.snsNews;

import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.SnsAblumRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsNews.SnsNewsDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsNews.SnsNewsMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 新闻信息 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsNewsServiceImpl implements SnsNewsService {

    @Resource
    private SnsNewsMapper snsNewsMapper;

    @Override
    public Long createSnsNews(SnsNewsSaveReqVO createReqVO) {
        // 插入
        SnsNewsDO snsNews = BeanUtils.toBean(createReqVO, SnsNewsDO.class);
        snsNewsMapper.insert(snsNews);
        // 返回
        return snsNews.getNewsId();
    }

    @Override
    public void updateSnsNews(SnsNewsSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsNewsExists(updateReqVO.getNewsId());
        // 更新
        SnsNewsDO updateObj = BeanUtils.toBean(updateReqVO, SnsNewsDO.class);
        snsNewsMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsNews(Long id) {
        // 校验存在
        validateSnsNewsExists(id);
        // 删除
        snsNewsMapper.deleteById(id);
    }

    private void validateSnsNewsExists(Long id) {
        if (snsNewsMapper.selectSnsNewsById(id) == null) {
            throw exception(SNS_NEWS_NOT_EXISTS);
        }
    }

    @Override
    public SnsNewsDO getSnsNews(Long id) {
        return snsNewsMapper.selectById(id);
    }

    @Override
    public PageResult<SnsNewsRespVO> getSnsNewsPage(SnsNewsPageReqVO pageReqVO) {
        //return snsNewsMapper.selectPage(pageReqVO);
        // 必须使用 MyBatis Plus 的分页对象
        IPage<SnsNewsRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        snsNewsMapper.selectSnsNewsLists(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public SnsNewsRespVO selectSnsNewsById(Long newId) {
        return snsNewsMapper.selectSnsNewsById(newId);
    }

    @Override
    public int updateSnsNewsId(SnsNewsSaveReqVO snsNewsSaveReqVO) {
        return snsNewsMapper.updateSnsNewsId(snsNewsSaveReqVO);
    }

    @Override
    public int deleteSnsNewsByIds(Long newsId) {
        return snsNewsMapper.deleteSnsNewsByIds(newsId);
    }

}
