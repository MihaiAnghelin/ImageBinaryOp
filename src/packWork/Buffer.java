package packWork;

public class Buffer {
    byte[] imageBuffer;
    private boolean available = false;

    public synchronized byte[] get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
        notifyAll();
        return imageBuffer;
    }


    public synchronized void put(byte[] bytes) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        imageBuffer = bytes;

        available = true;
        notifyAll();
    }
}
