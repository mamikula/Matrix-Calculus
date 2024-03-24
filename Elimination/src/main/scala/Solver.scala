package lab.gaussian.elimination

trait Solver {

  def solve(A: Array[Array[Double]], B: Array[Double]) : Array[Double];

  def printMatrix(matrix: Array[Array[Double]]): Unit = {
    for (row <- matrix) {
      for (elem <- row)
        print(f"$elem%1.2f\t")
      println()
    }
    println()
  }

}
