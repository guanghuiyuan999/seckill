package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanguanghui on 2016/9/27.
 */
public class line3 implements Runnable{
    private CountDownLatch cd;
    private int id;
    public line3(CountDownLatch cd,int id){
        this.cd=cd;
        this.id=id;
    }
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(id * 1000);
            cd.countDown();//这样貌似和CylicBarrier一个效果啊
            System.out.println("countDown"+id);
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("line3  "+id);


    }
    public static void main(String[] args) {
        CountDownLatch cd =new CountDownLatch(10);
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            es.execute(new line3(cd,i));
        }
        es.shutdown();
    }
}
