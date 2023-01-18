package packTest;

import packWork.*;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread producer = new Thread(new Producer(buffer, "images/1.bmp", "images/2.bmp"));
        Thread consumer = new Thread(new Consumer(buffer));

        producer.start();
        consumer.start();
    }
}