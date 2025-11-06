package cn.metast.tuoke.module.community.controller.admin.cmConfig;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigRespVO;
import cn.metast.tuoke.module.community.controller.admin.cmConfig.vo.CmConfigSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmConfig.CmConfigDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.service.cmConfig.CmConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 圈子配置")
@RestController
@RequestMapping("/community/cm-config")
@Validated
public class CmConfigController {

    @Resource
    private CmConfigService cmConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建圈子配置")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:create')")
    public CommonResult<Long> createCmConfig(@Valid @RequestBody CmConfigSaveReqVO createReqVO) {
        return success(cmConfigService.createCmConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子配置")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:update')")
    public CommonResult<Boolean> updateCmConfig(@Valid @RequestBody CmConfigSaveReqVO updateReqVO) {
        cmConfigService.updateCmConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子配置")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-config:delete')")
    public CommonResult<Boolean> deleteCmConfig(@RequestParam("id") Long id) {
        cmConfigService.deleteCmConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:query')")
    public CommonResult<CmConfigRespVO> getCmConfig(@RequestParam("id") Long id) {
        CmConfigDO cmConfig = cmConfigService.getCmConfig(id);
        return success(BeanUtils.toBean(cmConfig, CmConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子配置分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:query')")
    public CommonResult<PageResult<CmConfigRespVO>> getCmConfigPage(@Valid CmConfigPageReqVO pageReqVO) {
        PageResult<CmConfigDO> pageResult = cmConfigService.getCmConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子配置 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmConfigExcel(@Valid CmConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmConfigDO> list = cmConfigService.getCmConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子配置.xls", "数据", CmConfigRespVO.class,
                        BeanUtils.toBean(list, CmConfigRespVO.class));
    }

    @GetMapping("/topic-options")
    @Operation(summary = "获取圈子选项列表")
    //@PreAuthorize("@ss.hasPermission('community:cm-config:query')")
    public CommonResult<List<OptionVO>> getTopicOptions() {
        // 获取所有启用的圈子
        List<CmTopicDO> topicList = cmConfigService.getAllTopics();
        List<OptionVO> options = topicList.stream()
                .map(topic -> new OptionVO(topic.getId(), topic.getTopicName()))
                .collect(Collectors.toList());
        return success(options);
    }

    // 内部OptionVO类
    @Data
    @AllArgsConstructor
    public static class OptionVO {
        private Long value;
        private String label;
    }

}
