package DijkstraOpt;

public class Test2 {
    public static void main(String[] args) {
        Graph p = new Graph(10);
        int sp = p.sparsity();
        System.out.println(p.point+","+sp);
        for (int i = 0; i < p.point; i++) {
            for (int j = 0; j < p.point; j++) {
                System.out.print(p.matrix[i][j]+",");
            }
            System.out.println("");
        }
    }
}

