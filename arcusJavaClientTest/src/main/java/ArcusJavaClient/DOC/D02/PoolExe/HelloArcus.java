package ArcusJavaClient.DOC.D02.PoolExe;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ArcusClientPool;
import net.spy.memcached.ConnectionFactoryBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloArcus {

    private static final String ARCUS_ADMIN = "127.0.0.1:2181";
    private static final String SERVICE_CODE = "test";
//    private final ArcusClient arcusClient;
    private final ArcusClientPool arcusClient;

    private final int poolSize = 4;

    public static void main(String[] args) {
        HelloArcus hello = new HelloArcus();
        System.out.printf("hello.setTest() result=%b", hello.setTest());
        hello.closeArcusConnection();
    }

    public HelloArcus() {
//        arcusClient = ArcusClient.createArcusClient(ARCUS_ADMIN, SERVICE_CODE,
//                new ConnectionFactoryBuilder()); // (1)
        arcusClient = ArcusClient.createArcusClientPool(ARCUS_ADMIN, SERVICE_CODE, new ConnectionFactoryBuilder(), poolSize);
    }

    public boolean setTest() {
        Future<Boolean> future = null;
        try {
            future = arcusClient.set("sample:testKey", 10, "testValue"); // (2)
        } catch (IllegalStateException e) {
            // client operation queue 문제로 요청이 등록되지 않았을 때 예외처리.
        }

        if (future == null) return false;

        try {
            return future.get(500L, TimeUnit.MILLISECONDS); // (3)
        } catch (TimeoutException te) { // (4)
            future.cancel(true);
        } catch (ExecutionException re) { // (5)
            future.cancel(true);
        } catch (InterruptedException ie) { // (6)
            future.cancel(true);
        }

        return false;
    }

    public void closeArcusConnection() {
        arcusClient.shutdown(); // (7)
    }
}
