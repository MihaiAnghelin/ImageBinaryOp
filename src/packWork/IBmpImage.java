package packWork;


import java.awt.image.BufferedImage;

public interface IBmpImage { // interfata pentru clasa BmpImage
    void writeImageToPath(String filePath, byte[] imageBuffer);

    BufferedImage getImageFromBytes(byte[] imageBuffer);

    void writeImageToPath(BufferedImage image, String filePath);

    byte[] getHeader(byte[] imageBuffer);

    byte[] getImageBufferWithoutHeader(byte[] imageBuffer);

    int getWidth(byte[] imageBuffer);

    int getHeight(byte[] imageBuffer);

    int getPixel(byte[] imageBuffer, int x, int y);

    String getColorHexFromPixel(int pixel);

    int getRed(int pixel);

    int getGreen(int pixel);

    int getBlue(int pixel);

    byte[] setPixel(byte[] imageBuffer, int x, int y, int pixel);

    byte[] setHeight(byte[] imageBuffer, int height);

    byte[] setWidth(byte[] imageBuffer, int width);

    byte[] getImageBufferWithHeader(byte[] imageBuffer, byte[] header);
}
