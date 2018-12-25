package DijkstraOpt2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class UpdateArrT {
    private ExecutorService exes;
    private int cpuCoreNumber;
    private List<Future<Long>> tasks = new ArrayList<Future<Long>>();

    public UpdateArrT(ExecutorService exes, int cpuCoreNumber) {
        this.exes = exes;
        this.cpuCoreNumber = cpuCoreNumber;
    }
//    public int assignmentTask(int[] data){}
}
