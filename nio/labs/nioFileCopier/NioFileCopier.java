package week1.nio.labs.nioFileCopier;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileCopier {

  static void copyFile(String from, String to) throws IOException {
    Path pathFrom = Paths.get(from);
    Path pathTo = Paths.get(to);

    try (FileChannel fromChannel = FileChannel.open(pathFrom, StandardOpenOption.READ);
        FileChannel toChannel = FileChannel.open(pathTo, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

      ByteBuffer buffer = ByteBuffer.allocate(1024);

      Long size = Files.size(pathFrom);
      int totalBytesCopied = 0;

      while (fromChannel.read(buffer) != -1) {
        buffer.flip();
        toChannel.write(buffer);

        totalBytesCopied += buffer.remaining();

        if (totalBytesCopied % (1024 * 1024) == 0) {
          System.out.printf("Copied %d bytes (%.2f%%)\n", totalBytesCopied,
              (double) totalBytesCopied / size * 100);
        }
        buffer.clear();
      }

    }
  }

  public static void main(String[] args) throws IOException {
    copyFile("open.txt", "hello.txt");
    System.out.println("Done");
  }
}
