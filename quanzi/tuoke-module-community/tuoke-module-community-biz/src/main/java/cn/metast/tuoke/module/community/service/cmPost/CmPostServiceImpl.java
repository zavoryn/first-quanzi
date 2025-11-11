package cn.metast.tuoke.module.community.service.cmPost;

import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.module.community.controller.admin.cmMessage.vo.CmMessageSaveReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.CmTopicMemberPageReqVO;
import cn.metast.tuoke.module.community.controller.app.cmPost.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.service.cmMessage.CmMessageService;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
import cn.metast.tuoke.module.community.util.TopUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import cn.metast.tuoke.module.system.api.social.SocialUserApi;
import cn.metast.tuoke.module.system.api.social.dto.SocialUserRespDTO;
import cn.metast.tuoke.module.system.enums.social.SocialTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmPost.CmPostMapper;
import org.springframework.web.client.RestTemplate;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 用户发帖详情 Service 实现类
 *
 * @author 苏丹家园
 */
@Slf4j
@Service
@Validated
public class CmPostServiceImpl implements CmPostService {

    @Resource
    private CmPostMapper cmPostMapper;
    @Resource
    private SocialClientApi socialClientApi;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CmMessageService cmMessageService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Resource
    private SocialUserApi socialUserApi;
    @Override
    public Long createCmPost(CmPostSaveReqVO createReqVO) {
        // 插入
        CmPostDO cmPost = BeanUtils.toBean(createReqVO, CmPostDO.class);
        cmPostMapper.insert(cmPost);
        // 返回
        return cmPost.getId();
    }

    @Override
    public void updateCmPost(CmPostSaveReqVO updateReqVO) {
        // 校验存在
        validateCmPostExists(updateReqVO.getId());
        // 更新
        CmPostDO updateObj = BeanUtils.toBean(updateReqVO, CmPostDO.class);
        cmPostMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmPost(Long id) {
        // 校验存在
        validateCmPostExists(id);
        // 删除
        cmPostMapper.deleteById(id);
    }

    private void validateCmPostExists(Long id) {
        if (cmPostMapper.selectById(id) == null) {
            throw exception(CM_POST_NOT_EXISTS);
        }
    }

    @Override
    public CmPostDO getCmPost(Long id) {
        return cmPostMapper.selectById(id);
    }

    @Override
    public PageResult<CmPostDO> getCmPostPage(CmPostPageReqVO pageReqVO) {
        return cmPostMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CmPostDO> getCmPostMediaPage(CmPostPageReqVO pageReqVO) {
        IPage<CmPostDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmPostMapper.getCmPostMediaPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmPostDO> getCmPostPageNew(CmPostPageReqVO pageReqVO) {
        IPage<CmPostDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmPostMapper.getCmPostPageNew(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmPostDO> getCmPostPageTop(CmPostPageReqVO pageReqVO) {
        IPage<CmPostDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmPostMapper.getCmPostPageTop(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmPostDO> getCmPostCollectPage(CmPostPageReqVO pageReqVO) {
        IPage<CmPostDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmPostMapper.getCmPostCollectPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageResult<CmPostDO> getCmPostLikePage(CmPostPageReqVO pageReqVO) {
        IPage<CmPostDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        cmPostMapper.getCmPostLikePage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }
    @Override
    public void sendWeixinMsg(CmPostSaveReqVO createReqVO) {
       String accessToken=getAccess_token();
       List<Map<String, Object>> list=getFollow(accessToken);
       if(CollectionUtils.isEmpty(list)){
           return;
       }
      String appId=socialClientApi.getWxAppIdKey();
      //获取圈子下的成员
      CmTopicMemberPageReqVO pageReqVO=new CmTopicMemberPageReqVO();
      pageReqVO.setTopicId(createReqVO.getTopicId());
      pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
      List<CmTopicMemberDO> mList = cmTopicMemberService.getCmTopicMemberOwnList(pageReqVO).getList();
      for (CmTopicMemberDO cmTopicMemberDO : mList) {
          Long userId = cmTopicMemberDO.getUserId();
          //通过userId 查询绑定的unionid,获取opendID
          SocialUserRespDTO socialUser=socialUserApi.getSocialUserByUserId(UserTypeEnum.MEMBER.getValue(), userId, SocialTypeEnum.WECHAT_MINI_APP.getType());
          if(socialUser!=null && socialUser.getUnionid()!=null){
            for (Map<String, Object> mm:list) {
                      String unionid = (String) mm.get("unionid");
                      String openid = (String) mm.get("openid");
                      if(StringUtils.isBlank(unionid)){
                          continue;
                      }
                      if(StringUtils.isBlank(socialUser.getUnionid())){
                          continue;
                      }
                      if(socialUser.getUnionid().equals(unionid)){
                          String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken+"&t=" + System.currentTimeMillis();;
                          ModelMessage modelMessage = new ModelMessage();
                          modelMessage.setTouser(openid);
                          //modelMessage.setTemplate_id("BHxDL7cbr4vOqs8Rud5GRcmU2Tf9QqNuMPDoqYOXpKQ");
                          modelMessage.setTemplate_id("fcrca5qDEpsohX5tpjIRJ4vPIEkEoLbbiyczQB33eIo");
                          MiniProgram miniprogram=new MiniProgram();
                          miniprogram.setAppid(appId);
                          miniprogram.setPagepath("pages/create/myXq?id="+createReqVO.getTopicId());//小程序跳转
                          modelMessage.setMiniprogram(miniprogram);
                         /* KeyWord keyword1 = new KeyWord("与牛共舞金牛学习班");
                          KeyWord keyword2 = new KeyWord(TopUtils.getDayTime());
                          modelMessage.setData(new DataVo(keyword1,keyword2));*/
                          KeyWord first = new KeyWord("不显示了");
                          KeyWord keyword1 = new KeyWord("与牛共舞金牛学习班");
                          KeyWord keyword2 = new KeyWord(TopUtils.getDayTime());
                          KeyWord remark = new KeyWord("不显示了");
                          modelMessage.setData(new Data(first,keyword1,keyword2,remark));
                          String json = JSON.toJSONString(modelMessage);
                          ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, json, JSONObject.class);
                          if (responseEntity != null) {
                              String jsn = JSONObject.toJSONString(responseEntity.getBody());
                              JSONObject rs = JSONObject.parseObject(jsn);
                              int errorCode = (int) rs.get("errcode");
                              if (errorCode == 0) {
                                  log.info("Send Success");
                                  CmMessageSaveReqVO message = new CmMessageSaveReqVO();
                                  message.setFromUserId(1L);
                                  message.setToUserId(createReqVO.getUserId());
                                  message.setType(0);
                                  message.setContent(json);
                                  cmMessageService.createCmMessage(message);
                              } else {
                                  log.info("服务号发送失败:" + errorCode + "," + rs.get("errmsg"));
                         }
                      }
                }
             }
         }
      }
    }

    /**
     * 获取全部关注列表
     * @return
     */
    public List<Map<String, Object>> getFollow(String accessToken) {
        String nextOpenid = "";
        int total = 0;
        List<String> openIds=new ArrayList<>();
        while (true) {
            String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken + "&next_openid=" + nextOpenid;
            try {
                String json = restTemplate.getForObject(url, String.class);
                JSONObject jsonResponse = JSONObject.parseObject(json);
                    int count = jsonResponse.getInteger("count");
                    total += count;
                    if (count == 0) {
                        log.info("No more users to fetch.");
                        break;
                    }
                    JSONObject obj = jsonResponse.getJSONObject("data");
                    JSONArray data = obj.getJSONArray("openid");
                    for (int i = 0; i < data.size(); i++) {
                        String openid = data.getString(i);
                        log.info("openid: {}", openid);
                        openIds.add(openid);
                    }
                    nextOpenid = jsonResponse.getString("next_openid");
                    if (nextOpenid.isEmpty()) {
                        log.info("All users fetched.");
                        break;
                    }
                } catch(Exception e){
                    log.error("Failed to fetch user data", e);
                    break;
                }
        }
        log.info("Total users fetched: {}", total);
        //批量获取关注用户信息
        List<Map<String, Object>> mb=batchget(openIds,accessToken);
        return mb;
    }
    //批量获取opendId
    public List<Map<String, Object>> batchget(List<String> openIds,String accessToken) {
        List<Map<String, Object>> allResults = new ArrayList<>();
        if(CollectionUtils.isEmpty(openIds)){
            return allResults;
        }
        // 分批处理，每批100个
        int batchSize = 100;
        int totalBatches = (int) Math.ceil((double) openIds.size() / batchSize);

        for (int batchIndex = 0; batchIndex < totalBatches; batchIndex++) {
            int start = batchIndex * batchSize;
            int end = Math.min(start + batchSize, openIds.size());
            List<String> batchOpenIds = openIds.subList(start, end);

            log.debug("处理第 {}/{} 批次, 数量: {}", batchIndex + 1, totalBatches, batchOpenIds.size());

            // 调用单批次查询
            List<Map<String, Object>> batchResults = batchGetSingleBatch(accessToken, batchOpenIds);
            allResults.addAll(batchResults);

            // 添加延迟，避免触发微信API频率限制
            if (batchIndex < totalBatches - 1) { // 最后一批不需要延迟
                try {
                    Thread.sleep(200); // 200毫秒延迟
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("批次延迟被中断");
                    break;
                }
            }
        }
            log.info("批量获取用户信息完成，成功获取: {} 个用户信息", allResults.size());
            return allResults;
    }
    private List<Map<String, Object>> batchGetSingleBatch(String accessToken, List<String> openIds) {
        List<Map<String, Object>> results = new ArrayList<>();

        if (CollectionUtils.isEmpty(openIds) || openIds.size() > 100) {
            log.error("单批次openIds数量无效: {}", openIds == null ? 0 : openIds.size());
            return results;
        }
        String url2 = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;
        try {
            // 构建用户列表
            List<Map<String, String>> userList = new ArrayList<>();
            for (String openId : openIds) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("openid", openId);
                userMap.put("lang", "zh_CN");
                userList.add(userMap);
            }
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_list", userList);
            String json = JSON.toJSONString(requestBody);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url2, json, JSONObject.class);
            if (responseEntity != null) {
                String jsn = JSONObject.toJSONString(responseEntity.getBody());
                JSONObject rs = JSONObject.parseObject(jsn);
                JSONArray jsonArray=rs.getJSONArray("user_info_list");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // 检查单个用户是否有错误
                    Integer subscribe = jsonObject.getInteger("subscribe");
                    if (subscribe != null && subscribe == 0) {
                        // 用户未关注，跳过
                        continue;
                    }
                    String openid=jsonObject.getString("openid");
                    String unionid=jsonObject.getString("unionid");
                    Map<String, Object> mp=new HashMap<>();
                    mp.put("openid",openid);
                    mp.put("unionid", unionid != null ? unionid : "");
                    results.add(mp);
                }
            }
        } catch (Exception e) {
            log.error("批次获取用户信息异常，批次大小: {}", openIds.size(), e);
        }
        return results;
    }


    public String getAccess_token() {
        //获得key和secret
        Map<String, Object> client = socialClientApi.getWxServiceKey();
        String APPID = (String)client.get("client_id");
        String APPSECREY = (String)client.get("secret_key");
        if(StringUtils.isBlank(APPID)){
            throw new RuntimeException("请检查微信服务号配置");
        }
        //获得token
        String redisKey = APPID+"_"+APPSECREY+"_st_new";
        String token = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(token)) {
            try {
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                        "&appid=" + APPID + "&secret=" + APPSECREY;
                String json = restTemplate.getForObject(url, String.class);
                JSONObject myJson = JSONObject.parseObject(json);
                token=myJson.get("access_token").toString();
                stringRedisTemplate.opsForValue().set(redisKey, token, 7100, TimeUnit.SECONDS);
            }catch (Exception e){
                log.info("微信小店token 获取失败,请检查白名单获取");
            }
        }
        return token;
    }
}
