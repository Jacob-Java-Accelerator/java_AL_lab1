package week1.reflectionsXAnnotations.practice.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Main {
	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
		User user= new User();
		var fields=user.getClass().getDeclaredFields();

	for(Field field:fields) {
		System.out.println(field.getName());
		if(field.getName().equals("name")) {
			field.setAccessible(true);
			field.set(user, "Jacob");
			System.out.println(field.get(user));
		}
	}

	 var methods=user.getClass().getDeclaredMethods();
	for(var method:methods) {
		if(method.getName().equals("hello")) {
			method.setAccessible(true);
			method.invoke(user);
		};
	}
	}
}
