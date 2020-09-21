package org.coastline.one.lettuce.asynchronous;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * @author Jay.H.Zou
 * @date 2020/5/11
 */
public class MyAsynchronous {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

    }

    private static void showCompleteFuture1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        System.out.println("Current state: " + future.isDone());

        future.complete("my value");

        System.out.println("Current state: " + future.isDone());
        System.out.println("Got value: " + future.get());

    }

    private static void showCompleteFuture2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        future.thenRun(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("Got value: " + future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        System.out.println("Current state: " + future.isDone());
        future.complete("Second");
        System.out.println("Current state: " + future.isDone());
    }

    private static void showCompleteFuture3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println("Got value: " + value);
            }
        });

        System.out.println("Current state: " + future.isDone());
        future.complete("my value");
        System.out.println("Current state: " + future.isDone());
    }

}
