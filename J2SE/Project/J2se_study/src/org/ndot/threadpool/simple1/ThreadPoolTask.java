package org.ndot.threadpool.simple1;

import java.io.Serializable;

public class ThreadPoolTask implements Runnable, Serializable {

	private static final long serialVersionUID = 0;
	private static int consumeTaskSleepTime = 2000;
	// 保存任务所需要的数据
	private Object threadPoolTaskData;

	ThreadPoolTask(Object tasks) {
		this.threadPoolTaskData = tasks;
	}

	public void run() {
		// 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句
		System.out.println(Thread.currentThread().getName());
		System.out.println("start .." + threadPoolTaskData);
		try {
			// //便于观察，等待一段时间
			Thread.sleep(consumeTaskSleepTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		threadPoolTaskData = null;
	}

	public Object getTask() {
		return this.threadPoolTaskData;
	}

}
