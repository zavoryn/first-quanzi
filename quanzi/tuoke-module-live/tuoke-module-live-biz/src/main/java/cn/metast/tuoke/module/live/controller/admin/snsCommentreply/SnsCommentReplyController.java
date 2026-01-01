package cn.metast.tuoke.module.live.controller.admin.snsCommentreply;

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

import cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreply.SnsCommentReplyDO;
import cn.metast.tuoke.module.live.service.snsCommentreply.SnsCommentReplyService;

@Tag(name = "管理后台 - 评论回复")
@RestController
@RequestMapping("/live/sns-comment-reply")
@Validated
public class SnsCommentReplyController {

    @Resource
    private SnsCommentReplyService snsCommentReplyService;

    @PostMapping("/create")
    @Operation(summary = "创建评论回复")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:create')")
    public CommonResult<Long> createSnsCommentReply(@Valid @RequestBody SnsCommentReplySaveReqVO createReqVO) {
        return success(snsCommentReplyService.createSnsCommentReply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论回复")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:update')")
    public CommonResult<Boolean> updateSnsCommentReply(@Valid @RequestBody SnsCommentReplySaveReqVO updateReqVO) {
        snsCommentReplyService.updateSnsCommentReply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论回复")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:delete')")
    public CommonResult<Boolean> deleteSnsCommentReply(@RequestParam("id") Long id) {
        snsCommentReplyService.deleteSnsCommentReply(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论回复")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:query')")
    public CommonResult<SnsCommentReplyRespVO> getSnsCommentReply(@RequestParam("id") Long id) {
        SnsCommentReplyDO snsCommentReply = snsCommentReplyService.getSnsCommentReply(id);
        return success(BeanUtils.toBean(snsCommentReply, SnsCommentReplyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论回复分页")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:query')")
    public CommonResult<PageResult<SnsCommentReplyRespVO>> getSnsCommentReplyPage(@Valid SnsCommentReplyPageReqVO pageReqVO) {
        PageResult<SnsCommentReplyDO> pageResult = snsCommentReplyService.getSnsCommentReplyPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsCommentReplyRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论回复 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-comment-reply:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsCommentReplyExcel(@Valid SnsCommentReplyPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsCommentReplyDO> list = snsCommentReplyService.getSnsCommentReplyPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论回复.xls", "数据", SnsCommentReplyRespVO.class,
                        BeanUtils.toBean(list, SnsCommentReplyRespVO.class));
    }

}