package week1.reflectionsXAnnotations.practice.anotations;

import week1.reflectionsXAnnotations.practice.reflections.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RunImmediately {
	int times() default 1;
	int[] array() default {1,2,3};
	String name() default "Jacob";
}
