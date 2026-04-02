package cn.metast.tuoke.module.system.api.dept;

import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.system.api.dept.dto.PostRespDTO;
import cn.metast.tuoke.module.system.dal.dataobject.dept.PostDO;
import cn.metast.tuoke.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 岗位 API 实现类
 *
 * @author metast.cn
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

    @Override
    public List<PostRespDTO> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return BeanUtils.toBean(list, PostRespDTO.class);
    }

}
