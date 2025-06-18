package week1.nio.labs.nioWrite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;


public class NioWrite {

  static void writeToNioFile(String data) throws IOException {
    Path path = Paths.get("labs/nioWrite/open.txt");

    try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE,
        StandardOpenOption.TRUNCATE_EXISTING)) {

      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

      ByteBuffer buffer = ByteBuffer.wrap(dataBytes);

      while (buffer.hasRemaining()) {
        fileChannel.write(buffer);
      }
      System.out.println(Arrays.toString(buffer.array()));
      buffer.clear();
    }
  }



}
