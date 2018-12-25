package DijkstraOpt2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConCountSame {
    private ExecutorService exec;
    private int cpuCoreNumber;
    private List<Future<Long>> tasks = new ArrayList<Future<Long>>();

    public ConCountSame() {
        this.cpuCoreNumber = Runtime.getRuntime().availableProcessors();//获得核数
        exec = Executors.newFixedThreadPool(cpuCoreNumber);
    }


    public Long sum(int[] number){
        for (int i = 0; i < this.cpuCoreNumber; i++) {
            int increment = number.length / cpuCoreNumber + 1;
            int start = increment * i;
            int end = increment * i + increment;
            if (end > number.length)
                end = number.length;
            SumCalculator subCall = new SumCalculator(number,start,end);
            FutureTask<Long> task = new FutureTask<Long>(subCall);
            tasks.add(task);
            if (!exec.isShutdown()) {
                exec.submit(task);
            }
        }
        return 1L;
    }
}

class SumCalculator implements Callable<Long> {
    private int[] number;
    private int begin;
    private int end;

    public SumCalculator(int[] number, int begin, int end) {
        this.number = number;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Long call() throws Exception {
        Long sum = 0L;
        for (int i = begin; i < end; i++) {
            sum += number[i];
        }
        return sum;
    }
}

