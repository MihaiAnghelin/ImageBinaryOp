package packWork;

public abstract class AbstractConsole { // clasa abstracta pentru citirea si afisarea de date
    public abstract void printFileNames(String... fileNames);

    public abstract String readAndFileName(ImageProcessing.Operation operation);

    public abstract String[] readFileNames(int count);

    public abstract String readString();

    public abstract void printEmptyLine();

    public abstract void printInYellow(String text);

    public abstract void printInRed(String text);

    public abstract void printInGreen(String text);

    public abstract void printInBlue(String text);

    public abstract void printInPurple(String text);


}
