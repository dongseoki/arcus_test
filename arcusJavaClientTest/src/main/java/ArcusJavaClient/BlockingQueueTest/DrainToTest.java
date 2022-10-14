package ArcusJavaClient.BlockingQueueTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DrainToTest {
    public static void main(String[] args) {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(10);
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
//            tempList.add(""+i);
            bq.add(""+i);
        }
        bq.drainTo(tempList, 2);

        tempList.forEach((s)->{
            System.out.println("s = " + s);
        });
        bq.forEach((t)->{
            System.out.println("t = " + t);
        });
    }
}
