package week1.nio.labs.nioWrite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

public class NioWrite {

  static void writeToNioFile(String data) throws IOException, InterruptedException {
    Path path = Paths.get("labs/nioWrite/open.txt");

    ByteBuffer buffer = ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8));

    try (AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(
            path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

      CountDownLatch latch = new CountDownLatch(1);

      asyncChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
          System.out.println("Write completed with " + result + " bytes");
          latch.countDown();
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
          System.err.println("Write failed: " + exc.getMessage());
          latch.countDown();
        }
      });

      latch.await();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    writeToNioFile("This is non-blocking file write using AsynchronousFileChannel.");
  }
}
