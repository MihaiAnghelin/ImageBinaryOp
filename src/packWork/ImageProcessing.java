package packWork;

import java.awt.image.BufferedImage;

public class ImageProcessing extends BmpImage { // Clasa ce se ocupa de procesarea imaginii

    public enum Operation { // Enumeratia ce contine operatiile ce pot fi efectuate asupra imaginii
        AND,
        OR,
        XOR
    }

    private int getNewRGB(int rgb1, int rgb2, Operation operation) { // Metoda ce returneaza un nou pixel RGB in functie de operatia aleasa
        int r1 = (rgb1 >> 16) & 0xFF; // Red pentru primul pixel
        int g1 = (rgb1 >> 8) & 0xFF; // Green pentru primul pixel
        int b1 = rgb1 & 0xFF; // Blue pentru primul pixel

        int r2 = (rgb2 >> 16) & 0xFF; // Red pentru al doilea pixel
        int g2 = (rgb2 >> 8) & 0xFF; // Green pentru al doilea pixel
        int b2 = rgb2 & 0xFF; // Blue pentru al doilea pixel

        switch (operation) { // In functie de operatia aleasa se efectueaza operatia dorita
            case AND:
                return (r1 & r2) << 16 | (g1 & g2) << 8 | (b1 & b2); // Returneaza un nou pixel RGB in functie de operatia AND
            case OR:
                return (r1 | r2) << 16 | (g1 | g2) << 8 | (b1 | b2); // Returneaza un nou pixel RGB in functie de operatia OR
            case XOR:
                return (r1 ^ r2) << 16 | (g1 ^ g2) << 8 | (b1 ^ b2); // Returneaza un nou pixel RGB in functie de operatia XOR
            default:
                return 0; // Returneaza 0 daca operatia nu este valida
        }
    }

    public BufferedImage processImages(byte[] imageBuffer1, byte[] imageBuffer2, Operation operation) { // Metoda ce proceseaza doua imagini in functie de operatia aleasa

        System.out.println("Procesare imagine operatie: " + operation); // Afiseaza operatia aleasa

        BufferedImage image1 = getImageFromBytes(imageBuffer1); // Se obtine imaginea 1 din buffer
        BufferedImage image2 = getImageFromBytes(imageBuffer2); // Se obtine imaginea 2 din buffer

        int width = Math.min(image1.getWidth(), image2.getWidth()); // Se obtine latimea minima dintre cele doua imagini
        int height = Math.min(image1.getHeight(), image2.getHeight()); // Se obtine inaltimea minima dintre cele doua imagini

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Se creeaza o noua imagine cu latimea si inaltimea minime

        for (int i = 0; i < width; i++) { // Se parcurge imaginea pe latime
            for (int j = 0; j < height; j++) { // Se parcurge imaginea pe inaltime
                int rgb1 = image1.getRGB(i, j); // Se obtine pixelul RGB pentru imaginea 1
                int rgb2 = image2.getRGB(i, j); // Se obtine pixelul RGB pentru imaginea 2
                int newRGB = getNewRGB(rgb1, rgb2, operation); // Se obtine un nou pixel RGB in functie de operatia aleasa
                result.setRGB(i, j, newRGB); // Se seteaza pixelul RGB pentru imaginea rezultat
            }
        }

        System.out.println("Imaginea a fost procesata.");
        return result; // Se returneaza imaginea rezultat
    }
}
