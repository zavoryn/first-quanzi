package cn.metast.tuoke.module.live.controller.admin.snsAblum;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import cn.metast.tuoke.module.live.service.snsAblum.SnsAblumService;

@Tag(name = "管理后台 - 相册信息")
@RestController
@RequestMapping("/live/sns-ablum")
@Validated
public class SnsAblumController {

    @Resource
    private SnsAblumService snsAblumService;

    @PostMapping("/create")
    @Operation(summary = "创建相册信息")
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:create')")
    public CommonResult<Long> createSnsAblum(@Valid @RequestBody SnsAblumSaveReqVO createReqVO) {
        return success(snsAblumService.createSnsAblum(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新相册信息")
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:update')")
    public CommonResult<Boolean> updateSnsAblum(@Valid @RequestBody SnsAblumSaveReqVO updateReqVO) {
        snsAblumService.updateSnsAblum(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除相册信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:delete')")
    public CommonResult<Boolean> deleteSnsAblum(@RequestParam("id") Long id) {
        snsAblumService.deleteSnsAblum(id);
        return success(true);
    }
    /**
     * 获取相册信息详细信息
     */
    @GetMapping(value = "/{id}")
    public CommonResult<List> getInfo(@PathVariable("id") Long id)
    {
        List<SnsAblumDO> snsAblums=snsAblumService.selectSnsAblumPostId(id);
        if(CollectionUtils.isNotEmpty(snsAblums)){
            for(SnsAblumDO sns:snsAblums){
                if(StringUtils.isNotEmpty(sns.getUrl())){
                    sns.setUrl(sns.getUrl());
                }
            }
        }
        return success(snsAblums);
    }


    @GetMapping("/get")
    @Operation(summary = "获得相册信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:query')")
    public CommonResult<SnsAblumRespVO> getSnsAblum(@RequestParam("id") Long id) {
        SnsAblumDO snsAblum = snsAblumService.getSnsAblum(id);
        return success(BeanUtils.toBean(snsAblum, SnsAblumRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得相册信息分页")
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:query')")
    public CommonResult<PageResult<SnsAblumRespVO>> getSnsAblumPage(@Valid SnsAblumPageReqVO pageReqVO) {
        PageResult<SnsAblumDO> pageResult = snsAblumService.getSnsAblumPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsAblumRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出相册信息 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-ablum:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsAblumExcel(@Valid SnsAblumPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsAblumDO> list = snsAblumService.getSnsAblumPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "相册信息.xls", "数据", SnsAblumRespVO.class,
                        BeanUtils.toBean(list, SnsAblumRespVO.class));
    }

}
