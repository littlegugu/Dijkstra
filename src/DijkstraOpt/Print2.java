package DijkstraOpt;

import java.io.FileWriter;
public class Print2 {
    public static void main(String[] args) throws Exception {
        long before, after;
        int t1, t2, t3;
        int sp;
        FileWriter writer = new FileWriter("D:\\workspace\\Dijkstra\\src\\DijkstraOpt\\ans2.txt", true);
        for (int i = 2000; i <= 20000; i += 2000) {
            Graph p = new Graph(i);
            p.randomGraph();
            sp = p.sparsity();

            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstra(0);
            after = System.currentTimeMillis();
            t1 = (int) (after - before);


            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstraOpt4(0);
            after = System.currentTimeMillis();
            t2 = (int) (after - before);


            before = System.currentTimeMillis();//获取运行之前的时间
            p.dijkstraOptThread5(0);
            after = System.currentTimeMillis();
            t3 = (int) (after - before);

            writer.write(p.point + "," + sp + "," + t1 + "," + t2 + "," + t3 + ",\r\n");
            writer.flush();
            System.out.println(p.point);

        }
        writer.close();
    }

}
