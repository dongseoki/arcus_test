package ArcusJavaClient.FutureTest;

import java.time.LocalTime;
import java.util.concurrent.*;

public class HelloFutureWithTimeOut {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor
                = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            System.out.println(LocalTime.now() + " Starting runnable");
            Integer sum = 1 + 1;
            Thread.sleep(4000);
            System.out.println(LocalTime.now() + " Exiting runnable");
            return sum;
        });

        System.out.println(LocalTime.now() + " Waiting the task done");
        Integer result = null;
        try {
            result = future.get(2000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            System.out.println(LocalTime.now() + " Timed out");
            result = 0;
        }
        System.out.println(LocalTime.now() + " Result : " + result);
    }
}
