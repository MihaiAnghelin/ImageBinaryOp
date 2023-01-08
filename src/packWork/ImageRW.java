package packWork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageRW {
    private BufferedImage image;

    public ImageRW() {
        image = null;
    }

    public ImageRW(BufferedImage image) {
        this.image = image;
    }

    private String readImagePath() {
        System.out.println("Image path: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void readImage() {
        try {
            String imagePath = readImagePath();
            image = ImageIO.read(new File(imagePath));
            System.out.println("Reading complete.");
        } catch (IOException e) {
            System.out.println("Error reading image");
        }
    }

    public void writeImage(String path) {
        try {
            ImageIO.write(image, "bmp", new File(path));
            System.out.println("Writing complete.");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
