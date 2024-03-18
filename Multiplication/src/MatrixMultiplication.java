import java.util.Optional;

public class MatrixMultiplication implements Multiplicator {
    private Optional<OperationCounter> operationCounter = Optional.empty();


    private Optional<OperationTimer> operationTimer = Optional.empty();

    public MatrixMultiplication() {
    }

    public MatrixMultiplication(OperationTimer operationTimer, OperationCounter operationCounter) {
        this.operationCounter = Optional.of(operationCounter);
        this.operationTimer = Optional.of(operationTimer);
    }
    public MatrixMultiplication(OperationTimer operationTimer) {
        this.operationTimer = Optional.of(operationTimer);
    }
    public MatrixMultiplication(OperationCounter operationCounter) {
        this.operationCounter = Optional.of(operationCounter);
    }

    public int[][] multiply(int[][] A, int[][] B) {
        System.out.println("\nClassic");
        this.operationTimer.ifPresent(OperationTimer::start);
        int n = A.length;
        int sum = 0;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    this.operationCounter.ifPresent(OperationCounter::increment);
                    sum = sum + A[i][k] * B[k][j];
                }
                C[i][j] = sum;
                sum = 0;
            }
        }
        this.operationTimer.ifPresent(OperationTimer::stop);
        return C;
    }

//    public static void printResult(int[][] result) {
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                System.out.print(result[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

//    public static void main(String[] args) {
//
//        /*
//          1. Generate martices A and B with given size
//          2. Measure time and multiplications for both methods
//         */
//        int MAX_K = 8;
//        for (int k = 2; k < MAX_K; k++) {
//            for (int l = 3; l < k; l++) {
//                int[][] A = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
//                int[][] B = RandomMatrixGenerator.generateMatrix((int) Math.pow(3, k));
//                var operationTimer = new OperationTimer();
//                var operationCounter = new OperationCounter();
//                var Mu
//            }
//        }
//
//        int[][] A = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//        int[][] B = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//
//        int[][] result = multiply(A, B);
//
//        printResult(result);
//    }

}
