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

    //ģ�������̳߳� ͬʱ4���̴߳���
    private static final ThreadPoolExecutor THREAD_POOL = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    //ģ����Ϣ��������
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    //�����ж��Ƿ�رն���
    private static volatile boolean isClose = false;

    public static void main(String[] args) throws InterruptedException {

        //ע�ṳ�ӷ���
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

    //ģ����Ϣ����������
    private static void producer(final BlockingQueue  queue){
        //ÿ200����������з���һ����Ϣ
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                queue.offer("msg:" + Math.random());
            }
        }, 0L, 200L, TimeUnit.MILLISECONDS);
    }

    //ģ����Ϣ���������� ������ÿ������5��   ������4���߳�����1��1��  ÿ���ѹ1��
    private static void consumer(final BlockingQueue queue) throws InterruptedException {
        while (!isClose){
            getPoolBacklogSize();
            //�Ӷ������õ���Ϣ
            final String msg = (String)queue.take();
            //�����̳߳ش���
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

    //�鿴�̳߳ضѻ���Ϣ����
    private static long getPoolBacklogSize(){
        long backlog = THREAD_POOL.getTaskCount()- THREAD_POOL.getCompletedTaskCount();
        System.out.println(String.format("[%s]THREAD_POOL backlog:%s", System.currentTimeMillis(), backlog));
        return backlog;
    }

    private static void close(){
        System.out.println("�յ�kill��Ϣ��ִ�йرղ���");
        //�رն�������
        isClose = true;
        //�ر��̳߳أ��ȴ��̳߳ػ�ѹ��Ϣ����
        THREAD_POOL.shutdown();
        //�ж��̳߳��Ƿ�ر�
        while (!THREAD_POOL.isTerminated()) {
            try {
                //ÿ200���� �ж��̳߳ػ�ѹ����
                getPoolBacklogSize();
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("�����߹رգ��̳߳ش������");
    }

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName != null && osName.indexOf("window") == -1) {
            //ע��linux kill�ź���  kill -12
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
