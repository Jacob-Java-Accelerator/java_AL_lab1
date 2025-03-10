package week1.forkJoin.labs.sumTask;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



public class SumTask extends RecursiveTask<Long> {

  private final int THRESHOLD = 1000;
  private final int[] n;

  public SumTask(int[] n) {
    this.n = n;
  }

  @Override
  protected Long compute() {
    if (n.length < THRESHOLD) {

      Long sum = 0L;
      for (int i = 0; i < n.length; i++) {
        sum += n[i];
      }
      return sum;
    } else {
      int[] leftArr = Arrays.copyOfRange(n, 0, n.length / 2);
      int[] rightArr = Arrays.copyOfRange(n, n.length / 2, n.length);

      SumTask left = new SumTask(leftArr);
      left.fork();
      SumTask right = new SumTask(rightArr);
      right.fork();

      return left.join() + right.join();
    }
  }

  public static void main(String[] args) {
    int[] n = new int[50];

    for (int i = 0; i < n.length; i++) {
      n[i] = i;
    }

    try (ForkJoinPool pool = new ForkJoinPool(4)) {

      SumTask task = new SumTask(n);
      long sum = pool.invoke(task);

      System.out.println("sum:");
      System.out.println(sum);
    }

  }

}
