package cn.metast.tuoke.module.community.controller.app.cmBuylog;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogRespVO;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.CmBuyLogSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import cn.metast.tuoke.module.community.service.cmBuylog.CmBuyLogService;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员购买记录")
@RestController
@RequestMapping("/community/cm-buy-log")
@Validated
public class AppCmBuyLogController {

    @Resource
    private CmBuyLogService cmBuyLogService;
    @Resource
    private CmTopicService cmTopicService;
    @PostMapping("/create")
    @Operation(summary = "创建会员购买记录")
    public CommonResult<Long> createcmBuyLog(@Valid @RequestBody CmBuyLogSaveReqVO createReqVO) {
        return success(cmBuyLogService.createcmBuyLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会员购买记录")
    public CommonResult<Boolean> updatecmBuyLog(@Valid @RequestBody CmBuyLogSaveReqVO updateReqVO) {
        cmBuyLogService.updatecmBuyLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除会员购买记录")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletecmBuyLog(@RequestParam("id") Long id) {
        cmBuyLogService.deletecmBuyLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得会员购买记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CmBuyLogRespVO> getcmBuyLog(@RequestParam("id") Long id) {
        CmBuyLogDO cmBuyLog = cmBuyLogService.getcmBuyLog(id);
        return success(BeanUtils.toBean(cmBuyLog, CmBuyLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得会员购买记录分页")
    public CommonResult<PageResult<CmBuyLogRespVO>> getcmBuyLogPage(@Valid CmBuyLogPageReqVO pageReqVO) {
        PageResult<CmBuyLogDO> pageResult = cmBuyLogService.getcmBuyLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmBuyLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出会员购买记录 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportcmBuyLogExcel(@Valid CmBuyLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmBuyLogDO> list = cmBuyLogService.getcmBuyLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "会员购买记录.xls", "数据", CmBuyLogRespVO.class,
                        BeanUtils.toBean(list, CmBuyLogRespVO.class));
    }

}
