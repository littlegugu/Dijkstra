package DijkstraOpt2;

import org.junit.Test;


public class DijkstraMethodTest {

    @Test
    public void testDijkstraMultiP() {
        Graph p = new Graph(10,4);
//        long before,after;
//        long t1,t2;
//        before = System.currentTimeMillis();//获取运行之前的时间
        int[] dist1 = DijkstraMethod.dijkstra(p.adjArr,0);
//        after = System.currentTimeMillis();//获取运行之前的时间
//        t1 = after-before;
//        System.gc();
//        System.out.printf("time of dijkstra:%d\n",t1);
//
////        before = System.currentTimeMillis();//获取运行之前的时间
        int[] dist2 = DijkstraMethod.dijkstraMultiP(p.adjArr,0);
////        after = System.currentTimeMillis();//获取运行之前的时间
////        t2 = after-before;

        ////        before = System.currentTimeMillis();//获取运行之前的时间
        int[] dist3 = DijkstraMethod.dijkstraMultiParallel(p.adjArr,0);
////        after = System.currentTimeMillis();//获取运行之前的时间
////        t2 = after-before;



//        System.out.printf("time of dijkstraMult:%d\n",t2);
        for (int i = 0; i < p.adjArr.length; i++) {
            System.out.printf("%d,",dist1[i]);
        }
        System.out.println();
        for (int i = 0; i < p.adjArr.length; i++) {
            System.out.printf("%d,",dist2[i]);
        }
        System.out.println();
        for (int i = 0; i < p.adjArr.length; i++) {
            System.out.printf("%d,",dist3[i]);
        }
        System.out.println();

    }
//    @Test
    public void parTest() {
        Graph p = new Graph(10,4);
        MyHeap mh1 = DijkstraMethod.multest(p.adjArr,0);
        MyHeap mh2 = DijkstraMethod.para(p.adjArr,0);
        System.out.println("ppp");

    }


}