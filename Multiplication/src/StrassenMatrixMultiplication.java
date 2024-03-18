import java.util.Optional;

public class StrassenMatrixMultiplication implements Multiplicator {

    private Optional<OperationCounter> operationCounter = Optional.empty();
    private Optional<OperationTimer> operationTimer = Optional.empty();


    public StrassenMatrixMultiplication(OperationTimer operationTimer, OperationCounter operationCounter) {
        this.operationCounter = Optional.of(operationCounter);
        this.operationTimer = Optional.of(operationTimer);
    }
    public StrassenMatrixMultiplication(OperationTimer operationTimer) {
        this.operationTimer = Optional.of(operationTimer);
    }
    public StrassenMatrixMultiplication(OperationCounter operationCounter) {
        this.operationCounter = Optional.of(operationCounter);
    }

    public int[][] multiply(int[][] firstMatrix, int[][] secondMatrix) {
        System.out.println("Strassen");
        this.operationTimer.ifPresent(OperationTimer::start);
        var result = multiplyInner(firstMatrix, secondMatrix);
        this.operationTimer.ifPresent(OperationTimer::stop);
        return result;
    }

    private int[][] multiplyInner(int[][] A, int[][] B) {
        int n = A.length;

        // Warunek stopu rekurencji
        if (n == 1) {
            int[][] C = new int[1][1];
            C[0][0] = A[0][0] * B[0][0];
            return C;
        } else {

            // Dzielenie macierzy na cztery podmacierze
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];

            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];

            //Przepisywanie wartości z wejściowej do mniejszych macierzy
            divideMatrix(A, A11, 0, 0);
            divideMatrix(A, A12, 0, n/2);
            divideMatrix(A, A21, n/2, 0);
            divideMatrix(A, A22, n/2, n/2);

            divideMatrix(B, B11, 0, 0);
            divideMatrix(B, B12, 0, n/2);
            divideMatrix(B, B21, n/2, 0);
            divideMatrix(B, B22, n/2, n/2);

            //Nowe wartości, które zmniejszają liczbę mnożeń z 8 do 7
            int[][] M1 = multiplyInner(addMatrix(A11, A22), addMatrix(B11, B22));
            int[][] M2 = multiplyInner(addMatrix(A21, A22), B11);
            int[][] M3 = multiplyInner(A11, subtractMatrix(B12, B22));
            int[][] M4 = multiplyInner(A22, subtractMatrix(B21, B11));
            int[][] M5 = multiplyInner(addMatrix(A11, A12),  B22);
            int[][] M6 = multiplyInner(subtractMatrix(A21, A11), addMatrix(B11, B12));
            int[][] M7 = multiplyInner(subtractMatrix(A12, A22), addMatrix(B21, B22));

            // Obliczanie podmacierzy wynikowej
            int[][] C11 = addMatrix(subtractMatrix(addMatrix(M1, M4), M5), M7);
            int[][] C12 = addMatrix(M3, M5);
            int[][] C21 = addMatrix(M2, M4);
            int[][] C22 = addMatrix(addMatrix(subtractMatrix(M1, M2), M3), M6);

            // Łączenie podmacierzy wynikowej w jedną macierz
            int[][] C = new int[n][n];
            combineMatrix(C, C11, 0, 0);
            combineMatrix(C, C12, 0, n/2);
            combineMatrix(C, C21, n/2, 0);
            combineMatrix(C, C22, n/2, n/2);

            return C;
        }
    }

    // Metoda do dzielenia macierzy
    public void divideMatrix(int[][] parent, int[][] child, int rowShift, int colShift) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                this.operationCounter.ifPresent(OperationCounter::increment);
                child[i][j] = parent[i + rowShift][j + colShift];
            }
        }
    }

    // Metoda do łączenia podmacierzy w jedną macierz
    public static void combineMatrix(int[][] parent, int[][] child, int rowShift, int colShift) {
        for (int i = 0; i < child.length; i++) {
            for (int j = 0; j < child.length; j++) {
                parent[i + rowShift][j + colShift] = child[i][j];
            }
        }
    }

    // Metoda do dodawania macierzy
    public int[][] addMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.operationCounter.ifPresent(OperationCounter::increment);
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    // Metoda do odejmowania macierzy
    public int[][] subtractMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.operationCounter.ifPresent(OperationCounter::increment);
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    public static void printResult(int[][] result) {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
