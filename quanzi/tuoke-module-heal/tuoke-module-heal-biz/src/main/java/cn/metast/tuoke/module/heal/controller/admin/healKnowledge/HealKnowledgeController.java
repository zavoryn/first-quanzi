package cn.metast.tuoke.module.heal.controller.admin.healKnowledge;

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

import cn.metast.tuoke.module.heal.controller.admin.healKnowledge.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healKnowledge.HealKnowledgeDO;
import cn.metast.tuoke.module.heal.service.healKnowledge.HealKnowledgeService;

@Tag(name = "管理后台 - 健康知识")
@RestController
@RequestMapping("/heal/knowledge")
@Validated
public class HealKnowledgeController {

    @Resource
    private HealKnowledgeService knowledgeService;

    @PostMapping("/create")
    @Operation(summary = "创建健康知识")
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:create')")
    public CommonResult<Long> createKnowledge(@Valid @RequestBody HealKnowledgeSaveReqVO createReqVO) {
        return success(knowledgeService.createKnowledge(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新健康知识")
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:update')")
    public CommonResult<Boolean> updateKnowledge(@Valid @RequestBody HealKnowledgeSaveReqVO updateReqVO) {
        knowledgeService.updateKnowledge(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除健康知识")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:delete')")
    public CommonResult<Boolean> deleteKnowledge(@RequestParam("id") Long id) {
        knowledgeService.deleteKnowledge(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得健康知识")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:query')")
    public CommonResult<HealKnowledgeRespVO> getKnowledge(@RequestParam("id") Long id) {
        HealKnowledgeDO knowledge = knowledgeService.getKnowledge(id);
        return success(BeanUtils.toBean(knowledge, HealKnowledgeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得健康知识分页")
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:query')")
    public CommonResult<PageResult<HealKnowledgeRespVO>> getKnowledgePage(@Valid HealKnowledgePageReqVO pageReqVO) {
        PageResult<HealKnowledgeDO> pageResult = knowledgeService.getKnowledgePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealKnowledgeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出健康知识 Excel")
    //@PreAuthorize("@ss.hasPermission('heal:knowledge:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportKnowledgeExcel(@Valid HealKnowledgePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealKnowledgeDO> list = knowledgeService.getKnowledgePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "健康知识.xls", "数据", HealKnowledgeRespVO.class,
                        BeanUtils.toBean(list, HealKnowledgeRespVO.class));
    }

}
