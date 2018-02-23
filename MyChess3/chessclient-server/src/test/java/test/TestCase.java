package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestCase {
    

    public void test1(){
        Thread t = new Thread(new T1());
        while (true) {
            if (!t.isAlive()) {
                t = new Thread(new T1());
                t.start();
            }
        }
    }
    @Test
    public void test2(){
        Map<Integer,String> map = new HashMap<Integer, String>();
        System.out.println(map.remove(1));
    }
}

class T1 implements Runnable {

    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}