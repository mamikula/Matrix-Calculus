package lab.gaussian.elimination

object GaussElimination extends GaussEliminationSolver {

  override def solveEquations(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    val n = B.length
    val X = Array.ofDim[Double](n)
    X(n - 1) = B(n - 1)
    for (i <- n - 2 to 0 by -1) {
      var sum = 0.0
      for (j <- i + 1 until n) {
        sum += A(i)(j) * X(j)
      }
      X(i) = B(i) - sum
    }
    X
  }

  override def normalizeMatrix(A: Array[Array[Double]], B: Array[Double]): Unit = {
    val n = B.length

    for (i <- 0 until n) {
      val factor = A(i)(i)
      for (j <- i until n) {
        A(i)(j) /= factor
      }
      B(i) /= factor

      for (k <- i + 1 until n) {
        val coefficient = A(k)(i)
        for (j <- i until n) {
          A(k)(j) -= coefficient * A(i)(j)
        }
        B(k) -= coefficient * B(i)
      }
    }
  }

}
