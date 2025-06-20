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
			System.out.println("The  thread running the program "+Thread.currentThread().getName());
			System.out.println("Slow method running...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notAnnotatedMethod() {
		System.out.println("Not annotated method running...");
	}
}

