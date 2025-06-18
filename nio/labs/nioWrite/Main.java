package week1.nio.labs.nioWrite;

import java.io.IOException;

import static week1.nio.labs.nioWrite.NioWrite.writeToNioFile;

public class Main {
	public static void main(String[] args) throws IOException {
		writeToNioFile("Hello jacob adiaba");
	}
}
