package cn.metast.tuoke.module.member.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.string.StrUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.member.controller.admin.sync.MemberUserSyncService;
import cn.metast.tuoke.module.member.controller.admin.user.vo.*;
import cn.metast.tuoke.module.member.convert.user.MemberUserConvert;
import cn.metast.tuoke.module.member.dal.dataobject.group.MemberGroupDO;
import cn.metast.tuoke.module.member.dal.dataobject.level.MemberLevelDO;
import cn.metast.tuoke.module.member.dal.dataobject.tag.MemberTagDO;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.enums.point.MemberPointBizTypeEnum;
import cn.metast.tuoke.module.member.service.group.MemberGroupService;
import cn.metast.tuoke.module.member.service.level.MemberLevelService;
import cn.metast.tuoke.module.member.service.point.MemberPointRecordService;
import cn.metast.tuoke.module.member.service.tag.MemberTagService;
import cn.metast.tuoke.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 会员用户")
@RestController
@RequestMapping("/member/user")
@Validated
public class MemberUserController {

    @Resource
    private MemberUserService memberUserService;
    @Resource
    private MemberTagService memberTagService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private MemberGroupService memberGroupService;
    @Resource
    private MemberPointRecordService memberPointRecordService;
    @Resource
    private MemberUserSyncService memberUserSyncService;
    @PutMapping("/update")
    @Operation(summary = "更新会员用户")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody MemberUserUpdateReqVO updateReqVO) {
        memberUserService.updateUser(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-level")
    @Operation(summary = "更新会员用户等级")
    @PreAuthorize("@ss.hasPermission('member:user:update-level')")
    public CommonResult<Boolean> updateUserLevel(@Valid @RequestBody MemberUserUpdateLevelReqVO updateReqVO) {
        memberLevelService.updateUserLevel(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-point")
    @Operation(summary = "更新会员用户积分")
    @PreAuthorize("@ss.hasPermission('member:user:update-point')")
    public CommonResult<Boolean> updateUserPoint(@Valid @RequestBody MemberUserUpdatePointReqVO updateReqVO) {
        memberPointRecordService.createPointRecord(updateReqVO.getId(), updateReqVO.getPoint(),
                MemberPointBizTypeEnum.ADMIN, String.valueOf(getLoginUserId()));
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得会员用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<MemberUserRespVO> getUser(@RequestParam("id") Long id) {
        MemberUserDO user = memberUserService.getUser(id);
        return success(MemberUserConvert.INSTANCE.convert03(user));
    }

    @GetMapping("/page")
    @Operation(summary = "获得会员用户分页")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<PageResult<MemberUserRespVO>> getUserPage(@Valid MemberUserPageReqVO pageVO) {
        PageResult<MemberUserDO> pageResult = memberUserService.getUserPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }
        pageResult.getList().forEach(itemRespVO -> {
        // 设置用户信息并隐藏手机号
        if (itemRespVO.getMobile() != null) {
            // 隐藏手机号中间四位
            itemRespVO.setMobile(StrUtils.desensitizeMobile(itemRespVO.getMobile()));
        }
        });
        // 处理用户标签返显
        Set<Long> tagIds = pageResult.getList().stream()
                .map(MemberUserDO::getTagIds)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        List<MemberTagDO> tags = memberTagService.getTagList(tagIds);
        // 处理用户级别返显
        List<MemberLevelDO> levels = memberLevelService.getLevelList(
                convertSet(pageResult.getList(), MemberUserDO::getLevelId));
        // 处理用户分组返显
        List<MemberGroupDO> groups = memberGroupService.getGroupList(
                convertSet(pageResult.getList(), MemberUserDO::getGroupId));
        return success(MemberUserConvert.INSTANCE.convertPage(pageResult, tags, levels, groups));
    }
   /* @GetMapping("/syncMemberUser")
    @Operation(summary = "同步小鹅通用户--暂定先不用")
    public CommonResult<Boolean> syncProductSpu() {
        memberUserSyncService.syncTechMemberUser();
        return success(true);
    }*/

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入用户模板")
    @PreAuthorize("@ss.hasPermission('member:user:template')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        /*// 手动创建导出 demo
        List<MemberUserImportExcelVO> list = Arrays.asList(
                MemberUserImportExcelVO.builder().username("yunai").deptId(1L).email("yunai@metast.cn").mobile("15601691300")
                        .nickname("元圈").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
                MemberUserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@metast.cn").mobile("15601701300")
                        .nickname("源码").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
        );*/
        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", MemberUserImportExcelVO.class, new ArrayList<>());
    }
    @PostMapping("/import")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<MemberUserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<MemberUserImportExcelVO> list = ExcelUtils.read(file, MemberUserImportExcelVO.class);
        return success(memberUserService.importUserList(list, updateSupport));
    }
}
