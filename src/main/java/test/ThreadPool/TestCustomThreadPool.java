package test.ThreadPool;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestCustomThreadPool {
	public class MyThread implements Runnable{
		protected String name;
		public MyThread(){
		}
		public MyThread(String name){
			this.name=name;
		}
		public void run() {
			try {
				Thread.sleep(100);
				System.out.println(name+" in run"+" "+Thread.currentThread().getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public String getName(){
			return name;
		}
	}

	public class MyThreadPoolExecutor extends ThreadPoolExecutor{
		public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
									long keepAliveTime, TimeUnit unit,
									BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		protected void beforeExecute(Thread t, Runnable r) {
			System.out.println("beforeExecute MyThread Name:"+((MyThread)r).getName()+" TID:"+t.getId());
		}

		protected void afterExecute(Runnable r, Throwable t) {
			System.out.println("afterExecute TID:"+Thread.currentThread().getId());
			System.out.println("afterExecute PoolSize:"+this.getPoolSize());
		}
	}

	@Test
	public void testThreadPoolExecutor1() throws InterruptedException {

		long starttime=System.currentTimeMillis();
		//ExecutorService exe=new MyThreadPoolExecutor(100,600,0L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(200));
		ExecutorService exe=new MyThreadPoolExecutor(0, Integer.MAX_VALUE,
				60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		for(int i=0;i<1000;i++){
			exe.execute(new MyThread("testThreadPoolExecutor1_"+Integer.toString(i)));
		}
		System.out.println();
		long endtime=System.currentTimeMillis();
		System.out.println("testThreadPoolExecutor1"+": "+(endtime-starttime));
		System.out.println("testThreadPoolExecutor1 exe size"+": "+((ThreadPoolExecutor)exe).getPoolSize());
		Thread.sleep(1000*101);
	}

}
