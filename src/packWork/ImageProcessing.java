package packWork;

public class ImageProcessing extends BmpImage {

    public ImageProcessing() {
    }

    enum Operation {
        AND,
        OR,
        XOR
    }

    private int getNewRGB(int rgb1, int rgb2, Operation operation) {
        int r1 = getRed(rgb1);
        int g1 = getGreen(rgb1);
        int b1 = getBlue(rgb1);

        int r2 = getRed(rgb2);
        int g2 = getGreen(rgb2);
        int b2 = getBlue(rgb2);

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

    private byte[] makeOperation(Operation operation, byte[] imageBuffer1, byte[] imageBuffer2) {
        int width = Math.min(getWidth(imageBuffer1), getWidth(imageBuffer2));
        int height = Math.min(getHeight(imageBuffer1), getHeight(imageBuffer2));

        int offset = 54;
        int size = width * height * 3 + offset;
        byte[] imageBuffer = new byte[size];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb1 = getPixel(imageBuffer1, i, j);
                int rgb2 = getPixel(imageBuffer2, i, j);

                int newRGB = getNewRGB(rgb1, rgb2, operation);

                String colorHex1 = getColorHexFromPixel(rgb1);
                String colorHex2 = getColorHexFromPixel(rgb2);
                String colorHex = getColorHexFromPixel(newRGB);

                imageBuffer = setPixel(imageBuffer, i, j, newRGB);
            }
        }

        return imageBuffer;
    }

    public byte[] processImages(byte[] imageBuffer1, byte[] imageBuffer2, Operation operation) {
        byte[] header1 = getHeader(imageBuffer1);
        byte[] header2 = getHeader(imageBuffer2);

        byte[] imageBuffer1WithoutHeader = getImageBufferWithoutHeader(imageBuffer1);
        byte[] imageBuffer2WithoutHeader = getImageBufferWithoutHeader(imageBuffer2);

        int minLength = Math.min(imageBuffer1WithoutHeader.length, imageBuffer2WithoutHeader.length);

        int minWidth = Math.min(getWidth(imageBuffer1), getWidth(imageBuffer2));
        int minHeight = Math.min(getHeight(imageBuffer1), getHeight(imageBuffer2));

        byte[] result = new byte[minLength];
        byte[] header = minLength == imageBuffer1WithoutHeader.length ? header1 : header2;

        header = setWidth(header, minWidth);
        header = setHeight(header, minHeight);

        switch (operation) {
            case AND:
                result = makeOperation(Operation.AND, imageBuffer1, imageBuffer2);
                break;
            case OR:
                result = makeOperation(Operation.OR, imageBuffer1, imageBuffer2);
                break;
            case XOR:
                result = makeOperation(Operation.XOR, imageBuffer1, imageBuffer2);
                break;
        }

        return getImageBufferWithHeader(result, header);
    }
}
