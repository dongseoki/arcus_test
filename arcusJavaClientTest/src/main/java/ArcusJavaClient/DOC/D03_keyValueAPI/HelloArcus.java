package ArcusJavaClient.DOC.D03_keyValueAPI;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.transcoders.SerializingTranscoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloArcus {

    private static final String ARCUS_ADMIN = "127.0.0.1:2181";
    private static final String SERVICE_CODE = "test";
    private final ArcusClient arcusClient;

    public static void main(String[] args) {
        HelloArcus hello = new HelloArcus();
        System.out.printf("hello.setTest() result=%b\n", hello.setTest());

        //

        // Arcus의 "test:hello" 키의 값을 조회합니다.
        // Arcus에서는 가능한 모든 명령에 명시적으로 timeout 값을 지정하도록 가이드 하고 있으며
        // 사용자는 set을 제외한 모든 요청에 async로 시작하는 API를 사용하셔야 합니다.



        //
        hello.closeArcusConnection();
    }

    private static void tryGet(HelloArcus hello) {
        String result;
        Future<Object> future;
        future = hello.arcusClient.asyncGet("sample:testKey");

        try {
            result = (String)future.get(700L, TimeUnit.MILLISECONDS);
            System.out.println("GET result = " + result);
        } catch (Exception e) {
            if (future != null) future.cancel(true);
            e.printStackTrace();
        }
    }

    public HelloArcus() {
        ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
        arcusClient = ArcusClient.createArcusClient(ARCUS_ADMIN, SERVICE_CODE,
                cfb); // (1)
    }

    public boolean setTest() {
        Future<Boolean> future = null;
        try {
            future = arcusClient.set("sample:testKey", 10, "testValue"); // (2)
            tryGet(this);

            Thread.sleep(1000);

            arcusClient.replace("sample:testKey", 10,"newValue");

            Thread.sleep(1000);
            arcusClient.delete("sample:testKey");

            Thread.sleep(1000);
            tryGet(this);
        } catch (IllegalStateException e) {
            // client operation queue 문제로 요청이 등록되지 않았을 때 예외처리.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
