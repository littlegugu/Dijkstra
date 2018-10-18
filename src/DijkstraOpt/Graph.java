package DijkstraOpt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.*;

public class Graph extends Thread{
    private static int point;
    public  static int[][] matrix;
    private static Random ran = new Random();
    public  static ExecutorService service;



    public Graph(int point) {
        this.point = point;
        this.service = Executors.newFixedThreadPool(100);
    }

    public static int[][] randomGraph(){
        int row,col;
        matrix=new int[point][point];
        int num;
        for (row = 0; row < point; row++) {
            for (col = row; col < point; col++) {
                if(col==row) {
                    matrix[col][row] = -1;
                }else{
                    num = randomProbability(0.25);
                    matrix[col][row] = num;
                    matrix[row][col] = num;
                }
            }
        }
        return matrix;
    }

    private static int randomProbability(double probability){
        int num = ran.nextInt(101);
        int probability_int = (int) (probability*100);
        if(num<=probability_int){
            return 0;
        }else{
            return (int) (Math.abs(ran.nextGaussian()*10)+1);
//            return 1;
        }
    }


    public static int minIndexSim(int[] arr) {
        int value = arr[0];
        int index = 0;
        for (int col = 1; col < arr.length; col++) {
            if (value < 0) {
                if (arr[col] > 0) {
                    value = arr[col];
                    index = col;
                }
            }else {
                if (arr[col]>0 && arr[col] < value) {
                    value = arr[col];
                    index = col;
                }
            }
        }
        return index;
    }

    public static int[] dijkstra(int index){
        int[] arr   = matrix[index].clone();//用于选当前指针的数组
        int[] dist  = new int[point];
        dist[index] = -1;
        arr[index]  = -1;
        int pointer = minIndexSim(arr);
        int value   = arr[pointer];
        int count = 0;
        while (value>0){
//            System.out.println("step"+(count++)+","+pointer);
//            System.out.print("arr:");
//            for (int i = 0; i < arr.length; i++) {
//                System.out.print(arr[i]+",");
//            }
//            System.out.println("");
            arr = update(arr,pointer);
            arr[pointer]  = -1;//改值，以防查找
            dist[pointer] = value;
            pointer = minIndexSim(arr);
            value   = arr[pointer];
        }
        return dist;
    }

    private static int[] update(int[] arr, int pointer){
        for (int col = 0; col < point; col++) {
            if (pointer!=col && matrix[pointer][col]>0 && arr[col] != -1){
                arr[col] = arr[col]< (matrix[pointer][col]+arr[pointer])?arr[col]:(matrix[pointer][col]+arr[pointer]);
            }
        }
        return arr;
    }

//    private static int[] update(int[] arr,int pointer,int dist){
//        for (int col = 0; col < point; col++) {
//            if ((matrix[pointer][col] != 0 && arr[col]>=0) &&((dist+matrix[pointer][col])<arr[col]) )
//                arr[col] = dist+matrix[pointer][col];
//        }
//        return  arr;
//    }

    private static ArrayList<Integer> minIndexf(int[] arr){
        int index;
        int minNum   = arr[0];
        int minIndex = 0;
        ArrayList<Integer> minList = new  ArrayList<Integer>();
        minList.add(minIndex);
        for (index = 1; index < arr.length; index++) {
            if (arr[index] > 0 && (minNum < 0 ||minNum>arr[index])) {
                minNum   = arr[index];
                minIndex = index;
                minList.clear();
                minList.add(minIndex);
            }else if(arr[index] > 0 && arr[index]==minNum){
                minList.add(index);
            }
        }
        return minList;
    }



    public static int[] updateThread(int[] arr,ArrayList<Integer> pArr,int points) throws Exception{
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=-1&& (!pArr.contains(i)) ){
//                System.out.println("in update"+":"+i+","+arr[i]);
                service.submit(new MyCallable(matrix,arr,pArr,i));
            }
        }
        MyCallable.latch.await();
//        System.out.println("update");
        return arr;
    }

    public static int[] dijkstraOptThread(int index) throws Exception {
//        System.out.println("begindi");
        ArrayList<Integer> t = new ArrayList<Integer>();//在此Arraylist里面不再查找
        t.add(index);
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];
        dist[index] = -1;
//        arr[index]=-1;
        int pointer;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value = arr[pArr.get(0)];
        int count= point-1;
        int counts=0;
        while (value>0) {
//            System.out.println("step:"+(counts++)+",size:"+pArr.size());
//            System.out.print("arr:");
//            for (int i = 0; i < arr.length; i++) {
//                System.out.print(arr[i]+",");
//            }
//            System.out.println("");
            if (pArr.size()==1){
                pointer = pArr.get(0);
                update(arr,pointer);
                count --;
            }else{
                ;
//                System.out.println(count+"..."+pArr.size());
                MyCallable.latch = new CountDownLatch(count-=pArr.size());
                arr = updateThread(arr,pArr,count);

            }
            Iterator<Integer> pArrIt = pArr.iterator();
            while (pArrIt.hasNext()){
                pointer       = pArrIt.next();
                dist[pointer] = arr[pointer];//获得最短路径
                arr[pointer] = -1;
            }
            pArr = minIndexf(arr);
            value = arr[pArr.get(0)];
        }
        service.shutdown();
//        System.out.println("disk ok");
        return dist;
    }

    public static int[] dijkstraOpt(int index){
        ArrayList<Integer> t = new ArrayList<Integer>();//在此Arraylist里面不再查找
        t.add(index);
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        int pointer;
        int count = 0;
        ArrayList<Integer> pArr;
        while (t.size()!=point) {
            pArr = minIndexf(arr);
            Iterator<Integer> pArrIt = pArr.iterator();
            count++;
            while (pArrIt.hasNext()){
                pointer       = pArrIt.next();
                dist[pointer] = arr[pointer];//获得最短路径
                arr[pointer]  = -1;//改值，以防查找
                t.add(pointer);
                update(arr,pointer);
            }
        }
//        System.out.println(count);
        return dist;
    }




    


}


