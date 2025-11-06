package cn.metast.tuoke.module.community.controller.admin.cmBlock;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmBlock.cmBlockDO;
import cn.metast.tuoke.module.community.service.cmBlock.CmBlockService;

@Tag(name = "管理后台 - 拉黑记录")
@RestController
@RequestMapping("/community/cm-block")
@Validated
public class cmBlockController {

    @Resource
    private CmBlockService cmBlockService;

    @PostMapping("/create")
    @Operation(summary = "创建拉黑记录")
    @PreAuthorize("@ss.hasPermission('community:cm-block:create')")
    public CommonResult<Long> createcmBlock(@Valid @RequestBody cmBlockSaveReqVO createReqVO) {
        return success(cmBlockService.createcmBlock(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新拉黑记录")
    @PreAuthorize("@ss.hasPermission('community:cm-block:update')")
    public CommonResult<Boolean> updatecmBlock(@Valid @RequestBody cmBlockSaveReqVO updateReqVO) {
        cmBlockService.updatecmBlock(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除拉黑记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('community:cm-block:delete')")
    public CommonResult<Boolean> deletecmBlock(@RequestParam("id") Long id) {
        cmBlockService.deletecmBlock(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得拉黑记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('community:cm-block:query')")
    public CommonResult<cmBlockRespVO> getcmBlock(@RequestParam("id") Long id) {
        cmBlockDO cmBlock = cmBlockService.getcmBlock(id);
        return success(BeanUtils.toBean(cmBlock, cmBlockRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得拉黑记录分页")
    @PreAuthorize("@ss.hasPermission('community:cm-block:query')")
    public CommonResult<PageResult<cmBlockRespVO>> getcmBlockPage(@Valid cmBlockPageReqVO pageReqVO) {
        PageResult<cmBlockDO> pageResult = cmBlockService.getcmBlockPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, cmBlockRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出拉黑记录 Excel")
    @PreAuthorize("@ss.hasPermission('community:cm-block:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportcmBlockExcel(@Valid cmBlockPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<cmBlockDO> list = cmBlockService.getcmBlockPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "拉黑记录.xls", "数据", cmBlockRespVO.class,
                        BeanUtils.toBean(list, cmBlockRespVO.class));
    }

}
