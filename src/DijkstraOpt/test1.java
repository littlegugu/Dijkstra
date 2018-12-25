package DijkstraOpt;

public class test1 {
    public static void main(String[] args) throws InterruptedException{
        long before,after;
        Graph p = new Graph(7,4);
        System.out.println("sp:"+p.point);
        p.regularGraph();


        before = System.currentTimeMillis();
        int ans[] = p.dijkstra(0);
        after = System.currentTimeMillis();
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans[k]+",");
        }
        System.out.print("\n");
        System.out.println("dijkstra:"+(after-before));
        System.out.print("\n");





        before = System.currentTimeMillis();
        int ans2[] = p.dijkstraOpt4(0);
        after = System.currentTimeMillis();
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans2[k]+",");
        }
        System.out.print("\n");
        System.out.println("dijkstra:"+(after-before));


//        Graph.service = Executors.newFixedThreadPool(50);
        before = System.currentTimeMillis();
        ans    = p.dijkstraOptThread2(0);
        after = System.currentTimeMillis();
        System.out.println("dijkstraOptThread2");
        System.out.print("short path:");
        for (int k = 0; k < p.point; k++) {
            System.out.print(ans[k]+",");
        }
        System.out.print("\n");
        System.out.println("dijkstra:"+(after-before));
    }
}
