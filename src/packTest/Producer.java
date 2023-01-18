package packTest;

import packWork.ConsoleIO;

import java.io.*;

import static java.lang.Thread.sleep;

public class Producer implements Runnable {
    private final Buffer buffer; // referinta catre buffer
    private static String fileName1; // numele fisierului 1 de intrare
    private static String fileName2; // numele fisierului 2 de intrare

    static int sleepTime; // timpul de asteptare intre citiri din fisier

    static { // se apeleaza o singura data la incarcarea clasei
        sleepTime = 1000; // 1 secunda
        fileName1 = null;
        fileName2 = null;
    }

    public Producer(Buffer buffer, String fileName1, String fileName2) {
        this.buffer = buffer;
        Producer.fileName1 = fileName1;
        Producer.fileName2 = fileName2;

    }

    public void run() {
        fileRead(fileName1); // citeste din fisierul 1
        fileRead(fileName2); // citeste din fisierul 2
    }

    private void fileRead(String fileName) { // citeste din fisier
        File file = new File(fileName);
        ConsoleIO consoleIO = new ConsoleIO();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r"); // deschide fisierul pentru citire
            int bufferSize = (int) randomAccessFile.length() / 4; // dimensiunea bufferului

            for (int i = 0; i < 4; i++) { // citeste fisierul impartit in 4 parti
                byte[] byteBuffer = new byte[bufferSize]; // bufferul de citire

                randomAccessFile.readFully(byteBuffer); // citeste din fisier pana umple bufferul

                buffer.put(byteBuffer); // pune bufferul in bufferul comun

                consoleIO.printInGreen("Producatorul a pus:\t" + (i + 1) + "/" + 4 + " din " + fileName);
                try {
                    sleep(sleepTime); // asteapta un timp
                } catch (InterruptedException ignored) {
                }
            }

            int bufferRest = (int) randomAccessFile.length() % 4; // citeste restul de bytes din fisier
            if (bufferRest != 0) { // daca exista rest
                byte[] byteBuffer = new byte[bufferRest]; // bufferul de citire

                randomAccessFile.readFully(byteBuffer); // citeste din fisier pana umple bufferul

                buffer.put(byteBuffer); // pune bufferul in bufferul comun

                consoleIO.printInGreen("Producatorul a pus restul de biti %4 din " + fileName);
            }

            randomAccessFile.close(); // inchide fisierul
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
