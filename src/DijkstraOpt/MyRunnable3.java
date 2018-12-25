package DijkstraOpt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class MyRunnable3 implements Runnable {
    public static int[][] matrix;
    public static int[]   arr;
    public static ArrayList<Integer> pArr;
    public  static CountDownLatch latch;
    private int    index;
    public static int dist;


    public MyRunnable3(int index) {
        this.index    = index;
    }
    @Override
    public void run() {
        Iterator<Integer> it = pArr.iterator();
        while (it.hasNext()){
            Integer pIndex = it.next();
            if (matrix[pIndex][index]>0 &&(arr[index]==0 || arr[index]>matrix[index][pIndex]+dist))
                arr[index] = matrix[index][pIndex]+dist;
        }
        latch.countDown();

    }
}
