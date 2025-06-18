package week1.reflectionsXAnnotations.logger;

import java.lang.reflect.*;

public class LogExecutionTimeProxy implements InvocationHandler {
	private final Object target;

	public LogExecutionTimeProxy(Object target) {
		this.target = target;
	}

	public static Object createProxy(Object target) {
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new LogExecutionTimeProxy(target)
		);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

		if (realMethod.isAnnotationPresent(LogExecutionTime.class)) {
			long start = System.nanoTime();

			Object result = method.invoke(target, args);

			long end = System.nanoTime();
			System.out.println(method.getName() + " executed in " + (end - start) / 1_000_000 + " ms");

			return result;
		}

		return method.invoke(target, args);
	}
}

