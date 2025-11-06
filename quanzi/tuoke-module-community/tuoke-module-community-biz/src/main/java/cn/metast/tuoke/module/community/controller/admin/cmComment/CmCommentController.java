package cn.metast.tuoke.module.community.controller.admin.cmComment;

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

import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.service.cmComment.CmCommentService;

@Tag(name = "管理后台 - 圈子评论")
@RestController
@RequestMapping("/community/cm-comment")
@Validated
public class CmCommentController {

    @Resource
    private CmCommentService cmCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建圈子评论")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment:create')")
    public CommonResult<Long> createCmComment(@Valid @RequestBody CmCommentSaveReqVO createReqVO) {
        return success(cmCommentService.createCmComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子评论")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment:update')")
    public CommonResult<Boolean> updateCmComment(@Valid @RequestBody CmCommentSaveReqVO updateReqVO) {
        cmCommentService.updateCmComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子评论")
    @Parameter(name = "id", description = "编号", required = true)
   // @PreAuthorize("@ss.hasPermission('community:cm-comment:delete')")
    public CommonResult<Boolean> deleteCmComment(@RequestParam("id") Long id) {
        cmCommentService.deleteCmComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment:query')")
    public CommonResult<CmCommentRespVO> getCmComment(@RequestParam("id") Long id) {
        CmCommentDO cmComment = cmCommentService.getCmComment(id);
        return success(BeanUtils.toBean(cmComment, CmCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子评论分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment:query')")
    public CommonResult<PageResult<CmCommentRespVO>> getCmCommentPage(@Valid CmCommentPageReqVO pageReqVO) {
        PageResult<CmCommentDO> pageResult = cmCommentService.getCmCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmCommentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子评论 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-comment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmCommentExcel(@Valid CmCommentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCommentDO> list = cmCommentService.getCmCommentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子评论.xls", "数据", CmCommentRespVO.class,
                        BeanUtils.toBean(list, CmCommentRespVO.class));
    }

}
