package cn.metast.tuoke.module.community.service.cmTopic;

import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicRespVO;
import cn.metast.tuoke.module.community.controller.admin.cmTopic.vo.CmTopicSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.dal.mysql.cmConfig.CmConfigMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopicmember.CmTopicMemberMapper;
import cn.metast.tuoke.module.community.service.cmMessage.CmMessageService;
import cn.metast.tuoke.module.community.service.cmTopicmember.CmTopicMemberService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import cn.metast.tuoke.module.system.api.social.SocialUserApi;
import cn.metast.tuoke.module.system.api.social.dto.SocialUserRespDTO;
import cn.metast.tuoke.module.system.api.social.dto.SocialWxQrcodeReqDTO;
import cn.metast.tuoke.module.system.enums.social.SocialTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.CM_TOPIC_NOT_EXISTS;

/**
 * 圈子详情 Service 实现类
 *
 * @author 苏丹家园
 */
@Slf4j
@Service
@Validated
public class CmTopicServiceImpl implements CmTopicService {

    @Resource
    private CmTopicMapper cmTopicMapper;

    @Resource
    private CmTopicMemberMapper cmTopicMemberMapper;

    @Resource
    private CmConfigMapper cmConfigMapper;

    @Resource
    private CmMessageService cmMessageService;
    @Resource
    private CmTopicMemberService cmTopicMemberService;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SocialUserApi socialUserApi;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private MemberUserApi memberUserApi;
    @Override
    public Long createCmTopic(CmTopicSaveReqVO createReqVO) {
        // 插入
        CmTopicDO cmTopic = BeanUtils.toBean(createReqVO, CmTopicDO.class);
        cmTopicMapper.insert(cmTopic);
        // 返回
        return cmTopic.getId();
    }
    @Override
    public void updateCmTopic(CmTopicSaveReqVO updateReqVO) {
        // 校验存在
        validateCmTopicExists(updateReqVO.getId());
        // 更新
        CmTopicDO updateObj = BeanUtils.toBean(updateReqVO, CmTopicDO.class);
        cmTopicMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmTopic(Long id) {
        // 校验存在
        validateCmTopicExists(id);
        // 删除
        cmTopicMapper.deleteById(id);
    }

    private void validateCmTopicExists(Long id) {
        if (cmTopicMapper.selectById(id) == null) {
            throw exception(CM_TOPIC_NOT_EXISTS);
        }
    }

    @Override
    public CmTopicDO getCmTopic(Long id) {
        return cmTopicMapper.selectById(id);
    }

    @Override
    public PageResult<CmTopicDO> getCmTopicPage(CmTopicPageReqVO pageReqVO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 根据部门获取允许查看的圈子ID（超级管理员返回所有圈子）
        List<Long> topicIds = cmConfigMapper.selectTopicIdsByDeptId(loginUserDeptId, cmTopicMapper);
        // 如果该部门没有配置圈子，返回空结果
        if (topicIds == null || topicIds.isEmpty()) {
            return PageResult.empty();
        }
        return cmTopicMapper.selectPageByTopicIds(pageReqVO, topicIds);
    }
    /**
     * 获取当天剩余次数
     */
    public Long getRemainingCountOptimized(CmTopicDO cmTopicDO) {
        LocalDateTime sendTime = cmTopicDO.getSendTime();
        LocalDate today = LocalDate.now();
        // 如果不是今天，返回20次；如果是今天，返回剩余次数
        if (sendTime == null || !sendTime.toLocalDate().equals(today)) {
            cmTopicDO.setSendTime(LocalDateTime.now());
            cmTopicDO.setSendNum(20L);
            cmTopicMapper.updateById(cmTopicDO);
            return 20L;
        } else {
            return cmTopicDO.getSendNum();
        }
    }
    @Override
    public Map<String, Object> getTopicList(CmTopicRespVO cmTopicRespVO) {
        Map<String, Object> mm=new HashMap<>();
        //查询我创建的新球
        List<CmTopicDO> ownTopic=cmTopicMapper.getTopicList(cmTopicRespVO);
        if(CollectionUtils.isNotEmpty(ownTopic)){
            for(CmTopicDO cm:ownTopic){
                //更新微信发送次数
                Long num=getRemainingCountOptimized(cm);
                cm.setSendNum(num);
            }
        }
        mm.put("ownTopic", ownTopic);
        //我加入的
        List<CmTopicDO> joinTopic=new ArrayList<>();
        List<CmTopicMemberDO> cmTopicMemberDOS=cmTopicMemberMapper.getJoinTopicList(cmTopicRespVO);
        if(CollectionUtils.isNotEmpty(cmTopicMemberDOS)){
            for(CmTopicMemberDO cm:cmTopicMemberDOS){
                if(cm.getRole()!=null && cm.getRole()==0) {
                    //不是管理员的
                    CmTopicDO cmTopicDO=cmTopicMapper.selectOne(CmTopicDO::getId, cm.getTopicId(),CmTopicDO::getStatus,0);
                    if(cmTopicDO!=null) {
                        //到期时间
                        cmTopicDO.setEndTime(cm.getEndTime());
                        joinTopic.add(cmTopicDO);
                    }
                }
            }
        }
        mm.put("joinTopic", joinTopic);
        return mm;
    }

    @Override
    public Map<String,Object> paytypeoy(Long topicId, Long type) {
        // 免费会员直接跳过签署检验
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        CmTopicMemberDO member = cmTopicMemberMapper.selectOne(
                CmTopicMemberDO::getTopicId, topicId,
                CmTopicMemberDO::getUserId, userId
        );
        if (member != null
                && Integer.valueOf(1).equals(member.getFreeStatus())
                && member.getEndTime() != null
                && member.getEndTime().isAfter(LocalDateTime.now())) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", "true");
            result.put("code", 200);
            return result;
        }
        //默认首次签约
        Integer isContract = 0;
        if (member != null && member.getIsContract()!=null && member.getIsContract()==1) {
            //续费签约
            isContract = 1;
        }

        CmTopicDO cmTopic=cmTopicMapper.selectById(topicId);
        if(cmTopic!=null){
            //金额
            Integer price=0;
            Integer day=0;
            String unionid="";
            if(type==1){
                price=cmTopic.getMonthlyPrice();
                day=30;
            }else if(type==2){
                price=cmTopic.getBimonthlyPrice();
                day=60;
            }else if(type==3){
                price=cmTopic.getQuarterlyPrice();
                day=90;
            }else if(type==4){
                price=cmTopic.getAprilPrice();
                day=180;
            }else if(type==5){
                price=cmTopic.getHalfYearlyPrice();
                day=7;
            }
            //unionid获取
            SocialUserRespDTO socialUser=socialUserApi.getSocialUserByUserId(UserTypeEnum.MEMBER.getValue(), SecurityFrameworkUtils.getLoginUserId(), SocialTypeEnum.WECHAT_MINI_APP.getType());
            if(socialUser!=null){
                unionid=socialUser.getUnionid();
            }
            try {
                String url2 = "https://signtd.tdtz888.com/api/Wechatspay/paytypeoy";
                Map<String, Object> requestBody = new HashMap<>();
                if(StringUtils.isNotBlank(unionid)) {
                    requestBody.put("unionid", unionid);
                }else{
                    MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(userId);
                    if(memberUserRespDTO!=null) {
                        requestBody.put("unionid", memberUserRespDTO.getMobile());
                        unionid=memberUserRespDTO.getMobile();
                    }
                }
                requestBody.put("isContract", isContract);
                String json = JSON.toJSONString(requestBody);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, requestEntity, String.class);
                if (responseEntity != null) {
                    String responseBody = responseEntity.getBody();
                    JSONObject rs = JSONObject.parseObject(responseBody);
                    String message = rs.getString("success");
                    if (message.equals("true")) {
                        //已签署
                        Map<String,Object> mm=new HashMap<>();
                        mm.put("success", "true");
                        mm.put("code", 200);
                        //更新已签署的状态-------待定
                        //更新用户的真实姓名和身份证
                        pii(unionid);
                        return mm;
                    } else {
                        //未签署
                        return eqbadd(price,day,unionid,topicId,isContract);
                    }

                }
            } catch (Exception e) {
            }
        }
        return null;
    }
    public void pii(String unionid) {
        try {
            //如果存在就不调用了
            MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(SecurityFrameworkUtils.getLoginUserId());
            if(memberUserRespDTO!=null && StringUtils.isBlank(memberUserRespDTO.getName())) {
                String url2 = "https://signtd.tdtz888.com/api/Wechatspay/pii";
                Map<String, Object> requestBody = new HashMap<>();
                if(StringUtils.isNotBlank(unionid)) {
                    requestBody.put("unionid", unionid);
                }else{
                    if(memberUserRespDTO!=null) {
                        requestBody.put("unionid", memberUserRespDTO.getMobile());
                    }
                }
                //requestBody.put("unionid", unionid);
                String json = JSON.toJSONString(requestBody);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, requestEntity, String.class);
                if (responseEntity != null) {
                    String responseBody = responseEntity.getBody();
                    JSONObject rs = JSONObject.parseObject(responseBody);
                    String message = rs.getString("success");
                    if (message.equals("true")) {
                        JSONObject data = rs.getJSONObject("data");
                        String name = data.getString("namePsn");
                        String idNumberPsn = data.getString("idNumberPsn");
                        if (StringUtils.isNotBlank(name)) {
                            memberUserApi.updateUserName(SecurityFrameworkUtils.getLoginUserId(), name);
                        }
                    }
                }
              }
        } catch (Exception e) {
        }
    }
    @Override
    public String getQrdUrl(Long topicId) {
        try {
            CmTopicDO cmTopic=cmTopicMapper.selectById(topicId);
            if(cmTopic!=null){
                if(StringUtils.isNotBlank(cmTopic.getQrcodeUrl())){
                    return cmTopic.getQrcodeUrl();
                }
            }
            SocialWxQrcodeReqDTO reqVO=new SocialWxQrcodeReqDTO();
            String pathWithParams = "pages/create/myXq";
            reqVO.setPath(pathWithParams);
            reqVO.setScene(String.valueOf(topicId));
            byte[] qrd=socialClientApi.getWxaQrcode(reqVO);
            String base64String = "data:image/png;base64,"+Base64.getEncoder().encodeToString(qrd);
            cmTopic.setQrcodeUrl(base64String);
            cmTopicMapper.updateById(cmTopic);
            return base64String;

           /*path 路径的
           SocialWxQrcodeReqDTO reqVO=new SocialWxQrcodeReqDTO();
            String pathWithParams = "pages/create/myXq?id="+topicId;
            reqVO.setPath(pathWithParams);
            reqVO.setScene(String.valueOf(topicId));
            byte[] qrd=socialClientApi.getWxaQrcode(reqVO);
            String base64String = "data:image/png;base64,"+Base64.getEncoder().encodeToString(qrd);
            cmTopic.setQrcodeUrl(base64String);
            cmTopicMapper.updateById(cmTopic);
            return base64String;*/

           /* String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + getAccess_token();
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("path", "pages/create/myXq?id=" + topicId);
            requestBody.put("width", 400);
            String json = JSON.toJSONString(requestBody);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity != null) {
                String jsn = JSONObject.toJSONString(responseEntity.getBody());
                log.info("微信二维码获取成功:{}", jsn);
                cmTopic.setQrcodeUrl(jsn);
                cmTopicMapper.updateById(cmTopic);
                return jsn;
            }*/
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getShortUrl(Long topicId) {
        CmTopicDO cmTopic=cmTopicMapper.selectById(topicId);
        if(cmTopic!=null){
            String pageUrl = "pages/create/myXq?id="+topicId;
            String title = cmTopic.getTopicName();
            String qrd=socialClientApi.getShortUrl(pageUrl,title);
            return qrd;
        }
        return "链接不存在";
    }

    @Override
    public String getLinkUrl(Long topicId) {
        CmTopicDO cmTopic=cmTopicMapper.selectById(topicId);
        if(cmTopic!=null){
            String pageUrl = "pages/create/myXq";
            String title ="id="+topicId;
            String qrd=socialClientApi.getLinkUrl(pageUrl,title);
            return qrd;
        }
        return "链接不存在";
    }

    public String getAccess_token(){
        String access_token=socialClientApi.getWxxxhServiceKey();
        return access_token;
    }


    public Map<String,Object> eqbadd(Integer price, Integer day, String unionid,Long topicId,Integer isContract) {
        try {
            String url2 = "https://signtd.tdtz888.com/acircleoy/index/eqbadd";
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("money", price);
            requestBody.put("days", day);
            requestBody.put("topicId", topicId);
            requestBody.put("isContract", isContract);
            requestBody.put("unionid", unionid);
            String json = JSON.toJSONString(requestBody);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url2, json, JSONObject.class);
            if (responseEntity != null) {
                String jsn = JSONObject.toJSONString(responseEntity.getBody());
                JSONObject rs = JSONObject.parseObject(jsn);
                String message = rs.getString("success");
                if (message.equals("true")) {
                    //创建成功
                    String url="https://signtd.tdtz888.com/acircleoy/index/index?unionid="+unionid+"&isContract="+isContract;
                    Map<String,Object> mm=new HashMap<>();
                    mm.put("success", "true");
                    mm.put("code", 300);//返回url
                    mm.put("url", url);
                    return mm;
                } else {
                    //创建失败
                    Map<String,Object> mm=new HashMap<>();
                    mm.put("code", 500);//失败
                    return mm;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
