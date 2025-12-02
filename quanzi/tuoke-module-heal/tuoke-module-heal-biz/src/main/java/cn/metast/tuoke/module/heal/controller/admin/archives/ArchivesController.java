package cn.metast.tuoke.module.heal.controller.admin.archives;

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

import cn.metast.tuoke.module.heal.controller.admin.archives.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.module.heal.service.archives.ArchivesService;

@Tag(name = "管理后台 - 档案信息")
@RestController
@RequestMapping("/heal/archives")
@Validated
public class ArchivesController {

    @Resource
    private ArchivesService archivesService;

    @PostMapping("/create")
    @Operation(summary = "创建档案信息")
    @PreAuthorize("@ss.hasPermission('wennuan:archives:create')")
    public CommonResult<Long> createArchives(@Valid @RequestBody ArchivesSaveReqVO createReqVO) {
        return success(archivesService.createArchives(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新档案信息")
    @PreAuthorize("@ss.hasPermission('wennuan:archives:update')")
    public CommonResult<Boolean> updateArchives(@Valid @RequestBody ArchivesSaveReqVO updateReqVO) {
        archivesService.updateArchives(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除档案信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wennuan:archives:delete')")
    public CommonResult<Boolean> deleteArchives(@RequestParam("id") Long id) {
        archivesService.deleteArchives(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得档案信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wennuan:archives:query')")
    public CommonResult<ArchivesRespVO> getArchives(@RequestParam("id") Long id) {
        ArchivesDO archives = archivesService.getArchives(id);
        return success(BeanUtils.toBean(archives, ArchivesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得档案信息分页")
    @PreAuthorize("@ss.hasPermission('wennuan:archives:query')")
    public CommonResult<PageResult<ArchivesRespVO>> getArchivesPage(@Valid ArchivesPageReqVO pageReqVO) {
        PageResult<ArchivesDO> pageResult = archivesService.getArchivesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ArchivesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出档案信息 Excel")
    @PreAuthorize("@ss.hasPermission('wennuan:archives:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportArchivesExcel(@Valid ArchivesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ArchivesDO> list = archivesService.getArchivesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "档案信息.xls", "数据", ArchivesRespVO.class,
                        BeanUtils.toBean(list, ArchivesRespVO.class));
    }

}
