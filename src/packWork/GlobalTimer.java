package packWork;

public class GlobalTimer { // Clasa GlobalTimer
    private static final Timer timer = new Timer(); // Instanta Timer globala

    public static void start() { // Metoda de pornire a timerului
        timer.start();
    }

    public static void stop() { // Metoda de oprire a timerului
        timer.stop();
    }

    public static long getElapsedTime() { // Metoda de returnare a timpului
        return timer.getElapsedTime();
    }
}
