package DijkstraOpt2;

import DijkstraOpt2.ThreadUpdateDemo.ThreadDemo;

import java.util.ArrayList;
import java.util.LinkedList;

public class DijkstraMethod {
    public static int[] dijkstraMultiParallel(AdjacencyList[] matrix,int indexS){
        int[] dist   = initDistArr(matrix.length,indexS);
        MyHeap heapT = new MyHeap(matrix[indexS],matrix.length);
        ArrayList<AdjacencyList> arrP = new ArrayList<AdjacencyList>();
        ThreadDemo td = new ThreadDemo();
        while (heapT.size()>0){
            int count = heapT.popArr(arrP);
            td.updateHeapTParallel(matrix,dist,arrP,heapT);
        }
        return dist;
    }
//    public static MyHeap para(AdjacencyList[] matrix,int indexS){
//        int[] dist   = initDistArr(matrix.length,indexS);
//        MyHeap heapT = new MyHeap(matrix[indexS],matrix.length);
//        ArrayList<AdjacencyList> arrP = new ArrayList<AdjacencyList>();
//        int count = heapT.popArr(arrP);
//        ThreadDemo td = new ThreadDemo();
//        td.updateHeapTParallel(matrix,dist,arrP,heapT);
//        return heapT;
//    }
//    public static MyHeap multest(AdjacencyList[] matrix,int indexS){
//        int[] dist   = initDistArr(matrix.length,indexS);
//        MyHeap heapT = new MyHeap(matrix[indexS],matrix.length);
//        AdjacencyList nodeP = heapT.pop();
//        updateHeapTMP(matrix,dist,nodeP,heapT);
//        return heapT;
//
//    }



    public static int[] dijkstraMultiP(AdjacencyList[] matrix,int indexS){
        int[] dist   = initDistArr(matrix.length,indexS);
        MyHeap heapT = new MyHeap(matrix[indexS],matrix.length);
        while (heapT.size()>0&&heapT.minNode!=null){
//            System.out.printf("Mult:%d\n\n",heapT.size());
            AdjacencyList nodeP = heapT.pop();
            updateHeapTMP(matrix,dist,nodeP,heapT);
        }
        return dist;
    }

    private static void updateHeapTMP(AdjacencyList[] matrix,int[] dist, AdjacencyList nodeP, MyHeap heapT) {
        AdjacencyList nodeI = nodeP;
        while (nodeI!=null){
            int indexI  = nodeI.index;
            int weightI = nodeI.weight;
            AdjacencyList nodeJ = matrix[indexI];
            while (nodeJ!=null){
                int indexJ  = nodeJ.index;
                int weightJ = nodeJ.weight;
                if (dist[indexJ]==0){
                    int newWeight = weightJ+weightI;
                    int oldWeight = heapT.getWeight(indexJ);
//                    System.out.printf("index:%d;newWeight:%d;oldWeight:%d\n",indexJ,newWeight,oldWeight);
                    if (oldWeight==0){
                        //加入heapT
                        heapT.insert(indexJ,newWeight);
                    }else{
                        //修改heapT
                        if (newWeight<oldWeight){
                            heapT.update(indexJ,newWeight);//新权值大于原来权值，改
                        }
                    }
//                    dist[indexJ] = weightJ;
                }

                nodeJ = nodeJ.next;

            }
            dist[indexI] = weightI;
            nodeI = nodeI.next;

        }

    }

    private static void updateDistMP(AdjacencyList nodeP,int[] dist){
        AdjacencyList node = nodeP;
        while (node!=null){
            int index  = node.index;
            int weight = node.weight;
            dist[index] = weight;
        }

    }




    public static int[] dijkstra(AdjacencyList[] matrix,int indexS){
        int[] dist     = initDistArr(matrix.length,indexS);
        FibHeap  heapT = initFibHeapT(matrix[0],matrix.length);
        int count = 0;
        while (heapT.size()>0){
//            System.out.println(count++);
            FibHeap.FibNode nodeP = heapT.pop();
            dist[nodeP.index] = nodeP.weight;
            updateFibHeapT(matrix,indexS,dist,nodeP,heapT);
        }
        return dist;
    }
    public static void updateFibHeapT(AdjacencyList[] matrix,int indexS,int[] dist,FibHeap.FibNode nodeP,FibHeap  heapT){
        int indexI  = nodeP.index;
        int weightI = nodeP.weight;
        AdjacencyList node = matrix[indexI];
        while (node!=null){
            int indexJ  = node.index;
            int weightJ = node.weight;
//            System.out.println(indexJ+":"+weightJ);
            if (dist[indexJ]==0){
                FibHeap.FibNode nodePre = heapT.get(indexJ);
                int newWeight = weightJ+weightI;
                if (nodePre==null){
                    //加入heapT
                    heapT.insert(indexJ,newWeight);
                }else{
                    //修改heapT
                    if (newWeight<nodePre.weight){
                        heapT.update(nodePre,newWeight);//新权值大于原来权值，改
                    }
                }

            }
            node = node.next;
        }
    }

    public static FibHeap initFibHeapT(AdjacencyList head,int length){
        AdjacencyList node = head;
        FibHeap       HeapT = new FibHeap(length);
        while (node!=null){
            HeapT.insert(node.index,node.weight);
            node = node.next;
        }
        return HeapT;

    }
    public static int[] initDistArr(int length,int indexS){
        int[] dist   = new int[length];
        dist[indexS] = -1;
        return dist;
    }









//    public static LinkedList initMinArrT(AdjacencyList head,LinkedList<AdjacencyList> arrT,int[] pos){
//        /*使用LinkedList，作为队列优先的数组储存标号t*/
//        int length = merge(head,-1)-1;
//        AdjacencyList node = head;
//        while (node!=null){
//            pos[node.index] = length--;
//            arrT.push(node);
//            node = node.next;
//        }
//
//        return arrT;
//
//
//    }


    public ArrayList<AdjacencyList> initArrT(AdjacencyList head){
        /*普通数组*/
        ArrayList<AdjacencyList> arrT = new ArrayList<AdjacencyList>();
        AdjacencyList node = head;
        while (node!=null){
            arrT.add(node);
            node = node.next;
        }
        return arrT;
    }
    public int minT(AdjacencyList head){
        AdjacencyList node = head;
        int minWeight = head.weight;
        int index=0;
        while (node!=null){
            if (minWeight>node.weight||minWeight==-1){
                minWeight=node.weight;
                index = node.index;
            }
            node = node.next;

        }
        return minWeight;
    }

    private int partition(ArrayList<AdjacencyList> arrT,int begin,int end){
        if (arrT==null||begin>=end){
            return -1;
        }

        int mid  = (int)Math.ceil((begin+end)/2.0);
        return 0;

    }
}
