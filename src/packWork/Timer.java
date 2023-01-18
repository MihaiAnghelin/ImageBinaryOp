package packWork;

public class Timer { // Clasa ce se ocupa de timpul de executie al unei operatii
    private long startTime; // Momentul in care a inceput executia
    private long endTime; // Momentul in care s-a terminat executia

    public Timer() { // Constructorul clasei
        startTime = 0;
        endTime = 0;
    }

    public void start() { // Metoda ce porneste timer-ul
        startTime = System.currentTimeMillis();
    }

    public void stop() { // Metoda ce opreste timer-ul
        endTime = System.currentTimeMillis();
    }

    public long getElapsedTime() { // Metoda ce returneaza timpul de executie al unei operatii
        return endTime - startTime;
    }
}
