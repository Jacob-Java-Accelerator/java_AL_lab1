package week1.reflectionsXAnnotations.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * This method is my handler to actually execute the time log for
 * the @LogExecutionTime annotation
 **/
public class LogExecutionTimeHandler implements InvocationHandler {
  private final Object target;

  public LogExecutionTimeHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.isAnnotationPresent(LogExecutionTime.class)) {
      long start = System.currentTimeMillis();
      Object result = method.invoke(target, args);
      long end = System.currentTimeMillis();
      System.out.println("Time taken: " + (end - start) + " ms");
      return result;
    }
    return method.invoke(target, args);
  }
}
