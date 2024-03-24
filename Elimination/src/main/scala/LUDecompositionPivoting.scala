package lab.gaussian.elimination

object LUDecompositionPivoting extends Solver{
  def solve(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    val n = A.length

    // Dekompozycja LU z pivotingiem
    val (mL, mU, mP) = decomposeLU(A)

    // Permutacja wektora prawych stron
    val PB = permuteVector(B, mP) //P

    // Rozwiązanie Ly = PB
    val Y = forwardSubstitution(mL, PB)

    // Rozwiązanie Ux = Y
    val X = backwardSubstitution(mU, Y)

    X
  }

  private def decomposeLU(A: Array[Array[Double]]): (Array[Array[Double]], Array[Array[Double]], Array[Array[Double]]) = {
    val n = A.length
    val L = Array.ofDim[Double](n, n)
    val U = Array.ofDim[Double](n, n)
    val P = Array.ofDim[Double](n, n)

    for (i <- 0 until n) {
      P(i)(i) = 1.0
    }

    for (i <- 0 until n) {
      var pivotIndex = i
      var pivotValue = A(i)(i)

      for (j <- i + 1 until n) {
        if (math.abs(A(j)(i)) > math.abs(pivotValue)) {
          pivotIndex = j
          pivotValue = A(j)(i)
        }
      }

      if (pivotIndex != i) {
        var tmp = A(i)
        A(i) = A(pivotIndex)
        A(pivotIndex) = tmp

        tmp = P(i)
        P(i) = P(pivotIndex)
        P(pivotIndex) = tmp
      }

      for (k <- i until n) {
        var sum = 0.0
        for (l <- 0 until i) {
          sum += L(k)(l) * U(l)(i)
        }
        L(k)(i) = A(k)(i) - sum
      }

      for (k <- i until n) {
        var sum = 0.0
        for (l <- 0 until i) {
          sum += L(i)(l) * U(l)(k)
        }
        U(i)(k) = (A(i)(k) - sum) / L(i)(i)
      }
    }
//    printMatrix(L)
//    printMatrix(U)
//    printMatrix(P)
    (L, U, P)
  }

  private def permuteVector(B: Array[Double], P: Array[Array[Double]]): Array[Double] = {
    val n = B.length
    val PB = Array.ofDim[Double](n)

    for (i <- 0 until n) {
      for (j <- 0 until n) {
        PB(i) += P(i)(j) * B(j)
      }
    }

    PB
  }

  private def forwardSubstitution(L: Array[Array[Double]], PB: Array[Double]): Array[Double] = {
    val n = L.length
    val Y = Array.ofDim[Double](n)

    for (i <- 0 until n) {
      var sum = 0.0
      for (j <- 0 until i) {
        sum += L(i)(j) * Y(j)
      }
      Y(i) = (PB(i) - sum) / L(i)(i)
    }

    Y
  }

  private def backwardSubstitution(U: Array[Array[Double]], Y: Array[Double]): Array[Double] = {
    val n = U.length
    val X = Array.ofDim[Double](n)

    for (i <- n - 1 to 0 by -1) {
      var sum = 0.0
      for (j <- i + 1 until n) {
        sum += U(i)(j) * X(j)
      }
      X(i) = (Y(i) - sum) / U(i)(i)
    }
    X
  }

}
