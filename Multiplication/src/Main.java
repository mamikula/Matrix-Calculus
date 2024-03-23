import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int MIN_K = 2;
        int MAX_K = 10;
        var l_values = List.of(2, 4, 6, 8);
        var data = new ArrayList<DataEntry>();
        var strassen_data = new ArrayList<DataEntry>();
        var classic_data = new ArrayList<DataEntry>();

        for (int k = MIN_K; k < MAX_K; k++) {
            System.out.println("k = " + k);
            int[][] A = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
            int[][] B = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
            var operationTimer = new OperationTimer();
            var operationCounter = new OperationCounter();
            var classicMultiplication = new MatrixMultiplication(operationTimer, operationCounter);
            classicMultiplication.multiply(A, B);
            classic_data.add(new DataEntry(
                    (int) Math.pow(2, k),
                    0,
                    operationTimer.getElapsedTimeInSeconds(),
                    operationCounter.getCounter(),
                    MultiplicationType.CLASSIC
            ));
            System.out.println(operationTimer.getElapsedTimeInSeconds());
        }
        saveCSV(classic_data, "classic_data.csv");

        for (int k = MIN_K; k < MAX_K; k++) {
            System.out.println("k = " + k);
            int[][] A = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
            int[][] B = RandomMatrixGenerator.generateMatrix((int) Math.pow(2, k));
            var operationTimer = new OperationTimer();
            var operationCounter = new OperationCounter();
            var strassenMultiplication = new StrassenMatrixMultiplication(operationTimer, operationCounter);
            strassenMultiplication.multiply(A, B);
            strassen_data.add(new DataEntry(
                    (int) Math.pow(2, k),
                    0,
                    operationTimer.getElapsedTimeInSeconds(),
                    operationCounter.getCounter(),
                    MultiplicationType.STRASSEN
            ));
        }
        saveCSV(strassen_data, "strassen_data.csv");



        for (int l : l_values) {
            for (int k = MIN_K; k < MAX_K; k++) {
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
        saveCSV(data, "data.csv");
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

    private static void saveCSV(List<DataEntry> data, String filename) {
        File file = new File(filename);
        try (PrintWriter pw = new PrintWriter(file)) {
            data.stream()
                    .map(d -> d.size + "," + d.l + "," + d.time + "," + d.operations + "," + d.multiplicationType)
                    .map(l -> l + ',')
                    .forEach(pw::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}