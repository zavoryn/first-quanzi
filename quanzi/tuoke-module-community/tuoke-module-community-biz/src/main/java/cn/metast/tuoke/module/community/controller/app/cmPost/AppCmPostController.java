package cn.metast.tuoke.module.community.controller.app.cmPost;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmCollect.vo.CmCollectSaveReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.CmCommentPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo.CmPostLikeSaveReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicSaveReqVO;
import cn.metast.tuoke.module.community.convert.cmPost.CmPostConvert;
import cn.metast.tuoke.module.community.dal.dataobject.cmCollect.CmCollectDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmBlock.CmBlockService;
import cn.metast.tuoke.module.community.service.cmCollect.CmCollectService;
import cn.metast.tuoke.module.community.service.cmComment.CmCommentService;
import cn.metast.tuoke.module.community.service.cmPostlike.CmPostLikeService;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.promotion.api.kefu.KefuMessageApi;
import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuMessageDTO;
import cn.metast.tuoke.module.promotion.enums.kefu.KeFuMessageContentTypeEnum;
import cn.metast.tuoke.module.social.api.notify.SocialNotifyApi;
import cn.metast.tuoke.module.social.api.notify.dto.SocialNotifySendReqDTO;
import cn.metast.tuoke.module.social.enums.notify.SocialNotifyTypeEnum;
import cn.metast.tuoke.module.social.enums.notify.SocialTargetTypeEnum;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.common.util.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.service.cmPost.CmPostService;

@Tag(name = "管理后台 - 用户发帖详情")
@RestController
@RequestMapping("/community/cm-post")
@Validated
@Slf4j
public class AppCmPostController {

    @Resource
    private CmPostService cmPostService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private CmPostLikeService cmPostLikeService;
    @Resource
    private CmCollectService cmCollectService;
    @Resource
    private CmCommentService cmCommentService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Resource
    private CmTopicService cmTopicService;
    @Resource
    private KefuMessageApi kefuMessageApi;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private CmBlockService cmBlockService;

    @Resource
    private SocialNotifyApi socialNotifyApi;

    @PostMapping("/create")
    @Operation(summary = "创建用户发帖详情")
    public CommonResult<Long> createCmPost(@Valid @RequestBody CmPostSaveReqVO createReqVO) {
        createReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        //发送微信服务号消息
        CmTopicDO cmTopicDO=cmTopicService.getCmTopic(createReqVO.getTopicId());
        //cmPostService.sendWeixinMsg(createReqVO);
        if(createReqVO.getIsSyncWx()!=null && createReqVO.getIsSyncWx()==1) {
            //更新次数
            // 如果不是今天，返回20次；如果是今天，返回剩余次数
            Long num=getRemainingCountOptimized(cmTopicDO);
            if (num <= 0) {
                //没有发送次数了，不能发送到微信
            }else{
                threadPoolTaskExecutor.execute(() -> {
                    cmPostService.sendWeixinMsg(createReqVO);
                });
                // 扣减次数并更新发送时间
                cmTopicDO.setSendNum(num - 1);
                cmTopicDO.setSendTime(LocalDateTime.now());
            }
            //更新微信同步状态
            cmTopicDO.setIsSyncWx(createReqVO.getIsSyncWx()==null?0L:createReqVO.getIsSyncWx());
        }
        //同步到聊天室
        if(createReqVO.getIsSyncChat()!=null && createReqVO.getIsSyncChat()==1){
            Long receiverId = Long.parseLong(createReqVO.getTopicId().toString() + "520");
            KeFuMessageDTO sendReqVO = new KeFuMessageDTO();
            sendReqVO.setReceiverId(receiverId);
            //TEXT(1, "文本消息"), IMAGE(2, "图片消息"), VOICE(3, "语音消息"), VIDEO(4, "视频消息"), SYSTEM(5, "系统消息"),
            sendReqVO.setContentType(KeFuMessageContentTypeEnum.TEXT.getType());
            sendReqVO.setContent(createReqVO.getContent());
            sendReqVO.setConversationId(kefuMessageApi.createConversationGroup(cmTopicDO.getUserId(), receiverId));
            kefuMessageApi.sendMessage(sendReqVO);
            //更新聊天室选中状态
            cmTopicDO.setIsSyncChat(createReqVO.getIsSyncChat()==null?0L:createReqVO.getIsSyncChat());
        }
        cmTopicService.updateCmTopic(BeanUtils.toBean(cmTopicDO, CmTopicSaveReqVO.class));
        return success(cmPostService.createCmPost(createReqVO));
    }
    /**
     * 获取剩余次数
     */
    public Long getRemainingCountOptimized(CmTopicDO cmTopicDO) {
        if (cmTopicDO == null) {
            return 0L;
        }
        LocalDateTime sendTime = cmTopicDO.getSendTime();
        LocalDate today = LocalDate.now();
        // 如果不是今天，返回20次；如果是今天，返回剩余次数
        if (sendTime == null || !sendTime.toLocalDate().equals(today)) {
            return 20L;
        } else {
            return cmTopicDO.getSendNum();
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户发帖详情")
    public CommonResult<Boolean> updateCmPost(@Valid @RequestBody CmPostSaveReqVO updateReqVO) {
        if(updateReqVO.getPostTop()!=null && updateReqVO.getPostTop()==1){
            //置顶时间
            updateReqVO.setTopTime(LocalDateTime.now());
        }
        cmPostService.updateCmPost(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户发帖详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteCmPost(@RequestParam("id") Long id) {
        cmPostService.deleteCmPost(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户发帖详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CmPostRespVO> getCmPost(@RequestParam("id") Long id) {
        CmPostDO item = cmPostService.getCmPost(id);
        if(item!=null){
            List<Long> blockIds=cmBlockService.getBlockUserIds(SecurityFrameworkUtils.getLoginUserId(), item.getTopicId());
            MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(item.getUserId());
            if(memberUserRespDTO!=null){
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
                /*//是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                cmCommentPageReqV.setBlockUserIds(blockIds);
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        cmCommentPageReqV2.setBlockUserIds(blockIds);
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
                                item2.setAvatar(memberUse2.getAvatar());
                            }
                        });
                        item1.setReplies(comments2);
                    }
                });
                //parent_id=0,父级
                item.setComments(comments);
            }
        }
        return success(BeanUtils.toBean(item, CmPostRespVO.class));
    }
    /**
     * 查询全部的
     */
    @GetMapping("/page")
    @Operation(summary = "获得全部发帖详情分页")
    public CommonResult<PageResult<CmPostRespVO>> getCmPostPage(@Valid CmPostPageReqVO pageReqVO) {
        //pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        //判断是否加入了圈子
        CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),pageReqVO.getTopicId());
        if(cmTopicMember==null || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==5) || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==3)){//终止服务
            return success(new PageResult<>());
        }else{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endTime = cmTopicMember.getEndTime();
            if (now.isAfter(endTime)) {
                //到期了续费
                return success(new PageResult<>());
            }
        }
        //pageReqVO.setDeleted("0");
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostPageNew(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
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

               /* //是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/
                //拉黑数据
                List<Long> blockIds=cmBlockService.getBlockUserIds(SecurityFrameworkUtils.getLoginUserId(), item.getTopicId());

                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                cmCommentPageReqV.setBlockUserIds(blockIds);
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        cmCommentPageReqV2.setBlockUserIds(blockIds);
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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


    /**
     * 查询我发布的
     */
    @GetMapping("/ownPage")
    @Operation(summary = "获得我发布的详情分页")
    public CommonResult<PageResult<CmPostRespVO>> getOwnPage(@Valid CmPostPageReqVO pageReqVO) {
        pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        //pageReqVO.setDeleted("0");
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostPageNew(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
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

                /*//是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/
                //拉黑数据
                List<Long> blockIds=cmBlockService.getBlockUserIds(SecurityFrameworkUtils.getLoginUserId(), item.getTopicId());
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                cmCommentPageReqV.setBlockUserIds(blockIds);
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        cmCommentPageReqV2.setBlockUserIds(blockIds);
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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

    /**
     * 查询置顶的
     */
    @GetMapping("/topPage")
    @Operation(summary = "获得置顶发帖")
    public CommonResult<PageResult<CmPostRespVO>> getCmPostTopPage(@Valid CmPostPageReqVO pageReqVO) {
        //pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),pageReqVO.getTopicId());
        if(cmTopicMember==null || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==5) || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==3)){//终止服务
            return success(new PageResult<>());
        }else{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endTime = cmTopicMember.getEndTime();
            if (now.isAfter(endTime)) {
                //到期了续费
                return success(new PageResult<>());
            }
        }
       //pageReqVO.setDeleted("0");
        pageReqVO.setPostTop(1);
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostPageTop(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
              /*  //是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/
                //拉黑数据
                List<Long> blockIds=cmBlockService.getBlockUserIds(SecurityFrameworkUtils.getLoginUserId(), item.getTopicId());
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                cmCommentPageReqV.setBlockUserIds(blockIds);
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        cmCommentPageReqV2.setBlockUserIds(blockIds);
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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
    /**
     * 查询文件的==没用
     */
    @GetMapping("/mediaPage")
    @Operation(summary = "获得文件发帖")
    public CommonResult<PageResult<CmPostRespVO>> getCmPostMediaPage(@Valid CmPostPageReqVO pageReqVO) {
        //pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),pageReqVO.getTopicId());
        if(cmTopicMember==null || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==5) || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==3)){//终止服务
            return success(new PageResult<>());
        }else{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endTime = cmTopicMember.getEndTime();
            if (now.isAfter(endTime)) {
                //到期了续费
                return success(new PageResult<>());
            }
        }
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostMediaPage(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
               /* //是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/

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
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
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
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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



    /**
     * 获得我点赞的帖子
     */
    @GetMapping("/likePage")
    @Operation(summary = "获得我点赞的贴子")
    public CommonResult<PageResult<CmPostRespVO>> likePage(@Valid CmPostPageReqVO pageReqVO) {
        pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostLikePage(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
               /* //是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/
                //拉黑数据
                List<Long> blockIds=cmBlockService.getBlockUserIds(SecurityFrameworkUtils.getLoginUserId(), item.getTopicId());
                //评论集合List
                CmCommentPageReqVO cmCommentPageReqV= new CmCommentPageReqVO();
                cmCommentPageReqV.setPostId(item.getId());
                cmCommentPageReqV.setParentId(0L);
                cmCommentPageReqV.setUserId(SecurityFrameworkUtils.getLoginUserId());
                cmCommentPageReqV.setBlockUserIds(blockIds);
                List<CmCommentDO> comments = cmCommentService.getCmCommentPageShow(cmCommentPageReqV);
                // 拼接结果返回
                List<MemberUserRespDTO> users1 = memberUserApi.getUserList(
                        convertSet(comments, CmCommentDO::getUserId));
                Map<Long, MemberUserRespDTO> userMap1 = convertMap(users1, MemberUserRespDTO::getId);
                comments.forEach(item1 -> {
                    MemberUserRespDTO memberUse1 = userMap1.get(item1.getUserId());
                    if (memberUse1 != null) {
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
                        item1.setAvatar(memberUse1.getAvatar());
                        //评论回复
                        CmCommentPageReqVO cmCommentPageReqV2 = new CmCommentPageReqVO();
                        cmCommentPageReqV2.setPostId(item.getId());
                        cmCommentPageReqV2.setParentId(item1.getId());
                        cmCommentPageReqV2.setUserId(SecurityFrameworkUtils.getLoginUserId());
                        cmCommentPageReqV2.setBlockUserIds(blockIds);
                        List<CmCommentDO> comments2 = cmCommentService.getCmCommentPageShow(cmCommentPageReqV2);
                        // 拼接结果返回
                        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                                convertSet(comments2, CmCommentDO::getUserId));
                        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
                        comments2.forEach(item2 -> {
                            MemberUserRespDTO memberUse2 = userMap2.get(item2.getUserId());
                            if (memberUse2 != null) {
                                item2.setReplyTo(memberUse1.getNickname());
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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


    /**
     * 点赞帖子
     */
    @GetMapping("/like")
    @Operation(summary = "点赞贴子")
    public CommonResult<Boolean> like(@RequestParam("id") Long id)
    {
        // 获取当前用户ID
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        
        // 检查是否已点赞
        CmPostLikeDO cmPostLikeD = cmPostLikeService.selectLike(userId, id);
        if (cmPostLikeD != null) {
            return success(true); // 已点赞，直接返回成功
        }
        
        // 获取帖子信息（用于通知和更新点赞数）
        CmPostDO post = cmPostService.getCmPost(id);
        if (post == null) {
            return success(false); // 帖子不存在
        }
        
        // 创建点赞记录
        CmPostLikeSaveReqVO createReqVO = new CmPostLikeSaveReqVO();
        createReqVO.setUserId(userId);
        createReqVO.setPostId(id);
        cmPostLikeService.createCmPostLike(createReqVO);
        
        // 发布点赞通知事件（非自嗨情况）
        try {
            if (!userId.equals(post.getUserId())) {
                // 不是给自己点赞，发送通知
                String targetContentSnapshot = post.getTitle() != null ? post.getTitle() : "";
                
                SocialNotifySendReqDTO reqDTO = new SocialNotifySendReqDTO();
                reqDTO.setReceiverId(post.getUserId());
                reqDTO.setSenderId(userId);
                reqDTO.setTargetId(id);
                reqDTO.setTargetType(SocialTargetTypeEnum.POST.getType());
                reqDTO.setNotifyType(SocialNotifyTypeEnum.LIKE.getType());
                reqDTO.setContent("点赞了你的帖子");
                reqDTO.setTargetContentSnapshot(targetContentSnapshot);
                socialNotifyApi.sendSocialNotify(reqDTO);
            }
        } catch (Exception e) {
            // 通知事件发布失败不影响主流程，记录日志即可
            log.error("[like][发布点赞通知事件失败，postId={}, userId={}]", id, userId, e);
         }
        
        // 更新帖子点赞数
            post.setLikeCount((post.getLikeCount() != null ? post.getLikeCount() : 0) + 1);
            cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
        
            return success(true);
        }
    /**
     * 取消点赞帖子
     */
    @GetMapping("/unlike")
    @Operation(summary = "点赞贴子")
    public CommonResult<Boolean> unlike(@RequestParam("id") Long id)
    {
        //取消点赞
        Long userId=SecurityFrameworkUtils.getLoginUserId();
        CmPostLikeDO cmPostLikeDO=cmPostLikeService.selectLike(userId,id);
        if(cmPostLikeDO!=null){
            cmPostLikeService.deleteCmPostLike(cmPostLikeDO.getId());
        }
        // 暂时只更新点赞数
        CmPostDO post = cmPostService.getCmPost(id);
        if (post != null) {
            int likeCount = Math.max(0, (post.getLikeCount() != null ? post.getLikeCount() : 0) - 1);
            post.setLikeCount(likeCount);
            cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
            return success(true);
        }
        return success(false);
    }





    /**
     * 获得我收藏的帖子
     */
    @GetMapping("/collectPage")
    @Operation(summary = "获得我收藏的帖子")
    public CommonResult<PageResult<CmPostRespVO>> collectPage(@Valid CmPostPageReqVO pageReqVO) {
        CmTopicMemberDO cmTopicMember=cmTopicMemberService.getCmTopicMember(SecurityFrameworkUtils.getLoginUserId(),pageReqVO.getTopicId());
        if(cmTopicMember==null || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==5) || (cmTopicMember.getStatus()!=null && cmTopicMember.getStatus()==3)){//终止服务
            return success(new PageResult<>());
        }else{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endTime = cmTopicMember.getEndTime();
            if (now.isAfter(endTime)) {
                //到期了续费
                return success(new PageResult<>());
            }
        }
        pageReqVO.setTopicId(pageReqVO.getTopicId());
        pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<CmPostDO> pageResult = cmPostService.getCmPostCollectPage(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), CmPostDO::getUserId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);
        pageResult.getList().forEach(item -> {
            MemberUserRespDTO memberUserRespDTO = userMap.get(item.getUserId());
            if (memberUserRespDTO != null) {
                item.setNickname(memberUserRespDTO.getNickname());
                //item.setNickname(StringUtils.isNotBlank(memberUserRespDTO.getName())?memberUserRespDTO.getName() : memberUserRespDTO.getNickname());
                item.setAvatar(memberUserRespDTO.getAvatar());
                //评论数
                item.setCommentCount(cmCommentService.selectCmCommentCount(item.getId(), SecurityFrameworkUtils.getLoginUserId()));
                // 是否点赞
                CmPostLikeDO cmPostLikeDO = cmPostLikeService.selectLike(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmPostLikeDO != null) {
                    item.setIsLiked(true);
                } else {
                    item.setIsLiked(false);
                }
              /*  //是否收藏
                CmCollectDO cmCollectDO = cmCollectService.selectCollect(SecurityFrameworkUtils.getLoginUserId(), item.getId());
                if (cmCollectDO != null) {
                    item.setIsCollected(true);
                } else {
                    item.setIsCollected(false);
                }*/

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
                        item1.setNickname(memberUse1.getNickname());
                        //item1.setNickname(StringUtils.isNotBlank(memberUse1.getName())?memberUse1.getName() : memberUse1.getNickname());
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
                                item2.setNickname(memberUse2.getNickname());
                                //item2.setNickname(StringUtils.isNotBlank(memberUse2.getName())?memberUse2.getName() : memberUse2.getNickname());
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
    /**
     * 收藏帖子
     */
    @PostMapping("/collect")
    @Operation(summary = "收藏贴子")
    public CommonResult<Boolean> collect(@PathVariable Long id)
    {
        //插入收藏
        Long userId=SecurityFrameworkUtils.getLoginUserId();
        CmCollectSaveReqVO createReqVO=new CmCollectSaveReqVO();
        createReqVO.setUserId(userId);
        createReqVO.setPostId(id);
        cmCollectService.createCmCollect(createReqVO);
        // 只更新收藏数
        CmPostDO post = cmPostService.getCmPost(id);
        if (post != null) {
            post.setCollectCount((post.getCollectCount() != null ? post.getCollectCount() : 0) + 1);
            cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
            return success(true);
        }
        return success(true);
    }
    /**
     * 取消收藏
     */
    @PostMapping("/uncollect")
    @Operation(summary = "取消收藏贴子")
    public CommonResult<Boolean> uncollect(@PathVariable Long id)
    {
        //取消收藏
        Long userId=SecurityFrameworkUtils.getLoginUserId();
        CmCollectDO cmPostLikeDO=cmCollectService.selectCollect(userId,id);
        if(cmPostLikeDO!=null){
            cmCollectService.deleteCmCollect(cmPostLikeDO.getId());
        }
        // 更新收藏数
        CmPostDO post = cmPostService.getCmPost(id);
        if (post != null) {
            int collectCount = Math.max(0, (post.getCollectCount() != null ? post.getCollectCount() : 0) - 1);
            post.setCollectCount(collectCount);
            cmPostService.updateCmPost(BeanUtils.toBean(post, CmPostSaveReqVO.class));
            return success(true);
        }
        return success(true);
    }
}
