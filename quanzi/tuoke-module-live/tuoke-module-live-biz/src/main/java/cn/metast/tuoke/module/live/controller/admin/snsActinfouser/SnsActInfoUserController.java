package cn.metast.tuoke.module.live.controller.admin.snsActinfouser;

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

import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfouser.SnsActInfoUserDO;
import cn.metast.tuoke.module.live.service.snsActinfouser.SnsActInfoUserService;

@Tag(name = "管理后台 - 报名填写用户信息")
@RestController
@RequestMapping("/live/sns-act-info-user")
@Validated
public class SnsActInfoUserController {

    @Resource
    private SnsActInfoUserService snsActInfoUserService;

    @PostMapping("/create")
    @Operation(summary = "创建报名填写用户信息")
    public CommonResult<Long> createSnsActInfoUser(@Valid @RequestBody SnsActInfoUserSaveReqVO createReqVO) {
        return success(snsActInfoUserService.createSnsActInfoUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报名填写用户信息")
    public CommonResult<Boolean> updateSnsActInfoUser(@Valid @RequestBody SnsActInfoUserSaveReqVO updateReqVO) {
        snsActInfoUserService.updateSnsActInfoUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报名填写用户信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsActInfoUser(@RequestParam("id") Long id) {
        snsActInfoUserService.deleteSnsActInfoUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报名填写用户信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsActInfoUserRespVO> getSnsActInfoUser(@RequestParam("id") Long id) {
        SnsActInfoUserDO snsActInfoUser = snsActInfoUserService.getSnsActInfoUser(id);
        return success(BeanUtils.toBean(snsActInfoUser, SnsActInfoUserRespVO.class));
    }
    @PermitAll
    @GetMapping("/page")
    @Operation(summary = "获得报名填写用户信息分页")
    public CommonResult<PageResult<SnsActInfoUserRespVO>> getSnsActInfoUserPage(@Valid SnsActInfoUserPageReqVO pageReqVO) {
        PageResult<SnsActInfoUserDO> pageResult = snsActInfoUserService.getSnsActInfoUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActInfoUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报名填写用户信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActInfoUserExcel(@Valid SnsActInfoUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActInfoUserDO> list = snsActInfoUserService.getSnsActInfoUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报名填写用户信息.xls", "数据", SnsActInfoUserRespVO.class,
                        BeanUtils.toBean(list, SnsActInfoUserRespVO.class));
    }

}
