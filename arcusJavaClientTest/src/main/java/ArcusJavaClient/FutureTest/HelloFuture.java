package ArcusJavaClient.FutureTest;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor
                = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            System.out.println(LocalTime.now() + " Starting runnable");
            Integer sum = 1 + 1;
            Thread.sleep(3000);
            return sum;
        });

        System.out.println(LocalTime.now() + " Waiting the task done");
        Integer result = future.get();
        System.out.println(LocalTime.now() + " Result : " + result);


    }
}
