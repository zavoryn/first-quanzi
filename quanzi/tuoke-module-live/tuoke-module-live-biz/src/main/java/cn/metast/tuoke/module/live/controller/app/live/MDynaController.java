package cn.metast.tuoke.module.live.controller.app.live;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.vo.PostInfoVo;
import cn.metast.tuoke.module.live.service.dynaService.IDynaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

/**
 * 动态管理 Controller
 */
@Api("动态管理")
@RestController
@RequestMapping("/api/post")
public class MDynaController{

    @Autowired
    private IDynaService dynaService;

    @ApiOperation("获取详情")
    @GetMapping("/getPostInfo")
    public CommonResult<PostInfoVo> getPostInfo(String postType, Long postId) {
        Long uid = SecurityFrameworkUtils.getLoginUserId();
        if(uid==null){
            uid = -1L;
        }
        PostInfoVo data = dynaService.getPostInfo(postType, postId, uid);

        if (uid == null || uid.equals("") || uid == -1){
            data.setCommentFlag("N");
            return success(data);
        }


        /*if (StringUtils.isBlank(sysUserService.selectUserById(uid).getIscomment())){
            data.setCommentFlag("N");
        }else if (!StringUtils.isBlank(sysUserService.selectUserById(uid).getIscomment())){
            String iscomment = sysUserService.selectUserById(uid).getIscomment();
            if (iscomment.equals("1")){
                data.setCommentFlag("N");
            }else {
                data.setCommentFlag("Y");
            }
        }*/
        return success(data);

    }
}
