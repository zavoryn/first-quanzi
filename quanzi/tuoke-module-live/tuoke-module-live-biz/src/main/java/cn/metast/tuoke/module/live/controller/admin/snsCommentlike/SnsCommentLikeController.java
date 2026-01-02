package cn.metast.tuoke.module.live.controller.admin.snsCommentlike;

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

import cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentlike.SnsCommentLikeDO;
import cn.metast.tuoke.module.live.service.snsCommentlike.SnsCommentLikeService;

@Tag(name = "管理后台 - 评论点赞人")
@RestController
@RequestMapping("/live/sns-comment-like")
@Validated
public class SnsCommentLikeController {

    @Resource
    private SnsCommentLikeService snsCommentLikeService;

    @PostMapping("/create")
    @Operation(summary = "创建评论点赞人")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:create')")
    public CommonResult<Long> createSnsCommentLike(@Valid @RequestBody SnsCommentLikeSaveReqVO createReqVO) {
        return success(snsCommentLikeService.createSnsCommentLike(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论点赞人")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:update')")
    public CommonResult<Boolean> updateSnsCommentLike(@Valid @RequestBody SnsCommentLikeSaveReqVO updateReqVO) {
        snsCommentLikeService.updateSnsCommentLike(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论点赞人")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:delete')")
    public CommonResult<Boolean> deleteSnsCommentLike(@RequestParam("id") Long id) {
        snsCommentLikeService.deleteSnsCommentLike(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论点赞人")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:query')")
    public CommonResult<SnsCommentLikeRespVO> getSnsCommentLike(@RequestParam("id") Long id) {
        SnsCommentLikeDO snsCommentLike = snsCommentLikeService.getSnsCommentLike(id);
        return success(BeanUtils.toBean(snsCommentLike, SnsCommentLikeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论点赞人分页")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:query')")
    public CommonResult<PageResult<SnsCommentLikeRespVO>> getSnsCommentLikePage(@Valid SnsCommentLikePageReqVO pageReqVO) {
        PageResult<SnsCommentLikeDO> pageResult = snsCommentLikeService.getSnsCommentLikePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsCommentLikeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论点赞人 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-like:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsCommentLikeExcel(@Valid SnsCommentLikePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsCommentLikeDO> list = snsCommentLikeService.getSnsCommentLikePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论点赞人.xls", "数据", SnsCommentLikeRespVO.class,
                        BeanUtils.toBean(list, SnsCommentLikeRespVO.class));
    }

}