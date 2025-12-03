package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCourseDo;
import cn.metast.tuoke.module.heal.controller.app.vo.HealCoursePageReqVO;
import cn.metast.tuoke.module.heal.service.healKnowledge.HealKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Api("知识管理")
@RestController
@RequestMapping("/api/knowledge")
public class MKnowledgeController {
    @Resource
    private HealKnowledgeService knowledgeService;

    @GetMapping("/knowledgeList")
    @Operation(summary = "查询健康知识")
    @PermitAll
    public CommonResult<PageResult<HealCourseDo>> createKnowledge(@Valid HealCoursePageReqVO pageReqVO) {
        PageResult<HealCourseDo> pageResult = knowledgeService.getCoursePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealCourseDo.class));
    }
}
