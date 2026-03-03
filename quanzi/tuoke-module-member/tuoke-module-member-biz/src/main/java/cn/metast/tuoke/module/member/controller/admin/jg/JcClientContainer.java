package cn.metast.tuoke.module.member.controller.admin.jg;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JcClientContainer {
    private static JcClientContainer container = null;

    private OkHttpClient client = null;

    private JcClientContainer() {
        this.client = new OkHttpClient.Builder().connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(60, TimeUnit.SECONDS).build();
    }

    public synchronized static JcClientContainer getInstance() {
        if (container == null) {
            container = new JcClientContainer();
        }

        return container;
    }

    public synchronized OkHttpClient getClient() {
        return client;
    }
}
