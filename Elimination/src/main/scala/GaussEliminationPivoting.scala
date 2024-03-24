package lab.gaussian.elimination

object GaussEliminationPivoting extends GaussEliminationSolver {
  override def solveEquations(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    val n = B.length
    val X = Array.ofDim[Double](n)

    for (i <- n - 1 to 0 by -1) {
      var sum = 0.0
      for (j <- i + 1 until n)
        sum += A(i)(j) * X(j)

      X(i) = (B(i) - sum) / A(i)(i)
    }
    X
  }

  override def normalizeMatrix(A: Array[Array[Double]], B: Array[Double]): Unit = {
    val n = B.length

    for (i <- 0 until n) {
      var pivotIndex = i
      var pivotValue = A(i)(i)
      for (k <- i + 1 until n) {
        if (math.abs(A(k)(i)) > math.abs(pivotValue)) {
          pivotIndex = k
          pivotValue = A(k)(i)
        }
      }
      if (pivotIndex != i) {
        val tmpA = A(i)
        val tmpB = B(i)
        A(i) = A(pivotIndex)
        B(i) = B(pivotIndex)
        A(pivotIndex) = tmpA
        B(pivotIndex) = tmpB
      }

      for (k <- i + 1 until n) {
        val factor = A(k)(i) / A(i)(i)
        for (j <- i until n)
          A(k)(j) -= factor * A(i)(j)

        B(k) -= factor * B(i)
      }
    }
  }
}
