import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HelloArcus {

    private String arcusAdmin;
    private String serviceCode;
    private ArcusClient arcusClient;

    public HelloArcus(String arcusAdmin, String serviceCode) {
        this.arcusAdmin = arcusAdmin;
        this.serviceCode = serviceCode;

        // log4j logger를 사용하도록 설정합니다.
        // 코드에 직접 추가하지 않고 아래의 JVM 환경변수를 사용해도 됩니다.
        //   -Dnet.spy.log.LoggerImpl=net.spy.memcached.compat.log.Log4JLogger
        System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.Log4JLogger");

        // Arcus 클라이언트 객체를 생성합니다.
        // - arcusAdmin : Arcus 캐시 서버들의 그룹을 관리하는 admin 서버(ZooKeeper)의 주소입니다.
        // - serviceCode : 사용자에게 할당된 Arcus 캐시 서버들의 집합에 대한 코드값입니다. 
        // - connectionFactoryBuilder : 클라이언트 생성 옵션을 지정할 수 있습니다.
        //
        // 정리하면 arcusAdmin과 serviceCode의 조합을 통해 유일한 캐시 서버들의 집합을 얻어 연결할 수 있는 것입니다.
        this.arcusClient = ArcusClient.createArcusClient(arcusAdmin, serviceCode, new ConnectionFactoryBuilder());
    }

    public boolean sayHello() {
        Future<Boolean> future = null;
        boolean setSuccess = false;

        // Arcus의 "test:hello" 키에 "Hello, Arcus!"라는 값을 저장합니다.
        // 그리고 Arcus의 거의 모든 API는 Future를 리턴하도록 되어 있으므로
        // 비동기 처리에 특화된 서버가 아니라면 반드시 명시적으로 future.get()을 수행하여
        // 반환되는 응답을 기다려야 합니다.
        future = this.arcusClient.set("test:hello", 600, "Hello, Arcus!");

        try {
            setSuccess = future.get(700L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (future != null) future.cancel(true);
            e.printStackTrace();
        }

        return setSuccess;
    }

    public boolean saySomethingWithKey(String key, String something) {
        Future<Boolean> future = null;
        boolean setSuccess = false;

        // Arcus의 "test:hello" 키에 "Hello, Arcus!"라는 값을 저장합니다.
        // 그리고 Arcus의 거의 모든 API는 Future를 리턴하도록 되어 있으므로
        // 비동기 처리에 특화된 서버가 아니라면 반드시 명시적으로 future.get()을 수행하여
        // 반환되는 응답을 기다려야 합니다.
        future = this.arcusClient.set(key, 600, something);

        try {
            setSuccess = future.get(700L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (future != null) future.cancel(true);
            e.printStackTrace();
        }

        return setSuccess;
    }

    public String listenHello() {
        Future<Object> future = null;
        String result = "Not OK.";

        // Arcus의 "test:hello" 키의 값을 조회합니다.
        // Arcus에서는 가능한 모든 명령에 명시적으로 timeout 값을 지정하도록 가이드 하고 있으며
        // 사용자는 set을 제외한 모든 요청에 async로 시작하는 API를 사용하셔야 합니다.
        future = this.arcusClient.asyncGet("test:hello");

        try {
            result = (String)future.get(700L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (future != null) future.cancel(true);
            e.printStackTrace();
        }

        return result;
    }

    public String listenSomethingWithKey(String key) {
        Future<Object> future = null;
        String result = "Not OK.";

        // Arcus의 "test:hello" 키의 값을 조회합니다.
        // Arcus에서는 가능한 모든 명령에 명시적으로 timeout 값을 지정하도록 가이드 하고 있으며
        // 사용자는 set을 제외한 모든 요청에 async로 시작하는 API를 사용하셔야 합니다.
        future = this.arcusClient.asyncGet(key);

        try {
            result = (String)future.get(700L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (future != null) future.cancel(true);
            e.printStackTrace();
        }

        return result;
    }

}