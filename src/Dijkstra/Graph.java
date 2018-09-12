package Dijkstra;


import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private int point;
    public int[][] matrix;
    private static Random ran = new Random();



    public Graph(int point) {
        this.point = point;
    }

    public int[][] randomGraph(){
        int row,col;
        matrix=new int[this.point][this.point];
        int num;
        for (row = 0; row < this.point; row++) {
            for (col = row; col < this.point; col++) {
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

    public int[] dijkstra(int index){
        ArrayList<Integer> t = new ArrayList<Integer>();//在此Arraylist里面不再查找
        t.add(index);
        int arr[]  = matrix[index].clone();//用于选当前指针的数组
//        int[] dist={matrix[index].length};//最短路径数组
        int[] dist = new int[point];
        dist[index] = -1;
        int pointer;
        while (t.size()!=point) {
            pointer       = minIndex(arr);
            dist[pointer] = arr[pointer];//获得最短路径
            arr[pointer]  = -1;//改值，以防查找
            arr = update(arr,pointer,dist[pointer]);
            t.add(pointer);
        }
        return dist;
    }
    private int[] update(int[] arr,int pointer,int dist){
        for (int col = 0; col < point; col++) {
            if ((matrix[pointer][col] != 0 && arr[col]>=0) &&((dist+matrix[pointer][col])<arr[col]) )
                arr[col] = dist+matrix[pointer][col];
        }
        return  arr;
    }

    private int minIndex(int[] arr){
        int index;
        int minNum   = arr[0];
        int minIndex = 0;
        for (index = 1; index < arr.length; index++) {
            if (arr[index] > 0 && (minNum < 0 ||minNum>arr[index])) {
                minNum   = arr[index];
                minIndex = index;
            }
        }
        return minIndex;
    }



    


}


