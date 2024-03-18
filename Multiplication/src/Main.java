
public class Main {
    public static void main(String[] args) {

        /*
          1. Generate martices A and B with given size
          2. Measure time and multiplications for both methods
         */


        int MAX_K = 10;
//        int k = 8;
        for (int k = 2; k < MAX_K; k++) {
            for (int l = 3; l < k; l++) {
                int[][] A = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
                int[][] B = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
                var operationTimer = new OperationTimer();
                var operationCounter = new OperationCounter();
                var classicMultiplication = new MatrixMultiplication(operationTimer, operationCounter);
                var strassenMultiplication = new StrassenMatrixMultiplication(operationTimer, operationCounter);
                var combinedMultiplication = new CombinedMultiplicator((int) Math.pow(2, l), classicMultiplication, strassenMultiplication);
                combinedMultiplication.multiply(A, B);

                System.out.println("k: " + k + " l: " + l);
                System.out.println("Matrix size: " + (int) Math.pow(2, k) + "x" + (int) Math.pow(2, k));
                System.out.println("Multiplication: " + operationTimer.getElapsedTimeInSeconds() + " " + operationCounter.getCounter());
            }
        }
    }
}