package test.jms;

import sun.misc.Signal;
import sun.misc.SignalHandler;
import java.util.concurrent.*;

/**
 * https://mp.weixin.qq.com/s?__biz=MjM5NzMyMjAwMA==&mid=2651479396&idx=2&sn=e46bec1f2ed0e0104ffe96a7ba325a68&chksm=bd25311b8a52b80d373c669a687e9916dc256542f9604d922515420222213cd98eabe6034782&mpshare=1&scene=23&srcid=110870wdY51EyPPsPxQugnTG#rd
 * @Description:
 * @date 2016/11/14
 */
public class MsgClient {

    //模拟消费线程池 同时4个线程处理
    private static final ThreadPoolExecutor THREAD_POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    //模拟消息生产任务
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    //用于判断是否关闭订阅
    private static volatile boolean isClose = false;

    public static void main(String[] args) throws InterruptedException {

        //注册钩子方法
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                close();
            }
        });

        BlockingQueue <String> queue = new ArrayBlockingQueue<String>(100);
        producer(queue);
        consumer(queue);

    }

    //模拟消息队列生产者
    private static void producer(final BlockingQueue  queue){
        //每200毫秒向队列中放入一个消息
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                queue.offer("msg:" + Math.random());
            }
        }, 0L, 200L, TimeUnit.MILLISECONDS);
    }

    //模拟消息队列消费者 生产者每秒生产5个   消费者4个线程消费1个1秒  每秒积压1个
    private static void consumer(final BlockingQueue queue) throws InterruptedException {
        while (!isClose){
            getPoolBacklogSize();
            //从队列中拿到消息
            final String msg = (String)queue.take();
            //放入线程池处理
            if(!THREAD_POOL.isShutdown()) {
                THREAD_POOL.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(msg);
                            TimeUnit.MILLISECONDS.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    //查看线程池堆积消息个数
    private static long getPoolBacklogSize(){
        long backlog = THREAD_POOL.getTaskCount()- THREAD_POOL.getCompletedTaskCount();
        System.out.println(String.format("[%s]THREAD_POOL backlog:%s", System.currentTimeMillis(), backlog));
        return backlog;
    }

    private static void close(){
        System.out.println("收到kill消息，执行关闭操作");
        //关闭订阅消费
        isClose = true;
        //关闭线程池，等待线程池积压消息处理
        THREAD_POOL.shutdown();
        //判断线程池是否关闭
        while (!THREAD_POOL.isTerminated()) {
            try {
                //每200毫秒 判断线程池积压数量
                getPoolBacklogSize();
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("订阅者关闭，线程池处理完毕");
    }

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName != null && osName.indexOf("window") == -1) {
            //注册linux kill信号量  kill -12
            Signal sig = new Signal("USR2");
            Signal.handle(sig, new SignalHandler() {
                @Override
                public void handle(Signal signal) {
                    close();
                }
            });
        }
    }

}
