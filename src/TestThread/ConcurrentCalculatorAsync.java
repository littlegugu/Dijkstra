package TestThread;

import java.util.concurrent.*;

public class ConcurrentCalculatorAsync {
    private ExecutorService exec;
    private CompletionService<Long> completionService;
    private int cpuCoreNumber;


    class SumCalculator implements Callable<Long> {
        private int[] numbers;
        private int start;
        private int end;


        public SumCalculator(final int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }


        public Long call() throws Exception {
            Long sum = 0l;
            for (int i = start; i < end; i++) {
                numbers[i] +=1;
                Thread.sleep(100);
            }


            return sum;
        }
    }


    public ConcurrentCalculatorAsync() {
        cpuCoreNumber = Runtime.getRuntime().availableProcessors();
        exec = Executors.newFixedThreadPool(cpuCoreNumber);
        completionService = new ExecutorCompletionService<Long>(exec);
    }


    public void sum(final int[] numbers) {
        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor
        for (int i = 0; i < cpuCoreNumber; i++) {
            int increment = numbers.length / cpuCoreNumber + 1;
            int start = increment * i;
            int end = increment * i + increment;
            if (end > numbers.length){
                end = numbers.length;
            }
            SumCalculator subCalc = new SumCalculator(numbers, start, end);
            if (!exec.isShutdown()) {
                completionService.submit(subCalc);
            }
            try {
                completionService.take().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
//        getResult();
    }


    /**
     * 迭代每个只任务，获得部分和，相加返回
     */
    public void getResult() {
        Long result = 0l;
        for (int i = 0; i < cpuCoreNumber; i++) {
            try {
                completionService.take().get();
                System.out.println("subSum=,result="+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    public void close() {
        exec.shutdown();
    }

}
