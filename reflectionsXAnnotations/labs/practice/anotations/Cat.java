package week1.reflectionsXAnnotations.practice.anotations;

@VeryImportant
public class Cat {

	@ImportantString
	String name;
	int age;


	public Cat(String name, int age) {
		this.name=name;
		this.age=age;
	}


	@RunImmediately(times=2)
	public void hello() {
		System.out.println("hello");
	}
	public void goodbye() {
		System.out.println("goodbye");
	}
}
