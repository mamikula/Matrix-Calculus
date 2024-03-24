package lab.gaussian.elimination

trait GaussEliminationSolver extends Solver {

  def solveEquations(A: Array[Array[Double]], B: Array[Double]): Array[Double]

  def normalizeMatrix(A: Array[Array[Double]], B: Array[Double]): Unit
  def solve(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    normalizeMatrix(A, B)
//    printMatrix(A)
//    println(B.mkString("Array(", ", ", ")"))
    solveEquations(A, B)
  }

}
