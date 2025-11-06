package cn.metast.tuoke.module.community.controller.admin.cmPostlike;

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

import cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.module.community.service.cmPostlike.CmPostLikeService;

@Tag(name = "管理后台 - 帖子点赞")
@RestController
@RequestMapping("/community/cm-post-like")
@Validated
public class CmPostLikeController {

    @Resource
    private CmPostLikeService cmPostLikeService;

    @PostMapping("/create")
    @Operation(summary = "创建帖子点赞")
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:create')")
    public CommonResult<Integer> createCmPostLike(@Valid @RequestBody CmPostLikeSaveReqVO createReqVO) {
        return success(cmPostLikeService.createCmPostLike(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新帖子点赞")
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:update')")
    public CommonResult<Boolean> updateCmPostLike(@Valid @RequestBody CmPostLikeSaveReqVO updateReqVO) {
        cmPostLikeService.updateCmPostLike(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除帖子点赞")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:delete')")
    public CommonResult<Boolean> deleteCmPostLike(@RequestParam("id") Integer id) {
        cmPostLikeService.deleteCmPostLike(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得帖子点赞")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:query')")
    public CommonResult<CmPostLikeRespVO> getCmPostLike(@RequestParam("id") Integer id) {
        CmPostLikeDO cmPostLike = cmPostLikeService.getCmPostLike(id);
        return success(BeanUtils.toBean(cmPostLike, CmPostLikeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得帖子点赞分页")
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:query')")
    public CommonResult<PageResult<CmPostLikeRespVO>> getCmPostLikePage(@Valid CmPostLikePageReqVO pageReqVO) {
        PageResult<CmPostLikeDO> pageResult = cmPostLikeService.getCmPostLikePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmPostLikeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出帖子点赞 Excel")
    @PreAuthorize("@ss.hasPermission('community:cm-post-like:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmPostLikeExcel(@Valid CmPostLikePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmPostLikeDO> list = cmPostLikeService.getCmPostLikePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "帖子点赞.xls", "数据", CmPostLikeRespVO.class,
                        BeanUtils.toBean(list, CmPostLikeRespVO.class));
    }

}