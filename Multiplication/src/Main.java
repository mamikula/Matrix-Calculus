import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*
          1. Generate martices A and B with given size
          2. Measure time and multiplications for both methods
         */


        int MAX_K = 9;
        var l_values = List.of(2, 4, 6, 8);
        var data = new ArrayList<DataEntry>();
        for (int l : l_values) {
            for (int k = 2; k < MAX_K; k++) {
                int[][] A = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
                int[][] B = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
                var operationTimer = new OperationTimer();
                var operationCounter = new OperationCounter();
                var classicMultiplication = new MatrixMultiplication(operationTimer, operationCounter);
                var strassenMultiplication = new StrassenMatrixMultiplication(operationTimer, operationCounter);
                var combinedMultiplication = new CombinedMultiplicator((int) Math.pow(2, l), classicMultiplication, strassenMultiplication);
                combinedMultiplication.multiply(A, B);

                data.add(new DataEntry(
                        (int) Math.pow(2, k),
                        l,
                        operationTimer.getElapsedTimeInSeconds(),
                        operationCounter.getCounter(),
                        A.length <= l ? MultiplicationType.CLASSIC : MultiplicationType.STRASSEN
                ));
            }
        }

        data.forEach(d -> System.out.println(d.size + " " + d.l + " " + d.time + " " + d.operations + " " + d.multiplicationType));
        File file = new File("data.csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            data.stream()
                    .map(d -> d.size + "," + d.l + "," + d.time + "," + d.operations + "," + d.multiplicationType)
                    .map(l -> l + ',')
                    .forEach(pw::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private record DataEntry(int size, int l, double time, int operations, MultiplicationType multiplicationType) {}
    private enum MultiplicationType {
        CLASSIC,
        STRASSEN;

        @Override
        public String toString() {
            return switch (this) {
                case CLASSIC -> "Classic";
                case STRASSEN -> "Strassen";
            };
        }
    }

}