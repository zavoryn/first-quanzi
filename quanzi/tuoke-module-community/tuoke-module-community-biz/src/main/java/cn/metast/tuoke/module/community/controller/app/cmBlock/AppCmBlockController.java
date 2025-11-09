package cn.metast.tuoke.module.community.controller.app.cmBlock;

import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.cmBlockSaveReqVO;
import cn.metast.tuoke.module.community.service.cmBlock.CmBlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 圈子拉黑")
@RestController
@RequestMapping("/community/cm-block")
@Validated
public class AppCmBlockController {

    @Resource
    private CmBlockService cmBlockService;

    @PostMapping("/block")
    @Operation(summary = "拉黑用户")
    public CommonResult<Long> blockUser(@Valid @RequestBody cmBlockSaveReqVO createReqVO) {
        // 设置当前登录用户为拉黑发起人
        createReqVO.setUserId(getLoginUserId());

        // 去重校验：检查是否已存在拉黑记录
        if (cmBlockService.existsBlock(createReqVO.getUserId(), createReqVO.getBlockUserId(), createReqVO.getTopicId())) {
            return success(-1L); // 返回-1表示已拉黑，前端可据此提示
        }

        return success(cmBlockService.createcmBlock(createReqVO));
    }

}
