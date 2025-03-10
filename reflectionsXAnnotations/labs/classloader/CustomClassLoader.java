package week1.reflectionsXAnnotations.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/*
 * creating my custom class loader to load class from a specific path
 * ***/
public class CustomClassLoader extends ClassLoader {
  private final String path;

  public CustomClassLoader(String path) {
    this.path = path;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] classData = loadClassData(name);
    if (classData == null) {
      throw new ClassNotFoundException("classname not found");
    }

    return defineClass(name, classData, 0, classData.length);
  }

  private byte[] loadClassData(String name) {
    String classFilePath = path + File.separator + name.replace('.', File.separatorChar) + ".class";

    try (FileInputStream fis = new FileInputStream(classFilePath)) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      byte[] buffer = new byte[1024];
      int bytesRead;

      while ((bytesRead = fis.read(buffer)) != -1) {
        baos.write(buffer, 0, bytesRead);
      }
      return baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
