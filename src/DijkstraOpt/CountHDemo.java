package DijkstraOpt;

public class CountHDemo {
    public static void main(String[] args) throws InterruptedException{
        for (int out = 2; out < 11; out++) {
            System.out.print(out+":");
            Graph p = new Graph(8000,out,0);
            p.zhengTree();
            long before, after;
            int t1, t2, t3;
            int[] ans;
            before = System.currentTimeMillis();//获取运行之前的时间
            ans = p.dijkstra(0);
            after = System.currentTimeMillis();
            t1 = (int) (after - before);
            System.out.print("dijkstra:"+t1+";");
//            for (int i = 0; i < p.point; i++) {
//                System.out.print(ans[i]+",");
//
//            }
//            System.out.println("\n");

            System.gc();
            before = System.currentTimeMillis();//获取运行之前的时间
            ans = p.dijkstraOpt4(0);
            after = System.currentTimeMillis();
            t2 = (int) (after - before);
            System.out.print("dijkstraOpt:"+t2+";");
//            for (int i = 0; i < p.point; i++) {
//                System.out.print(ans[i]+",");
//
//            }
//            System.out.println("\n");

            System.gc();
            before = System.currentTimeMillis();//获取运行之前的时间
            ans = p.dijkstraOptThread5(0);
            after = System.currentTimeMillis();
            t3 = (int) (after - before);
            System.out.println("dijkstraThread:"+t3+";");
//            for (int i = 0; i < p.point; i++) {
//                System.out.print(ans[i]+",");
//
//            }
//            System.out.println("\n");
            System.gc();

        }


    }
    public static int  hNum(int out,int point){
        return (int)Math.ceil(Math.log(((out-1)*point)+1)/Math.log(out))-1;
    }
}
