public class MatrixMultiplication {

    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        int sum = 0;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    sum = sum + A[i][k] * B[k][j];
                }
                C[i][j] = sum;
                sum = 0;
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

    public static void main(String[] args) {
        int[][] A = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] B = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

        int[][] result = multiply(A, B);

        printResult(result);
    }

}
