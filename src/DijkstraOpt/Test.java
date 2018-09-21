package DijkstraOpt;


import Jama.Matrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        long before,after;
        FileWriter writer = new FileWriter("D:\\project\\Dijkstra\\src\\DijkstraOpt\\hh.txt",true);
        for(int i = 10;i<=10000;i+=10){
            Graph p = new Graph(i);
            p.randomGraph();
            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstra(0);
            after = System.currentTimeMillis();
            writer.write("dijkstra-"+"point:"+i+"; time:"+(after-before)+";\r\n");
            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstraOpt(0);
            after = System.currentTimeMillis();
            writer.write("dijkstraopt-"+"point:"+i+"; time:"+(after-before)+";\r\n");
            writer.flush();
        }
        writer.close();


    }
//    public static void main(String[] args) {
//        long before,after;
//        Graph p = new Graph(10000);
//        p.randomGraph();
////        int[][] matrix = p.randomGraph();
////        //打印矩阵
////        for (int i = 0; i < 10; i++) {
////            for (int j = 0; j < 10; j++) {
////                if(j==9) {
////                    System.out.println(matrix[i][j]);
////                }else{
////                    System.out.print(matrix[i][j]+",");
////                }
////            }
////        }
//        before = System.currentTimeMillis();//获取运行之前的时间
//        int ans[] = p.dijkstra(0);
//        after = System.currentTimeMillis();
//        //打印最短路径
//        for (int k = 0; k < 10; k++) {
//            System.out.print(ans[k]+",");
//
//        }
//        System.out.print("\n");
//        System.out.println(before+","+after);
//        System.out.println("Dijkstra:"+":"+(after-before));
//
//        System.out.println("#####################");
//
//        before = System.currentTimeMillis();
//        int ans1[] = p.dijkstraOpt(0);
//        after = System.currentTimeMillis();
//        //打印最短路径
//        for (int m = 0; m < 10; m++) {
//            System.out.print(ans1[m]+",");
//        }
//        System.out.print("\n");
//        System.out.println(before+","+after);
//        System.out.println("Dijkstraopt:"+":"+(after-before));
//
//
//
//    }



}
