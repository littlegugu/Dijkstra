package TestThread;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurrentCalculatorAsyncTest {

    @Test
    public void sumTest() {
        int n = 30;
        int[] numbers = new int[n];
        int sum = 0;
        for(int i=1;i<=n;i++){
            numbers[i-1]=i;
            sum += i;
        }
        concurrentCaculatorAsync(numbers);



    }
    private static void concurrentCaculatorAsync(int[] numbers) {
        ConcurrentCalculatorAsync calc = new ConcurrentCalculatorAsync();
        calc.sum(numbers);
//        calc.close();
    }
}