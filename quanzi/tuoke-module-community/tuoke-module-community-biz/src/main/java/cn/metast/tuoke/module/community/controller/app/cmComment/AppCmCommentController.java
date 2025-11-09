package cn.metast.tuoke.module.community.controller.app.cmComment;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo.CmCommentLikeSaveReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.CmPostSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike.CmCommentLikeDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.service.cmCommentlike.CmCommentLikeService;
import cn.metast.tuoke.module.community.service.cmPost.CmPostService;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import java.util.*;

import cn.metast.tuoke.module.social.api.notify.SocialNotifyApi;
import cn.metast.tuoke.module.social.api.notify.dto.SocialNotifySendReqDTO;
import cn.metast.tuoke.module.social.enums.notify.SocialNotifyTypeEnum;
import cn.metast.tuoke.module.social.enums.notify.SocialTargetTypeEnum;

import lombok.extern.slf4j.Slf4j;

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

import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.service.cmComment.CmCommentService;

@Tag(name = "管理后台 - 圈子评论")
@RestController
@RequestMapping("/community/cm-comment")
@Validated
@Slf4j
public class AppCmCommentController {

    @Resource
    private CmCommentService cmCommentService;
    @Resource
    private CmCommentLikeService cmCommentLikeService;
    @Resource
    private CmPostService cmPostService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private CmTopicService cmTopicService;

    @Resource
    private SocialNotifyApi socialNotifyApi;

    @PostMapping("/create")
    @Operation(summary = "创建圈子评论")
    public CommonResult<Long> createCmComment(@Valid @RequestBody CmCommentSaveReqVO createReqVO) {
        CmPostDO post = cmPostService.getCmPost(createReqVO.getPostId());
        if(post!=null) {
            CmTopicDO cmTopicDO=cmTopicService.getCmTopic(post.getTopicId());
            if (cmTopicDO != null) {
                if (cmTopicDO.getIsComment()==1) {
                    //需要审核
                    createReqVO.setStatus(1);
                }
            }
        }
        createReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        createReqVO.setPostId(createReqVO.getPostId());
        if (createReqVO.getParentId() == null || createReqVO.getParentId()==0L) {
            createReqVO.setParentId(0L); // 默认为顶级评论
        }
        Long id=cmCommentService.createCmComment(createReqVO);
        //更新post评论数==顶级评论才会评论数才会加1
        if(createReqVO.getParentId()==0L) {
            if (post != null) {
                post.setCommentCount((post.getCommentCount() != null ? post.getCommentCount() : 0) + 1);
                cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
            }
        }
        return success(id);
    }

    @PutMapping("/updateCheck")
    @Operation(summary = "更新圈子详情")
    public CommonResult<Boolean> updateCheck(@RequestParam("id") Long id,@RequestParam("status") Integer status) {
        CmCommentSaveReqVO updateReqVO=new CmCommentSaveReqVO();
        updateReqVO.setId(id);
        updateReqVO.setStatus(status);
        cmCommentService.updateCmComment(updateReqVO);
        return success(true);
    }
    @PutMapping("/update")
    @Operation(summary = "更新圈子评论")
    public CommonResult<Boolean> updateCmComment(@Valid @RequestBody CmCommentSaveReqVO updateReqVO) {
        cmCommentService.updateCmComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除圈子评论")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteCmComment(@RequestParam("id") Long id) {
        CmCommentDO cmCommentDO=cmCommentService.getCmComment(id);
        if(cmCommentDO.getParentId()==0) {
            //一级评论
            CmPostDO post = cmPostService.getCmPost(cmCommentDO.getPostId());
            if (post != null) {
                Long commentCount = Math.max(0, (post.getCommentCount() != null ? post.getCommentCount() : 0) - 1);
                post.setCommentCount(commentCount);
                cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
            }
        }
        cmCommentService.deleteCmComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得圈子评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CmCommentRespVO> getCmComment(@RequestParam("id") Long id) {
        CmCommentDO cmComment = cmCommentService.getCmComment(id);
        if(cmComment!=null){
            MemberUserRespDTO memberUserRespDTO= memberUserApi.getUser(cmComment.getUserId());
            if(memberUserRespDTO!=null){
                cmComment.setNickname(memberUserRespDTO.getNickname());
                cmComment.setAvatar(memberUserRespDTO.getAvatar());
            }
        }
        return success(BeanUtils.toBean(cmComment, CmCommentRespVO.class));
    }
    /**
     * 评论管理
     */
    @GetMapping("/page")
    @Operation(summary = "评论管理")
    public CommonResult<PageResult<CmCommentRespVO>> getCmCommentPage(@Valid CmCommentPageReqVO pageReqVO) {
        //pageReqVO.setToUserId(SecurityFrameworkUtils.getLoginUserId());
        if(pageReqVO.getTopicId()==null){
            return success(new PageResult<>());
        }
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        //type=0待审核1已回答2未回答3已删除
        PageResult<CmCommentDO> pageResult = cmCommentService.getCmCommentPageNew(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(convertSet(pageResult.getList(), CmCommentDO::getToUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getToUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getPostId());
                cmCommentPageReqV.setParentId(item.getId());
                cmCommentPageReqV.setStatus(0);
                cmCommentPageReqV.setPageSize(PageParam.PAGE_SIZE_NONE);
                List<CmCommentDO> comments= cmCommentService.getCmCommentPage(cmCommentPageReqV).getList();
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setReplyTo(memberUserRespDTO.getNickname());
                        item1.setNickname(memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                    }
                });
                item.setReplies(comments);
            }
            //帖子内容
            CmPostDO post = cmPostService.getCmPost(item.getPostId());
            if (post != null) {
                item.setPost(post);
            }
        });
        return success(BeanUtils.toBean(pageResult, CmCommentRespVO.class));
    }

    /**
     * 评论管理待审核总数
     */
    @GetMapping("/getCommentCount")
    @Operation(summary = "评论管理待审核总数")
    public CommonResult<Integer> pageCount(@Valid CmCommentPageReqVO pageReqVO) {
        if(pageReqVO.getTopicId()==null){
            return success(0);
        }
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        pageReqVO.setType(0);
        //type=0待审核1已回答2未回答3已删除
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CmCommentDO> pageResult = cmCommentService.getCmCommentPageNew(pageReqVO).getList();
        return success(!CollectionUtils.isEmpty(pageResult)?pageResult.size():0);
    }



    /**
     * 我的评论管理
     */
    @GetMapping("/ownPage")
    @Operation(summary = "我的评论管理")
    public CommonResult<PageResult<CmCommentRespVO>> getOwnPage(@Valid CmCommentPageReqVO pageReqVO) {
        pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<CmCommentDO> pageResult = cmCommentService.getCmCommentPageNew(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(convertSet(pageResult.getList(), CmCommentDO::getToUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getToUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getPostId());
                cmCommentPageReqV.setParentId(item.getId());
                cmCommentPageReqV.setStatus(0);
                cmCommentPageReqV.setPageSize(PageParam.PAGE_SIZE_NONE);
                List<CmCommentDO> comments= cmCommentService.getCmCommentPage(cmCommentPageReqV).getList();
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setReplyTo(memberUserRespDTO.getNickname());
                        item1.setNickname(memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                    }
                });
                item.setReplies(comments);
            }
            //帖子内容
            CmPostDO post = cmPostService.getCmPost(item.getPostId());
            if (post != null) {
                item.setPost(post);
            }
        });
        return success(BeanUtils.toBean(pageResult, CmCommentRespVO.class));
    }

    //批量处理
    @GetMapping("/batchStatus")
    @Operation(summary = "批量处理")
    public CommonResult<Boolean> batch(@RequestParam("ids") List<Long> ids,String type) {
        if("1".equals(type)){
            //批量通过
            for(Long id:ids){
                CmCommentSaveReqVO updateReqVO=new CmCommentSaveReqVO();
                updateReqVO.setId(id);
                updateReqVO.setStatus(0);
                cmCommentService.updateCmComment(updateReqVO);
            }
        } else if("2".equals(type)){
            //批量删除
            for(Long id:ids){
                cmCommentService.deleteCmComment(id);
            }

        }
        return success(true);
    }

    /**
     * 点赞评论
     */
    @PostMapping("/commonLike")
    @Operation(summary = "点赞评论")
    public CommonResult<Boolean> commonLike(@PathVariable Long id)
    {
        // 获取当前用户ID
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        
        // 检查是否已点赞
        CmCommentLikeDO existingLike = cmCommentLikeService.selectCommentLike(userId, id);
        if (existingLike != null) {
            return success(true); // 已点赞，直接返回成功
        }
        
        // 获取评论信息（用于通知和更新点赞数）
        CmCommentDO comment = cmCommentService.getCmComment(id);
        if (comment == null) {
            return success(false); // 评论不存在
        }
        
        // 创建点赞记录
        CmCommentLikeSaveReqVO createReqVO = new CmCommentLikeSaveReqVO();
        createReqVO.setUserId(userId);
        createReqVO.setCommentId(id);
        cmCommentLikeService.createCmCommentLike(createReqVO);
        
        // 发布点赞通知事件
        try {
            if (!userId.equals(comment.getUserId())) {
                // 不是给自己点赞，发送通知
                String targetContentSnapshot = comment.getContent() != null ? comment.getContent() : "";
                // 截取部分内容作为快照
                if (targetContentSnapshot.length() > 50) {
                    targetContentSnapshot = targetContentSnapshot.substring(0, 50) + "...";
                }
                
                // 使用API发送通知
                SocialNotifySendReqDTO reqDTO = new SocialNotifySendReqDTO();
                reqDTO.setReceiverId(comment.getUserId());
                reqDTO.setSenderId(userId);
                reqDTO.setTargetId(id);
                reqDTO.setTargetType(SocialTargetTypeEnum.COMMENT.getType());
                reqDTO.setNotifyType(SocialNotifyTypeEnum.LIKE.getType());
                reqDTO.setContent("点赞了你的评论");
                reqDTO.setTargetContentSnapshot(targetContentSnapshot);
                
                socialNotifyApi.sendSocialNotify(reqDTO);
            }
        } catch (Exception e) {
            // 通知事件发布失败不影响主流程，记录日志即可
            log.error("[commonLike][发布评论点赞通知事件失败，commentId={}, userId={}]", id, userId, e);
        }
        
        // 更新评论点赞数
        comment.setLikeCount((comment.getLikeCount() != null ? comment.getLikeCount() : 0) + 1);
        cmCommentService.updateCmComment(BeanUtils.toBean(comment, CmCommentSaveReqVO.class));
        
            return success(true);
        }
    /**
     * 取消点赞评论
     */
    @PostMapping("/unCommonLike")
    @Operation(summary = "取消点赞评论")
    public CommonResult<Boolean> unCommonLike(@PathVariable Long id)
    {
        //取消点赞
        Long userId=SecurityFrameworkUtils.getLoginUserId();
        CmCommentLikeDO createReqVO=cmCommentLikeService.selectCommentLike(userId,id);
        if(createReqVO!=null){
            cmCommentLikeService.deleteCmCommentLike(createReqVO.getId());
        }
        // 暂时只更新点赞数
        CmCommentDO post = cmCommentService.getCmComment(id);
        if (post != null) {
            int likeCount = Math.max(0, (post.getLikeCount() != null ? post.getLikeCount() : 0) - 1);
            post.setLikeCount(likeCount);
            cmCommentService.updateCmComment(BeanUtils.toBean(post, CmCommentSaveReqVO.class));
            return success(true);
        }
        return success(false);
    }

}
