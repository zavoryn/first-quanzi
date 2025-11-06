package cn.metast.tuoke.module.community.controller.admin.cmCollect;

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

import cn.metast.tuoke.module.community.controller.admin.cmCollect.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCollect.CmCollectDO;
import cn.metast.tuoke.module.community.service.cmCollect.CmCollectService;

@Tag(name = "管理后台 - 收藏记录")
@RestController
@RequestMapping("/community/cm-collect")
@Validated
public class CmCollectController {

    @Resource
    private CmCollectService cmCollectService;

    @PostMapping("/create")
    @Operation(summary = "创建收藏记录")
    //@PreAuthorize("@ss.hasPermission('community:cm-collect:create')")
    public CommonResult<Long> createCmCollect(@Valid @RequestBody CmCollectSaveReqVO createReqVO) {
        return success(cmCollectService.createCmCollect(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收藏记录")
    //@PreAuthorize("@ss.hasPermission('community:cm-collect:update')")
    public CommonResult<Boolean> updateCmCollect(@Valid @RequestBody CmCollectSaveReqVO updateReqVO) {
        cmCollectService.updateCmCollect(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收藏记录")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-collect:delete')")
    public CommonResult<Boolean> deleteCmCollect(@RequestParam("id") Long id) {
        cmCollectService.deleteCmCollect(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收藏记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
   // @PreAuthorize("@ss.hasPermission('community:cm-collect:query')")
    public CommonResult<CmCollectRespVO> getCmCollect(@RequestParam("id") Long id) {
        CmCollectDO cmCollect = cmCollectService.getCmCollect(id);
        return success(BeanUtils.toBean(cmCollect, CmCollectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收藏记录分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-collect:query')")
    public CommonResult<PageResult<CmCollectRespVO>> getCmCollectPage(@Valid CmCollectPageReqVO pageReqVO) {
        PageResult<CmCollectDO> pageResult = cmCollectService.getCmCollectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmCollectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收藏记录 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-collect:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmCollectExcel(@Valid CmCollectPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCollectDO> list = cmCollectService.getCmCollectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收藏记录.xls", "数据", CmCollectRespVO.class,
                        BeanUtils.toBean(list, CmCollectRespVO.class));
    }

}
