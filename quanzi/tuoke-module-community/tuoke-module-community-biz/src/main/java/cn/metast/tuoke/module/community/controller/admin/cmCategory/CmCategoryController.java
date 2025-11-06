package cn.metast.tuoke.module.community.controller.admin.cmCategory;

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

import cn.metast.tuoke.module.community.controller.admin.cmCategory.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCategory.CmCategoryDO;
import cn.metast.tuoke.module.community.service.cmCategory.CmCategoryService;

@Tag(name = "管理后台 - 圈子分类")
@RestController
@RequestMapping("/community/cm-category")
@Validated
public class CmCategoryController {

    @Resource
    private CmCategoryService cmCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建圈子分类")
    //@PreAuthorize("@ss.hasPermission('community:cm-category:create')")
    public CommonResult<Long> createCmCategory(@Valid @RequestBody CmCategorySaveReqVO createReqVO) {
        return success(cmCategoryService.createCmCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子分类")
    //@PreAuthorize("@ss.hasPermission('community:cm-category:update')")
    public CommonResult<Boolean> updateCmCategory(@Valid @RequestBody CmCategorySaveReqVO updateReqVO) {
        cmCategoryService.updateCmCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子分类")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-category:delete')")
    public CommonResult<Boolean> deleteCmCategory(@RequestParam("id") Long id) {
        cmCategoryService.deleteCmCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-category:query')")
    public CommonResult<CmCategoryRespVO> getCmCategory(@RequestParam("id") Long id) {
        CmCategoryDO cmCategory = cmCategoryService.getCmCategory(id);
        return success(BeanUtils.toBean(cmCategory, CmCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子分类分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-category:query')")
    public CommonResult<PageResult<CmCategoryRespVO>> getCmCategoryPage(@Valid CmCategoryPageReqVO pageReqVO) {
        PageResult<CmCategoryDO> pageResult = cmCategoryService.getCmCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子分类 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmCategoryExcel(@Valid CmCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCategoryDO> list = cmCategoryService.getCmCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子分类.xls", "数据", CmCategoryRespVO.class,
                        BeanUtils.toBean(list, CmCategoryRespVO.class));
    }

}
