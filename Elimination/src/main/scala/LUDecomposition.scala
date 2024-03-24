package lab.gaussian.elimination

object LUDecomposition extends Solver {
  private def decomposeLU(A: Array[Array[Double]]): (Array[Array[Double]], Array[Array[Double]]) = {
    val n = A.length
    val L = Array.ofDim[Double](n, n)
    val U = Array.ofDim[Double](n, n)

    for (i <- 0 until n) {
      L(i)(i) = 1.0

      for (j <- i until n) {
        var sum = 0.0
        for (k <- 0 until i) {
          sum += L(i)(k) * U(k)(j)
        }
        U(i)(j) = A(i)(j) - sum
      }

      for (j <- i + 1 until n) {
        var sum = 0.0
        for (k <- 0 until i) {
          sum += L(j)(k) * U(k)(i)
        }
        L(j)(i) = (A(j)(i) - sum) / U(i)(i)
      }
    }
//    printMatrix(L)
//    printMatrix(U)
    (L, U)
  }

  private def solveLU(L: Array[Array[Double]], U: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    val n = B.length
    val Y = Array.ofDim[Double](n)
    val X = Array.ofDim[Double](n)

    // Rozwiązanie Ly = B

    for (i <- 0 until n) {
      var sum = 0.0
      for(j <- 0 until i) {
        sum += L(i)(j) * Y(j)
      }
      Y(i) = B(i) - sum
    }

    // Rozwiązanie Ux = y
    for(i <- n - 1 to 0 by -1) {
      var sum = 0.0
      for (j <- i + 1 until n){
        sum += U(i)(j) * X(j)
      }
      X(i) = (Y(i) - sum) / U(i)(i)
    }
    X
  }

  override def solve(A: Array[Array[Double]], B: Array[Double]) : Array[Double] = {
    val (mL, mU) = decomposeLU(A)
    val X = solveLU(mL, mU, B)
    X
  }

}
