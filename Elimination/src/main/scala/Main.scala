package lab.gaussian.elimination


object Main extends App {
    val A: Array[Array[Double]] = Array(
        Array(1.0, 7.0, 3.0, 5.0, 6.0, 2.0, 8.0, 5.0, 5.0),
        Array(4.0, 2.0, 9.0, 8.0, 1.0, 5.0, 8.0, 2.0, 2.0),
        Array(2.0, 3.0, 3.0, 2.0, 6.0, 4.0, 3.0, 7.0, 7.0),
        Array(9.0, 3.0, 4.0, 4.0, 4.0, 9.0, 5.0, 3.0, 4.0),
        Array(6.0, 6.0, 6.0, 5.0, 9.0, 3.0, 5.0, 7.0, 9.0),
        Array(3.0, 5.0, 4.0, 5.0, 5.0, 7.0, 5.0, 3.0, 4.0),
        Array(9.0, 4.0, 6.0, 8.0, 9.0, 7.0, 2.0, 7.0, 5.0),
        Array(8.0, 6.0, 9.0, 8.0, 4.0, 5.0, 5.0, 8.0, 7.0),
        Array(2.0, 5.0, 8.0, 9.0, 4.0, 6.0, 5.0, 6.0, 9.0)
    )

        val B = Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
//    val A: Array[Array[Double]] = Array(
//        Array(1.0, 4.0, 1.0),
//        Array(1.0, 1.0, 3.0),
//        Array(2.0, -2.0, 1.0)
//    )
//    val B = Array(1.0, 1.0, 1.0)

    val X = GaussElimination.solve(A.map(_.clone()), B.clone())
    val Y = GaussEliminationPivoting.solve(A.map(_.clone()), B.clone())
    val Z = LUDecomposition.solve(A.map(_.clone()), B.clone())
    val Q = LUDecompositionPivoting.solve(A.map(_.clone()), B.clone())
    println("Rozwiązanie:")
    X.foreach(println)
    println("Rozwiązanie:")
    Y.foreach(println)
    println("Rozwiązanie:")
    Z.foreach(println)
    println("Rozwiązanie:")
    Q.foreach(println)
}
