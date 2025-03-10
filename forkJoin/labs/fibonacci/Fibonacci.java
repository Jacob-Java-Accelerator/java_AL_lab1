package week1.forkJoin.labs.fibonacci;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



public class Fibonacci extends RecursiveTask<Integer> {

  private final int n;

  public Fibonacci(int n) {
    this.n = n;
  }

  @Override
  protected Integer compute() {
    if (n == 0)
      return n;
    if (n == 1)
      return n;

    Fibonacci left = new Fibonacci(n - 1);
    left.fork();

    Fibonacci right = new Fibonacci(n - 2);
    right.fork();

    return left.join() + right.join();
  }

  public static void main(String[] args) {

    try (ForkJoinPool forkJoinPool = new ForkJoinPool(2)) {
      Fibonacci task = new Fibonacci(20);
      int result = forkJoinPool.invoke(task);

      System.out.println("results is ...");
      System.out.println(result);
    }

  }

}
