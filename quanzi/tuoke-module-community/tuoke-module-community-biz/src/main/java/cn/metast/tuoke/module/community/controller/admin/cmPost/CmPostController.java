package cn.metast.tuoke.module.community.controller.admin.cmPost;

import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.CmCommentPageReqVO;
import cn.metast.tuoke.module.community.convert.cmPost.CmPostConvert;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmComment.CmCommentService;
import cn.metast.tuoke.module.community.service.cmPostlike.CmPostLikeService;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import io.micrometer.common.util.StringUtils;
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
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;

import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.service.cmPost.CmPostService;

@Tag(name = "管理后台 - 用户发帖详情")
@RestController
@RequestMapping("/community/cm-post")
@Validated
public class CmPostController {
    @Resource
    private CmPostService cmPostService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private CmCommentService cmCommentService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Resource
    private CmPostLikeService cmPostLikeService;
    @PostMapping("/create")
    @Operation(summary = "创建用户发帖详情")
    //@PreAuthorize("@ss.hasPermission('community:cm-post:create')")
    public CommonResult<Long> createCmPost(@Valid @RequestBody CmPostSaveReqVO createReqVO) {
        return success(cmPostService.createCmPost(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户发帖详情")
    //@PreAuthorize("@ss.hasPermission('community:cm-post:update')")
    public CommonResult<Boolean> updateCmPost(@Valid @RequestBody CmPostSaveReqVO updateReqVO) {
        cmPostService.updateCmPost(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户发帖详情")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('community:cm-post:delete')")
    public CommonResult<Boolean> deleteCmPost(@RequestParam("id") Long id) {
        cmPostService.deleteCmPost(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户发帖详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('community:cm-post:query')")
    public CommonResult<CmPostRespVO> getCmPost(@RequestParam("id") Long id) {
        CmPostDO cmPost = cmPostService.getCmPost(id);
        return success(BeanUtils.toBean(cmPost, CmPostRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户发帖详情分页")
    public CommonResult<PageResult<CmPostRespVO>> getCmPostPage(@Valid CmPostPageReqVO pageReqVO) {
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostPageNew(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数==直接获取库里的评论数
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
                                item2.setAvatar(memberUse2.getAvatar());
                            }
                        });
                        item1.setReplies(comments2);
                    }
                });
                //parent_id=0,父级
                item.setComments(comments);
            }
        });
        return success(CmPostConvert.INSTANCE.convertPage(pageResult, users));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户发帖详情 Excel")
    //@PreAuthorize("@ss.hasPermission('community:cm-post:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCmPostExcel(@Valid CmPostPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmPostDO> list = cmPostService.getCmPostPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户发帖详情.xls", "数据", CmPostRespVO.class,
                        BeanUtils.toBean(list, CmPostRespVO.class));
    }

}
