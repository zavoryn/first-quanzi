package cn.metast.tuoke.module.heal.controller.app;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.heal.controller.admin.archives.vo.ArchivesPageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.archives.vo.ArchivesRespVO;
import cn.metast.tuoke.module.heal.controller.admin.archives.vo.ArchivesSaveReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.module.heal.service.archives.ArchivesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 档案信息")
@RestController
@RequestMapping("/heal/archives")
@Validated
public class AppArchivesController {

    @Resource
    private ArchivesService archivesService;

    @PostMapping("/create")
    @Operation(summary = "创建档案信息")
    public CommonResult<Long> createArchives(@Valid @RequestBody ArchivesSaveReqVO createReqVO) {
        createReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        return success(archivesService.createArchives(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新档案信息")
    public CommonResult<Boolean> updateArchives(@Valid @RequestBody ArchivesSaveReqVO updateReqVO) {
        archivesService.updateArchives(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除档案信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteArchives(@RequestParam("id") Long id) {
        archivesService.deleteArchives(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得档案信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ArchivesRespVO> getArchives(@RequestParam("id") Long id) {
        ArchivesDO archives = archivesService.getArchives(id);
        return success(BeanUtils.toBean(archives, ArchivesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得档案信息分页")
    public CommonResult<PageResult<ArchivesRespVO>> getArchivesPage(@Valid ArchivesPageReqVO pageReqVO) {
        pageReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        PageResult<ArchivesDO> pageResult = archivesService.getArchivesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ArchivesRespVO.class));
    }

}
