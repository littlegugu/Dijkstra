package DijkstraOpt;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

//    public static int T1(ExecutorService service){
//        Th thread;
//        int i = 0;
//        while (i<10){
//            thread = new Th(i++);
//            service.submit(thread);
//        }
//        return 0;
//    }
//    public static void main(String[] args) throws Exception{
//        ExecutorService service = Executors.newFixedThreadPool(10);
//        T1(service);
//        System.out.println("main");
//        service.shutdown();
//
//    }
//    public static void main(String[] args) throws Exception{
//
//        ExecutorService service = Executors.newFixedThreadPool(5);
//
//        MyCallable c1 = new MyCallable(1,2,0);
//        MyCallable c2 = new MyCallable(3,4,0);
//        service.submit(c1);
//        service.submit(c2);
//        MyCallable.barrier.await();
//        System.out.println("MyCallable:"+MyCallable.arr[0]);
//    }


    public static void main(String[] args) throws Exception {
        long before,after;
//         p;
//        FileWriter writer = new FileWriter("D:\\project\\Dijkstra\\src\\DijkstraOpt\\hh.txt",true);
        for(int i = 10;i<=20;i+=10){
            Graph p = new Graph(i);
            p.randomGraph();

            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstra(0);
            after = System.currentTimeMillis();
//            writer.write("dijkstra-"+"point:"+i+"; time:"+(after-before)+";\r\n");
//            writer.flush();

            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstraOpt(0);
            after = System.currentTimeMillis();
//            writer.write("dijkstraopt-"+"point:"+i+"; time:"+(after-before)+";\r\n");
//            writer.flush();

            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstraOptThread(0);
            after = System.currentTimeMillis();
//            writer.write("dijkstraoptthread-"+"point:"+i+"; time:"+(after-before)+";\r\n");
//            writer.flush();
            System.out.println(i);
        }
//        writer.close();
    }

//测试
//    public static void main(String[] args) throws Exception{
//        long before,after;
//        int point = 8000;
//        Graph p = new Graph(point);
////        int[] list = {-1,0,0};
////        int index = p.minIndexSim(list);
////        System.out.println(index+":"+list[index]);
//        p.randomGraph();
//        int[][] matrix = p.randomGraph();
////        打印矩阵
////        for (int i = 0; i < point; i++) {
////            for (int j = 0; j < point; j++) {
////                if(j==point-1) {
////                    System.out.println(matrix[i][j]);
////                }else{
////                    System.out.print(matrix[i][j]+",");
////                }
////            }
////        }
//        System.out.println("");
//        before = System.currentTimeMillis();//获取运行之前的时间
//        int ans[] = p.dijkstra(0);
//        after = System.currentTimeMillis();
//        //打印最短路径
//        System.out.print("short path:");
//        for (int k = 0; k < 10; k++) {
//            System.out.print(ans[k]+",");
//        }
//        System.out.print("\n");
//        System.out.println(before+","+after);
//        System.out.println("Dijkstra:"+":"+(after-before));
//
//        System.out.println("#####################");
//
//        before = System.currentTimeMillis();
//        int ans1[] = p.dijkstraOptThread(0);
//        after = System.currentTimeMillis();
//        System.out.print("short path:");
//        //打印最短路径
//        for (int m = 0; m < 10; m++) {
//            System.out.print(ans1[m]+",");
//        }
//        System.out.print("\n");
//        System.out.println(before+","+after);
//        System.out.println("dijkstraOptThread:"+":"+(after-before));
//
//
//
//    }



}
