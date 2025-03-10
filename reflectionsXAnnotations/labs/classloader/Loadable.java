package week1.reflectionsXAnnotations.classloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// i am creating this annotation  to put on the class to use reflection to ensure it is to be loaded
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Loadable {

}
