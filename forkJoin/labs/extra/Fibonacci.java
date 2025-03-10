package week1.forkJoin.labs.extra;

public class Fibonacci {

  static int fib(int n) {
    if (n <= 1)
      return n;
    return fib(n - 1) + fib(n - 2);
  }

  public static void main(String[] args) {
    System.out.println("processing...");
    System.out.println(fib(20));
  }
}
