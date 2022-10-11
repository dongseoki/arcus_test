package ArcusJavaClient.FutureTest;

import java.time.LocalTime;
import java.util.concurrent.*;

public class HelloFuture2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            System.out.println(LocalTime.now() + " Doing something");
            Integer sum = 1 + 1;
            Thread.sleep(3000);
            future.complete(sum);
            return null;
        });

        System.out.println(LocalTime.now() + " Waiting the task done");
        Integer result = future.get();
        System.out.println(LocalTime.now() + " Result : " + result);
    }
}
