package packTest;

import packWork.ImageProcessing;
import packWork.ImageRW;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        ImageRW imageRW = new ImageRW();
        imageRW.readImage();
        BufferedImage image1 = imageRW.getImage();

        imageRW.readImage();
        BufferedImage image2 = imageRW.getImage();

        ImageProcessing imageProcessing = new ImageProcessing(image1, image2);

        BufferedImage andOperation = imageProcessing.getAndOperation();
        imageRW.setImage(andOperation);
        imageRW.writeImage("images/and.bmp");

        BufferedImage orOperation = imageProcessing.getOrOperation();
        imageRW.setImage(orOperation);
        imageRW.writeImage("images/or.bmp");

        BufferedImage xorOperation = imageProcessing.getXorOperation();
        imageRW.setImage(xorOperation);
        imageRW.writeImage("images/xor.bmp");
    }
}