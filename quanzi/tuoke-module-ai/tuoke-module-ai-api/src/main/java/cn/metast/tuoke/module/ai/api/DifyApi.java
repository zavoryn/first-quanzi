package cn.metast.tuoke.module.ai.api;


import com.alibaba.fastjson.JSONObject;

/**
 * 商品 SPU API 接口
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface DifyApi {

    /**
     * 根据图片链接 生成商品标题
     *
     * @param imgUrl SPU 图片链接
     * @return JSONObject
     */
    JSONObject generateWorkByXc(String imgUrl);

}
