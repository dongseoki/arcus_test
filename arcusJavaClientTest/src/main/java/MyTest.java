public class MyTest {
    public static void main(String[] args) {
        HelloArcus helloArcus = new HelloArcus("127.0.0.1:2181", "test");
//        helloArcus.sayHello();
        helloArcus.saySomethingWithKey("test:Iknow", "by");
//        String result = helloArcus.listenHello();
        String result = helloArcus.listenSomethingWithKey("test:Iknow");

        System.out.println("result = " + result);

    }
}
