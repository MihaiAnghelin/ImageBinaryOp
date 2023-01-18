package packWork;

import java.util.Scanner;

public class ConsoleIO extends AbstractConsole { // clasa care se ocupa de citirea si afisarea datelor de la tastatura

    public String[] readFileNames(int count) { // citeste numele fisierelor de la tastatura
        String[] fileNames = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Introduceti numele fisierului " + (i + 1) + ": ");
            fileNames[i] = readString();
        }
        return fileNames;
    }

    public String readString() { // citeste un string de la tastatura
        return new Scanner(System.in).nextLine();
    }

    public String readAndFileName(ImageProcessing.Operation operation) { // citeste numele fisierului de la tastatura
        System.out.print("Introduceti numele fisierului pt op.: " + operation + ": ");
        return readString();
    }

    public void printFileNames(String... fileNames) { // afiseaza numele fisierelor de ieșire (varargs)
        System.out.println("Fisierele de ieșire sunt:");
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
    }

    public void printInYellow(String text) { // afiseaza textul in culori
        System.out.println("\033[0;33m" + text + "\033[0m");
    }

    public void printInRed(String text) {
        System.out.println("\033[0;31m" + text + "\033[0m");
    }

    public void printInGreen(String text) {
        System.out.println("\033[0;32m" + text + "\033[0m");
    }

    public void printInBlue(String text) {
        System.out.println("\033[0;34m" + text + "\033[0m");
    }

    public void printInPurple(String text) {
        System.out.println("\033[0;35m" + text + "\033[0m");
    }

    public void printEmptyLine() { // afiseaza o linie goala
        System.out.println();
    }
}
