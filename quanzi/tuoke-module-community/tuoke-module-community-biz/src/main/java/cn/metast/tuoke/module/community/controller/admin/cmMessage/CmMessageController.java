package cn.metast.tuoke.module.community.controller.admin.cmMessage;

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

import cn.metast.tuoke.module.community.controller.admin.cmMessage.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmMessage.CmMessageDO;
import cn.metast.tuoke.module.community.service.cmMessage.CmMessageService;

@Tag(name = "管理后台 - 圈子消息")
@RestController
@RequestMapping("/community/cm-message")
@Validated
public class CmMessageController {

    @Resource
    private CmMessageService cmMessageService;

    @PostMapping("/create")
    @Operation(summary = "创建圈子消息")
    //@PreAuthorize("@ss.hasPermission('community:cm-message:create')")
    public CommonResult<Long> createCmMessage(@Valid @RequestBody CmMessageSaveReqVO createReqVO) {
        return success(cmMessageService.createCmMessage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子消息")
    //@PreAuthorize("@ss.hasPermission('community:cm-message:update')")
    public CommonResult<Boolean> updateCmMessage(@Valid @RequestBody CmMessageSaveReqVO updateReqVO) {
        cmMessageService.updateCmMessage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子消息")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-message:delete')")
    public CommonResult<Boolean> deleteCmMessage(@RequestParam("id") Long id) {
        cmMessageService.deleteCmMessage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子消息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-message:query')")
    public CommonResult<CmMessageRespVO> getCmMessage(@RequestParam("id") Long id) {
        CmMessageDO cmMessage = cmMessageService.getCmMessage(id);
        return success(BeanUtils.toBean(cmMessage, CmMessageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子消息分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-message:query')")
    public CommonResult<PageResult<CmMessageRespVO>> getCmMessagePage(@Valid CmMessagePageReqVO pageReqVO) {
        PageResult<CmMessageDO> pageResult = cmMessageService.getCmMessagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmMessageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子消息 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-message:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmMessageExcel(@Valid CmMessagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmMessageDO> list = cmMessageService.getCmMessagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子消息.xls", "数据", CmMessageRespVO.class,
                        BeanUtils.toBean(list, CmMessageRespVO.class));
    }

}
