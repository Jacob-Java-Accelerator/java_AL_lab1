package week1.reflectionsXAnnotations.logger;

import java.lang.reflect.Proxy;

/**
 * This is the proxy factory class
 * create a dynamic proxy of the input class and add the LogExecutionTimeHandler
 * invocation class handler
 **/
public class ProxyFactory {
  @SuppressWarnings("unchecked")
  public static <T> T createProxy(T target, Class<?> interfaceType) {
    return (T) Proxy.newProxyInstance(
        interfaceType.getClassLoader(),
        new Class<?>[] { interfaceType },
        new LogExecutionTimeHandler(target));
  }

}
