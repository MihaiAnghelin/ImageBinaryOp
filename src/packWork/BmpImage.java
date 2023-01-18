package packWork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BmpImage implements IBmpImage {

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

    public void writeImageToPath(String filePath, byte[] imageBuffer) { // scrie imaginea de tip byte[] in fisierul specificat
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImageFromBytes(byte[] imageBuffer) { // transforma byte[] in BufferedImage
        try {
            return ImageIO.read(new ByteArrayInputStream(imageBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeImageToPath(BufferedImage image, String filePath) { // scrie imaginea de tip BufferedImage in fisierul specificat
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ImageIO.write(image, "bmp", fos);
            System.out.println("Imaginea a fost salvata la: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getHeader(byte[] imageBuffer) { // extrage header-ul din imagine
        byte[] header = new byte[54];
        System.arraycopy(imageBuffer, 0, header, 0, header.length);
        return header;
    }

    public byte[] getImageBufferWithoutHeader(byte[] imageBuffer) { // extrage restul de bytes din imagine fara header
        int headerSize = 54;
        byte[] imageBufferCopy = new byte[imageBuffer.length - headerSize];
        System.arraycopy(imageBuffer, headerSize, imageBufferCopy, 0, imageBufferCopy.length);
        return imageBufferCopy;
    }

    public int getWidth(byte[] imageBuffer) { // extrage latimea imaginii
        return (imageBuffer[21] & 0xFF) << 24 | (imageBuffer[20] & 0xFF) << 16 | (imageBuffer[19] & 0xFF) << 8 | (imageBuffer[18] & 0xFF);
    }

    public int getHeight(byte[] imageBuffer) { // extrage inaltimea imaginii
        return (imageBuffer[25] & 0xFF) << 24 | (imageBuffer[24] & 0xFF) << 16 | (imageBuffer[23] & 0xFF) << 8 | (imageBuffer[22] & 0xFF);
    }

    public int getPixel(byte[] imageBuffer, int x, int y) { // extrage pixelul de pe pozitia (x, y)
        int width = getWidth(imageBuffer);
        int height = getHeight(imageBuffer);
        int offset = 54; // offsetul de la inceputul imaginii pana la inceputul datelor
        int padding = 0; // padding-ul de la sfarsitul unei linii pana la inceputul urmatoarei
        int pixel = 0;
        if (width % 4 != 0) {
            padding = 4 - (3 * width) % 4;
        }
        int position = offset + (width * 3 + padding) * y + x * 3; // pozitia pixelului de pe pozitia (x, y)
        pixel = (imageBuffer[position + 2] & 0xFF) << 16 | (imageBuffer[position + 1] & 0xFF) << 8 | (imageBuffer[position] & 0xFF); // pixelul de pe pozitia (x, y)
        return pixel;
    }

    public String getColorHexFromPixel(int pixel) { // transforma pixelul in hex
        String colorHex = Integer.toHexString(pixel); // transforma pixelul in hex
        while (colorHex.length() < 6) { // adauga 0 in fata hex-ului pana cand are 6 caractere
            colorHex = "0" + colorHex;
        }
        return colorHex;
    }

    public int getRed(int pixel) { // extrage componenta rosie din pixel
        return (pixel >> 16) & 0xFF;
    }

    public int getGreen(int pixel) { // extrage componenta verde din pixel
        return (pixel >> 8) & 0xFF;
    }

    public int getBlue(int pixel) { // extrage componenta albastra din pixel
        return pixel & 0xFF;
    }

    public byte[] setPixel(byte[] imageBuffer, int x, int y, int pixel) { // seteaza pixelul de pe pozitia (x, y)
        int width = getWidth(imageBuffer);
        int height = getHeight(imageBuffer);
        int offset = 54; // offsetul de la inceputul imaginii pana la inceputul datelor
        int padding = 0; // padding-ul de la sfarsitul unei linii pana la inceputul urmatoarei
        if (width % 4 != 0) {
            padding = 4 - (3 * width) % 4;
        }
        int position = offset + (width * 3 + padding) * y + x * 3; // pozitia pixelului de pe pozitia (x, y)
        imageBuffer[position + 2] = (byte) getRed(pixel); // seteaza componenta rosie
        imageBuffer[position + 1] = (byte) getGreen(pixel); // seteaza componenta verde
        imageBuffer[position] = (byte) getBlue(pixel); // seteaza componenta albastra

        return imageBuffer;
    }

    public byte[] setHeight(byte[] imageBuffer, int height) { // seteaza inaltimea imaginii
        imageBuffer[25] = (byte) ((height >> 24) & 0xFF);
        imageBuffer[24] = (byte) ((height >> 16) & 0xFF);
        imageBuffer[23] = (byte) ((height >> 8) & 0xFF);
        imageBuffer[22] = (byte) (height & 0xFF);
        return imageBuffer;
    }

    public byte[] setWidth(byte[] imageBuffer, int width) { // seteaza latimea imaginii
        imageBuffer[21] = (byte) ((width >> 24) & 0xFF);
        imageBuffer[20] = (byte) ((width >> 16) & 0xFF);
        imageBuffer[19] = (byte) ((width >> 8) & 0xFF);
        imageBuffer[18] = (byte) (width & 0xFF);
        return imageBuffer;
    }

    public byte[] getImageBufferWithHeader(byte[] imageBuffer, byte[] header) { // returneaza imaginea cu header
        byte[] imageBufferWithHeader = new byte[imageBuffer.length + header.length];
        System.arraycopy(header, 0, imageBufferWithHeader, 0, header.length);
        System.arraycopy(imageBuffer, 0, imageBufferWithHeader, header.length, imageBuffer.length);
        return imageBufferWithHeader;
    }
}
