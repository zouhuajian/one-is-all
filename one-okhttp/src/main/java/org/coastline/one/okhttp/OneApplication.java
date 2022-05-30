package org.coastline.one.okhttp;

import okhttp3.*;
import org.coastline.one.core.HttpClientTool;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/5/29
 */
public class OneApplication {

    private static boolean RUNNING = true;
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    // http
    public static final MediaType TEXT_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private static final String URL = "http://xxx:20000/ip";

    private static final int PARALLEL = Runtime.getRuntime().availableProcessors() / 2;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(PARALLEL, PARALLEL,
            1, TimeUnit.MINUTES,
            new SynchronousQueue<>());

    public static void main(String[] args) throws InterruptedException {
        initShutdownHook();
        for (int i = 0; i < PARALLEL; i++) {
            THREAD_POOL_EXECUTOR.execute(new DataProcessor());
        }
        countDownLatch.await();
    }

    private static void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("shutdown...");
            try {
                RUNNING = false;
                THREAD_POOL_EXECUTOR.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }));
    }

    private static class DataProcessor implements Runnable {
        private final OkHttpClient httpClient;

        public DataProcessor() {
            httpClient = HttpClientTool.getClient(4, 4, true);
        }

        @Override
        public void run() {
            while (RUNNING) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException ignored) {
                }
                Request request = new Request.Builder()
                        .url(URL)
                        .get()
                        .build();
                httpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.out.println("onFailure: write data into victoria error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            System.out.println("onResponse: write data into victoria error, response = " + response);
                        }
                        //System.out.println(response);
                        response.close();
                    }
                });
            }
        }
    }

}
