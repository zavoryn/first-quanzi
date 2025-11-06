package cn.metast.tuoke.module.community.controller.admin.cmLink;

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

import cn.metast.tuoke.module.community.controller.admin.cmLink.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmLink.CmLinkDO;
import cn.metast.tuoke.module.community.service.cmLink.CmLinkService;

@Tag(name = "管理后台 - 首页轮播图")
@RestController
@RequestMapping("/community/cm-link")
@Validated
public class CmLinkController {

    @Resource
    private CmLinkService cmLinkService;

    @PostMapping("/create")
    @Operation(summary = "创建首页轮播图")
    //@PreAuthorize("@ss.hasPermission('community:cm-link:create')")
    public CommonResult<Long> createCmLink(@Valid @RequestBody CmLinkSaveReqVO createReqVO) {
        return success(cmLinkService.createCmLink(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新首页轮播图")
    //@PreAuthorize("@ss.hasPermission('community:cm-link:update')")
    public CommonResult<Boolean> updateCmLink(@Valid @RequestBody CmLinkSaveReqVO updateReqVO) {
        cmLinkService.updateCmLink(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除首页轮播图")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-link:delete')")
    public CommonResult<Boolean> deleteCmLink(@RequestParam("id") Long id) {
        cmLinkService.deleteCmLink(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得首页轮播图")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-link:query')")
    public CommonResult<CmLinkRespVO> getCmLink(@RequestParam("id") Long id) {
        CmLinkDO cmLink = cmLinkService.getCmLink(id);
        return success(BeanUtils.toBean(cmLink, CmLinkRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得首页轮播图分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-link:query')")
    public CommonResult<PageResult<CmLinkRespVO>> getCmLinkPage(@Valid CmLinkPageReqVO pageReqVO) {
        PageResult<CmLinkDO> pageResult = cmLinkService.getCmLinkPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmLinkRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出首页轮播图 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-link:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmLinkExcel(@Valid CmLinkPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmLinkDO> list = cmLinkService.getCmLinkPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "首页轮播图.xls", "数据", CmLinkRespVO.class,
                        BeanUtils.toBean(list, CmLinkRespVO.class));
    }

}
