package packTest;

import packWork.ConsoleIO;
import packWork.GlobalTimer;
import packWork.ImageProcessing;
import packWork.Timer;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Consumer extends ImageProcessing implements Runnable {
    private final Buffer buffer; // referinta catre buffer
    byte[] imageBuffer1; // imaginea 1
    byte[] imageBuffer2; // imaginea 2

    private String andOutputFile; // numele fisierului de iesire pentru operatia AND
    private String orOutputFile; // numele fisierului de iesire pentru operatia OR
    private String xorOutputFile; // numele fisierului de iesire pentru operatia XOR

    // initializare variabile
    {
        andOutputFile = null;
        orOutputFile = null;
        xorOutputFile = null;
    }

    public Consumer(Buffer buffer, String andOutputFile, String orOutputFile, String xorOutputFile) {
        this.buffer = buffer;

        imageBuffer1 = new byte[0];
        imageBuffer2 = new byte[0];
        this.andOutputFile = andOutputFile;
        this.orOutputFile = orOutputFile;
        this.xorOutputFile = xorOutputFile;
    }

    public void run() {
        imageBuffer1 = addToImageBuffer(imageBuffer1); // adaugare imagine 1 in buffer
        imageBuffer2 = addToImageBuffer(imageBuffer2); // adaugare imagine 2 in buffer
        ConsoleIO consoleIO = new ConsoleIO();

        Timer timer = new Timer();
        timer.start(); // pornire timer
        BufferedImage imageBufferAnd = processImages(imageBuffer1, imageBuffer2, Operation.AND); // procesare imagine 1 si 2 cu operatia AND
        timer.stop(); // oprire timer
        consoleIO.printInYellow("Timpul de procesare a imaginii AND: " + timer.getElapsedTime() + " ms"); // afisare timp de procesare

        timer.start();
        writeImageToPath(imageBufferAnd, andOutputFile); // scriere imagine AND in fisier
        timer.stop();
        consoleIO.printInYellow("Timpul de scriere a imaginii AND: " + timer.getElapsedTime() + " ms");

        consoleIO.printEmptyLine();

        timer.start();
        BufferedImage imageBufferOr = processImages(imageBuffer1, imageBuffer2, Operation.OR); // procesare imagine 1 si 2 cu operatia OR
        timer.stop();
        consoleIO.printInYellow("Timpul de procesare a imaginii OR: " + timer.getElapsedTime() + " ms");

        timer.start();
        writeImageToPath(imageBufferOr, orOutputFile); // scriere imagine OR in fisier
        timer.stop();
        consoleIO.printInYellow("Timpul de scriere a imaginii OR: " + timer.getElapsedTime() + " ms");

        consoleIO.printEmptyLine();

        timer.start();
        BufferedImage imageBufferXor = processImages(imageBuffer1, imageBuffer2, Operation.XOR); // procesare imagine 1 si 2 cu operatia XOR
        timer.stop();
        consoleIO.printInYellow("Timpul de procesare a imaginii XOR: " + timer.getElapsedTime() + " ms");

        timer.start();
        writeImageToPath(imageBufferXor, xorOutputFile); // scriere imagine XOR in fisier
        timer.stop();
        consoleIO.printInYellow("Timpul de scriere a imaginii XOR: " + timer.getElapsedTime() + " ms");

        consoleIO.printEmptyLine();

        GlobalTimer.stop();
        consoleIO.printInYellow("Timpul total de executie: " + GlobalTimer.getElapsedTime() + " ms");
    }

    private byte[] addToImageBuffer(byte[] imageBuffer) { // adaugare imagine in buffer

        ConsoleIO consoleIO = new ConsoleIO();

        for (int i = 0; i < 4; i++) {
            byte[] bytes = buffer.get(); // extragere sfert de imagine din buffer

            int oldLength = imageBuffer.length; // lungimea imaginii

            imageBuffer = Arrays.copyOf(imageBuffer, oldLength + bytes.length); // redimensionare imagine
            System.arraycopy(bytes, 0, imageBuffer, oldLength, bytes.length); // copiere sfert de imagine in imagine

            consoleIO.printInBlue("Consumatorul a luat:\t" + (i + 1) + " din " + 4);
        }

        byte[] bytes = buffer.get(); // extragere restul de imagine din buffer datorita faptului ca imaginea nu este divizibila cu 4
        if (bytes.length != 0) { // daca exista rest de imagine
            int oldLength = imageBuffer.length;

            imageBuffer = Arrays.copyOf(imageBuffer, oldLength + bytes.length); // redimensionare imagine
            System.arraycopy(bytes, 0, imageBuffer, oldLength, bytes.length); // copiere rest de imagine in imagine

            consoleIO.printInBlue("Consumatorul a luat restul de biti %4");
        }

        System.out.println();

        return imageBuffer;
    }
}

