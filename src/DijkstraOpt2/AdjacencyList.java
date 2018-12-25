package DijkstraOpt2;


import java.util.Objects;

public class AdjacencyList {
    public int index;
    public int weight;
    public AdjacencyList next;


    public AdjacencyList(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }
    public static int length(AdjacencyList head){
        AdjacencyList node = head;
        if (head==null){
            return 0;
        }
        int length = 0;
        while (node!=null){
            length++;
            node = node.next;
        }
        return length;
    }




//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AdjacencyList that = (AdjacencyList) o;
//        return index == that.index &&
//                weight == that.weight;
//    }
//    @Override
//    public boolean equals(Object o) {
//        AdjacencyList that = (AdjacencyList) o;
//        if (this.index==that.index&&this.weight==that.weight){
//            return true;
//        }
//        return false;
//    }


//    @Override
//    public int hashCode() {
//        return Objects.hash(index, weight);
//    }
}
