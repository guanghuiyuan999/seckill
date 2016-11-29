package test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanguanghui on 2016/9/27.
 */
public class TestExchanger {
    static Exchanger exchanger = new Exchanger();
    public static void main(String[] args) {
        new Thread() {
            public void run() {
                Object a = 1;
                try {
                    a = (Object) exchanger.exchange(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1: "+a);
            }
        }.start();

        new Thread() {
            public void run() {
                Object a = 2;
                try {
                    a = (Object) exchanger.exchange(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2: "+a);
            }
        }.start();



        new Thread() {
            public void run() {
                Object a = 4;
                try {
                    a = (Object) exchanger.exchange(a, 2000, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Thread4: "+a);
            }
        }.start();
    }
}
