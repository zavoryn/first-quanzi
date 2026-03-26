package cn.metast.tuoke.module.pay.service.channel;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.util.json.JsonUtils;
import cn.metast.tuoke.framework.pay.core.client.PayClient;
import cn.metast.tuoke.framework.pay.core.client.PayClientConfig;
import cn.metast.tuoke.framework.pay.core.client.PayClientFactory;
import cn.metast.tuoke.framework.pay.core.enums.channel.PayChannelEnum;
import cn.metast.tuoke.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import cn.metast.tuoke.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import cn.metast.tuoke.module.pay.convert.channel.PayChannelConvert;
import cn.metast.tuoke.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.metast.tuoke.module.pay.dal.mysql.channel.PayChannelMapper;
import cn.metast.tuoke.module.pay.framework.pay.core.WalletPayClient;
import cn.metast.tuoke.module.pay.ylsw.pay.YlswAppPayClient;
import cn.metast.tuoke.module.pay.ylsw.pay.YlswLitePayClient;
import cn.metast.tuoke.module.pay.ylsw.pay.YlswWapPayClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.pay.enums.ErrorCodeConstants.*;

/**
 * 支付渠道 Service 实现类
 *
 * @author aquan
 */
@Service
@Slf4j
@Validated
public class PayChannelServiceImpl implements PayChannelService {

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayChannelMapper payChannelMapper;

    @Resource
    private Validator validator;

    /**
     * 初始化，为了注册钱包
     */
    @PostConstruct
    public void init() {
        payClientFactory.registerPayClientClass(PayChannelEnum.WALLET, WalletPayClient.class);
        payClientFactory.registerPayClientClass(PayChannelEnum.YLSW_LITE, YlswLitePayClient.class);
        payClientFactory.registerPayClientClass(PayChannelEnum.YLSW_APP, YlswAppPayClient.class);
        payClientFactory.registerPayClientClass(PayChannelEnum.YLSW_WAP, YlswWapPayClient.class);
    }

    @Override
    public Long createChannel(PayChannelCreateReqVO reqVO) {
        // 断言是否有重复的
        PayChannelDO dbChannel = getChannelByAppIdAndCode(reqVO.getAppId(), reqVO.getCode());
        if (dbChannel != null) {
            throw exception(CHANNEL_EXIST_SAME_CHANNEL_ERROR);
        }

        // 新增渠道
        PayChannelDO channel = PayChannelConvert.INSTANCE.convert(reqVO)
                .setConfig(parseConfig(reqVO.getCode(), reqVO.getConfig()));
        payChannelMapper.insert(channel);
        return channel.getId();
    }

    @Override
    public void updateChannel(PayChannelUpdateReqVO updateReqVO) {
        // 校验存在
        PayChannelDO dbChannel = validateChannelExists(updateReqVO.getId());

        // 更新
        PayChannelDO channel = PayChannelConvert.INSTANCE.convert(updateReqVO)
                .setConfig(parseConfig(dbChannel.getCode(), updateReqVO.getConfig()));
        payChannelMapper.updateById(channel);
    }

    /**
     * 解析并校验配置
     *
     * @param code      渠道编码
     * @param configStr 配置
     * @return 支付配置
     */
    private List<PayClientConfig> parseConfig(String code, String configStr) {
        List<PayClientConfig> list = new ArrayList<>();
        // 解析配置
        Class<? extends PayClientConfig> payClass = PayChannelEnum.getByCode(code).getConfigClass();
        if (ObjectUtil.isNull(payClass)) {
            throw exception(CHANNEL_NOT_FOUND);
        }

        // 使用Hutool工具判断JSON类型
        cn.hutool.json.JSON json = cn.hutool.json.JSONUtil.parse(configStr);
        if (json instanceof cn.hutool.json.JSONArray) {
            // 如果是数组格式，根据业务需求决定如何处理
            JSONArray jsonArray = JSONArray.parseArray(configStr);
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                PayClientConfig config = JsonUtils.parseObject2(jsonObject.toString(), payClass);
                Assert.notNull(config);

                // 验证参数
                config.validate(validator);
                list.add(config);
            }
        }
        else if (json instanceof cn.hutool.json.JSONObject) {
            // 如果是对象格式，正常解析
            PayClientConfig config = JsonUtils.parseObject2(configStr, payClass);
            Assert.notNull(config);

            // 验证参数
            config.validate(validator);
            list.add(config);
        }
        return list;
    }

    @Override
    public void deleteChannel(Long id) {
        // 校验存在
        validateChannelExists(id);

        // 删除
        payChannelMapper.deleteById(id);
    }

    private PayChannelDO validateChannelExists(Long id) {
        PayChannelDO channel = payChannelMapper.selectById(id);
        if (channel == null) {
            throw exception(CHANNEL_NOT_FOUND);
        }
        return channel;
    }

    @Override
    public PayChannelDO getChannel(Long id) {
        return payChannelMapper.selectById(id);
    }

    @Override
    public List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds) {
        return payChannelMapper.selectListByAppIds(appIds);
    }

    @Override
    public PayChannelDO getChannelByAppIdAndCode(Long appId, String code) {
        return payChannelMapper.selectByAppIdAndCode(appId, code);
    }

    @Override
    public PayChannelDO validPayChannel(Long id) {
        PayChannelDO channel = payChannelMapper.selectById(id);
        validPayChannel(channel);
        return channel;
    }

    @Override
    public PayChannelDO validPayChannel(Long appId, String code) {
        PayChannelDO channel = payChannelMapper.selectByAppIdAndCode(appId, code);
        validPayChannel(channel);
        return channel;
    }

    private void validPayChannel(PayChannelDO channel) {
        if (channel == null) {
            throw exception(CHANNEL_NOT_FOUND);
        }
        if (CommonStatusEnum.DISABLE.getStatus().equals(channel.getStatus())) {
            throw exception(CHANNEL_IS_DISABLE);
        }
    }

    @Override
    public List<PayChannelDO> getEnableChannelList(Long appId) {
        return payChannelMapper.selectListByAppId(appId, CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public PayClient getPayClient(Long id) {
        PayChannelDO channel = validPayChannel(id);
//        List<PayClientConfig> configs = channel.getConfig();
        Object configObj = channel.getConfig();

        List<?> rawConfigs = (List<?>) configObj;
        // 尝试将 List<?> 转换为 List<PayClientConfig>
        List<PayClientConfig> configs = new ArrayList<>();
        for (Object obj : rawConfigs) {
            if (obj instanceof PayClientConfig) {
                configs.add((PayClientConfig) obj);
            } else if (obj instanceof Map) {
                // 如果是 Map，说明反序列化未成功，需要重新处理
                // 获取渠道类型以确定具体配置类
                Class<? extends PayClientConfig> configClass = PayChannelEnum.getByCode(channel.getCode()).getConfigClass();
                if (configClass != null) {
                    String jsonString = JsonUtils.toJsonString(obj);
                    PayClientConfig config = JsonUtils.parseObject2(jsonString, configClass);
                    configs.add(config);
                }
            }
        }

        PayClientConfig payClientConfig = configs.get(0);
        if(configs.size() > 1){
            if(channel.getPayType() == 1) {
                // 随机取 一个
                payClientConfig = configs.get(new Random().nextInt(configs.size()));
            }
            else if(channel.getPayType() == 2) {
                // 轮询
                Integer upPayIndex = channel.getUpPayIndex();
                int nextIndex = 0;
                if (upPayIndex == null || upPayIndex < 0 || upPayIndex >= configs.size()) {
                    nextIndex = 0; // 超出范围则从头开始
                } else {
                    nextIndex = upPayIndex + 1; // 下一个
                    if (nextIndex >= configs.size()) {
                        nextIndex = 0; // 循环回到开头
                    }
                }
                payClientConfig = configs.get(nextIndex);

                // 更新数据库中的索引
                PayChannelDO updateObj = new PayChannelDO();
                updateObj.setUpPayIndex(nextIndex);
                payChannelMapper.updateById(updateObj);
            }
        }
        return payClientFactory.createOrUpdatePayClient(id, channel.getCode(), payClientConfig);
    }
    @Override
    public Map<String, Object> getPayClientToMap(Long id) {
        Map<String, Object> map = new HashMap<>();
        PayChannelDO channel = validPayChannel(id);
//        List<PayClientConfig> configs = channel.getConfig();
        Object configObj = channel.getConfig();

        List<?> rawConfigs = (List<?>) configObj;
        // 尝试将 List<?> 转换为 List<PayClientConfig>
        List<PayClientConfig> configs = new ArrayList<>();
        for (Object obj : rawConfigs) {
            if (obj instanceof PayClientConfig) {
                configs.add((PayClientConfig) obj);
            } else if (obj instanceof Map) {
                // 如果是 Map，说明反序列化未成功，需要重新处理
                // 获取渠道类型以确定具体配置类
                Class<? extends PayClientConfig> configClass = PayChannelEnum.getByCode(channel.getCode()).getConfigClass();
                if (configClass != null) {
                    String jsonString = JsonUtils.toJsonString(obj);
                    PayClientConfig config = JsonUtils.parseObject2(jsonString, configClass);
                    configs.add(config);
                }
            }
        }

        PayClientConfig payClientConfig = configs.get(0);
        if(configs.size() > 1){
            if(channel.getPayType() == 1) {
                // 随机取 一个
                payClientConfig = configs.get(new Random().nextInt(configs.size()));
            }
            else if(channel.getPayType() == 2) {
                // 轮询
                Integer upPayIndex = channel.getUpPayIndex();
                int nextIndex = 0;
                if (upPayIndex == null || upPayIndex < 0 || upPayIndex >= configs.size()) {
                    nextIndex = 0; // 超出范围则从头开始
                } else {
                    nextIndex = upPayIndex + 1; // 下一个
                    if (nextIndex >= configs.size()) {
                        nextIndex = 0; // 循环回到开头
                    }
                }
                payClientConfig = configs.get(nextIndex);

                // 更新数据库中的索引
                PayChannelDO updateObj = new PayChannelDO();
                updateObj.setUpPayIndex(nextIndex);
                payChannelMapper.updateById(updateObj);
            }
        }
        PayClient payClient = payClientFactory.createOrUpdatePayClient(id, channel.getCode(), payClientConfig);
        map.put("client", payClient);
        map.put("config", JsonUtils.toJsonString(payClientConfig));
        return map;
    }
    @Override
    public PayClient getPayClient(Long id, String channelConfig) {
        PayChannelDO channel = validPayChannel(id);
        Object configObj = channel.getConfig();

        List<?> rawConfigs = (List<?>) configObj;
        // 尝试将 List<?> 转换为 List<PayClientConfig>
        List<PayClientConfig> configs = new ArrayList<>();
        for (Object obj : rawConfigs) {
            if (obj instanceof PayClientConfig) {
                configs.add((PayClientConfig) obj);
            } else if (obj instanceof Map) {
                // 如果是 Map，说明反序列化未成功，需要重新处理
                // 获取渠道类型以确定具体配置类
                Class<? extends PayClientConfig> configClass = PayChannelEnum.getByCode(channel.getCode()).getConfigClass();
                if (configClass != null) {
                    String jsonString = JsonUtils.toJsonString(obj);
                    PayClientConfig config = JsonUtils.parseObject2(jsonString, configClass);
                    configs.add(config);
                }
            }
        }

        PayClientConfig config = configs.get(0);
        if(StringUtil.isNotBlank(channelConfig)){
            // 获取渠道类型以确定具体配置类
            Class<? extends PayClientConfig> configClass = PayChannelEnum.getByCode(channel.getCode()).getConfigClass();
            if (configClass != null) {
//                String jsonString = JsonUtils.toJsonString(channelConfig);
                String jsonString = channelConfig;
                config = JsonUtils.parseObject2(jsonString, configClass);
            }
        }
        else{
            if(configs.size() > 1){
                if(channel.getPayType() == 1) {
                    // 随机取 一个
                    config = configs.get(new Random().nextInt(configs.size()));
                }
                else if(channel.getPayType() == 2) {
                    // 轮询
                    Integer upPayIndex = channel.getUpPayIndex();
                    int nextIndex = 0;
                    if (upPayIndex == null || upPayIndex < 0 || upPayIndex >= configs.size()) {
                        nextIndex = 0; // 超出范围则从头开始
                    } else {
                        nextIndex = upPayIndex + 1; // 下一个
                        if (nextIndex >= configs.size()) {
                            nextIndex = 0; // 循环回到开头
                        }
                    }
                    config = configs.get(nextIndex);

                    // 更新数据库中的索引
                    PayChannelDO updateObj = new PayChannelDO();
                    updateObj.setUpPayIndex(nextIndex);
                    payChannelMapper.updateById(updateObj);
                }
            }
        }
        return payClientFactory.createOrUpdatePayClient(id, channel.getCode(), config);
    }

}
