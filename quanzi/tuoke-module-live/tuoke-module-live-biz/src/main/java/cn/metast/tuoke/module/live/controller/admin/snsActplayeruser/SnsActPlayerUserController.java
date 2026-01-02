package cn.metast.tuoke.module.live.controller.admin.snsActplayeruser;

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

import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActplayeruser.SnsActPlayerUserDO;
import cn.metast.tuoke.module.live.service.snsActplayeruser.SnsActPlayerUserService;

@Tag(name = "管理后台 - 参与人信息")
@RestController
@RequestMapping("/live/sns-act-player-user")
@Validated
public class SnsActPlayerUserController {

    @Resource
    private SnsActPlayerUserService snsActPlayerUserService;

    @PostMapping("/create")
    @Operation(summary = "创建参与人信息")
    public CommonResult<Long> createSnsActPlayerUser(@Valid @RequestBody SnsActPlayerUserSaveReqVO createReqVO) {
        return success(snsActPlayerUserService.createSnsActPlayerUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新参与人信息")
    public CommonResult<Boolean> updateSnsActPlayerUser(@Valid @RequestBody SnsActPlayerUserSaveReqVO updateReqVO) {
        snsActPlayerUserService.updateSnsActPlayerUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除参与人信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsActPlayerUser(@RequestParam("id") Long id) {
        snsActPlayerUserService.deleteSnsActPlayerUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得参与人信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsActPlayerUserRespVO> getSnsActPlayerUser(@RequestParam("id") Long id) {
        SnsActPlayerUserDO snsActPlayerUser = snsActPlayerUserService.getSnsActPlayerUser(id);
        return success(BeanUtils.toBean(snsActPlayerUser, SnsActPlayerUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得参与人信息分页")
    public CommonResult<PageResult<SnsActPlayerUserRespVO>> getSnsActPlayerUserPage(@Valid SnsActPlayerUserPageReqVO pageReqVO) {
        PageResult<SnsActPlayerUserDO> pageResult = snsActPlayerUserService.getSnsActPlayerUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActPlayerUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出参与人信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActPlayerUserExcel(@Valid SnsActPlayerUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActPlayerUserDO> list = snsActPlayerUserService.getSnsActPlayerUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "参与人信息.xls", "数据", SnsActPlayerUserRespVO.class,
                        BeanUtils.toBean(list, SnsActPlayerUserRespVO.class));
    }

}
