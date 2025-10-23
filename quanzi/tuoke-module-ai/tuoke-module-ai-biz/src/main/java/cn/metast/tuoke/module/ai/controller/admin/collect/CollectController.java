package cn.metast.tuoke.module.ai.controller.admin.collect;

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

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.metast.tuoke.module.ai.controller.admin.collect.vo.*;
import cn.metast.tuoke.module.ai.dal.dataobject.collect.CollectDO;
import cn.metast.tuoke.module.ai.service.collect.CollectService;

@Tag(name = "管理后台 - AI 功能 收藏")
@RestController
@RequestMapping("/ai/collect")
@Validated
public class CollectController {

    @Resource
    private CollectService collectService;

    @PostMapping("/create")
    @Operation(summary = "创建AI 功能 收藏")
//    @PreAuthorize("@ss.hasPermission('ai:collect:create')")
    public CommonResult<Long> createCollect(@Valid @RequestBody CollectSaveReqVO createReqVO) {
        createReqVO.setUid(getLoginUserId());
        createReqVO.setStatus(0);
        return success(collectService.createCollect(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新AI 功能 收藏")
//    @PreAuthorize("@ss.hasPermission('ai:collect:update')")
    public CommonResult<Boolean> updateCollect(@Valid @RequestBody CollectSaveReqVO updateReqVO) {
        collectService.updateCollect(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除AI 功能 收藏")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('ai:collect:delete')")
    public CommonResult<Boolean> deleteCollect(@RequestParam("id") Long id) {
        collectService.deleteCollect(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得AI 功能 收藏")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('ai:collect:query')")
    public CommonResult<CollectRespVO> getCollect(@RequestParam("id") Long id) {
        CollectDO collect = collectService.getCollect(id);
        return success(BeanUtils.toBean(collect, CollectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得AI 功能 收藏分页")
//    @PreAuthorize("@ss.hasPermission('ai:collect:query')")
    public CommonResult<PageResult<CollectRespVO>> getCollectPage(@Valid CollectPageReqVO pageReqVO) {
        PageResult<CollectDO> pageResult = collectService.getCollectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CollectRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得AI 功能 收藏分页")
//    @PreAuthorize("@ss.hasPermission('ai:collect:query')")
    public CommonResult<List<CollectRespVO>> getCollectList(@Valid CollectPageReqVO pageReqVO) {
        if(pageReqVO.getUid() == null){
            pageReqVO.setUid(getLoginUserId());
        }
        List<CollectDO> list = collectService.getCollectList(pageReqVO);
        return success(BeanUtils.toBean(list, CollectRespVO.class));
    }

}
