package week1.reflectionsXAnnotations.classloader;

@Loadable
public class LoadableClass {

  public LoadableClass() {
  }

  public void load() {
    System.out.println("this method has been loaded");
  }
}
