package cn.metast.tuoke.module.member.controller.admin.point;

import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointImportExcelVO;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointImportRespVO;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointRecordPageReqVO;
import cn.metast.tuoke.module.member.controller.admin.point.vo.recrod.MemberPointRecordRespVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserImportExcelVO;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserImportRespVO;
import cn.metast.tuoke.module.member.convert.point.MemberPointRecordConvert;
import cn.metast.tuoke.module.member.dal.dataobject.point.MemberPointRecordDO;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.service.point.MemberPointRecordService;
import cn.metast.tuoke.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/member/point/record")
@Validated
public class MemberPointRecordController {

    @Resource
    private MemberPointRecordService pointRecordService;

    @Resource
    private MemberUserService memberUserService;

    @GetMapping("/page")
    @Operation(summary = "获得用户积分记录分页")
    @PreAuthorize("@ss.hasPermission('point:record:query')")
    public CommonResult<PageResult<MemberPointRecordRespVO>> getPointRecordPage(@Valid MemberPointRecordPageReqVO pageVO) {
        // 执行分页查询
        PageResult<MemberPointRecordDO> pageResult = pointRecordService.getPointRecordPage(pageVO);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接结果返回
        List<MemberUserDO> users = memberUserService.getUserList(
                convertSet(pageResult.getList(), MemberPointRecordDO::getUserId));
        return success(MemberPointRecordConvert.INSTANCE.convertPage(pageResult, users));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得积分模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", MemberPointImportExcelVO.class, new ArrayList<>());
    }
    @PostMapping("/import")
    @Operation(summary = "导入积分")
    @PreAuthorize("@ss.hasPermission('point:record:template')")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<MemberPointImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                             @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<MemberPointImportExcelVO> list = ExcelUtils.read(file, MemberPointImportExcelVO.class);
        return success(pointRecordService.importUserList(list, updateSupport));
    }
}
