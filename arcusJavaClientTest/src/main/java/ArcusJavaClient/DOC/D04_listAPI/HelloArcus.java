package ArcusJavaClient.DOC.D04_listAPI;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.collection.CollectionAttributes;
import net.spy.memcached.collection.ElementValueType;
import net.spy.memcached.internal.CollectionFuture;
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


        //start
        String key = "Sample:EmptyList";
        //test

        CollectionFuture<Boolean> future = null;
        CollectionAttributes attribute = new CollectionAttributes();

        try {
            future = hello.arcusClient.asyncLopCreate(key, ElementValueType.OTHERS, attribute); // (1)
//            attribute.
//            future = hello.arcusClient.asyncLopCreate()
        } catch (IllegalStateException e) {
            // handle exception
        }

        if (future == null)
            return;

        try {
            System.out.println("TEST RESULT START");
            Boolean result = future.get(1000L, TimeUnit.MILLISECONDS); // (2)
            System.out.println(result);
            System.out.println(future.getOperationStatus().getResponse()); // (3)
            System.out.println("TEST RESULT END");
        } catch (TimeoutException e) {
            System.out.println("e = " + e);
            future.cancel(true);
        } catch (InterruptedException e) {
            System.out.println("e = " + e);
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("e = " + e);
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
