package packWork;

import java.io.*;

import static java.lang.Thread.sleep;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final String fileName1;
    private final String fileName2;

    public Producer(Buffer buffer, String fileName1, String fileName2) {
        this.buffer = buffer;
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;
    }

    public void run() {
        fileRead(fileName1);
        fileRead(fileName2);
    }

    private void fileRead(String fileName) {
        File file = new File(fileName);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            int bufferSize = (int) randomAccessFile.length() / 4;

            for (int i = 0; i < 4; i++) {
                byte[] byteBuffer = new byte[bufferSize];

                randomAccessFile.readFully(byteBuffer);

                buffer.put(byteBuffer);

                System.out.println("Producatorul a pus:\t" + (i + 1) + "/" + 4 + " din " + fileName);
                try {
                    sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
