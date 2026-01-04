package cn.metast.tuoke.module.live.controller.admin.snsNews;

import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.SnsAblumRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.service.snsAblum.SnsAblumService;
import cn.metast.tuoke.module.live.service.snsAppconfig.SnsAppConfigService;
import cn.metast.tuoke.module.live.service.snsPost.SnsPostService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

import cn.metast.tuoke.module.live.controller.admin.snsNews.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsNews.SnsNewsDO;
import cn.metast.tuoke.module.live.service.snsNews.SnsNewsService;

@Tag(name = "管理后台 - 新闻信息")
@RestController
@RequestMapping("/live/sns-news")
@Validated
public class SnsNewsController {

    @Resource
    private SnsNewsService snsNewsService;
    @Autowired
    private SnsPostService snsPostService;
    @Autowired
    private SnsAblumService snsAblumService;
    @Autowired
    private SnsAppConfigService configService;

    @PostMapping("/create")
    @Operation(summary = "创建新闻信息")
    public CommonResult<Long> createSnsNews(@Valid @RequestBody SnsPostRespVO snsPost) {

        Long uid =SecurityFrameworkUtils.getLoginUserId();
        snsPost.setUserId(uid);
        snsPost.setPostType("news");
        if(!CollectionUtils.isEmpty(snsPost.getPicUrl())){
            List<Map<String,Object>> msp=snsPost.getPicUrl();
            snsPost.setPicNum(msp.size());
        }else{
            snsPost.setPicNum(0);
        }
        if(StringUtils.isNotBlank(snsPost.getCoverUrl())){
            snsPost.setCoverUrl(snsPost.getCoverUrl());
        }
        snsPostService.insertSnsPost(snsPost);
        snsAblumService.insertSnsAblum(snsPost);

        SnsNewsSaveReqVO snsNews=new SnsNewsSaveReqVO();
        snsNews.setPostId(snsPost.getPostId());
        snsNews.setTypeSeting(snsPost.getTypeSeting());
        snsNews.setNewsConfig(snsPost.getNewsConfig());
        return success(snsNewsService.createSnsNews(snsNews));
    }
    @PutMapping("/update")
    @Operation(summary = "更新新闻信息")
    public CommonResult<Boolean> updateSnsNews(@Valid @RequestBody SnsPostSaveReqVO snsPost) {
        //先删除
        snsAblumService.deleteSnsAblumById(snsPost.getPostId());
        snsAblumService.insertSnsAblumList(snsPost);
        if(!CollectionUtils.isEmpty(snsPost.getPicUrl())){
            List<Map<String,Object>> msp=snsPost.getPicUrl();
            snsPost.setPicNum(msp.size());
        }else{
            snsPost.setPicNum(0);
        }
        SnsNewsSaveReqVO snsNews=new SnsNewsSaveReqVO();
        snsNews.setNewsId(snsPost.getNewsId());
        snsNews.setTypeSeting(snsPost.getTypeSeting());
        snsNews.setNewsConfig(snsPost.getNewsConfig());
        if(StringUtils.isNotBlank(snsPost.getCoverUrl())){
            snsPost.setCoverUrl(snsPost.getCoverUrl());
        }
        if(StringUtils.isNotBlank(snsPost.getMediaUrl())) {
            snsPost.setMediaUrl(snsPost.getMediaUrl());
        }
        if(StringUtils.isNotBlank(snsPost.getFileUrl())){
            snsPost.setFileUrl(snsPost.getFileUrl());
        }
        snsNewsService.updateSnsNewsId(snsNews);
        snsPostService.updateSnsPostPc(snsPost);
        return success(true);
    }

    /**
     * 修改新闻内容信息
     */
    @PutMapping(value = "/updateNewsContent")
    public CommonResult<Boolean> updateNewsContent(@RequestBody SnsPostSaveReqVO snsPost)
    {
        snsPostService.updateSnsPostPc(snsPost);
        return success(true);
    }

    /**
     * 删除新闻信息
     */
    @DeleteMapping("/{newsIds}")
    public CommonResult<Boolean> remove(@PathVariable Long[] newsIds)
    {
        if(newsIds!=null){
            for(Long id:newsIds){
                SnsNewsRespVO snsNews=snsNewsService.selectSnsNewsById(id);
                if(snsNews!=null){
                    snsAblumService.deleteSnsAblumById(snsNews.getPostId());
                    snsPostService.deleteSnsPostById(snsNews.getPostId());
                }
            }
        }
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除新闻信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsNews(@RequestParam("id") Long id) {
        SnsNewsRespVO snsNews=snsNewsService.selectSnsNewsById(id);
        if(snsNews!=null){
            snsAblumService.deleteSnsAblumById(snsNews.getPostId());
            snsPostService.deleteSnsPostById(snsNews.getPostId());
            snsNewsService.deleteSnsNewsByIds(id);
        }
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得新闻信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsNewsRespVO> getSnsNews(@RequestParam("newsId") Long newsId) {
        SnsNewsRespVO snsNews = snsNewsService.selectSnsNewsById(newsId);
        return success(BeanUtils.toBean(snsNews, SnsNewsRespVO.class));
    }
    @PermitAll
    @GetMapping("/page")
    @Operation(summary = "获得新闻信息分页")
    public CommonResult<PageResult<SnsNewsRespVO>> getSnsNewsPage(@Valid SnsNewsPageReqVO pageReqVO) {
        PageResult<SnsNewsRespVO> pageResult = snsNewsService.getSnsNewsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsNewsRespVO.class));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeNewsFlag")
    public CommonResult<Boolean> changeNewsFlag(@Valid @RequestBody SnsNewsSaveReqVO snsNews)
    {
        snsNewsService.updateSnsNewsId(snsNews);
        return success(true);
    }

    @GetMapping(value = "/{newsId}")
    public  CommonResult<SnsNewsRespVO> getInfo(@RequestParam("newsId") Long newsId)
    {
        SnsNewsRespVO snsNews= snsNewsService.selectSnsNewsById(newsId);
        if(snsNews!=null){
            if(StringUtils.isNotBlank(snsNews.getCoverUrl())){
                snsNews.setCoverUrl(snsNews.getCoverUrl());
            }
            if(StringUtils.isNotBlank(snsNews.getMediaUrl())) {
                snsNews.setMediaUrl(snsNews.getMediaUrl());
            }
            if(StringUtils.isNotBlank(snsNews.getFileUrl())){
                snsNews.setFileUrl(snsNews.getFileUrl());
            }
        }
        return success(BeanUtils.toBean(snsNews, SnsNewsRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出新闻信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsNewsExcel(@Valid SnsNewsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsNewsRespVO> list = snsNewsService.getSnsNewsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "新闻信息.xls", "数据", SnsNewsRespVO.class,
                        BeanUtils.toBean(list, SnsNewsRespVO.class));
    }

}
