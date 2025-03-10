package week1.forkJoin.labs.matrixMultiplier;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class MatrixMultiplier extends RecursiveTask<MatrixMultiplier.Matrix> {

  static class Matrix {
    private final int rows;
    private final int cols;
    private final int[][] data;

    public Matrix(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      data = new int[rows][cols];
    }

    public Matrix(int rows, int cols, int[][] data) {
      this.data = data;
      this.cols = cols;
      this.rows = rows;
    }
  }

  private final Matrix a;
  private final Matrix b;
  private final int threshold;

  public MatrixMultiplier(Matrix a, Matrix b, int threshold) {
    this.a = a;
    this.b = b;
    this.threshold = threshold;
  }

  @Override
  protected Matrix compute() {
    if (a.rows < threshold || a.cols < threshold) {
      return multiply(a, b);
    }

    int halfRows = a.rows / 2;
    int halfCols = a.cols / 2;

    Matrix suba11 = getSubMatrix(a, 0, 0, halfRows, halfCols);
    Matrix suba12 = getSubMatrix(a, 0, halfCols, halfRows, a.cols - halfCols);
    Matrix suba21 = getSubMatrix(a, halfRows, 0, a.rows - halfRows, halfCols);
    Matrix suba22 = getSubMatrix(a, halfRows, halfCols, a.rows - halfRows, a.cols - halfCols);

    Matrix subb11 = getSubMatrix(b, 0, 0, halfRows, halfCols);
    Matrix subb12 = getSubMatrix(b, 0, halfCols, halfRows, b.cols - halfCols);
    Matrix subb21 = getSubMatrix(b, halfRows, 0, b.rows - halfRows, halfCols);
    Matrix subb22 = getSubMatrix(b, halfRows, halfCols, b.rows - halfRows, b.cols - halfCols);

    MatrixMultiplier task1 = new MatrixMultiplier(suba11, subb11, threshold);
    MatrixMultiplier task2 = new MatrixMultiplier(suba12, subb21, threshold);
    MatrixMultiplier task3 = new MatrixMultiplier(suba21, subb12, threshold);
    MatrixMultiplier task4 = new MatrixMultiplier(suba22, subb22, threshold);

    task1.fork();
    task2.fork();
    task3.fork();
    task4.fork();

    Matrix C11 = task1.join();
    Matrix C12 = task2.join();
    Matrix C21 = task3.join();
    Matrix C22 = task4.join();

    return combineSubMatrices(C11, C12, C21, C22);
  }

  private Matrix combineSubMatrices(Matrix C11, Matrix C12, Matrix C21, Matrix C22) {
    int rows = C11.rows;
    int cols = C22.cols;
    Matrix c = new Matrix(rows, cols);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        c.data[i][j] = C11.data[i][j] + C22.data[i][j] - C12.data[i][j] - C21.data[i][j];
      }
    }

    return c;
  }

  private Matrix getSubMatrix(Matrix A, int startRow, int startCol, int rows, int cols) {
    int[][] subData = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(A.data[startRow + i], startCol, subData[i], 0, cols);
    }

    return new Matrix(rows, cols, subData);
  }

  private Matrix multiply(Matrix a2, Matrix b2) {
    int rows = a.rows;
    int cols = b.cols;
    Matrix c = new Matrix(rows, cols);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        for (int k = 0; k < a.cols; k++) {
          c.data[i][j] += b.data[i][k] * b.data[k][j];
        }
      }
    }

    return c;
  }

  public static void main(String[] args) {
    int[][] Adata = { { 1, 2, 3 }, { 4, 5, 6 } };
    int[][] Bdata = { { 7, 8 }, { 9, 10 }, { 11, 12 } };

    Matrix A = new MatrixMultiplier.Matrix(Adata.length - 1, Adata[0].length - 1, Adata);
    Matrix B = new MatrixMultiplier.Matrix(Bdata.length - 1, Bdata[0].length - 1, Bdata);

    int threshold = 4;

    try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
      MatrixMultiplier task = new MatrixMultiplier(A, B, threshold);
      Matrix C = pool.invoke(task);

      System.out.println("Resultant Matrix:");
      for (int i = 0; i < C.rows; i++) {
        for (int j = 0; j < C.cols; j++) {
          System.out.print(C.data[i][j] + " ");
        }
        System.out.println();
      }
    }
  }
}
