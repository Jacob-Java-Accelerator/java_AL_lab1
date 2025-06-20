package week1.reflectionsXAnnotations.logger;

public class Main {
	public static void main(String[] args) {
		MyService service = (MyService) LogExecutionTimeProxy.createProxy(new MyServiceImpl());

		service.fastMethod();
		service.slowMethod();
		service.notAnnotatedMethod();
	}
}
