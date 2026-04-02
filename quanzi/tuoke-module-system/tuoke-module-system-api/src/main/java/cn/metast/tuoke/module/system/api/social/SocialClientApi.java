package cn.metast.tuoke.module.system.api.social;

import cn.metast.tuoke.module.system.api.social.dto.*;
import cn.metast.tuoke.module.system.enums.social.SocialTypeEnum;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * 社交应用的 API 接口
 *
 * @author metast.cn
 */
public interface SocialClientApi {

    /**
     * 获得社交平台的授权 URL
     *
     * @param socialType  社交平台的类型 {@link SocialTypeEnum}
     * @param userType    用户类型
     * @param redirectUri 重定向 URL
     * @return 社交平台的授权 URL
     */
    String getAuthorizeUrl(Integer socialType, Integer userType, String redirectUri);

    /**
     * 创建微信公众号 JS SDK 初始化所需的签名
     *
     * @param userType 用户类型
     * @param url      访问的 URL 地址
     * @return 签名
     */
    SocialWxJsapiSignatureRespDTO createWxMpJsapiSignature(Integer userType, String url);

    //======================= 微信小程序独有 =======================

    /**
     * 获得微信小程序的手机信息
     *
     * @param userType  用户类型
     * @param phoneCode 手机授权码
     * @return 手机信息
     */
    SocialWxPhoneNumberInfoRespDTO getWxMaPhoneNumberInfo(Integer userType, String phoneCode);


    String getWxUnionid(Integer userType, String loginCode);

    /**
     * 获得小程序二维码
     *
     * @param reqVO 请求信息
     * @return 小程序二维码
     */
    byte[] getWxaQrcode(@Valid SocialWxQrcodeReqDTO reqVO);

    /**
     * 获得微信小程序的断链链接
     *
     * @return Access Token
     */
    String getLinkUrl(String pageUrl,String title);

    String getShortUrl(String pageUrl,String title);

    /**
     * 获得微信小程订阅模板
     *
     * @return 小程序订阅消息模版
     */
    List<SocialWxaSubscribeTemplateRespDTO> getWxaSubscribeTemplateList(Integer userType);

    /**
     * 发送微信小程序订阅消息
     *
     * @param reqDTO 请求
     */
    void sendWxaSubscribeMessage(SocialWxaSubscribeMessageSendReqDTO reqDTO);
    //======================= 微信小店独有 =======================
    Map<String,Object> getWxxdKey();
    //======================= 微信服务号独有 =======================
    Map<String,Object> getWxServiceKey();
    //======================= 微信小程序 =======================
    //获取token
    String getWxxxhServiceKey();
    //获取appid
    String getWxAppIdKey();
}
