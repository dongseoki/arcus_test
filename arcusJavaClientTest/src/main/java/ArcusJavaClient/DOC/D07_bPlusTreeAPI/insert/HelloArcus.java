package ArcusJavaClient.DOC.D07_bPlusTreeAPI.insert;

import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.collection.CollectionAttributes;
import net.spy.memcached.collection.ElementValueType;
import net.spy.memcached.internal.CollectionFuture;
import net.spy.memcached.transcoders.SerializingTranscoder;

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
        String key = "Prefix:BTreeKey";
        long bkey = 1L;
        String value = "This is a value.";
        byte[] eflag = new byte[] { 1, 1, 1, 1 };

        CollectionAttributes attributesForCreate = new CollectionAttributes();
        CollectionFuture<Boolean> future = null;

        try {
            future = hello.arcusClient.asyncBopInsert(key, bkey, eflag, value, attributesForCreate); // (1)
        } catch (IllegalStateException e) {
            // handle exception
        }

        if (future == null)
            return;

        try {
            Boolean result = future.get(1000L, TimeUnit.MILLISECONDS); // (2)
            System.out.println(result);
            System.out.println(future.getOperationStatus().getResponse()); // (3)
        } catch (TimeoutException e) {
            future.cancel(true);
        } catch (InterruptedException e) {
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
