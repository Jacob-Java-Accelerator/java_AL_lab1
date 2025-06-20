package week1.reflectionsXAnnotations.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomClassLoader extends ClassLoader {
  private final String path;

  public CustomClassLoader(String path) {
    this.path = path;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    String classFilePath = path + "/" + name.replace('.', '/') + ".class";
    try {
      byte[] classBytes = Files.readAllBytes(Path.of(classFilePath));
      return defineClass(name, classBytes, 0, classBytes.length);
    } catch (IOException e) {
      throw new ClassNotFoundException("Could not load class: " + name, e);
    }
  }
}
