package cn.metast.tuoke.module.live.controller.admin.snsActuser;

import jakarta.annotation.security.PermitAll;
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

import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActuser.SnsActUserDO;
import cn.metast.tuoke.module.live.service.snsActuser.SnsActUserService;

@Tag(name = "管理后台 - 活动报名人员")
@RestController
@RequestMapping("/live/sns-act-user")
@Validated
public class SnsActUserController {

    @Resource
    private SnsActUserService snsActUserService;

    @PostMapping("/create")
    @Operation(summary = "创建活动报名人员")
    public CommonResult<Long> createSnsActUser(@Valid @RequestBody SnsActUserSaveReqVO createReqVO) {
        return success(snsActUserService.createSnsActUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动报名人员")
    public CommonResult<Boolean> updateSnsActUser(@Valid @RequestBody SnsActUserSaveReqVO updateReqVO) {
        snsActUserService.updateSnsActUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动报名人员")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsActUser(@RequestParam("id") Long id) {
        snsActUserService.deleteSnsActUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动报名人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsActUserRespVO> getSnsActUser(@RequestParam("id") Long id) {
        SnsActUserDO snsActUser = snsActUserService.getSnsActUser(id);
        return success(BeanUtils.toBean(snsActUser, SnsActUserRespVO.class));
    }
    @PermitAll
    @GetMapping("/page")
    @Operation(summary = "获得活动报名人员分页")
    public CommonResult<PageResult<SnsActUserRespVO>> getSnsActUserPage(@Valid SnsActUserPageReqVO pageReqVO) {
        PageResult<SnsActUserRespVO> pageResult = snsActUserService.selectSnsActUserList(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动报名人员 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActUserExcel(@Valid SnsActUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActUserDO> list = snsActUserService.getSnsActUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动报名人员.xls", "数据", SnsActUserRespVO.class,
                        BeanUtils.toBean(list, SnsActUserRespVO.class));
    }

}
