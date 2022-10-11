package ArcusJavaClient.AssertTest;

public class AssertionTest {
    public static void main(String[] args) {
        int value = -1;
        assert value>=0: "음수값입니다.";
        System.out.println("value = " + value);
    }
}
