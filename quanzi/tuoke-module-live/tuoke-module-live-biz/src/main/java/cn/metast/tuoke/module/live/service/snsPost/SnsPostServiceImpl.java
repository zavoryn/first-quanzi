package cn.metast.tuoke.module.live.service.snsPost;

import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.SnsNewsRespVO;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import cn.metast.tuoke.module.live.dal.mysql.snsAblum.SnsAblumMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsComment.SnsCommentMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentlike.SnsCommentLikeMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentreply.SnsCommentReplyMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsPost.SnsPostDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsPost.SnsPostMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 动态信息 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsPostServiceImpl implements SnsPostService {

    @Resource
    private SnsPostMapper snsPostMapper;
    @Autowired
    private SnsCommentMapper commentMapper;
    @Autowired
    private SnsCommentLikeMapper commentLikeMapper;
    @Autowired
    private SnsCommentReplyMapper commentReplyMapper;
    @Autowired
    private SnsAblumMapper snsAblumMapper;
    public Long createSnsPost(SnsPostSaveReqVO createReqVO) {
        // 插入
        SnsPostDO snsPost = BeanUtils.toBean(createReqVO, SnsPostDO.class);
        snsPostMapper.insert(snsPost);
        // 返回
        return snsPost.getPostId();
    }

    @Override
    public void updateSnsPost(SnsPostSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsPostExists(updateReqVO.getPostId());
        // 更新
        SnsPostDO updateObj = BeanUtils.toBean(updateReqVO, SnsPostDO.class);
        snsPostMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsPost(Long id) {
        // 校验存在
        validateSnsPostExists(id);
        // 删除
        snsPostMapper.deleteById(id);
    }

    private void validateSnsPostExists(Long id) {
        if (snsPostMapper.selectSnsPostById(id) == null) {
            throw exception(SNS_POST_NOT_EXISTS);
        }
    }

    @Override
    public SnsPostDO getSnsPost(Long id) {
        return snsPostMapper.selectById(id);
    }

    @Override
    public PageResult<SnsPostDO> getSnsPostPage(SnsPostPageReqVO pageReqVO) {
        return snsPostMapper.selectPage(pageReqVO);
    }

    @Override
    public int updateSnsPostPc(SnsPostSaveReqVO snsPost) {
        if(snsPost != null && snsPost.getPostId() != null) {
            return snsPostMapper.updateSnsPostPc(snsPost);
        }
        return 0;
    }

    @Override
    public int deleteSnsPostById(Long postId) {
        int i = snsPostMapper.deleteSnsPostById(postId);
        if(i == 1){
            //删除评论
            SnsCommentDO comment = new SnsCommentDO();
            comment.setPostId(postId);
            List<SnsCommentDO> snsComments = commentMapper.selectSnsCommentList(comment);
            if(!CollectionUtils.isEmpty(snsComments)) {
                for (SnsCommentDO c : snsComments) {
                    commentLikeMapper.deleteSnsCommentLikeByCommentid(c.getCommentId());
                    commentReplyMapper.deleteSnsCommentReplyByCommentid(c.getCommentId());
                }
            }
            commentMapper.deleteSnsCommentByPostId(postId);
            //删除相册,分享，收藏等等
            snsAblumMapper.deleteSnsAblumByPostId(postId);
        }
        return i;
    }

    @Override
    public PageResult<SnsPostSaveReqVO> selectSnsNewsListApp(SnsPostPageReqVO pageReqVO) {
        IPage<SnsPostSaveReqVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        snsPostMapper.selectSnsNewsListApp(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public SnsPostSaveReqVO selectSnsPostById(Long postId) {
        return snsPostMapper.selectSnsPostById(postId);
    }

    @Override
    public int insertSnsPost(SnsPostRespVO snsPost) {
       return snsPostMapper.insertSnsPost(snsPost);
    }

}
