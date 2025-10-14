package cn.metast.tuoke.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author metast.cn
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------\n\t" +
                            "项目启动成功！\n\t" +
                            "接口文档: \t{} \n\t" +
                            "访问地址: \t{} \n\t" +
                            "----------------------------------------------------------",
                    "https://doc.metast.cn/api-doc/",
                    "https://tk.metast.cn");

            // 数据报表
            if (isNotPresent("cn.metast.tuoke.module.report.framework.security.config.SecurityConfiguration")) {
                System.out.println("[报表模块 tuoke-module-report - 已禁用]");
            }
            // 工作流
            if (isNotPresent("cn.metast.tuoke.module.bpm.framework.flowable.config.BpmFlowableConfiguration")) {
                System.out.println("[工作流模块 tuoke-module-bpm - 已禁用]");
            }
            // 商城系统
            if (isNotPresent("cn.metast.tuoke.module.trade.framework.web.config.TradeWebConfiguration")) {
                System.out.println("[商城系统 tuoke-module-mall - 已禁用]");
            }
            // ERP 系统
            if (isNotPresent("cn.metast.tuoke.module.erp.framework.web.config.ErpWebConfiguration")) {
                System.out.println("[ERP 系统 tuoke-module-erp - 已禁用]");
            }
            // CRM 系统
            if (isNotPresent("cn.metast.tuoke.module.crm.framework.web.config.CrmWebConfiguration")) {
                System.out.println("[CRM 系统 tuoke-module-crm - 已禁用]");
            }
            // 微信公众号
//            if (isNotPresent("cn.metast.tuoke.module.mp.framework.mp.config.MpConfiguration")) {
//                System.out.println("[微信公众号 tuoke-module-mp - 已禁用]");
//            }
            // 支付平台
            if (isNotPresent("cn.metast.tuoke.module.pay.framework.pay.config.PayConfiguration")) {
                System.out.println("[支付系统 tuoke-module-pay - 已禁用]");
            }
            // AI 大模型
            if (isNotPresent("cn.metast.tuoke.module.ai.framework.web.config.AiWebConfiguration")) {
                System.out.println("[AI 大模型 tuoke-module-ai - 已禁用]");
            }
            // IoT 物联网
//            if (isNotPresent("cn.metast.tuoke.module.iot.framework.web.config.IotWebConfiguration")) {
//                System.out.println("[IoT 物联网 tuoke-module-iot - 已禁用]");
//            }
        });
    }

    private static boolean isNotPresent(String className) {
        return !ClassUtils.isPresent(className, ClassUtils.getDefaultClassLoader());
    }

}
