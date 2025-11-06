package cn.metast.tuoke.module.community.controller.admin.cmFollow;

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

import cn.metast.tuoke.module.community.controller.admin.cmFollow.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmFollow.CmFollowDO;
import cn.metast.tuoke.module.community.service.cmFollow.CmFollowService;

@Tag(name = "管理后台 - 用户关注中间")
@RestController
@RequestMapping("/community/cm-follow")
@Validated
public class CmFollowController {

    @Resource
    private CmFollowService cmFollowService;

    @PostMapping("/create")
    @Operation(summary = "创建用户关注中间")
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:create')")
    public CommonResult<Long> createCmFollow(@Valid @RequestBody CmFollowSaveReqVO createReqVO) {
        return success(cmFollowService.createCmFollow(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户关注中间")
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:update')")
    public CommonResult<Boolean> updateCmFollow(@Valid @RequestBody CmFollowSaveReqVO updateReqVO) {
        cmFollowService.updateCmFollow(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户关注中间")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:delete')")
    public CommonResult<Boolean> deleteCmFollow(@RequestParam("id") Long id) {
        cmFollowService.deleteCmFollow(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户关注中间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:query')")
    public CommonResult<CmFollowRespVO> getCmFollow(@RequestParam("id") Long id) {
        CmFollowDO cmFollow = cmFollowService.getCmFollow(id);
        return success(BeanUtils.toBean(cmFollow, CmFollowRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户关注中间分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:query')")
    public CommonResult<PageResult<CmFollowRespVO>> getCmFollowPage(@Valid CmFollowPageReqVO pageReqVO) {
        PageResult<CmFollowDO> pageResult = cmFollowService.getCmFollowPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmFollowRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户关注中间 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-follow:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmFollowExcel(@Valid CmFollowPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmFollowDO> list = cmFollowService.getCmFollowPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户关注中间.xls", "数据", CmFollowRespVO.class,
                        BeanUtils.toBean(list, CmFollowRespVO.class));
    }

}
