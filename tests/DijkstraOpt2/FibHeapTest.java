package DijkstraOpt2;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibHeapTest {

    @Test
    public void testInsert() {
//        int b[] = {18, 35, 20, 42,  9,31, 23,  6, 48, 11,24, 52, 13,  2 };
//        FibHeap hb=new FibHeap();
//        int indexP = 0;
//        for (int i = 0; i < b.length; i++) {
//            hb.insert(indexP,b[i]);
//            indexP++;
//        }
//
//
//        hb.update(0,1);
////        hb.print();
//        System.out.println("ppp");
////        hb.update();
        int b[] = {18, 35, 20};
        FibHeap hb=new FibHeap(4);
        int index = 0;
        for (int i = 0; i < b.length; i++) {
            hb.insert(index++,b[i]);
        }
        FibHeap.FibNode p = hb.get(0);
        FibHeap.FibNode c = hb.pos[0];
        hb.update(p,3);
        System.out.println("ppp");
    }
}