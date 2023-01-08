package packWork;

import java.awt.image.BufferedImage;

public class ImageProcessing {
    private final BufferedImage image1;
    private final BufferedImage image2;

    public ImageProcessing(BufferedImage image1, BufferedImage image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    enum Operation {
        AND,
        OR,
        XOR
    }

    private int getNewRGB(int rgb1, int rgb2, Operation operation) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;

        switch (operation) {
            case AND:
                return (r1 & r2) << 16 | (g1 & g2) << 8 | (b1 & b2);
            case OR:
                return (r1 | r2) << 16 | (g1 | g2) << 8 | (b1 | b2);
            case XOR:
                return (r1 ^ r2) << 16 | (g1 ^ g2) << 8 | (b1 ^ b2);
            default:
                return 0;
        }
    }

    private BufferedImage makeOperation(Operation operation) {
        int width = Math.min(image1.getWidth(), image2.getWidth());
        int height = Math.min(image1.getHeight(), image2.getHeight());

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb1 = image1.getRGB(i, j);
                int rgb2 = image2.getRGB(i, j);
                int newRGB = getNewRGB(rgb1, rgb2, operation);
                result.setRGB(i, j, newRGB);
            }
        }

        return result;
    }

    public BufferedImage getAndOperation() {
        return makeOperation(Operation.AND);
    }

    public BufferedImage getOrOperation() {
        return makeOperation(Operation.OR);
    }

    public BufferedImage getXorOperation() {
        return makeOperation(Operation.XOR);
    }

}
