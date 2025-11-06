package cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs;

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

import cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentthumbs.CmCommentThumbsDO;
import cn.metast.tuoke.module.community.service.cmCommentthumbs.CmCommentThumbsService;

@Tag(name = "管理后台 - 评论用户关联")
@RestController
@RequestMapping("/community/cm-comment-thumbs")
@Validated
public class CmCommentThumbsController {

    @Resource
    private CmCommentThumbsService cmCommentThumbsService;

    @PostMapping("/create")
    @Operation(summary = "创建评论用户关联")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:create')")
    public CommonResult<Long> createCmCommentThumbs(@Valid @RequestBody CmCommentThumbsSaveReqVO createReqVO) {
        return success(cmCommentThumbsService.createCmCommentThumbs(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论用户关联")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:update')")
    public CommonResult<Boolean> updateCmCommentThumbs(@Valid @RequestBody CmCommentThumbsSaveReqVO updateReqVO) {
        cmCommentThumbsService.updateCmCommentThumbs(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论用户关联")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:delete')")
    public CommonResult<Boolean> deleteCmCommentThumbs(@RequestParam("id") Long id) {
        cmCommentThumbsService.deleteCmCommentThumbs(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论用户关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:query')")
    public CommonResult<CmCommentThumbsRespVO> getCmCommentThumbs(@RequestParam("id") Long id) {
        CmCommentThumbsDO cmCommentThumbs = cmCommentThumbsService.getCmCommentThumbs(id);
        return success(BeanUtils.toBean(cmCommentThumbs, CmCommentThumbsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论用户关联分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:query')")
    public CommonResult<PageResult<CmCommentThumbsRespVO>> getCmCommentThumbsPage(@Valid CmCommentThumbsPageReqVO pageReqVO) {
        PageResult<CmCommentThumbsDO> pageResult = cmCommentThumbsService.getCmCommentThumbsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmCommentThumbsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论用户关联 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-thumbs:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmCommentThumbsExcel(@Valid CmCommentThumbsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCommentThumbsDO> list = cmCommentThumbsService.getCmCommentThumbsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论用户关联.xls", "数据", CmCommentThumbsRespVO.class,
                        BeanUtils.toBean(list, CmCommentThumbsRespVO.class));
    }

}
