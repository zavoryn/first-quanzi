package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmx.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.*;
import cn.metast.tuoke.module.kaifa.service.email.emailmx.*;

@Tag(name = "管理后台 - 邮箱域名MX值")
@RestController
@RequestMapping("/kaifa/email-mx")
@Validated
public class EmailMxController {

    @Resource
    private EmailMxService emailMxService;

    @PostMapping("/create")
    @Operation(summary = "创建邮箱域名MX值")
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:create')")
    public CommonResult<Long> createEmailMx(@Valid @RequestBody EmailMxSaveReqVO createReqVO) {
        return success(emailMxService.createEmailMx(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮箱域名MX值")
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:update')")
    public CommonResult<Boolean> updateEmailMx(@Valid @RequestBody EmailMxSaveReqVO updateReqVO) {
        emailMxService.updateEmailMx(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除邮箱域名MX值")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:delete')")
    public CommonResult<Boolean> deleteEmailMx(@RequestParam("id") Long id) {
        emailMxService.deleteEmailMx(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得邮箱域名MX值")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:query')")
    public CommonResult<EmailMxRespVO> getEmailMx(@RequestParam("id") Long id) {
        EmailMxDO emailMx = emailMxService.getEmailMx(id);
        return success(BeanUtils.toBean(emailMx, EmailMxRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得邮箱域名MX值分页")
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:query')")
    public CommonResult<PageResult<EmailMxRespVO>> getEmailMxPage(@Valid EmailMxPageReqVO pageReqVO) {
        PageResult<EmailMxDO> pageResult = emailMxService.getEmailMxPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EmailMxRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出邮箱域名MX值 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:email-mx:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEmailMxExcel(@Valid EmailMxPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EmailMxDO> list = emailMxService.getEmailMxPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "邮箱域名MX值.xls", "数据", EmailMxRespVO.class,
                        BeanUtils.toBean(list, EmailMxRespVO.class));
    }

}
