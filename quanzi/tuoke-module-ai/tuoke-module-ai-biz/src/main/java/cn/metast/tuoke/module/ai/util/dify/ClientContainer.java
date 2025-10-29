package cn.metast.tuoke.module.ai.util.dify;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class ClientContainer {
    private static ClientContainer container = null;

    private OkHttpClient client = null;

    private ClientContainer() {
        this.client = new OkHttpClient.Builder().connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(60, TimeUnit.SECONDS).build();
    }

    public synchronized static ClientContainer getInstance() {
        if (container == null) {
            container = new ClientContainer();
        }

        return container;
    }

    public synchronized OkHttpClient getClient() {
        return client;
    }
}
