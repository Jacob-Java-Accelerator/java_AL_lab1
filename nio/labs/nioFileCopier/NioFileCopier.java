package week1.nio.labs.nioFileCopier;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class NioFileCopier {

  static void copyFile(String from, String to) throws IOException, InterruptedException {
    System.out.println("🚀 [ASYNC-NON-BLOCKING] Starting asynchronous NIO copy...");
    System.out.println("🚀 [ASYNC-NON-BLOCKING] Main thread: " + Thread.currentThread().getName());

    Path pathFrom = Paths.get(from);
    Path pathTo = Paths.get(to);

    long fileSize = Files.size(pathFrom);
    CountDownLatch latch = new CountDownLatch(1);
    AtomicLong totalBytesCopied = new AtomicLong(0);

    // Open asynchronous channels
    AsynchronousFileChannel fromChannel = AsynchronousFileChannel.open(pathFrom, StandardOpenOption.READ);
    AsynchronousFileChannel toChannel = AsynchronousFileChannel.open(pathTo,
            StandardOpenOption.CREATE, StandardOpenOption.WRITE);

    System.out.println("🚀 [ASYNC-NON-BLOCKING] File copy initiated - main thread is now FREE!");

    // Start the async copy
    copyAsyncRecursive(fromChannel, toChannel, 0, fileSize, totalBytesCopied, latch);

    // Main thread can do other work here - NOT BLOCKED!
    System.out.println("🚀 [ASYNC-NON-BLOCKING] Main thread doing other work while copy happens in background...");
    for (int i = 0; i < 10; i++) {
      System.out.println("🚀 [ASYNC-NON-BLOCKING] Main thread working on task " + (i + 1) +
              " - Thread: " + Thread.currentThread().getName());
      Thread.sleep(20); // Simulate other work
    }

    // Wait for copy to complete
    System.out.println("🚀 [ASYNC-NON-BLOCKING] Main thread waiting for copy completion...");
    latch.await();

    fromChannel.close();
    toChannel.close();
    System.out.println("🚀 [ASYNC-NON-BLOCKING] ✅ Async copy completed!");
  }

  private static void copyAsyncRecursive(AsynchronousFileChannel fromChannel,
                                         AsynchronousFileChannel toChannel,
                                         long position, long fileSize,
                                         AtomicLong totalBytesCopied,
                                         CountDownLatch latch) {

    ByteBuffer buffer = ByteBuffer.allocate(1024);

    fromChannel.read(buffer, position, null, new CompletionHandler<Integer, Void>() {
      @Override
      public void completed(Integer bytesRead, Void attachment) {
        if (bytesRead == -1) {
          // End of file reached
          System.out.printf("🚀 [ASYNC-NON-BLOCKING] ✅ Copy complete: %d bytes (100%%)\n",
                  totalBytesCopied.get());
          latch.countDown();
          return;
        }

        System.out.println("🚀 [ASYNC-NON-BLOCKING] Read operation completed on thread: " +
                Thread.currentThread().getName());

        buffer.flip();
        long currentPosition = position;

        toChannel.write(buffer, currentPosition, null, new CompletionHandler<Integer, Void>() {
          @Override
          public void completed(Integer bytesWritten, Void attachment) {
            long newTotal = totalBytesCopied.addAndGet(bytesWritten);

            System.out.println("🚀 [ASYNC-NON-BLOCKING] Write operation completed on thread: " +
                    Thread.currentThread().getName());

            if (newTotal % (1024 * 1024) < 1024) {
              System.out.printf("🚀 [ASYNC-NON-BLOCKING] Copied %d bytes (%.2f%%)\n",
                      newTotal, (double) newTotal / fileSize * 100);
            }

            // Continue with next chunk
            copyAsyncRecursive(fromChannel, toChannel, currentPosition + bytesWritten,
                    fileSize, totalBytesCopied, latch);
          }

          @Override
          public void failed(Throwable exc, Void attachment) {
            System.err.println("🚀 [ASYNC-NON-BLOCKING] Write failed: " + exc.getMessage());
            latch.countDown();
          }
        });
      }

      @Override
      public void failed(Throwable exc, Void attachment) {
        System.err.println("🚀 [ASYNC-NON-BLOCKING] Read failed: " + exc.getMessage());
        latch.countDown();
      }
    });
  }
}

