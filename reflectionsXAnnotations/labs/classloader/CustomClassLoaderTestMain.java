package week1.reflectionsXAnnotations.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * Using reflections with my CustomClassLoader to laod the loadable class
 * instance, check whether it has the laodable
 * annotation if it does invoce the constructor to create an instance of the
 * class and invoke a method call of the (load) method
 * on that instance
 * 
 **/
public class CustomClassLoaderTestMain {

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    String currentdir = System.getProperty("user.dir");
    String absolutePath = new File(currentdir) + "/out";
    System.out.println(absolutePath);

    CustomClassLoader customClassLoader = new CustomClassLoader(absolutePath);
   var clazz = customClassLoader.loadClass("week1.reflectionsXAnnotations.classloader.LoadableClass");

    if (clazz.isAnnotationPresent(Loadable.class)) {
      System.out.println("Class is loadable...");
      Object instance = clazz.getDeclaredConstructor().newInstance();
      Method method = clazz.getMethod("load");
      method.invoke(instance);
    }
    System.out.println(Integer.MAX_VALUE);

  }
}
