package week1.reflectionsXAnnotations.logger;

interface LogInterface {
  @LogExecutionTime
  void tryLogger() throws InterruptedException;
}

class LogInterfaceClass implements LogInterface {

  public LogInterfaceClass() {
  }

  @Override
  @LogExecutionTime
  public void tryLogger() throws InterruptedException {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    System.out.println("Service method executed");
  }

}

public class LogExecutionTimeTestMain {

  public static void main(String[] args) throws InterruptedException {
    LogInterface logInterfaceClass = new LogInterfaceClass();

    // creating the proxy for the close to add logger invocations
    LogInterface proxy = ProxyFactory.createProxy(logInterfaceClass,
        LogInterface.class);

    proxy.tryLogger();
    System.out.println("test");
  }

}
