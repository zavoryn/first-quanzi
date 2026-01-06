package cn.metast.tuoke.module.live.service.dynaService;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.controller.admin.vo.PicVo;
import cn.metast.tuoke.module.live.controller.admin.vo.PostInfoVo;
import cn.metast.tuoke.module.live.controller.admin.vo.PostListVo;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import cn.metast.tuoke.module.live.dal.mysql.snsAblum.SnsAblumMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsComment.SnsCommentMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentlike.SnsCommentLikeMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentreply.SnsCommentReplyMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsPost.SnsPostMapper;
import cn.metast.tuoke.module.live.utils.BeanMapUtils;
import cn.metast.tuoke.module.live.utils.CommUtils;
import cn.metast.tuoke.module.live.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
@Service
public class DynaServiceImpl implements IDynaService {

    @Autowired
    private SnsPostMapper postMapper;
    @Autowired
    private SnsCommentMapper commentMapper;
    @Autowired
    private SnsCommentReplyMapper commentReplyMapper;
    @Autowired
    private SnsCommentLikeMapper commentLikeMapper;
    @Autowired
    private SnsAblumMapper snsAblumMapper;


    @Override
    public PostListVo getPostInfo(SnsPostSaveReqVO post, Long uid){
        if(post == null){ return null; }

        PostListVo pvo = new PostListVo();
        BeanMapUtils.copyBeanProp(pvo, post);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = CommUtils.beanToMap(post);
        //介绍人头像
        pvo.setAvatarUrl(post.getAvatar());
        pvo.setCreateTime(DateUtils.datetime(post.getCreateTime()));
        // 图片链接格式化 getPicList
        pvo.setPicInfo(getPicList(post));
        pvo.setMediaUrl(post.getMediaUrl());
        pvo.setCoverUrl(post.getCoverUrl());
        pvo.setAccessCount(post.getAccessCount());
        pvo.setLabel(post.getLabel());
        pvo.setComent(post.getComent());

        // 是否关注
        pvo.setIsFollow(false);

       /* // 用户信息
        SysUser user = userMapper.selectUserById(post.getUserId());
        if(user!=null) {
            pvo.setImuserId(user.getImUserId());
            pvo.setNickName(user.getNickName());
            pvo.setAvatar(CommUtils.getAvatarUrl(user.getAvatar()));
            pvo.setPhone(user.getPhonenumber());
            pvo.setUserId(user.getUserId());
        }*/
        return pvo;
    }

    @Override
    public PostInfoVo getPostInfo(String postType, Long postId, Long uid) {
        SnsPostSaveReqVO post = postMapper.selectSnsPostById(postId);
        post.setAccessCount(post.getAccessCount()+1);
        SnsPostRespVO snsPost=new SnsPostRespVO();
        BeanMapUtils.copyBeanProp(snsPost, post);
        postMapper.updateSnsPost(snsPost);
        if(post == null){ return null; }

        PostListVo postInfo = getPostInfo(post, uid);
        PostInfoVo pvo = new PostInfoVo();
        BeanMapUtils.copyBeanProp(pvo, postInfo);
       // pvo.setCommentInfo(getCommentInfo(postId, uid, null));
        return pvo;
    }

    private List<PicVo> getPicList(SnsPostSaveReqVO post) {
        List<PicVo> list = new ArrayList<>();
        SnsAblumDO snsAblum = new SnsAblumDO();
        snsAblum.setPostId(post.getPostId());
        List<SnsAblumDO> sns = snsAblumMapper.selectSnsAblumList(snsAblum);
        if (!CollectionUtils.isEmpty(sns)) {
            for (SnsAblumDO ab : sns) {
                PicVo pic = new PicVo();
                pic.setId(ab.getId().intValue());
                pic.setThumbnail(ab.getUrl());
                pic.setUrl(ab.getUrl());
                pic.setIdentifier(ab.getIdentifier());
                list.add(pic);
            }
        }
        return list;
    }
}
