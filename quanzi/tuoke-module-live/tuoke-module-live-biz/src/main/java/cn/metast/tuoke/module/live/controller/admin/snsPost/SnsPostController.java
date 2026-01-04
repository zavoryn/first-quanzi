package cn.metast.tuoke.module.live.controller.admin.snsPost;

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

import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsPost.SnsPostDO;
import cn.metast.tuoke.module.live.service.snsPost.SnsPostService;

@Tag(name = "管理后台 - 动态信息")
@RestController
@RequestMapping("/live/sns-post")
@Validated
public class SnsPostController {

    @Resource
    private SnsPostService snsPostService;

    @PostMapping("/create")
    @Operation(summary = "创建动态信息")
    public CommonResult<Long> createSnsPost(@Valid @RequestBody SnsPostSaveReqVO createReqVO) {
        return success(snsPostService.createSnsPost(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新动态信息")
    public CommonResult<Boolean> updateSnsPost(@Valid @RequestBody SnsPostSaveReqVO updateReqVO) {
        snsPostService.updateSnsPost(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除动态信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsPost(@RequestParam("id") Long id) {
        snsPostService.deleteSnsPost(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得动态信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsPostRespVO> getSnsPost(@RequestParam("id") Long id) {
        SnsPostDO snsPost = snsPostService.getSnsPost(id);
        return success(BeanUtils.toBean(snsPost, SnsPostRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得动态信息分页")
    public CommonResult<PageResult<SnsPostRespVO>> getSnsPostPage(@Valid SnsPostPageReqVO pageReqVO) {
        PageResult<SnsPostDO> pageResult = snsPostService.getSnsPostPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsPostRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出动态信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsPostExcel(@Valid SnsPostPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsPostDO> list = snsPostService.getSnsPostPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "动态信息.xls", "数据", SnsPostRespVO.class,
                        BeanUtils.toBean(list, SnsPostRespVO.class));
    }

}
