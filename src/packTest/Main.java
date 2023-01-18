package packTest;

import packWork.ConsoleIO;
import packWork.GlobalTimer;
import packWork.ImageProcessing.Operation;

public class Main {
    public static void main(String[] args) {

        GlobalTimer.start(); // start timer global

        ConsoleIO consoleIO = new ConsoleIO();

        String[] inputFileNames = consoleIO.readFileNames(2); // citeste numele fisierelor de intrare

        String andOutputFile = consoleIO.readAndFileName(Operation.AND); // citeste numele fisierului de iesire pentru operatia AND
        String orOutputFile = consoleIO.readAndFileName(Operation.OR); // citeste numele fisierului de iesire pentru operatia OR
        String xorOutputFile = consoleIO.readAndFileName(Operation.XOR); // citeste numele fisierului de iesire pentru operatia XOR

        consoleIO.printEmptyLine();

        consoleIO.printFileNames(andOutputFile, orOutputFile, xorOutputFile); // afiseaza numele fisierelor de iesire

        Buffer buffer = new Buffer();
        Thread producer = new Thread(new Producer(buffer, inputFileNames[0], inputFileNames[1])); // creeaza thread-ul producer
        Thread consumer = new Thread(new Consumer(buffer, andOutputFile, orOutputFile, xorOutputFile)); // creeaza thread-ul consumer

        producer.start(); // porneste thread-ul producer
        consumer.start(); // porneste thread-ul consumer
    }
}