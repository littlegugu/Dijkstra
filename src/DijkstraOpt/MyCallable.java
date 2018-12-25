package DijkstraOpt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MyCallable implements Callable {
    public static int[][] mat;
    public static int[] arr;
    public static ArrayList<Integer> p;
    public int index;
    public int pi;
//    public static CyclicBarrier barrier;
    public  static CountDownLatch latch;


    public MyCallable(int[][] mat,int[] arr,ArrayList<Integer> p,int index) {
        this.mat   = mat;
        this.arr   = arr;
        this.p     = p;
        this.index = index;

    }


    //    final static CyclicBarrier barrier = new CyclicBarrier(10);
    @Override
    public Object call() throws Exception{
//        System.out.println(index);
        Iterator<Integer> pit = p.iterator();
        Integer pIndex;
        while (pit.hasNext()){
            pIndex = pit.next();
//            if (index==pIndex){
//                break;
//            }
            if(mat[index][pIndex]>0){
                if (arr[index]==0){
                    arr[index] = mat[index][pIndex]+arr[pIndex];
                }else{
                    arr[index] = arr[index]<(mat[index][pIndex]+arr[pIndex])?arr[index]:(mat[index][pIndex]+arr[pIndex]);
                }
            }
        }
//        System.out.println("call");
        latch.countDown();
        return arr;
    }

//    @Override
//    public Object call() throws Exception{
//        arr[index]= a<b?a:b;
////        arr[index]=a;
//        System.out.println(Thread.currentThread().getName());
//        barrier.await();
////        latch.countDown();
//        return null;
//    }
}
