package cn.metast.tuoke.module.community.controller.admin.cmCommentlike;

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

import cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike.CmCommentLikeDO;
import cn.metast.tuoke.module.community.service.cmCommentlike.CmCommentLikeService;

@Tag(name = "管理后台 - 评论点赞")
@RestController
@RequestMapping("/community/cm-comment-like")
@Validated
public class CmCommentLikeController {

    @Resource
    private CmCommentLikeService cmCommentLikeService;

    @PostMapping("/create")
    @Operation(summary = "创建评论点赞")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:create')")
    public CommonResult<Integer> createCmCommentLike(@Valid @RequestBody CmCommentLikeSaveReqVO createReqVO) {
        return success(cmCommentLikeService.createCmCommentLike(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论点赞")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:update')")
    public CommonResult<Boolean> updateCmCommentLike(@Valid @RequestBody CmCommentLikeSaveReqVO updateReqVO) {
        cmCommentLikeService.updateCmCommentLike(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论点赞")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:delete')")
    public CommonResult<Boolean> deleteCmCommentLike(@RequestParam("id") Integer id) {
        cmCommentLikeService.deleteCmCommentLike(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论点赞")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:query')")
    public CommonResult<CmCommentLikeRespVO> getCmCommentLike(@RequestParam("id") Integer id) {
        CmCommentLikeDO cmCommentLike = cmCommentLikeService.getCmCommentLike(id);
        return success(BeanUtils.toBean(cmCommentLike, CmCommentLikeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论点赞分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:query')")
    public CommonResult<PageResult<CmCommentLikeRespVO>> getCmCommentLikePage(@Valid CmCommentLikePageReqVO pageReqVO) {
        PageResult<CmCommentLikeDO> pageResult = cmCommentLikeService.getCmCommentLikePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmCommentLikeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论点赞 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment-like:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmCommentLikeExcel(@Valid CmCommentLikePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCommentLikeDO> list = cmCommentLikeService.getCmCommentLikePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论点赞.xls", "数据", CmCommentLikeRespVO.class,
                        BeanUtils.toBean(list, CmCommentLikeRespVO.class));
    }

}
