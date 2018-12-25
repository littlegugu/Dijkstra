package DijkstraOpt2;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyHeapTest {

    @Test
    public void updateTest() {
        AdjacencyList head   = new AdjacencyList(1,4);
        AdjacencyList node1  = new AdjacencyList(2,3);
        AdjacencyList node2  = new AdjacencyList(3,1);
        AdjacencyList node3  = new AdjacencyList(4,1);
        AdjacencyList node4  = new AdjacencyList(5,10);
        AdjacencyList node5  = new AdjacencyList(6,8);
        AdjacencyList node6  = new AdjacencyList(7,6);
        AdjacencyList node7  = new AdjacencyList(7,5);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        MyHeap a1 = new MyHeap(head,10);
        a1.update(6,4);
//        System.out.println(a1.size());
//        AdjacencyList b1 = a1.pop();
//        System.out.println(a1.size());
    }
}