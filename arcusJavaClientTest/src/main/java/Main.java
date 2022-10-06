import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.internal.OperationFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    private static final String ADDRESS = "localhost:2181";
    private static final String SERVICE_CODE = "test";

    private static long time;

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.DefaultLogger");
        ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
        connectionFactoryBuilder.setOpTimeout(100);
        final ArcusClient client =
                ArcusClient.createArcusClient(ADDRESS, SERVICE_CODE, connectionFactoryBuilder);


        OperationFuture<Boolean> set = client.set("testkey", 60, "AAAAA");
        set.get(700, TimeUnit.MILLISECONDS);

        Object testkey = client.get("testkey");

        System.out.println(testkey);
    }
}