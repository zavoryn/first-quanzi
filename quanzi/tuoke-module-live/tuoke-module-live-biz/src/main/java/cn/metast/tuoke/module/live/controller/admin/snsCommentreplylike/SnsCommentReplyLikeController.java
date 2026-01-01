package cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike;

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

import cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreplylike.SnsCommentReplyLikeDO;
import cn.metast.tuoke.module.live.service.snsCommentreplylike.SnsCommentReplyLikeService;

@Tag(name = "管理后台 - 评论回复点赞人数")
@RestController
@RequestMapping("/live/sns-comment-reply-like")
@Validated
public class SnsCommentReplyLikeController {

    @Resource
    private SnsCommentReplyLikeService snsCommentReplyLikeService;

    @PostMapping("/create")
    @Operation(summary = "创建评论回复点赞人数")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:create')")
    public CommonResult<Long> createSnsCommentReplyLike(@Valid @RequestBody SnsCommentReplyLikeSaveReqVO createReqVO) {
        return success(snsCommentReplyLikeService.createSnsCommentReplyLike(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论回复点赞人数")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:update')")
    public CommonResult<Boolean> updateSnsCommentReplyLike(@Valid @RequestBody SnsCommentReplyLikeSaveReqVO updateReqVO) {
        snsCommentReplyLikeService.updateSnsCommentReplyLike(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论回复点赞人数")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:delete')")
    public CommonResult<Boolean> deleteSnsCommentReplyLike(@RequestParam("id") Long id) {
        snsCommentReplyLikeService.deleteSnsCommentReplyLike(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论回复点赞人数")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:query')")
    public CommonResult<SnsCommentReplyLikeRespVO> getSnsCommentReplyLike(@RequestParam("id") Long id) {
        SnsCommentReplyLikeDO snsCommentReplyLike = snsCommentReplyLikeService.getSnsCommentReplyLike(id);
        return success(BeanUtils.toBean(snsCommentReplyLike, SnsCommentReplyLikeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论回复点赞人数分页")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:query')")
    public CommonResult<PageResult<SnsCommentReplyLikeRespVO>> getSnsCommentReplyLikePage(@Valid SnsCommentReplyLikePageReqVO pageReqVO) {
        PageResult<SnsCommentReplyLikeDO> pageResult = snsCommentReplyLikeService.getSnsCommentReplyLikePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsCommentReplyLikeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论回复点赞人数 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply-like:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsCommentReplyLikeExcel(@Valid SnsCommentReplyLikePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsCommentReplyLikeDO> list = snsCommentReplyLikeService.getSnsCommentReplyLikePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论回复点赞人数.xls", "数据", SnsCommentReplyLikeRespVO.class,
                        BeanUtils.toBean(list, SnsCommentReplyLikeRespVO.class));
    }

}