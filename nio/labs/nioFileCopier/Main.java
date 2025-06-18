package week1.nio.labs.nioFileCopier;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		String source = "labs/nioFileCopier/open.txt";
		String nioTarget = "labs/nioFileCopier/hello_nio.txt";
		String ioTarget = "labs/nioFileCopier/hello_io.txt";

		createTestFileIfNeeded(source);

		// Variables to store timing results
		double ioTime = 0;
		double nioTime = 0;
		long fileSize = Files.size(Paths.get(source));

		ThreadMonitor monitor = new ThreadMonitor();


		System.out.println("BLOCKING I/O DEMONSTRATION");

		monitor.startMonitoring("BLOCKING-IO");
		long startIo = System.nanoTime();
		IoFileCopier.copyFile(source, ioTarget);
		long endIo = System.nanoTime();
		monitor.stopMonitoring();

		ioTime = (endIo - startIo) / 1_000_000.0;
		System.out.printf("⏱️ Traditional I/O took %.2f ms\n\n", ioTime);

		Thread.sleep(500); // Brief pause


		System.out.println("NON-BLOCKING ASYNC NIO DEMONSTRATION");


		monitor = new ThreadMonitor(); // Create new monitor
		monitor.startMonitoring("Non-blocking NIO");

		long startAsync = System.nanoTime();
		NioFileCopier.copyFile(source, nioTarget);
		long endAsync = System.nanoTime();

		nioTime = (endAsync - startAsync) / 1_000_000.0;
		System.out.printf("⏱️ Asynchronous NIO took %.2f ms\n\n", nioTime);

		monitor.stopMonitoring();

		// Enhanced summary with timing comparison

		System.out.println("PERFORMANCE SUMMARY");

		System.out.printf("📁 File size: %.2f MB\n", fileSize / 1_000_000.0);
		System.out.println();
		System.out.printf("⏱️  Blocking I/O:     %.2f ms\n", ioTime);
		System.out.printf("⏱️  Non-blocking NIO: %.2f ms\n", nioTime);
		System.out.println();

		// Calculate and show performance difference
		if (ioTime > nioTime) {
			double improvement = ((ioTime - nioTime) / ioTime) * 100;
			System.out.printf("🚀 NIO was %.2f ms faster (%.1f%% improvement)\n",
					ioTime - nioTime, improvement);
		} else if (nioTime > ioTime) {
			double slower = ((nioTime - ioTime) / ioTime) * 100;
			System.out.printf("📊 NIO was %.2f ms slower (%.1f%% slower)\n",
					nioTime - ioTime, slower);
		} else {
			System.out.println("📊 Both methods took similar time");
		}

		// Calculate throughput
		double ioThroughput = (fileSize / 1_000_000.0) / (ioTime / 1000.0); // MB/s
		double nioThroughput = (fileSize / 1_000_000.0) / (nioTime / 1000.0); // MB/s

		System.out.println();
		System.out.printf("📈 Blocking I/O throughput:     %.2f MB/s\n", ioThroughput);
		System.out.printf("📈 Non-blocking NIO throughput: %.2f MB/s\n", nioThroughput);

		System.out.println();

		System.out.println("BEHAVIOR SUMMARY");

		System.out.println("🔒 Blocking I/O: Main thread waits for each read/write operation");
		System.out.println("🔒 Blocking NIO: Main thread still waits (synchronous channels)");
		System.out.println("🚀 Non-blocking NIO: Main thread is free to do other work!");

	}

	private static void createTestFileIfNeeded(String filename) throws IOException {
		Path path = Paths.get(filename);
		if (!Files.exists(path)) {
			Files.createDirectories(path.getParent());

			System.out.println("📝 Creating 10MB test file (this may take a moment)...");

			try (FileOutputStream fos = new FileOutputStream(path.toFile());
				 BufferedOutputStream bos = new BufferedOutputStream(fos)) {

				byte[] chunk = new byte[8192]; // 8KB chunks
				for (int i = 0; i < chunk.length; i++) {
					chunk[i] = (byte) ('A' + (i % 26)); // Fill with A-Z
				}

				// 1280 * 8192 = 10,485,760 bytes ≈ 10MB
				for (int i = 0; i < 1280; i++) {
					bos.write(chunk);
					if (i % 256 == 0) {
						System.out.printf("📝 Writing... %.1f MB written\n", (i * 8192) / 1_000_000.0);
					}
				}
			}

			long fileSize = Files.size(path);
			System.out.printf("📝 ✅ Created test file: %s (%.2f MB)\n",
					filename, fileSize / 1_000_000.0);
		} else {
			long fileSize = Files.size(path);
			System.out.printf("📝 Using existing test file: %s (%.2f MB)\n",
					filename, fileSize / 1_000_000.0);
		}
	}
}
