package cn.metast.tuoke.module.live.service.dynaService;

import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.controller.admin.vo.PostInfoVo;
import cn.metast.tuoke.module.live.controller.admin.vo.PostListVo;

public interface IDynaService {
    PostListVo getPostInfo(SnsPostSaveReqVO snsPostSaveReqVO, Long uid);
    public PostInfoVo getPostInfo(String postType, Long postId, Long uid);
}
