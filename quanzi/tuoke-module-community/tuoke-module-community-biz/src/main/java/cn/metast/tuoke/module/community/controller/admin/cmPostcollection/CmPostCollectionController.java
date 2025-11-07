package cn.metast.tuoke.module.community.controller.admin.cmPostcollection;

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

import cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostcollection.CmPostCollectionDO;
import cn.metast.tuoke.module.community.service.cmPostcollection.CmPostCollectionService;

@Tag(name = "管理后台 - 用户帖子中间")
@RestController
@RequestMapping("/community/cm-post-collection")
@Validated
public class CmPostCollectionController {

    @Resource
    private CmPostCollectionService cmPostCollectionService;

    @PostMapping("/create")
    @Operation(summary = "创建用户帖子中间")
    //@PreAuthorize("@ss.hasPermission('community:cm-post-collection:create')")
    public CommonResult<Long> createCmPostCollection(@Valid @RequestBody CmPostCollectionSaveReqVO createReqVO) {
        return success(cmPostCollectionService.createCmPostCollection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户帖子中间")
    //@PreAuthorize("@ss.hasPermission('community:cm-post-collection:update')")
    public CommonResult<Boolean> updateCmPostCollection(@Valid @RequestBody CmPostCollectionSaveReqVO updateReqVO) {
        cmPostCollectionService.updateCmPostCollection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户帖子中间")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-post-collection:delete')")
    public CommonResult<Boolean> deleteCmPostCollection(@RequestParam("id") Long id) {
        cmPostCollectionService.deleteCmPostCollection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户帖子中间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-post-collection:query')")
    public CommonResult<CmPostCollectionRespVO> getCmPostCollection(@RequestParam("id") Long id) {
        CmPostCollectionDO cmPostCollection = cmPostCollectionService.getCmPostCollection(id);
        return success(BeanUtils.toBean(cmPostCollection, CmPostCollectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户帖子中间分页")
   // @PreAuthorize("@ss.hasPermission('community:cm-post-collection:query')")
    public CommonResult<PageResult<CmPostCollectionRespVO>> getCmPostCollectionPage(@Valid CmPostCollectionPageReqVO pageReqVO) {
        PageResult<CmPostCollectionDO> pageResult = cmPostCollectionService.getCmPostCollectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmPostCollectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户帖子中间 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-post-collection:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmPostCollectionExcel(@Valid CmPostCollectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmPostCollectionDO> list = cmPostCollectionService.getCmPostCollectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户帖子中间.xls", "数据", CmPostCollectionRespVO.class,
                        BeanUtils.toBean(list, CmPostCollectionRespVO.class));
    }

}
