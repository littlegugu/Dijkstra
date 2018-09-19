package DijkstraOpt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Graph {
    private static int point;
    public  static int[][] matrix;
    private static Random ran = new Random();



    public Graph(int point) {
        this.point = point;
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

    public static int[] dijkstra(int index){
        ArrayList<Integer> t = new ArrayList<Integer>();//在此Arraylist里面不再查找
        t.add(index);
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];
        dist[index] = -1;
        int pointer;
        while (t.size()!=point) {
            pointer       = minIndexf(arr).get(0);
            dist[pointer] = arr[pointer];//获得最短路径
            arr[pointer]  = -1;//改值，以防查找
            arr = update(arr,pointer,dist[pointer]);
            t.add(pointer);
        }
        return dist;
    }
    private static int[] update(int[] arr,int pointer,int dist){
        for (int col = 0; col < point; col++) {
            if ((matrix[pointer][col] != 0 && arr[col]>=0) &&((dist+matrix[pointer][col])<arr[col]) )
                arr[col] = dist+matrix[pointer][col];
        }
        return  arr;
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
                minList.add(minIndex);
            }else if(arr[index] > 0 && arr[index]==minNum){
                minList.add(index);
            }
        }
        return minList;
    }

    public static int[] dijkstraOpt(int index){
        ArrayList<Integer> t = new ArrayList<Integer>();//在此Arraylist里面不再查找
        t.add(index);
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
        int[] dist = new int[point];
        dist[index] = -1;
        int pointer;
        int count = 0;
        ArrayList<Integer> pArr;
        while (t.size()!=point) {
            pArr = minIndexf(arr);
//            optupdate(arr,pArr,dist);
            Iterator<Integer> pArrIt = pArr.iterator();
            count++;
            while (pArrIt.hasNext()){
                pointer       = pArrIt.next();
                dist[pointer] = arr[pointer];//获得最短路径
                arr[pointer]  = -1;//改值，以防查找
                t.add(pointer);
                update(arr,pointer,dist[pointer]);
            }
        }
//        System.out.println(count);
        return dist;
    }





    


}


