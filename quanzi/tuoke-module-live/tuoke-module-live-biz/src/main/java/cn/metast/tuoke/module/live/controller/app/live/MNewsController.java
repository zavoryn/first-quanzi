package cn.metast.tuoke.module.live.controller.app.live;
import cn.hutool.core.collection.CollUtil;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.SnsNewsPageReqVO;
import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.SnsNewsRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostPageReqVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.controller.admin.vo.PostListVo;
import cn.metast.tuoke.module.live.service.dynaService.IDynaService;
import cn.metast.tuoke.module.live.service.snsPost.SnsPostService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Validated
@RestController
@RequestMapping("/api/news")
public class MNewsController{
    @Autowired
    private SnsPostService snsPostService;
    @Autowired
    private IDynaService dynaService;
    @GetMapping("/getNewsLst")
    @PermitAll
    public CommonResult<PageResult<SnsPostSaveReqVO>> getServLst(HttpServletRequest request) {

        String searchKey = request.getParameter("searchKey");
        Integer pageSize =10;
        Integer pageNum=1;
        if(request.getParameter("pageSize")!=null){
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        if(request.getParameter("pageNum")!=null){
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }

        SnsPostPageReqVO snsPost=new SnsPostPageReqVO();
        snsPost.setTitle(searchKey);
        String param = request.getParameter("param");
        if(StringUtils.isNotEmpty(param)){
            snsPost.setNewsConfig(Long.parseLong(param));
        }
        String spType = request.getParameter("spType");
        if(StringUtils.isNotEmpty(spType)){
            snsPost.setSpType(spType);
        }
        Long uid = SecurityFrameworkUtils.getLoginUserId();
        PageResult<SnsPostSaveReqVO> pageResult = snsPostService.selectSnsNewsListApp(snsPost);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }
        List<PostListVo> ll = new ArrayList<PostListVo>();
        for(SnsPostSaveReqVO post : pageResult.getList()){
            PostListVo postInfo = dynaService.getPostInfo(post, uid);
            ll.add(postInfo);
        }
        return success(BeanUtils.toBean(pageResult, SnsPostSaveReqVO.class));
    }
}
