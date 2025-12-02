package cn.metast.tuoke.module.heal.controller.admin.archivesuser;

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

import cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archivesuser.ArchivesUserDO;
import cn.metast.tuoke.module.heal.service.archivesuser.ArchivesUserService;

@Tag(name = "管理后台 - 档案信息关联用户")
@RestController
@RequestMapping("/heal/archives-user")
@Validated
public class ArchivesUserController {

    @Resource
    private ArchivesUserService archivesUserService;

    @PostMapping("/create")
    @Operation(summary = "创建档案信息关联用户")
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:create')")
    public CommonResult<Long> createArchivesUser(@Valid @RequestBody ArchivesUserSaveReqVO createReqVO) {
        return success(archivesUserService.createArchivesUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新档案信息关联用户")
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:update')")
    public CommonResult<Boolean> updateArchivesUser(@Valid @RequestBody ArchivesUserSaveReqVO updateReqVO) {
        archivesUserService.updateArchivesUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除档案信息关联用户")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:delete')")
    public CommonResult<Boolean> deleteArchivesUser(@RequestParam("id") Long id) {
        archivesUserService.deleteArchivesUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得档案信息关联用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:query')")
    public CommonResult<ArchivesUserRespVO> getArchivesUser(@RequestParam("id") Long id) {
        ArchivesUserDO archivesUser = archivesUserService.getArchivesUser(id);
        return success(BeanUtils.toBean(archivesUser, ArchivesUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得档案信息关联用户分页")
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:query')")
    public CommonResult<PageResult<ArchivesUserRespVO>> getArchivesUserPage(@Valid ArchivesUserPageReqVO pageReqVO) {
        PageResult<ArchivesUserDO> pageResult = archivesUserService.getArchivesUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ArchivesUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出档案信息关联用户 Excel")
    @PreAuthorize("@ss.hasPermission('wennuan:archives-user:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportArchivesUserExcel(@Valid ArchivesUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ArchivesUserDO> list = archivesUserService.getArchivesUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "档案信息关联用户.xls", "数据", ArchivesUserRespVO.class,
                        BeanUtils.toBean(list, ArchivesUserRespVO.class));
    }

}
