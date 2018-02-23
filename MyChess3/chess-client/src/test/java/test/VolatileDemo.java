package test;

public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = Thread.currentThread();
        System.out.println(1);
        t.interrupt();
        Thread.interrupted();
        t.wait();
        System.out.println(1);
    }
}
