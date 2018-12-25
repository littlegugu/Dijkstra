package DijkstraOpt2;




import java.util.ArrayList;


public class MyHeap {
    public int number;//节点数量
    private int[] pos;
    public AdjacencyList minNode;

    public MyHeap(AdjacencyList head,int length) {
        this.minNode = merge(head,-1);
        this.pos     = new int[length];
        initPos();
    }
    public int size(){
        return number;
    }
    public int getWeight(int index){
        return pos[index];
    }
    private void initPos(){
        AdjacencyList node = this.minNode;
        int indexInList;
        while (node != null){
            pos[node.index] = node.weight;
            node = node.next;
        }
    }
    public int popArr(ArrayList<AdjacencyList> arr){
        arr.clear();
        if (number==0){
            return 0;
        }
        AdjacencyList head = minNode;
        AdjacencyList pre = null;
        AdjacencyList node = minNode;
        int weight = 0;
        try{
            weight = head.weight;

        }catch (Exception e){
            e.printStackTrace();
        }

        int count = 0;
        while (node!=null && node.weight==weight){
            count++;
            arr.add(node);
            node = node.next;
        }
        number -= count;
        minNode = node;
        return count;
    }

    public AdjacencyList pop(){
        if (number==0){
            return null;
        }
        AdjacencyList head = minNode;
        AdjacencyList pre = null;
        AdjacencyList node = minNode;
        int weight = head.weight;
        int count = 0;
        while (node!=null && node.weight==weight){
            count++;
            pre = node;
            node = node.next;
        }
        if (node==null){
            number  = 0;
            minNode = null;
        }else{
            number -= count;
            minNode = node;
            pre.next = null;
        }
        return head;

    }

    public AdjacencyList pop2(){
        AdjacencyList head = minNode;
        AdjacencyList node = head;
        minNode = minNode.next;
        number--;
        while (minNode!=null&&head.weight==minNode.weight){
            node = node.next;
            minNode = minNode.next;
            number--;
        }
        node.next = null;
        return head;

    }

    public  AdjacencyList merge(AdjacencyList heads, int length){
        if (length==1||heads==null){
            return heads;
        }
        if (length==2){
            swap(heads,heads.next);
            return heads;
        }
        int lengths = length;
        AdjacencyList head = heads ;
        if(length==-1){
            AdjacencyList node = null;
            AdjacencyList prenode = null;
            AdjacencyList nodes = heads;
            length = 0;
            while (nodes!=null){
                node = new AdjacencyList(nodes.index,nodes.weight);
                if (prenode!=null){
                    prenode.next = node;
                }else{
                    head = node;
                }
                prenode = node;
                length++;
                nodes = nodes.next;
            }
            number = length;
        }

        int timeBack = 0;

        if (length%2!=0){
            timeBack = (int)Math.ceil(length/2.0);

        }else{
            timeBack = length/2+1;
        }
        int timeFront  = length -timeBack;
        AdjacencyList mid = head;
        for (int i = 1; i < timeBack; i++) {
            mid = mid.next;
        }
        if (length%2==0){
            timeFront = length/2;
            timeBack = length-timeFront;
        }
        merge(head,timeFront);
        merge(mid,timeBack);
        return mergeWithTwoWay(head,mid,timeFront,timeBack,lengths);
    }
    private static AdjacencyList mergeWithTwoWay(AdjacencyList headMax,AdjacencyList headMin,int numMax,int numMin,int length){
        AdjacencyList node;
        AdjacencyList nodePre= null;
        AdjacencyList head   = null;
        int numMaxT = numMax;
        int numMinT = numMin;
        AdjacencyList nodeMax = headMax;
        AdjacencyList nodeMin = headMin;
        int count = numMaxT+numMinT;
        while (count-->0){
            if(nodeMax.weight<nodeMin.weight){
                node = new AdjacencyList(nodeMax.index,nodeMax.weight);
                if (numMaxT>1){
                    nodeMax = nodeMax.next;
                    numMaxT--;
                }else{
                    if (count==numMax+numMin-1){
                        head = node;
                    }else{
                        nodePre.next = node;
                    }
                    nodePre = node;
                    node = node.next;
                    while (numMinT-->0){
                        node = new AdjacencyList(nodeMin.index,nodeMin.weight);
                        nodePre.next = node;
                        nodePre = node;
                        node = node.next;
                        nodeMin = nodeMin.next;

                    }
                    break;
                }
            }else{
                node = new AdjacencyList(nodeMin.index,nodeMin.weight);
//                System.out.println(nodeMin.weight);
                if (numMinT>1){
                    nodeMin = nodeMin.next;
                    numMinT--;
                }else{
                    if (count==numMax+numMin-1){
                        head = node;
                    }else{
                        nodePre.next = node;
                    }
                    nodePre = node;
                    node = node.next;
                    while (numMaxT-->0){
                        node = new AdjacencyList(nodeMax.index,nodeMax.weight);
                        nodePre.next = node;
                        nodePre = node;
                        node = node.next;
                        nodeMax = nodeMax.next;
                    }
                    break;

                }
            }
            if (count==numMax+numMin-1){
                head = node;
            }else{
                nodePre.next = node;
            }
            nodePre = node;
            node = node.next;

        }
        if (length==-1){
            return head;
        }
        node = head;
        while (node!=null){
            headMax.weight = node.weight;
            headMax.index = node.index;
            headMax = headMax.next;
            node = node.next;
        }
        return headMax;



    }
    public static  boolean swap(AdjacencyList maxP,AdjacencyList minP){
        if (maxP.weight>minP.weight){
            /*交换，只交换值*/
            int tmpWeight    = maxP.weight;
            int tmpIndex     = maxP.index;
//            AdjacencyList tmp = maxP;
            maxP.weight      = minP.weight;
            maxP.index       = minP.index;
            minP.weight = tmpWeight;
            minP.index  = tmpIndex;
//            maxP = minP;
//            minP = tmp;
            return true;

        }
        return false;

    }
    public boolean insert(int index , int weight){
        if(weight<=0 && pos[index]!=0){
            return false;
        }
        AdjacencyList node = new AdjacencyList(index,weight);
        pos[index] = weight;
        number++;
        if (number==1){
            //防止空表头
            minNode = node;
            return true;
        }
        AdjacencyList place    = minNode;
        AdjacencyList placePre = null;
        boolean flag = false;
        while (place!=null){
            if (place.weight>=weight){
//                flag = true;
                break;
            }
            placePre = place;
            place = place.next;
        }
        if (place!=null){
            if (placePre==null){
                node.next = place;
                minNode = node;
            }else{
                placePre.next = node;
                node.next = place;
            }

        }else {
            placePre.next = node;
        }
        return true;
        


        



    }


    public void insert4(int indexJ, int newWeight) {
        if(newWeight<=0 && pos[indexJ]!=0){
            return;
        }
        AdjacencyList node = new AdjacencyList(indexJ,newWeight);
        if (minNode==null){
            minNode = node;
            pos[indexJ] = newWeight;
            number++;
            return;
        }

        /*插头*/
        if(minNode.weight>=newWeight){
            node.next = minNode;
            minNode = node;
            pos[indexJ] = newWeight;
            number++;
            return;
        }
        AdjacencyList nodePre    = minNode;
        AdjacencyList nodeNext   = minNode.next;
        while (nodeNext!=null&&nodeNext.weight<=newWeight){
            nodePre = nodePre.next;
            nodeNext= nodeNext.next;
        }
        nodePre.next = node;
        node.next = nodeNext;
        number++;
        pos[indexJ] = newWeight;
        return;
    }

    public boolean remove(AdjacencyList nodePre,AdjacencyList nodeRemove){
        if (minNode==null||nodePre.next!=nodeRemove||nodePre==null||nodeRemove==null){
            return false;
        }
        nodePre.next = nodeRemove.next;
        nodeRemove.next = null;
        return true;

    }
    public boolean update(int index,int newWeight){
        if (minNode==null){
            return false;
        }
        if (pos[index]<newWeight){
            return false;
        }
        AdjacencyList node = minNode;
        AdjacencyList nodePre = null;
        AdjacencyList place = null;
        AdjacencyList placePre = null;
        boolean flag = false;//标志
        while (node != null){
            if (node.index==index){
//                nodeOfIndex = node;//找到了
                break;
            }
            if (node.weight>=newWeight&&!flag){
                //找到新插入点
                place = node;
                placePre = nodePre;
                flag = true;

            }
            nodePre = node;
            node = node.next;
        }
        if (node != null){
            //找到了
            node.weight = newWeight;
            pos[index]     = newWeight;
            if (nodePre==null||!flag||nodePre.weight<=newWeight){
                //头节点和无需改位置
                return true;
            }
            remove(nodePre,node);
//            nodePre.next = node.next;
//            node.next = null;
            if (placePre==null &&place!=null){
                //插头
                node.next = minNode;
                minNode = node;
            }else{
                placePre.next = node;
                node.next = place;
            }


            return true;
        }else{
            //没找到
            System.out.println("have problem!");
            return false;
        }

    }

    public void update3(int indexJ, int newWeight) {
        AdjacencyList node = minNode;
        while (node!=null && node.index!=indexJ){
            node = node.next;
        }
        if (node!=null){
            node.weight = newWeight;
        }
        pos[indexJ] = newWeight;
        return;
    }
}
