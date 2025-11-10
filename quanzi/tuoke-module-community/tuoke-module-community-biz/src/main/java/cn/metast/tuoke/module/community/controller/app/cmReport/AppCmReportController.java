package cn.metast.tuoke.module.community.controller.app.cmReport;

import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.module.community.controller.admin.cmReport.vo.cmReportSaveReqVO;
import cn.metast.tuoke.module.community.service.cmReport.cmReportService;
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

@Tag(name = "用户 APP - 圈子举报")
@RestController
@RequestMapping("/community/cm-report")
@Validated
public class AppCmReportController {

    @Resource
    private cmReportService cmReportService;

    @PostMapping("/report")
    @Operation(summary = "举报用户")
    public CommonResult<Long> reportUser(@Valid @RequestBody cmReportSaveReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId());
        return success(cmReportService.createcmReport(createReqVO));
    }

}