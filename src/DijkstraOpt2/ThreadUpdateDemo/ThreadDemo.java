package DijkstraOpt2.ThreadUpdateDemo;

import DijkstraOpt2.AdjacencyList;
import DijkstraOpt2.MyHeap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadDemo {
    private ExecutorService exec;
    private int cpuCoreNumber;
    private CompletionService<Integer> completionService;

    public ThreadDemo() {
        cpuCoreNumber = Runtime.getRuntime().availableProcessors();
        exec = Executors.newFixedThreadPool(cpuCoreNumber);
        completionService = new ExecutorCompletionService<Integer>(exec);
    }


    public void updateHeapTParallel(AdjacencyList[] matrix,int[] dist, ArrayList<AdjacencyList> arrP, MyHeap heapT){
        /*根据cpu资源分配*/
        int pc= 0;
        if (arrP.size()<=cpuCoreNumber){
            for (AdjacencyList nodeP:arrP) {
                System.out.println(pc++);
                UpdateProcess up = new UpdateProcess(matrix,dist,nodeP,heapT);
                if (!exec.isShutdown()) {
                    completionService.submit(up);
                }
            }
        }else {
            System.out.println("ppp");

        }
        try {
            completionService.take().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        exec.shutdown();
    }

    public class UpdateProcess implements Callable<Integer>{
        private AdjacencyList[] matrix;
        private int[] dist;
        private AdjacencyList nodeP;
        private MyHeap heapT;
        Object lock = new Object();//锁


        public UpdateProcess(AdjacencyList[] matrix, int[] dist, AdjacencyList nodeP, MyHeap heapT) {
            this.matrix = matrix;
            this.dist   = dist;
            this.nodeP  = nodeP;
            this.heapT  = heapT;
        }

        @Override
        public Integer call() throws Exception {
//            System.out.println("in call");
            if (nodeP!=null){
                int indexI  = nodeP.index;
                int weightI = nodeP.weight;
                AdjacencyList nodeJ = matrix[indexI];//锁
                while (nodeJ!=null){
                    int indexJ  = nodeJ.index;
                    int weightJ = nodeJ.weight;
                    if (dist[indexJ]==0){
                        int newWeight = weightJ+weightI;
                        synchronized (lock){
                            int oldWeight = heapT.getWeight(indexJ);
                            if (oldWeight==0){
                                //加入heapT
                                heapT.insert(indexJ,newWeight);
                            }else{
                                //修改heapT
                                if (newWeight<oldWeight){
                                    heapT.update(indexJ,newWeight);//新权值大于原来权值，改
                                }
                            }
                        }
                    }
                    nodeJ = nodeJ.next;
                }
                dist[indexI] = weightI;

            }else{
                System.out.println("pppp");

            }


            return 0;
        }
    }
}
