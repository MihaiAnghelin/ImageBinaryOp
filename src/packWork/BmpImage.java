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

    protected int getWidth(byte[] imageBuffer) {
        return (imageBuffer[21] & 0xFF) << 24 | (imageBuffer[20] & 0xFF) << 16 | (imageBuffer[19] & 0xFF) << 8 | (imageBuffer[18] & 0xFF);
    }

    protected int getHeight(byte[] imageBuffer) {
        return (imageBuffer[25] & 0xFF) << 24 | (imageBuffer[24] & 0xFF) << 16 | (imageBuffer[23] & 0xFF) << 8 | (imageBuffer[22] & 0xFF);
    }

    protected int getPixel(byte[] imageBuffer, int x, int y) {
        int width = getWidth(imageBuffer);
        int height = getHeight(imageBuffer);
        int offset = 54;
        int padding = 0;
        int pixel = 0;
        if (width % 4 != 0) {
            padding = 4 - (3 * width) % 4;
        }
        int position = offset + (width * 3 + padding) * y + x * 3;
        pixel = (imageBuffer[position + 2] & 0xFF) << 16 | (imageBuffer[position + 1] & 0xFF) << 8 | (imageBuffer[position] & 0xFF);
        return pixel;
    }

    protected String getColorHexFromPixel(int pixel) {
        String colorHex = Integer.toHexString(pixel);
        while (colorHex.length() < 6) {
            colorHex = "0" + colorHex;
        }
        return colorHex;
    }

    protected int getRed(int pixel) {
        return (pixel >> 16) & 0xFF;
    }

    protected int getGreen(int pixel) {
        return (pixel >> 8) & 0xFF;
    }

    protected int getBlue(int pixel) {
        return pixel & 0xFF;
    }

    protected byte[] setPixel(byte[] imageBuffer, int x, int y, int pixel) {
        int width = getWidth(imageBuffer);
        int height = getHeight(imageBuffer);
        int offset = 54;
        int padding = 0;
        if (width % 4 != 0) {
            padding = 4 - (3 * width) % 4;
        }
        int position = offset + (width * 3 + padding) * y + x * 3;
        imageBuffer[position + 2] = (byte) getRed(pixel);
        imageBuffer[position + 1] = (byte) getGreen(pixel);
        imageBuffer[position] = (byte) getBlue(pixel);

        return imageBuffer;
    }

    protected byte[] setHeight(byte[] imageBuffer, int height) {
        imageBuffer[25] = (byte) ((height >> 24) & 0xFF);
        imageBuffer[24] = (byte) ((height >> 16) & 0xFF);
        imageBuffer[23] = (byte) ((height >> 8) & 0xFF);
        imageBuffer[22] = (byte) (height & 0xFF);
        return imageBuffer;
    }

    protected byte[] setWidth(byte[] imageBuffer, int width) {
        imageBuffer[21] = (byte) ((width >> 24) & 0xFF);
        imageBuffer[20] = (byte) ((width >> 16) & 0xFF);
        imageBuffer[19] = (byte) ((width >> 8) & 0xFF);
        imageBuffer[18] = (byte) (width & 0xFF);
        return imageBuffer;
    }

    protected byte[] getImageBufferWithHeader(byte[] imageBuffer, byte[] header) {
        byte[] imageBufferWithHeader = new byte[imageBuffer.length + header.length];
        System.arraycopy(header, 0, imageBufferWithHeader, 0, header.length);
        System.arraycopy(imageBuffer, 0, imageBufferWithHeader, header.length, imageBuffer.length);
        return imageBufferWithHeader;
    }
}
