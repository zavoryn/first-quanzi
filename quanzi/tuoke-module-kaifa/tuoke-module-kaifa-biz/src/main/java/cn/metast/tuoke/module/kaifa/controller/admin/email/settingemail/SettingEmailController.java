package cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail;

import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail.SettingEmailDO;
import cn.metast.tuoke.module.kaifa.service.email.settingemail.SettingEmailService;
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

import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.*;

@Tag(name = "管理后台 - 邮件配置")
@RestController
@RequestMapping("/kaifa/setting-email")
@Validated
public class SettingEmailController {

    @Resource
    private SettingEmailService settingEmailService;

    @PostMapping("/create")
    @Operation(summary = "创建邮件配置")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:create')")
    public CommonResult<Long> createSettingEmail(@Valid @RequestBody SettingEmailSaveReqVO createReqVO) {
        return success(settingEmailService.createSettingEmail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮件配置")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:update')")
    public CommonResult<Boolean> updateSettingEmail(@Valid @RequestBody SettingEmailSaveReqVO updateReqVO) {
        settingEmailService.updateSettingEmail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除邮件配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:delete')")
    public CommonResult<Boolean> deleteSettingEmail(@RequestParam("id") Long id) {
        settingEmailService.deleteSettingEmail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得邮件配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:query')")
    public CommonResult<SettingEmailRespVO> getSettingEmail(@RequestParam("id") Long id) {
        SettingEmailDO settingEmail = settingEmailService.getSettingEmail(id);
        return success(BeanUtils.toBean(settingEmail, SettingEmailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得邮件配置分页")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:query')")
    public CommonResult<PageResult<SettingEmailRespVO>> getSettingEmailPage(@Valid SettingEmailPageReqVO pageReqVO) {
        PageResult<SettingEmailDO> pageResult = settingEmailService.getSettingEmailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SettingEmailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出邮件配置 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-email:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSettingEmailExcel(@Valid SettingEmailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SettingEmailDO> list = settingEmailService.getSettingEmailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "邮件配置.xls", "数据", SettingEmailRespVO.class,
                        BeanUtils.toBean(list, SettingEmailRespVO.class));
    }

}
