package cn.metast.tuoke.module.system.controller.admin.sms;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.common.util.string.StrUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import cn.metast.tuoke.module.system.controller.admin.sms.vo.log.SmsLogRespVO;
import cn.metast.tuoke.module.system.dal.dataobject.sms.SmsLogDO;
import cn.metast.tuoke.module.system.service.sms.SmsLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 短信日志")
@RestController
@RequestMapping("/system/sms-log")
@Validated
public class SmsLogController {

    @Resource
    private SmsLogService smsLogService;

    @GetMapping("/page")
    @Operation(summary = "获得短信日志分页")
    @PreAuthorize("@ss.hasPermission('system:sms-log:query')")
    public CommonResult<PageResult<SmsLogRespVO>> getSmsLogPage(@Valid SmsLogPageReqVO pageReqVO) {
        PageResult<SmsLogDO> pageResult = smsLogService.getSmsLogPage(pageReqVO);

        // 创建vo对象
        PageResult<SmsLogRespVO> respVOPage = BeanUtils.toBean(pageResult, SmsLogRespVO.class);
        // 使用 StrUtils 进行脱敏
        respVOPage.getList().forEach(item -> item.setMobile(StrUtils.desensitizeMobile(item.getMobile())));

        return success(respVOPage);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出短信日志 Excel")
    @PreAuthorize("@ss.hasPermission('system:sms-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSmsLogExcel(@Valid SmsLogPageReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SmsLogDO> list = smsLogService.getSmsLogPage(exportReqVO).getList();

        // 使用 StrUtils 进行脱敏
        List<SmsLogRespVO> excelList = BeanUtils.toBean(list, SmsLogRespVO.class);

        // 使用 StrUtils 进行脱敏
        excelList.forEach(item -> item.setMobile(StrUtils.desensitizeMobile(item.getMobile())));

        ExcelUtils.write(response, "短信日志.xls", "数据", SmsLogRespVO.class, excelList);
    }

}
