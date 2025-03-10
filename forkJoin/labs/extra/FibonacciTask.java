package week1.forkJoin.labs.extra;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {
  private final int n;

  public FibonacciTask(int n) {
    this.n = n;
  }

  @Override
  protected Integer compute() {
    if (n <= 1)
      return n;

    FibonacciTask f1 = new FibonacciTask(n - 1);
    f1.fork();
    FibonacciTask f2 = new FibonacciTask(n - 2);
    Integer result = f2.compute();
    return result + f1.join();

  }

  public static void main(String[] args) {
    ForkJoinPool pool = ForkJoinPool.commonPool();

    FibonacciTask task = new FibonacciTask(20);
    Integer result = pool.invoke(task);
    System.out.println(result);

  }

}
