package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel;
import cn.metast.tuoke.module.kaifa.utils.EmailUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.error;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmodel.*;
import cn.metast.tuoke.module.kaifa.service.email.emailmodel.*;
@Tag(name = "管理后台 - 模板-快速文本")
@RestController
@RequestMapping("/kaifa/email-model")
@Validated
public class EmailModelController {
    @Resource
    private EmailModelService emailModelService;
    @Resource
    private EmailUtils emailUtils;
    @PostMapping("/create")
    @Operation(summary = "创建模板-快速文本")
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:create')")
    public CommonResult<Long> createEmailModel(@Valid @RequestBody EmailModelSaveReqVO createReqVO) {
        if(StringUtils.isNotEmpty(createReqVO.getContent())){
            createReqVO.setConteText(emailUtils.toPlainText(createReqVO.getContent()));
        }
        if(createReqVO.getTitle()!=null){
            EmailModelRespVO modelRespVO=new EmailModelRespVO();
            modelRespVO.setTitle(createReqVO.getTitle());
            List<EmailModelDO> list=emailModelService.selectEmailModelPage(modelRespVO);
            if(!CollectionUtils.isEmpty(list)){
                return error(500,"模板已经存在！");
            }
        }
        return success(emailModelService.createEmailModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新模板-快速文本")
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:update')")
    public CommonResult<Boolean> updateEmailModel(@Valid @RequestBody EmailModelSaveReqVO updateReqVO) {
        if(StringUtils.isNotEmpty(updateReqVO.getContent())){
            updateReqVO.setConteText(emailUtils.toPlainText(updateReqVO.getContent()));
        }
        emailModelService.updateEmailModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除模板-快速文本")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:delete')")
    public CommonResult<Boolean> deleteEmailModel(@RequestParam("id") Long id) {
        emailModelService.deleteEmailModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模板-快速文本")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:query')")
    public CommonResult<EmailModelRespVO> getEmailModel(@RequestParam("id") Long id) {
        EmailModelDO emailModel = emailModelService.getEmailModel(id);
        return success(BeanUtils.toBean(emailModel, EmailModelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模板-快速文本分页")
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:query')")
    public CommonResult<PageResult<EmailModelRespVO>> getEmailModelPage(@Valid EmailModelPageReqVO pageReqVO) {
        PageResult<EmailModelDO> pageResult = emailModelService.getEmailModelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EmailModelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模板-快速文本 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:email-model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEmailModelExcel(@Valid EmailModelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EmailModelDO> list = emailModelService.getEmailModelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模板-快速文本.xls", "数据", EmailModelRespVO.class,
                        BeanUtils.toBean(list, EmailModelRespVO.class));
    }

}
