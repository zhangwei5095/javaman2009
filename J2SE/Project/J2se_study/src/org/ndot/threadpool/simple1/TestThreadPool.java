package org.ndot.threadpool.simple1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
	private static int produceTaskSleepTime = 2;

	private static int produceTaskMaxNumber = 10;

	public static void main(String[] args) {

		// 构造一个线程池
		/**
		 * corePoolSize： 线程池维护线程的最少数量 maximumPoolSize：线程池维护线程的最大数量
		 * keepAliveTime： 线程池维护线程所允许的空闲时间 unit： 线程池维护线程所允许的空闲时间的单 workQueue：
		 * 线程池所使用的缓冲队列 handler： 线程池对拒绝任务的处理策略
		 */
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
				new ThreadPoolExecutor.DiscardOldestPolicy());

		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {
				// 产生一个任务，并将其加入到线程池
				String task = "task@ " + i;
				System.out.println("put " + task);
				threadPool.execute(new ThreadPoolTask(task));

				// 便于观察，等待一段时间
				//Thread.sleep(produceTaskSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
