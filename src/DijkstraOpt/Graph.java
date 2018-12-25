package DijkstraOpt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.*;

public class Graph {
    public  static int point;
    public  static int[][] matrix;
    private static Random ran;
    public  static ExecutorService service;
    public  static int high;
    public  static int out;






    public Graph(int point) {
        this.point = point;
        this.ran   = new Random();
        this.service = Executors.newFixedThreadPool(50);

    }

    public Graph(int high,int out){
        this.point = (int) (Math.pow(out,high)-1)/(out-1);
        this.high  = high;
        this.out   = out;
        this.ran   = new Random();
        this.service = Executors.newFixedThreadPool(100);
    }

    public static int  hNum(int out,int point){
        return (int)Math.ceil(Math.log(((out-1)*(point-1))+1)/Math.log(out))-1;
    }

    public Graph(int point,int out,int type){
        this.point = point;
        this.out = out;
        this.ran   = new Random();
        this.service = Executors.newFixedThreadPool(100);
        this.high  = hNum(out,point);
        this.matrix = new int[point][point];

    }


    public int[][] zhengTree(){
        ArrayList<Integer> arrList = new ArrayList<Integer>();
        arrList.add(0);
        int layer = 1;
        int index = 1;
        int value = 1;
        for (int i = 0; i < point; i++) {
            for (int j = 0; j < point; j++) {
                matrix[i][j]=0;

            }

        }
        while (layer<=high){
            arrList = setZheng(arrList,index,value);
            index += Math.pow(out,layer);
            value++;
            layer++;
        }
        return matrix;


    }

    private ArrayList<Integer> setZheng(ArrayList<Integer> indexList,int index,int value){
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (int indexI:indexList) {
            int count = out;
            while (count-->0&& index<point){
                matrix[indexI][index] = value;
                matrix[index][indexI] = value;
//                System.out.print(index+",");
                tmp.add(index);
                index++;
            }

        }
//        System.out.println("");
        return tmp;

    }

    public static int randomOut(int p){
        int value = (int) ran.nextGaussian()+p;
        while (value<0||value>p*2){
            value = (int) ran.nextGaussian()+p;
        }
        return value;
    }
    public static int initMatrix(int p){
        p +=3;
        matrix = new int [point][point];
//        matrix[0][0]=-1;
        int count=0,index,value;
        for (int i = 0; i < point; i++) {
//            for (int j = 0; j < point; j++) {
//                matrix[i][j]=0;
//            }
            if (i>point/2){
                for (int j = i+1; j < point; j++) {
                    if (matrix[i][j]!=0)
                        count++;
                }
                matrix[i][i]=-1;
                out = randomOut(p)-count;
    //            System.out.println(i+":"+out);
                while (out>0 ){
                    index = ran.nextInt(i);
                    value = (int) (Math.abs(ran.nextGaussian()*10)+1);
                    while (matrix[i][index]!=0 ){
                        index = ran.nextInt(i);
                    }
                    matrix[i][index]=value;
                    matrix[index][i]=value;
                    out--;
                }
            }else {
                matrix[i][i]=-1;
            }
        }

        return 0;
    }

    public static int initMatrix1(int p){
        p +=1;
        matrix = new int [point][point];
        int out,index,value,hasOut=0;
        for (int i = 0; i < point; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j]!=0){
                    index  = ran.nextInt(point);
                    hasOut++;
                }
//                matrix[i][j]=0;
            }
            matrix[i][i]=-1;
            out = randomOut(p)-hasOut;
            while (out>0){
                index  = ran.nextInt(point);
                while (index<=i && matrix[i][index]!=0){
                    index  = ran.nextInt(point);
                }
                System.out.println(i+","+index);
                value = (int) (Math.abs(ran.nextGaussian()*10)+1);
                matrix[i][index]=value;
                matrix[index][i]=value;
                out--;
            }
        }
        return 0;
    }

    public static int sparsity(){
        //稀疏矩阵
        int count = 0;
        for (int i = 0; i < point-1; i++) {
            for (int j = i+1; j < point; j++) {
                if (matrix[i][j]!=0)
                    count++;
            }
        }
//        System.out.println(count);
//        double ans = (double)point/ (double)count;
        return count;
    }

    public static int randomIndex(ArrayList<Integer> choice){
        int index = ((int)(Math.random()*point)) ;
        while (choice.contains(index)){
            index = ((int)(Math.random()*point)) ;
        }
        choice.add(index);
        return index;
    }
    public static int[][]  regularGraph(){
        matrix = new int [point][point];
        int count,value;
        int now;
        int index;
        ArrayList<Integer> begin  = new ArrayList<Integer>();
        ArrayList<Integer> tmp    = new ArrayList<Integer>();
        ArrayList<Integer> choice = new ArrayList<Integer>();
        begin.add(0);
        choice.add(0);
        for (int i = 0; i < point; i++) {
            matrix[i][i]=-1;
        }

        for (int h = 1; h < high; h++) {
            value = ((int)(Math.random()*30)) +1;
            Iterator<Integer> beginit = begin.iterator();
            while (beginit.hasNext()){
                index = beginit.next();
                for (int i = 0; i < out; i++) {
                    now = randomIndex(choice);
                    matrix[index][now] = value;
                    matrix[now][index] = value;
                    tmp.add(now);
                }
            }

            begin.clear();
            Iterator<Integer> tmpit = tmp.iterator();
            while (tmpit.hasNext()){
                begin.add(tmpit.next());
            }
            tmp.clear();
        }
        return matrix;
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
//        int count   = 0;
        while (value>0){
            arr = update(arr,pointer);
            arr[pointer]  = -1;//改值，以防查找
            dist[pointer] = value;
            pointer = minIndexSim(arr);
            value   = arr[pointer];
//            count++;
        }
//        System.out.println("count:"+count);
        return dist;
    }

    private static int[] update(int[] arr, int pointer){
        for (int col = 0; col < point; col++) {
            if (matrix[pointer][col]>0){
                if (arr[col]==0){
                    arr[col]=arr[pointer]+matrix[pointer][col];
                }else if(arr[col]!=-1){
                    arr[col] = arr[col]< (matrix[pointer][col]+arr[pointer])?arr[col]:(matrix[pointer][col]+arr[pointer]);
                }
            }

        }
        return arr;
    }



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
                minList.add(index);
            }else if(arr[index] > 0 && arr[index]==minNum){
                minList.add(index);
            }
        }
        return minList;
    }
    public static int[] updateThread1(int[] arr,int[] dist,ArrayList<Integer> pArr,int count) throws Exception{
//        long before,after;
//        before = System.currentTimeMillis();//获取运行之前的时间
        MyRunnable.dist = arr[pArr.get(0)];
        MyRunnable.latch = new CountDownLatch(count);
        for (int i = 0; i < point; i++) {
            if (arr[i]!=-1){
                service.submit(new MyRunnable(matrix,arr,dist,pArr,i));
            }

        }
        MyRunnable.latch.await();
        return arr;
    }

    public static int[]  dijkstraOptThread1(int index) throws Exception{
        int[] arr  = matrix[index].clone();
        int[] dist = new int[point];
        int count = point-1;
        dist[index]=-1;
        arr[index] =-1;
        int pointer;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value = arr[pArr.get(0)];
        while (value>0){
            updateThread1(arr,dist,pArr,count);
            count-=pArr.size();
            pArr = minIndexf(arr);
            value = arr[pArr.get(0)];
        }
        return dist;
    }


    public static int[] updateThread(int[] arr,ArrayList<Integer> pArr) throws Exception{
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=-1&& (!pArr.contains(i)) ){
                service.submit(new MyCallable(matrix,arr,pArr,i));
            }
        }
        MyCallable.latch.await();

        return arr;
    }

    public static int[] dijkstraOptThread(int index) throws Exception {
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];
        dist[index] = -1;
        arr[index]  = -1;
        int pointer;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value = arr[pArr.get(0)];
        int count = point-1;

        while (value>0) {
            if (pArr.size()==1){
                pointer = pArr.get(0);
                update(arr,pointer);
                count --;
                dist[pointer] = arr[pointer];
                arr[pointer]=-1;
            }else{
                MyCallable.latch = new CountDownLatch(count-=pArr.size());
                arr = updateThread(arr,pArr);
                Iterator<Integer> pArrIt = pArr.iterator();
                while (pArrIt.hasNext()){
                    pointer       = pArrIt.next();
                    dist[pointer] = arr[pointer];//获得最短路径
                    arr[pointer] = -1;
                }


            }
            pArr = minIndexf(arr);
            value = arr[pArr.get(0)];

        }
        service.shutdown();
//        System.out.println("disk ok");
        return dist;
    }

    public static int[] updateopt1(int[] arr,ArrayList<Integer> pArr){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=-1 && !pArr.contains(i)){
                Iterator<Integer> pit = pArr.iterator();
                while (pit.hasNext()){
                    Integer pIndex =pit.next();
                    if (matrix[pIndex][i]>0 && (arr[i]==0 || arr[i]>(matrix[pIndex][i]+arr[pIndex])))
                            arr[i] = matrix[pIndex][i]+arr[pIndex];
                }
            }
        }
        return arr;
    }

    public static int[] dijkstraOpt1(int index){
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value               = arr[pArr.get(0)];
        int pointer;
        while (value>0){
            updateopt1(arr,pArr);
            Iterator<Integer> pit = pArr.iterator();
            while (pit.hasNext()){
                Integer pIndex = pit.next();
                dist[pIndex]   = arr[pIndex];
                arr[pIndex]    = -1;
            }
            pArr  = minIndexf(arr);
            value = arr[pArr.get(0)];
        }
        return dist;

    }


    public static int[] updateopt(int[] arr,int[] dist,ArrayList<Integer> pArr){
        long before,after;
        before  = System.currentTimeMillis();//获取运行之前的时间
        int value = arr[pArr.get(0)];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=-1) {
                Iterator<Integer> pit = pArr.iterator();
                Integer pIndex;
                while (pit.hasNext()){
                    pIndex = pit.next();
                    if (pIndex==i){
                        dist[i] = value;
                        arr[i]  = -1;
                        break;
                    }
                    if(matrix[i][pIndex]>0){
                        if (arr[i]==0){
                            arr[i] = matrix[i][pIndex]+value;
                        }else if(arr[i]>matrix[i][pIndex]+value){
                            arr[i]=matrix[i][pIndex]+value;
                        }
                    }
                }

            }
        }
        after = System.currentTimeMillis();//获取运行之前的时间
//        System.out.println("update:"+(after-before));
        return arr;
    }

    public static int[] dijkstraOpt(int index){
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value               = arr[pArr.get(0)];
        int pointer;
        int count=0;
        while (value>0) {
            arr = updateopt(arr,dist,pArr);
            pArr    =   minIndexf(arr);
            value   =   arr[pArr.get(0)];
            count++;
        }
        System.out.println("count:"+count);
        return dist;
    }

    public static int[] updateOpt2(int[] arr,int[] dist,ArrayList<Integer> pArr){
        int value = arr[pArr.get(0)];
//        System.out.println(value);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]!=-1){
                if (pArr.contains(i)){
                    dist[i] = value;
                    arr[i]  = -1;
                }else{
                    Iterator<Integer> pit = pArr.iterator();
                    while (pit.hasNext()){
                        Integer pIndex = pit.next();
                        if (matrix[pIndex][i]>0 && (arr[i]==0 || arr[i]>matrix[pIndex][i]+value)){
                            arr[i] = matrix[pIndex][i]+value;
                        }
                    }
                }


            }
        }
        return arr;
    }

    public static int[] dijkstraOpt2(int index){
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = minIndexf(arr);
        int value               = arr[pArr.get(0)];
        while (value>0){
            updateOpt2(arr,dist,pArr);
            pArr  = minIndexf(arr);
            value = arr[pArr.get(0)];
        }
        return dist;
    }

    public static void minIndexThread(int[]arr, ArrayList<Integer> pArr,ArrayList<Integer> tArr){
        int index;
        int minNum   = arr[0];
        for (index = 1; index < arr.length; index++) {
            if (arr[index] > 0 && (minNum < 0 ||minNum>arr[index])) {
                minNum   = arr[index];
            }
        }
//        System.out.println(minNum);
        if (tArr.isEmpty()){
            for (index = 0; index < arr.length; index++) {
                if (arr[index]==minNum){
                    pArr.add(index);
                }else if(arr[index]!= -1){
                    tArr.add(index);
                }
            }
        }else{
            pArr.clear();
            if(minNum!=-1){
                ArrayList<Integer> tArrTmp = (ArrayList<Integer>) tArr.clone();
                Iterator<Integer> it = tArrTmp.iterator();
                while (it.hasNext()){
                    Integer pIndex = it.next();
                    if (arr[pIndex]==minNum){
                        pArr.add(pIndex);
                        tArr.remove(pIndex);
                    }
                }
            }else{
                pArr.add(0);
            }

        }

    }
    public static int[] updateThread2(int[] arr, ArrayList<Integer> pArr,ArrayList<Integer> tArr) throws InterruptedException{
        Iterator<Integer> it = tArr.iterator();
        while (it.hasNext()){
            Integer index = it.next();
            service.submit(new MyRunnable2(matrix,arr,pArr,index));
        }
        MyRunnable2.latch.await();
//        System.out.println("up");
        return arr;
    }

    public static int[] dijkstraOptThread2(int index) throws InterruptedException{
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = new ArrayList<Integer>();
        ArrayList<Integer> tArr = new ArrayList<Integer>();
        minIndexThread(arr,pArr,tArr);
        int value               = arr[pArr.get(0)];
        while (value>0){
            MyRunnable2.latch = new CountDownLatch(tArr.size());
            MyRunnable2.dist  = value;
            updateThread2(arr,pArr,tArr);
            Iterator<Integer> it = pArr.iterator();
            while (it.hasNext()){
                Integer itvalue = it.next();
                dist[itvalue]   = arr[itvalue];
                arr[itvalue]    = -1;
            }
            minIndexThread(arr,pArr,tArr);
            value = arr[pArr.get(0)];
        }
        service.shutdown();
        return dist;
    }
    public static int[] updateOpt4(int[] dist,int[] arr, ArrayList<Integer> pArr,ArrayList<Integer> tArr){
        Iterator<Integer> it = tArr.iterator();
        while (it.hasNext()){
            Integer indext = it.next();
            Iterator<Integer> ip = pArr.iterator();
            while (ip.hasNext()){
                Integer indexp = ip.next();
                if (matrix[indexp][indext]>0 && (arr[indext]==0||arr[indext]>matrix[indexp][indext]+arr[indexp])){
                    arr[indext]=matrix[indexp][indext]+arr[indexp];
                }
            }
        }
        return arr;
    }

    public static int[] dijkstraOpt4(int index){
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = new ArrayList<Integer>();
        ArrayList<Integer> tArr = new ArrayList<Integer>();
        minIndexThread(arr,pArr,tArr);
        int value               = arr[pArr.get(0)];
        while (value>0){
            updateOpt4(dist,arr,pArr,tArr);
            Iterator<Integer> it = pArr.iterator();
            while (it.hasNext()){
                Integer itvalue = it.next();
                dist[itvalue]   = arr[itvalue];
                arr[itvalue]    = -1;
            }
            minIndexThread(arr,pArr,tArr);
            value = arr[pArr.get(0)];

        }
        return dist;
    }

    public static int[] updateThread5(int[] arr, ArrayList<Integer> pArr,ArrayList<Integer> tArr) throws InterruptedException{
        MyRunnable3.arr = arr;
        MyRunnable3.pArr = pArr;
        Iterator<Integer> it = tArr.iterator();
        while (it.hasNext()){
            Integer index = it.next();
            service.submit(new MyRunnable3(index));
        }
        MyRunnable3.latch.await();
//        System.out.println("up");
        return arr;
    }

    public static int[] dijkstraOptThread5(int index) throws InterruptedException{
        /*多线程：不用每次改matrix*/
        MyRunnable3.matrix = matrix;
        int[] arr  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];//用于放置最小距离
        dist[index] = -1;
        arr [index] = -1;
        ArrayList<Integer> pArr = new ArrayList<Integer>();
        ArrayList<Integer> tArr = new ArrayList<Integer>();
        minIndexThread(arr,pArr,tArr);
        int value               = arr[pArr.get(0)];
        while (value>0){
            MyRunnable3.latch = new CountDownLatch(tArr.size());
            MyRunnable3.dist  = value;
            updateThread5(arr,pArr,tArr);
            Iterator<Integer> it = pArr.iterator();
            while (it.hasNext()){
                Integer itvalue = it.next();
                dist[itvalue]   = arr[itvalue];
                arr[itvalue]    = -1;
            }
            minIndexThread(arr,pArr,tArr);
            value = arr[pArr.get(0)];
        }
        service.shutdown();
        return dist;
    }






    


}


