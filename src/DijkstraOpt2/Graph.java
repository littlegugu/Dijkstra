package DijkstraOpt2;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Random;

public class Graph {
    public AdjacencyList[]  adjArr;
    public int point;//点数
    public int edge;//边数
    private int proportion;
    private Random ran = new Random();

    public Graph(int point,int proportion) {
        this.point = point;
        this.edge  = proportion*point;//固定点边比例比例
        this.proportion = proportion;
        this.adjArr = new AdjacencyList[this.point];
        initPoissonGraph();
    }
    public void initPoissonGraph(){
        /*初始化随机泊松分布图*/
        long seed = 225232;//随机种子，任意long，同一个种子分布相同。
        ArrayList<Integer> exist = new ArrayList<Integer>();//去重数组，使得随机选择下标时不会重复
        for (int indexI = 0; indexI < point; indexI++) {
            exist = initExist(indexI,exist);
            ran.setSeed(seed++);
            while (exist.size()-1<proportion) {
                int indexJ  = ranIndex(exist);
                int weight = ranWeight();
                assignAdjacencyTable(indexI,indexJ,weight);
                assignAdjacencyTable(indexJ,indexI,weight);
            }

        }

    }

    private void assignAdjacencyTable(int i,int j,int weight){
        if (adjArr[i]==null){
            adjArr[i] = new AdjacencyList(j,weight);
        }else{
            AdjacencyList node = adjArr[i];
            while (node.next!=null){
                node = node.next;
            }
            node.next = new AdjacencyList(j,weight);
        }
    }
    private ArrayList<Integer> initExist(int index,ArrayList<Integer> exist){
        exist.clear();
        exist.add(index);
        if (this.adjArr[index]!=null){
            AdjacencyList node = this.adjArr[index];//head
            while (node!=null){
                exist.add(node.index);
                node = node.next;
            }

        }

        return exist;
    }


    private int ranIndex(ArrayList<Integer> exist){
        int index = (int)(Math.random() * point);
        int count = 0;
        while (exist.contains(index)||AdjacencyList.length(adjArr[index])>=proportion){
            index = (int)(Math.random() * point);
            if (count++>this.point){
                break;
            }
        }
        exist.add(index);
        return index;
    }

    private int ranWeight(){
        return (int)Math.abs(ran.nextGaussian()*2)+1;

    }
    public int getWeight(int indexI,int indexJ){
        AdjacencyList node = adjArr[indexI];
        while (node!=null){
            if (node.index==indexJ){
                return node.weight;
            }
            node = node.next;
        }
        return -1;
    }


}
