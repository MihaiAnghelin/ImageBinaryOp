package packWork;

import java.io.FileOutputStream;
import java.io.IOException;

public class BmpImage {

    private byte[] imageBuffer;

    public BmpImage() {
        imageBuffer = new byte[0];
    }

    public BmpImage(byte[] imageBuffer) {
        this.imageBuffer = imageBuffer;
    }

    public byte[] getImageBuffer() {
        return imageBuffer;
    }

    public void setImageBuffer(byte[] imageBuffer) {
        this.imageBuffer = imageBuffer;
    }

    protected void writeImageToPath(String filePath, byte[] imageBuffer) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected byte[] getHeader(byte[] imageBuffer) {
        byte[] header = new byte[54];
        System.arraycopy(imageBuffer, 0, header, 0, header.length);
        return header;
    }

    protected byte[] getImageBufferWithoutHeader(byte[] imageBuffer) {
        int headerSize = 54;
        byte[] imageBufferCopy = new byte[imageBuffer.length - headerSize];
        System.arraycopy(imageBuffer, headerSize, imageBufferCopy, 0, imageBufferCopy.length);
        return imageBufferCopy;
    }

    protected int getWith(byte[] imageBuffer) {
        return (imageBuffer[21] & 0xFF) << 24 | (imageBuffer[20] & 0xFF) << 16 | (imageBuffer[19] & 0xFF) << 8 | (imageBuffer[18] & 0xFF);
    }

    protected int getHeight(byte[] imageBuffer) {
        return (imageBuffer[25] & 0xFF) << 24 | (imageBuffer[24] & 0xFF) << 16 | (imageBuffer[23] & 0xFF) << 8 | (imageBuffer[22] & 0xFF);
    }
}
