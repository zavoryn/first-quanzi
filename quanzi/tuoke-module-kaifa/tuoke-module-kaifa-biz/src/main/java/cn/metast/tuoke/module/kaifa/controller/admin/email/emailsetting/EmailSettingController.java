package cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailsetting.EmailSettingDO;
import cn.metast.tuoke.module.kaifa.service.email.emailsetting.EmailSettingService;
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

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting.vo.*;

@Tag(name = "管理后台 - 公共配置")
@RestController
@RequestMapping("/kaifa/email-setting")
@Validated
public class EmailSettingController {

    @Resource
    private EmailSettingService emailSettingService;

    @PostMapping("/create")
    @Operation(summary = "创建公共配置")
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:create')")
    public CommonResult<Long> createEmailSetting(@Valid @RequestBody EmailSettingSaveReqVO createReqVO) {
        return success(emailSettingService.createEmailSetting(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公共配置")
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:update')")
    public CommonResult<Boolean> updateEmailSetting(@Valid @RequestBody EmailSettingSaveReqVO updateReqVO) {
        emailSettingService.updateEmailSetting(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公共配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:delete')")
    public CommonResult<Boolean> deleteEmailSetting(@RequestParam("id") Long id) {
        emailSettingService.deleteEmailSetting(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公共配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:query')")
    public CommonResult<EmailSettingRespVO> getEmailSetting(@RequestParam("id") Long id) {
        EmailSettingDO emailSetting = emailSettingService.getEmailSetting(id);
        return success(BeanUtils.toBean(emailSetting, EmailSettingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公共配置分页")
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:query')")
    public CommonResult<PageResult<EmailSettingRespVO>> getEmailSettingPage(@Valid EmailSettingPageReqVO pageReqVO) {
        PageResult<EmailSettingDO> pageResult = emailSettingService.getEmailSettingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EmailSettingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公共配置 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:email-setting:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEmailSettingExcel(@Valid EmailSettingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EmailSettingDO> list = emailSettingService.getEmailSettingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公共配置.xls", "数据", EmailSettingRespVO.class,
                        BeanUtils.toBean(list, EmailSettingRespVO.class));
    }

}
