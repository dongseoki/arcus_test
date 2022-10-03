import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelloArcusTest {
    HelloArcus helloArcus = new HelloArcus("127.0.0.1:2181", "test");

    @Before
    public void setUp() throws Exception {
        helloArcus.sayHello();
    }

    @Test
    public void listenHello() {
        Assert.assertEquals("Hello, Arcus!", helloArcus.listenHello());
        System.out.println("test success");
    }
}