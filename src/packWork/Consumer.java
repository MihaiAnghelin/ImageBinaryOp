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
        imageBuffer2 = addToImageBuffer(imageBuffer2);

        byte[] imageBufferAnd = processImages(imageBuffer1, imageBuffer2, Operation.AND);
        writeImageToPath("images/outputAnd.bmp", imageBufferAnd);

        byte[] imageBufferOr = processImages(imageBuffer1, imageBuffer2, Operation.OR);
        writeImageToPath("images/outputOr.bmp", imageBufferOr);

        byte[] imageBufferXor = processImages(imageBuffer1, imageBuffer2, Operation.XOR);
        writeImageToPath("images/outputXor.bmp", imageBufferXor);
    }

    private byte[] addToImageBuffer(byte[] imageBuffer) {
        for (int i = 0; i < 4; i++) {
            byte[] bytes = buffer.get();

            int oldLength = imageBuffer.length;

            imageBuffer = Arrays.copyOf(imageBuffer, oldLength + bytes.length);
            System.arraycopy(bytes, 0, imageBuffer, oldLength, bytes.length);

            System.out.println("Consumatorul a luat:\t" + (i + 1) + " din " + 4);
        }

        byte[] bytes = buffer.get();
        if (bytes.length != 0) {
            int oldLength = imageBuffer.length;

            imageBuffer = Arrays.copyOf(imageBuffer, oldLength + bytes.length);
            System.arraycopy(bytes, 0, imageBuffer, oldLength, bytes.length);

            System.out.println("Consumatorul a luat restul de biti %4");
        }

        return imageBuffer;
    }
}

