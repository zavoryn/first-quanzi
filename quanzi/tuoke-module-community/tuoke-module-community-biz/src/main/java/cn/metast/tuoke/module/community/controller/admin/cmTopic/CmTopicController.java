package cn.metast.tuoke.module.community.controller.admin.cmTopic;

import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.CmTopicMemberSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
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

import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;

@Tag(name = "管理后台 - 圈子详情")
@RestController
@RequestMapping("/community/cm-topic")
@Validated
public class CmTopicController {

    @Resource
    private CmTopicService cmTopicService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @PostMapping("/create")
    @Operation(summary = "创建圈子详情")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:create')")
    public CommonResult<Long> createCmTopic(@Valid @RequestBody CmTopicSaveReqVO createReqVO) {
        return success(cmTopicService.createCmTopic(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新圈子详情")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:update')")
    public CommonResult<Boolean> updateCmTopic(@Valid @RequestBody CmTopicSaveReqVO updateReqVO) {
        cmTopicService.updateCmTopic(updateReqVO);
        //创建圈子管理员
        CmTopicMemberDO cmTopicMemberDO=cmTopicMemberService.getCmTopicMember(updateReqVO.getUserId(),updateReqVO.getId());
        if(cmTopicMemberDO==null) {
            CmTopicMemberSaveReqVO reqVO = new CmTopicMemberSaveReqVO();
            reqVO.setTopicId(updateReqVO.getId());
            reqVO.setUserId(updateReqVO.getUserId());
            reqVO.setRole(1);
            reqVO.setStatus(0);
            reqVO.setJoinTime(LocalDateTime.now());
            reqVO.setStartTime(LocalDateTime.now());
            reqVO.setEndTime(LocalDateTime.now().plusYears(100));
            cmTopicMemberService.createCmTopicMember(reqVO);
        }else{
            CmTopicMemberSaveReqVO reqVO = new CmTopicMemberSaveReqVO();
            reqVO.setId(cmTopicMemberDO.getId());
            reqVO.setTopicId(updateReqVO.getId());
            reqVO.setUserId(updateReqVO.getUserId());
            reqVO.setRole(1);
            reqVO.setStatus(0);
            reqVO.setJoinTime(LocalDateTime.now());
            reqVO.setStartTime(LocalDateTime.now());
            reqVO.setEndTime(LocalDateTime.now().plusYears(100));
            cmTopicMemberService.updateCmTopicMember(reqVO);
        }
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子详情")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:delete')")
    public CommonResult<Boolean> deleteCmTopic(@RequestParam("id") Long id) {
        cmTopicService.deleteCmTopic(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:query')")
    public CommonResult<CmTopicRespVO> getCmTopic(@RequestParam("id") Long id) {
        CmTopicDO cmTopic = cmTopicService.getCmTopic(id);
        return success(BeanUtils.toBean(cmTopic, CmTopicRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得圈子详情分页")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:query')")
    public CommonResult<PageResult<CmTopicRespVO>> getCmTopicPage(@Valid CmTopicPageReqVO pageReqVO) {
        PageResult<CmTopicDO> pageResult = cmTopicService.getCmTopicPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CmTopicRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出圈子详情 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-topic:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmTopicExcel(@Valid CmTopicPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmTopicDO> list = cmTopicService.getCmTopicPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "圈子详情.xls", "数据", CmTopicRespVO.class,
                        BeanUtils.toBean(list, CmTopicRespVO.class));
    }

}
