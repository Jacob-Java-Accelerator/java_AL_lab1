package week1.reflectionsXAnnotations.practice.anotations;

import java.lang.reflect.InvocationTargetException;

public class Main {
	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
		Cat cat=new Cat("Jacob", 1);
//	   if(cat.getClass().isAnnotationPresent(VeryImportant.class)){
//		   System.out.println("very important");
//	   }
//	   else {
//		   System.out.println("not very important");
//	   }

	   var methods=cat.getClass().getDeclaredMethods();
	   for(var method:methods) {
		   if(method.isAnnotationPresent(RunImmediately.class)) {
			   var annotation=method.getAnnotation(RunImmediately.class);
			   for(int i=0;i<annotation.times();i++)
			    method.invoke(cat);
		   }
		   else {
			   System.out.println("not run immediately");
		   }
	   }

	   var fields=cat.getClass().getDeclaredFields();
	   for(var field:fields) {
		   if(field.isAnnotationPresent(ImportantString.class)) {
			   field.setAccessible(true);
			  Object object= field.get(cat);
			  if(object instanceof String stringValue)
			   System.out.println(stringValue.toUpperCase());
		   }
	   }

	}
}
