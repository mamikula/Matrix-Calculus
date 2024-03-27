%% #studia  %%
parent:: [[Rachunek macierzowy i statystyka wielowymiarowa]]

# Algorytm eliminacji Gaussa
Kod algorytmu eliminacji Gaussa składa się z dwóch składowych
1. Normalizacja macierzy
2. Rozwiązanie równania

Poniżej przedstawiamy kod dla wymienionych procedur:
#### Normalizacja macierzy
```scala
  def normalizeMatrix(A: Array[Array[Double]], B: Array[Double]): Unit = {
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
```
#### Rozwiązanie równiania
```scala
  def solveEquations(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
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

```

### Porównanie wyników z MATLABem
todo

# Algorytm eliminacji Gaussa z pivotingiem
Kod algorytmu eliminacji Gaussa z pivotingiem różni się od powyższego w nieznacznym stopniu. Algorytm również sprowadza się do normalizacji oraz rozwiązania równiania.
#### Normalizacja
```scala
  def normalizeMatrix(A: Array[Array[Double]], B: Array[Double]): Unit = {
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
```

#### Rozwiązanie równania
```scala
  override def solveEquations(A: Array[Array[Double]], B: Array[Double]): Array[Double] = {
    val n = B.length
    val X = Array.ofDim[Double](n)
    for (i <- n - 1 to 0 by -1) {
      var sum = 0.0
      for (j <- i + 1 until n)
        sum += A(i)(j) * X(j)
      X(i) = (B(i) - sum) / A(i)(i)
    }
    return X
  }
```

# Faktoryzacja LU
Faktoryzacja LU polega na rozwiązaniu równania o postaci:
$$ A \cdot x = B$$
Za pomocą rozdzielenia macierzy $A$ na iloczyn dwóch macierzy: 
- trójkątnej dolnej - $L$
- trójkątnej górnej $U$
Pozwala to zapisać problem w postaci:
$$ L \cdot U \cdot x = B$$
Tę postać można zapisać jako układ równań:
$$
\begin{equation}
	\begin{cases}
		L \cdot z = y\\
		U \cdot x = z
	\end{cases}
\end{equation}
$$

Algorytm zrealizowaliśmy następującymi procedurami:
- dekompozycja macierzy A na macierze L oraz U
- rozwiazanie układu równań w uproszczonej postaci

Kod opisanych procedur wygląda następująco:
#### Dekompozycja
```scala
  def decomposeLU(A: Array[Array[Double]]): (Array[Array[Double]], Array[Array[Double]]) = {
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
    return (L, U)
  }
```

#### Rozwiązanie układu równań
```scala
  def solveLU(L: Array[Array[Double]], U: Array[Array[Double]], B: Array[Double]): Array[Double] = {
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
    return X
  }
}

```