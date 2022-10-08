package ArcusJavaClient.DOC.D04_listAPI.search;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.collection.CollectionAttributes;
import net.spy.memcached.collection.CollectionResponse;
import net.spy.memcached.collection.ElementValueType;
import net.spy.memcached.internal.CollectionFuture;
import net.spy.memcached.transcoders.SerializingTranscoder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloArcus {

    private static final String ARCUS_ADMIN = "127.0.0.1:2181";
    private static final String SERVICE_CODE = "test";
    private final ArcusClient arcusClient;

    public static void main(String[] args) {
        HelloArcus hello = new HelloArcus();


        //start
        String key = "Sample:List";
        int from = 0;
        int to = 5;
        boolean withDelete = false;
        boolean dropIfEmpty = false;
        CollectionFuture<List<Object>> future = null;

        try {
            future = hello.arcusClient.asyncLopGet(key, from, to, withDelete, dropIfEmpty); // (1)
        } catch (IllegalStateException e) {
            // handle exception
        }

        if (future == null)
            return;

        try {
            List<Object> result = future.get(1000L, TimeUnit.MILLISECONDS); // (2)
            System.out.println(result);
            CollectionResponse response = future.getOperationStatus().getResponse();  // (3)
            System.out.println(response);

            if (response.equals(CollectionResponse.NOT_FOUND)) {
                System.out.println("Key가 없습니다.(Key에 저장된 List가 없습니다.");
            } else if (response.equals(CollectionResponse.NOT_FOUND_ELEMENT)) {
                System.out.println("Key는 존재하지만 List에 저장된 값 중 조건에 맞는 것이 없습니다.");
            }

        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        }

        //end


        hello.closeArcusConnection();
    }

    public HelloArcus() {
        ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
        SerializingTranscoder trans = new SerializingTranscoder();
        trans.setCharset("UTF-8");
        trans.setCompressionThreshold(4096);

        cfb.setTranscoder(trans);
        arcusClient = ArcusClient.createArcusClient(ARCUS_ADMIN, SERVICE_CODE,
                cfb); // (1)
    }


    public void closeArcusConnection() {
        arcusClient.shutdown(); // (7)
    }
}
