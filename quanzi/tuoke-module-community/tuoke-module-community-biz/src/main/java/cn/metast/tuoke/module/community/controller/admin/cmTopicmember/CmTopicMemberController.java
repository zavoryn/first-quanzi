package cn.metast.tuoke.module.community.controller.admin.cmTopicmember;

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

import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;

import java.util.List;

@Tag(name = "管理后台 - 圈子成员")
@RestController
@RequestMapping("/community/cm-topic-member")
@Validated
public class CmTopicMemberController {

    @Resource
    private CmTopicMemberService cmTopicMemberService;

    @PostMapping("/create")
    @Operation(summary = "创建圈子成员")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:create')")
    public CommonResult<Long> createCmTopicMember(@Valid @RequestBody CmTopicMemberSaveReqVO createReqVO) {
        return success(cmTopicMemberService.createCmTopicMember(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子成员")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:update')")
    public CommonResult<Boolean> updateCmTopicMember(@Valid @RequestBody CmTopicMemberSaveReqVO updateReqVO) {
        cmTopicMemberService.updateCmTopicMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子成员")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:delete')")
    public CommonResult<Boolean> deleteCmTopicMember(@RequestParam("id") Long id) {
        cmTopicMemberService.deleteCmTopicMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子成员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:query')")
    public CommonResult<CmTopicMemberRespVO> getCmTopicMember(@RequestParam("id") Long id) {
        CmTopicMemberDO cmTopicMember = cmTopicMemberService.getCmTopicMember(id);
        return success(BeanUtils.toBean(cmTopicMember, CmTopicMemberRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子成员分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:query')")
    public CommonResult<PageResult<CmTopicMemberRespVO>> getCmTopicMemberPage(@Valid CmTopicMemberPageReqVO pageReqVO) {
        PageResult<CmTopicMemberDO> pageResult = cmTopicMemberService.getCmTopicMemberPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmTopicMemberRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子成员 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic-member:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmTopicMemberExcel(@Valid CmTopicMemberPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmTopicMemberDO> list = cmTopicMemberService.getCmTopicMemberPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子成员.xls", "数据", CmTopicMemberRespVO.class,
                BeanUtils.toBean(list, CmTopicMemberRespVO.class));
    }

    @GetMapping("/topic-simple-list")
    @Operation(summary = "获取圈子简单列表（下拉选择用）")
    public CommonResult<List<TopicSimpleRespVO>> getTopicSimpleList() {
        return success(cmTopicMemberService.getTopicSimpleList());
    }

    @PostMapping("/add-free-member")
    @Operation(summary = "添加免费会员")
    public CommonResult<Long> addFreeMember(@Valid @RequestBody FreeMemberAddReqVO reqVO) {
        return success(cmTopicMemberService.addFreeMember(reqVO));
    }

}
