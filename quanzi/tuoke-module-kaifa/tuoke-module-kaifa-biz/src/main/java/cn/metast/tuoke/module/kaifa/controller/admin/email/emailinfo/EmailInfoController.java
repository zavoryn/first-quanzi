package cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo;

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.EmailInfoPageReqVO;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.EmailInfoRespVO;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailinfo.EmailInfoDO;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.EmailMxDO;
import cn.metast.tuoke.module.kaifa.service.email.emailinfo.EmailInfoService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.EmailInfoSaveReqVO;
@Tag(name = "管理后台 - 邮件内容")
@RestController
@RequestMapping("/kaifa/email-info")
@Validated
public class EmailInfoController {

    @Resource
    private EmailInfoService emailInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建邮件内容")
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:create')")
    public CommonResult<Long> createEmailInfo(@Valid @RequestBody EmailInfoSaveReqVO createReqVO) {
        return success(emailInfoService.createEmailInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮件内容")
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:update')")
    public CommonResult<Boolean> updateEmailInfo(@Valid @RequestBody EmailInfoSaveReqVO updateReqVO) {
        emailInfoService.updateEmailInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除邮件内容")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:delete')")
    public CommonResult<Boolean> deleteEmailInfo(@RequestParam("id") Long id) {
        emailInfoService.deleteEmailInfo(id);
        return success(true);
    }

    @GetMapping("/chenkBindEmail")
    public CommonResult<EmailMxDO> chenkBindEmail(String email){
        return success(emailInfoService.chenkBindEmail(email));
    }

    @GetMapping("/get")
    @Operation(summary = "获得邮件内容")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:query')")
    public CommonResult<EmailInfoRespVO> getEmailInfo(@RequestParam("id") Long id) {
        EmailInfoDO emailInfo = emailInfoService.getEmailInfo(id);
        return success(BeanUtils.toBean(emailInfo, EmailInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得邮件内容分页")
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:query')")
    public CommonResult<PageResult<EmailInfoRespVO>> getEmailInfoPage(@Valid EmailInfoPageReqVO pageReqVO) {
        PageResult<EmailInfoDO> pageResult = emailInfoService.getEmailInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EmailInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出邮件内容 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:email-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEmailInfoExcel(@Valid EmailInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EmailInfoDO> list = emailInfoService.getEmailInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "邮件内容.xls", "数据", EmailInfoRespVO.class,
                        BeanUtils.toBean(list, EmailInfoRespVO.class));
    }

}
