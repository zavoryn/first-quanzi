package cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
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

import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord.MpTaskRecordDO;
import cn.metast.tuoke.module.mp.service.mpTaskrecord.MpTaskRecordService;

@Tag(name = "管理后台 - 公众号发送记录")
@RestController
@RequestMapping("/mp/task-record")
@Validated
public class MpTaskRecordController {

    @Resource
    private MpTaskRecordService taskRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建公众号发送记录")
    //@PreAuthorize("@ss.hasPermission('mp:task-record:create')")
    public CommonResult<Long> createTaskRecord(@Valid @RequestBody MpTaskRecordSaveReqVO createReqVO) {
        return success(taskRecordService.createTaskRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公众号发送记录")
   //@PreAuthorize("@ss.hasPermission('mp:task-record:update')")
    public CommonResult<Boolean> updateTaskRecord(@Valid @RequestBody MpTaskRecordSaveReqVO updateReqVO) {
        taskRecordService.updateTaskRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公众号发送记录")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('mp:task-record:delete')")
    public CommonResult<Boolean> deleteTaskRecord(@RequestParam("id") Long id) {
        taskRecordService.deleteTaskRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公众号发送记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('mp:task-record:query')")
    public CommonResult<MpTaskRecordRespVO> getTaskRecord(@RequestParam("id") Long id) {
        MpTaskRecordDO taskRecord = taskRecordService.getTaskRecord(id);
        return success(BeanUtils.toBean(taskRecord, MpTaskRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公众号发送记录分页")
    //@PreAuthorize("@ss.hasPermission('mp:task-record:query')")
    public CommonResult<PageResult<MpTaskRecordRespVO>> getTaskRecordPage(@Valid MpTaskRecordPageReqVO pageReqVO) {
        PageResult<MpTaskRecordDO> pageResult = taskRecordService.getTaskRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MpTaskRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公众号发送记录 Excel")
    //@PreAuthorize("@ss.hasPermission('mp:task-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTaskRecordExcel(@Valid MpTaskRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MpTaskRecordDO> list = taskRecordService.getTaskRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公众号发送记录.xls", "数据", MpTaskRecordRespVO.class,
                        BeanUtils.toBean(list, MpTaskRecordRespVO.class));
    }

}
