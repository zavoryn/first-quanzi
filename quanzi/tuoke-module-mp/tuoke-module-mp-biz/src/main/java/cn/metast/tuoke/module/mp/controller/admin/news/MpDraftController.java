package cn.metast.tuoke.module.mp.controller.admin.news;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.collection.CollectionUtils;
import cn.metast.tuoke.framework.common.util.object.PageUtils;
import cn.metast.tuoke.module.mp.controller.admin.dify.DifyMpClient;
import cn.metast.tuoke.module.mp.controller.admin.news.vo.MpDraftPageReqVO;
import cn.metast.tuoke.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.metast.tuoke.module.mp.framework.mp.core.MpServiceFactory;
import cn.metast.tuoke.module.mp.service.material.MpMaterialService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.*;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.MapUtils.findAndThen;
import static cn.metast.tuoke.module.mp.enums.ErrorCodeConstants.*;

@Tag(name = "管理后台 - 公众号草稿")
@RestController
@RequestMapping("/mp/draft")
@Validated
public class MpDraftController extends DifyMpClient {

    @Resource
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpMaterialService mpMaterialService;


    @GetMapping("/page")
    @Operation(summary = "获得草稿分页")
    @PreAuthorize("@ss.hasPermission('mp:draft:query')")
    public CommonResult<PageResult<WxMpDraftItem>> getDraftPage(MpDraftPageReqVO reqVO) {
        // 从公众号查询草稿箱
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        WxMpDraftList draftList;
        try {
            draftList = mpService.getDraftService().listDraft(PageUtils.getStart(reqVO), reqVO.getPageSize());
        } catch (WxErrorException e) {
            throw exception(DRAFT_LIST_FAIL, e.getError().getErrorMsg());
        }
        // 查询对应的图片地址。目的：解决公众号的图片链接无法在我们后台展示
        setDraftThumbUrl(draftList.getItems());

        // 返回分页
        return success(new PageResult<>(draftList.getItems(), draftList.getTotalCount().longValue()));
    }

    private void setDraftThumbUrl(List<WxMpDraftItem> items) {
        // 1.1 获得 mediaId 数组
        Set<String> mediaIds = new HashSet<>();
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem -> mediaIds.add(newsItem.getThumbMediaId())));
        if (CollUtil.isEmpty(mediaIds)) {
            return;
        }
        // 1.2 批量查询对应的 Media 素材
        Map<String, MpMaterialDO> materials = CollectionUtils.convertMap(mpMaterialService.getMaterialListByMediaId(mediaIds),
                MpMaterialDO::getMediaId);

        // 2. 设置回 WxMpDraftItem 记录
        items.forEach(item -> item.getContent().getNewsItem().forEach(newsItem ->
                findAndThen(materials, newsItem.getThumbMediaId(), material -> newsItem.setThumbUrl(material.getUrl()))));
    }

    @PostMapping("/create")
    @Operation(summary = "创建草稿")
    @Parameter(name = "accountId", description = "公众号账号的编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:draft:create')")
    public CommonResult<String> deleteDraft(@RequestParam("accountId") Long accountId,
                                            @RequestBody WxMpAddDraft draft) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            String mediaId = mpService.getDraftService().addDraft(draft);
            return success(mediaId);
        } catch (WxErrorException e) {
            throw exception(DRAFT_CREATE_FAIL, e.getError().getErrorMsg());
        }
    }

    @GetMapping("/aiCreate")
    @Operation(summary = "AI生成草稿")
    @PreAuthorize("@ss.hasPermission('mp:draft:create')")
    public CommonResult<JSONObject> aiCreate(@RequestParam("accountId") Long accountId,
                                             @RequestParam(required = true,name = "title") String title) {
        JSONObject result = new JSONObject();
        try {
            JSONObject params = new JSONObject();
            //文案生成
            params.put("response_mode","blocking");
            params.put("user","qinmy@metast.cn");
            params.put("files","");
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("type", "text");
            inputs.put("platform", "wxgzh");
            inputs.put("size","500");
            inputs.put("content",title);
            params.put("inputs",inputs);
            JSONObject objResult = sendWxRequest(getDeaultClient(), "POST", "/workflows/run", params, "app-TIGLwLfH2mILOLwIgxollM70");
            if(ObjectUtil.isNotEmpty(objResult) && objResult.containsKey("data")){
                JSONObject data = objResult.getJSONObject("data");
                if(ObjectUtil.isNotEmpty(data) && data.containsKey("outputs")){
                    //处理order 数据，组装fifee
                    JSONObject outputs = data.getJSONObject("outputs");
                    JSONObject output = outputs.getJSONObject("output");
                    result.put("content",JSONObject.parseObject(output.getString("output")));
                }
            }
            //图片生成
            inputs = new HashMap<>();
            inputs.put("type", "文生图片");
            inputs.put("prompt",title);
            params.put("inputs",inputs);
            objResult = sendWxRequest(getDeaultClient(), "POST", "/workflows/run", params, "app-FniSZO0Y6qPpzJp90iuz8MTV");
            if(ObjectUtil.isNotEmpty(objResult) && objResult.containsKey("data")){
                JSONObject data = objResult.getJSONObject("data");
                if(ObjectUtil.isNotEmpty(data) && data.containsKey("outputs")){
                    //处理order 数据，组装fifee
                    JSONObject outputs = data.getJSONObject("outputs");
                    JSONArray output = outputs.getJSONArray("output");
                    if(CollectionUtil.isNotEmpty(output)){
                        JSONObject jsonObject = output.getJSONObject(0);
                        JSONArray image_urls = jsonObject.getJSONArray("image_urls");
                        result.put("image",mpMaterialService.uploadPermanentMaterialURL(accountId,image_urls.getString(0)));
                    }
                }
            }

//            String data = "{\n" +
//                    "        \"image\": {\n" +
//                    "            \"createTime\": 1757644806122,\n" +
//                    "            \"updateTime\": 1757644806122,\n" +
//                    "            \"creator\": \"1661908204076027905\",\n" +
//                    "            \"updater\": \"1661908204076027905\",\n" +
//                    "            \"deleted\": null,\n" +
//                    "            \"id\": 103,\n" +
//                    "            \"accountId\": 6,\n" +
//                    "            \"appId\": \"wx3911e64555c673c7\",\n" +
//                    "            \"mediaId\": \"6i8pT9qLhMHulogNBXvN6Dk7iTkg1kyJ0V2NmSkZg3Ee88O9gWlm0FczoCXH6kAY\",\n" +
//                    "            \"type\": \"image\",\n" +
//                    "            \"permanent\": true,\n" +
//                    "            \"url\": \"https://dashscope-result-wlcb-acdr-1.oss-cn-wulanchabu-acdr-1.aliyuncs.com/1d/5c/20250912/5020e443/29e0b021-3bdc-45c6-959e-723857d8896e2765251993.png?Expires=1757731192&OSSAccessKeyId=LTAI5tKPD3TMqf2Lna1fASuh&Signature=gBVDsJYS0EI7Fz7ID%2BnZZ%2Fnu6ho%3D\",\n" +
//                    "            \"name\": \"29e0b021-3bdc-45c6-959e-723857d8896e2765251993.png\",\n" +
//                    "            \"mpUrl\": \"http://mmbiz.qpic.cn/mmbiz_png/5ORLLWEMKcafAAlFzFlNYHSCtkibYbLfNXQS75RLRg5Wr2YbjfDt0su0eGBmhiaMM2rgSdYoYKu25DBEdcNFbY6g/0?wx_fmt=png\",\n" +
//                    "            \"title\": null,\n" +
//                    "            \"introduction\": null\n" +
//                    "        },\n" +
//                    "        \"content\": {\n" +
//                    "            \"label\": \"孤独成长、心灵治愈、生活感悟、情感文案、正能量\",\n" +
//                    "            \"content\": \"别害怕孤独，连落日都会为你加冕。\\n\\n在这个喧嚣的世界里，我们总在追逐人群的温度，害怕独处，害怕沉默，害怕一个人走在黄昏的小路上。可你有没有发现，最美的风景，往往出现在你独自前行的时候？\\n\\n清晨的阳光洒在空荡的街道，午后的咖啡杯升腾着热气，夜晚的星空安静地铺满天际——这些时刻，没有观众，却最真实。孤独不是被遗忘的角落，而是灵魂得以呼吸的空间。\\n\\n你不必时刻合群，也不必为了融入而委屈自己。有时候，一个人吃饭、一个人旅行、一个人思考，反而能听见内心最清晰的声音。那些你以为的寂寞，其实是成长的前奏。\\n\\n就像每天落幕的夕阳，它不因无人观赏就停止绚烂。相反，它把最后的光洒向大地，像一顶金色的王冠，轻轻戴在每一个坚持走到黄昏的人头上。\\n\\n所以啊，别怕孤独。当你学会与自己相处，你会发现，寂静深处，藏着最强大的力量。你不是孤单，你是在加冕。\\n\\n愿你在独行的路上，也能看见属于自己的落日，温柔而辉煌，照亮你前行的每一步。\"\n" +
//                    "        }\n" +
//                    "    }";
//            result = JSONObject.parseObject(data);
        } catch (Exception e) {
        }
        return success(result);
    }

    @PutMapping("/update")
    @Operation(summary = "更新草稿")
    @Parameters({
            @Parameter(name = "accountId", description = "公众号账号的编号", required = true, example = "1024"),
            @Parameter(name = "mediaId", description = "草稿素材的编号", required = true, example = "xxx")
    })
    @PreAuthorize("@ss.hasPermission('mp:draft:update')")
    public CommonResult<Boolean> deleteDraft(@RequestParam("accountId") Long accountId,
                                             @RequestParam("mediaId") String mediaId,
                                             @RequestBody List<WxMpDraftArticles> articles) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            for (int i = 0; i < articles.size(); i++) {
                WxMpDraftArticles article = articles.get(i);
                mpService.getDraftService().updateDraft(new WxMpUpdateDraft(mediaId, i, article));
            }
            return success(true);
        } catch (WxErrorException e) {
            throw exception(DRAFT_UPDATE_FAIL, e.getError().getErrorMsg());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除草稿")
    @Parameters({
            @Parameter(name = "accountId", description = "公众号账号的编号", required = true, example = "1024"),
            @Parameter(name = "mediaId", description = "草稿素材的编号", required = true, example = "xxx")
    })
    @PreAuthorize("@ss.hasPermission('mp:draft:delete')")
    public CommonResult<Boolean> deleteDraft(@RequestParam("accountId") Long accountId,
                                             @RequestParam("mediaId") String mediaId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            mpService.getDraftService().delDraft(mediaId);
            return success(true);
        } catch (WxErrorException e) {
            throw exception(DRAFT_DELETE_FAIL, e.getError().getErrorMsg());
        }
    }

}
