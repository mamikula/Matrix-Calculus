public final class CombinedMultiplicator implements Multiplicator {
    private final int L;
    private final Multiplicator first;
    private final Multiplicator second;
    public CombinedMultiplicator(int l, Multiplicator first, Multiplicator second) {
        this.L = l;
        this.first = first;
        this.second = second;
    }


    @Override
    public int[][] multiply(int[][] firstMatrix, int[][] secondMatrix) {
        return firstMatrix.length <= L ? first.multiply(firstMatrix, secondMatrix) : second.multiply(firstMatrix, secondMatrix);
    }
}
