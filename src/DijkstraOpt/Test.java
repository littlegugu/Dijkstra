package DijkstraOpt;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws Exception {
        Graph p = new Graph(8000);
        p.initMatrix(2);
        System.out.println(p.sparsity());
//        for (int i = 0; i < p.point; i++) {
//            for (int j = 0; j < p.point; j++) {
//                System.out.print(p.matrix[i][j]+",");
//            }
//            System.out.println("");
//        }

        long before, after;
        int t1;
        System.out.println("dijkstra:");
        before = System.currentTimeMillis();//获取运行之前的时间
        int ans[] = p.dijkstra(0);
        after = System.currentTimeMillis();
        t1 = (int) (after-before);
        //打印最短路径
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans[k] + ",");
        }
        System.out.print("\n");
        System.out.println("dijkstra:" + t1);
        System.out.print("\n");
//
        System.out.println("dijkstraOpt:");
        before = System.currentTimeMillis();//获取运行之前的时间
        ans = p.dijkstraOpt4(0);
        after = System.currentTimeMillis();
        t1 = (int) (after-before);
        //打印最短路径
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans[k] + ",");
        }
        System.out.print("\n");
        System.out.println("dijkstraOpt:" + t1);
        System.out.print("\n");

        System.out.println("dijkstraThread:");
        before = System.currentTimeMillis();//获取运行之前的时间
        ans = p.dijkstraOptThread5(0);
        after = System.currentTimeMillis();
        t1 = (int) (after-before);
        //打印最短路径
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans[k] + ",");
        }
        System.out.print("\n");
        System.out.println("dijkstraThread:" + t1);
        System.out.print("\n");
    }
}