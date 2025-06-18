package week1.nio.labs.nioFileCopier;

class ThreadMonitor {
	private volatile boolean monitoring = true;

	public void startMonitoring(String phase) {
		Thread monitor = new Thread(() -> {
			int count = 0;
			while (monitoring && count < 15) {
				System.out.println("👁️ [MONITOR] " + phase + " - Checking main thread activity... " +
						Thread.currentThread().getName());
				try { Thread.sleep(100); } catch (InterruptedException e) { break; }
				count++;
			}
		});
		monitor.setDaemon(true);
		monitor.start();
	}

	public void stopMonitoring() {
		monitoring = false;
	}
}
