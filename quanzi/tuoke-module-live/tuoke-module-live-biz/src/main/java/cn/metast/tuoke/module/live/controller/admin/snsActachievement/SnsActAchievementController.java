package cn.metast.tuoke.module.live.controller.admin.snsActachievement;

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

import cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActachievement.SnsActAchievementDO;
import cn.metast.tuoke.module.live.service.snsActachievement.SnsActAchievementService;

@Tag(name = "管理后台 - 活动-成绩")
@RestController
@RequestMapping("/live/sns-act-achievement")
@Validated
public class SnsActAchievementController {

    @Resource
    private SnsActAchievementService snsActAchievementService;

    @PostMapping("/create")
    @Operation(summary = "创建活动-成绩")
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:create')")
    public CommonResult<Integer> createSnsActAchievement(@Valid @RequestBody SnsActAchievementSaveReqVO createReqVO) {
        return success(snsActAchievementService.createSnsActAchievement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动-成绩")
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:update')")
    public CommonResult<Boolean> updateSnsActAchievement(@Valid @RequestBody SnsActAchievementSaveReqVO updateReqVO) {
        snsActAchievementService.updateSnsActAchievement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动-成绩")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:delete')")
    public CommonResult<Boolean> deleteSnsActAchievement(@RequestParam("id") Integer id) {
        snsActAchievementService.deleteSnsActAchievement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动-成绩")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:query')")
    public CommonResult<SnsActAchievementRespVO> getSnsActAchievement(@RequestParam("id") Integer id) {
        SnsActAchievementDO snsActAchievement = snsActAchievementService.getSnsActAchievement(id);
        return success(BeanUtils.toBean(snsActAchievement, SnsActAchievementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动-成绩分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:query')")
    public CommonResult<PageResult<SnsActAchievementRespVO>> getSnsActAchievementPage(@Valid SnsActAchievementPageReqVO pageReqVO) {
        PageResult<SnsActAchievementDO> pageResult = snsActAchievementService.getSnsActAchievementPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActAchievementRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动-成绩 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-achievement:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActAchievementExcel(@Valid SnsActAchievementPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActAchievementDO> list = snsActAchievementService.getSnsActAchievementPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动-成绩.xls", "数据", SnsActAchievementRespVO.class,
                        BeanUtils.toBean(list, SnsActAchievementRespVO.class));
    }

}