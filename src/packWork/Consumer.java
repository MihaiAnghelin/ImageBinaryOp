package packWork;

import java.util.Arrays;

public class Consumer extends ImageProcessing implements Runnable {
    private final Buffer buffer;
    byte[] imageBuffer1;
    byte[] imageBuffer2;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;

        imageBuffer1 = new byte[0];
        imageBuffer2 = new byte[0];
    }

    public void run() {
        imageBuffer1 = addToImageBuffer(imageBuffer1);
        writeImageToPath("images/output1.bmp", imageBuffer1);

        imageBuffer2 = addToImageBuffer(imageBuffer2);
        writeImageToPath("images/output2.bmp", imageBuffer2);

    }

    private byte[] addToImageBuffer(byte[] imageBuffer) {
        for (int i = 0; i < 4; i++) {
            byte[] bytes = buffer.get();

            int oldLength = imageBuffer.length;

            imageBuffer = Arrays.copyOf(imageBuffer, oldLength + bytes.length);
            System.arraycopy(bytes, 0, imageBuffer, oldLength, bytes.length);

            System.out.println("Consumatorul a luat:\t" + (i + 1) + " din " + 4);
        }

        return imageBuffer;
    }
}

