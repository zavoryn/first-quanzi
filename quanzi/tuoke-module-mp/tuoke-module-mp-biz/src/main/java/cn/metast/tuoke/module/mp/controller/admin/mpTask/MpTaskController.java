package cn.metast.tuoke.module.mp.controller.admin.mpTask;

import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.MpTaskRecordSaveReqVO;
import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.MpTaskTemplateSaveReqVO;
import cn.metast.tuoke.module.mp.service.mpTaskrecord.MpTaskRecordService;
import cn.metast.tuoke.module.mp.service.mpTasktemplate.MpTaskTemplateService;
import org.springframework.scheduling.annotation.Scheduled;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTask.MpTaskDO;
import cn.metast.tuoke.module.mp.service.mpTask.MpTaskService;

@Tag(name = "管理后台 - 自动开发公众号信息")
@RestController
@RequestMapping("/mp/task")
@Validated
public class MpTaskController {

    @Resource
    private MpTaskService taskService;
    @Resource
    private MpTaskTemplateService taskTemplateService;
    @Resource
    private MpTaskRecordService mpTaskRecordService;
    @PostMapping("/create")
    @Operation(summary = "创建自动开发公众号信息")
    //@PreAuthorize("@ss.hasPermission('mp:task:create')")
    public CommonResult<Long> createTask(@Valid @RequestBody MpTaskSaveReqVO createReqVO) {
        return success(taskService.saveTask(createReqVO));
    }

    @PutMapping("/status")
    public CommonResult<Boolean> editStatus(@RequestBody MpTaskSaveReqVO imTask) {
        MpTaskDO taskDto = taskService.getTask(imTask.getId());
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
        if("0".equals(imTask.getStatus())){
            MpTaskSaveReqVO mpTaskSaveReqVO=BeanUtils.toBean(taskDto, MpTaskSaveReqVO.class);
            List<MpTaskSaveReqVO> taskList = new ArrayList<>();
            mpTaskSaveReqVO.setStatus(imTask.getStatus());
            taskList.add(mpTaskSaveReqVO);
            taskService.createEmailTask(taskList);
        }else{
            taskService.cancelTask(taskDto.getTaskId());
        }
        taskService.updateTask(imTask);
        return success(true);
    }


    @PutMapping("/update")
    @Operation(summary = "更新自动开发公众号信息")
    //@PreAuthorize("@ss.hasPermission('mp:task:update')")
    public CommonResult<Boolean> updateTask(@Valid @RequestBody MpTaskSaveReqVO updateReqVO) {
        updateReqVO.setTemplateId(Long.parseLong(updateReqVO.getTemplateValue()));
        taskService.updateTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除自动开发公众号信息")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('mp:task:delete')")
    public CommonResult<Boolean> deleteTask(@RequestParam("id") Long id) {
        taskService.deleteTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得自动开发公众号信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('mp:task:query')")
    public CommonResult<MpTaskRespVO> getTask(@RequestParam("id") Long id) {
        MpTaskDO task = taskService.getTask(id);
        return success(BeanUtils.toBean(task, MpTaskRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得自动开发公众号信息分页")
    //@PreAuthorize("@ss.hasPermission('mp:task:query')")
    public CommonResult<PageResult<MpTaskRespVO>> getTaskPage(@Valid MpTaskPageReqVO pageReqVO) {
        PageResult<MpTaskDO> pageResult = taskService.getTaskPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MpTaskRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出自动开发公众号信息 Excel")
    //@PreAuthorize("@ss.hasPermission('mp:task:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTaskExcel(@Valid MpTaskPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MpTaskDO> list = taskService.getTaskPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "自动开发公众号信息.xls", "数据", MpTaskRespVO.class,
                        BeanUtils.toBean(list, MpTaskRespVO.class));
    }

   /* @Scheduled(fixedRate = 60000)
    public void createEmailTask() {
        List<Map<String, Object>> maps = sysUserService.selectTenantInfo();
        for (Map<String, Object> map : maps) {
            //执行超管租户进行执行该定时任务
            String tenantId = map.get("id").toString();
            String tenantName = map.get("slave").toString();
            // 使用 try-finally 确保上下文清理
            try {
                // 绑定当前租户上下文
                SecurityContextHolder.setSourceName(tenantName);
                SecurityContextHolder.setEnterpriseId(tenantId);
                SecurityContextHolder.setUserType("00");
                SecurityContextHolder.setUserId("1");
                SecurityContextHolder.setUserName("admin");
                // 执行任务
                baseService.createSopTask();
            } finally {
                // 清除当前租户上下文，防止泄漏
                SecurityContextHolder.remove();
            }
        }
    }*/
}
