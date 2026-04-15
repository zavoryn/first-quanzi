package cn.metast.tuoke.server;

import cn.metast.tuoke.module.mp.service.mpTask.MpTaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 *
 * @author metast.cn
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${tuoke.info.base-package}
@SpringBootApplication(scanBasePackages = {"${tuoke.info.base-package}.server", "${tuoke.info.base-package}.module"})
public class TuokeServerApplication {
    @Autowired
    private MpTaskService mpTaskService;
    private static MpTaskService taskMpTaskService;

    @PostConstruct
    public void getHWYController(){
        taskMpTaskService = this.mpTaskService;
    }
    public static void main(String[] args) {

        SpringApplication.run(TuokeServerApplication.class, args);
//        new SpringApplicationBuilder(TuokeServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

        try {
            //公众号 定时任务
            taskMpTaskService.createMpTask_tnt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
