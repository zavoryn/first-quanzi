package cn.metast.tuoke.module.live.controller.admin.snsComment;

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

import cn.metast.tuoke.module.live.controller.admin.snsComment.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import cn.metast.tuoke.module.live.service.snsComment.SnsCommentService;

@Tag(name = "管理后台 - 评论")
@RestController
@RequestMapping("/live/sns-comment")
@Validated
public class SnsCommentController {

    @Resource
    private SnsCommentService snsCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    @PreAuthorize("@ss.hasPermission('live:sns-comment:create')")
    public CommonResult<Long> createSnsComment(@Valid @RequestBody SnsCommentSaveReqVO createReqVO) {
        return success(snsCommentService.createSnsComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论")
    @PreAuthorize("@ss.hasPermission('live:sns-comment:update')")
    public CommonResult<Boolean> updateSnsComment(@Valid @RequestBody SnsCommentSaveReqVO updateReqVO) {
        snsCommentService.updateSnsComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-comment:delete')")
    public CommonResult<Boolean> deleteSnsComment(@RequestParam("id") Long id) {
        snsCommentService.deleteSnsComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-comment:query')")
    public CommonResult<SnsCommentRespVO> getSnsComment(@RequestParam("id") Long id) {
        SnsCommentDO snsComment = snsCommentService.getSnsComment(id);
        return success(BeanUtils.toBean(snsComment, SnsCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    @PreAuthorize("@ss.hasPermission('live:sns-comment:query')")
    public CommonResult<PageResult<SnsCommentRespVO>> getSnsCommentPage(@Valid SnsCommentPageReqVO pageReqVO) {
        PageResult<SnsCommentDO> pageResult = snsCommentService.getSnsCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsCommentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出评论 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-comment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsCommentExcel(@Valid SnsCommentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsCommentDO> list = snsCommentService.getSnsCommentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "评论.xls", "数据", SnsCommentRespVO.class,
                        BeanUtils.toBean(list, SnsCommentRespVO.class));
    }

}