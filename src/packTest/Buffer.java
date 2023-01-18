package packTest;

public class Buffer {
    byte[] imageBuffer; // imagine salvata in buffer (byte array)
    private boolean available = false; // bufferul este gol

    public synchronized byte[] get() {
        while (!available) { // daca bufferul este gol, asteapta
            try {
                wait(); // asteapta pana cand bufferul este plin
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false; // bufferul este gol
        notifyAll(); // notifica thread-urile care asteapta ca bufferul sa fie gol
        return imageBuffer; // returneaza imaginea din buffer
    }


    public synchronized void put(byte[] bytes) {
        while (available) { // daca bufferul este plin, asteapta
            try {
                wait(); // asteapta pana cand bufferul este gol
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        imageBuffer = bytes; // adauga imaginea in buffer

        available = true; // bufferul este plin
        notifyAll(); // notifica thread-urile care asteapta ca bufferul sa fie plin
    }
}
