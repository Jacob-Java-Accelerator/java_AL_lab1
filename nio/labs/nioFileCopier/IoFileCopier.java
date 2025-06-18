package week1.nio.labs.nioFileCopier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class IoFileCopier {
	static void copyFile(String from, String to) throws IOException {
		System.out.println("🔒 [IO-BLOCKING] Starting traditional I/O copy...");
		System.out.println("🔒 [IO-BLOCKING] Thread: " + Thread.currentThread().getName());

		Path pathFrom = Paths.get(from);
		Path pathTo = Paths.get(to);

		long fileSize = Files.size(pathFrom);
		long totalBytesCopied = 0;

		try (InputStream in = new BufferedInputStream(new FileInputStream(pathFrom.toFile()));
			 OutputStream out = new BufferedOutputStream(new FileOutputStream(pathTo.toFile()))) {

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				System.out.println("🔒 [IO-BLOCKING] Main thread BLOCKED on read/write operation");

				// Add small delay to make blocking more visible
				try { Thread.sleep(5); } catch (InterruptedException e) {}

				out.write(buffer, 0, bytesRead);
				totalBytesCopied += bytesRead;

				if (totalBytesCopied % (1024 * 1024) < 1024) {
					System.out.printf("🔒 [IO-BLOCKING] Copied %d bytes (%.2f%%)\n", totalBytesCopied,
							(double) totalBytesCopied / fileSize * 100);
				}
			}

			System.out.printf("🔒 [IO-BLOCKING] ✅ Copy complete: %d bytes (100%%)\n", totalBytesCopied);
		}
	}
}
