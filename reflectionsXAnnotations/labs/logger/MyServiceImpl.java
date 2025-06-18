package week1.reflectionsXAnnotations.logger;

public class MyServiceImpl implements MyService {

	@Override
	@LogExecutionTime
	public void fastMethod() {
		System.out.println("Fast method running...");
	}

	@Override
	@LogExecutionTime
	public void slowMethod() {
		try {
			Thread.sleep(500); // Simulate delay
			System.out.println("Slow method running...");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}

