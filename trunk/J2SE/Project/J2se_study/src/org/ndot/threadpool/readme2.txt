socket通讯的项目，项目中有个需求：根据客户端的请求记时，计时时间不定。

由于考虑到安全等因素，决定将所有客户端计时任务放在服务器端。最大同时计时任务需求为500个。

如果采用timer为每个任务计时，肯定不现实（服务器线程消耗严重）。出了这个考虑之外，还要考虑多线程线程安全的因素。

基于这些，我用到了ArrayBlockingQueue来实现。ArrayBlockingQueue是一个阻塞的同步队列，具体参考JDK文档。

主要思想是一个ArrayBlockingQueue来保存计时队列 一个ArrayBlockingQueue来保存计时完成要处理的队列。

一个线程不断扫描计时队列，检测时间是否到了，到了就添加到处理队列，由另外的线程来处理逻辑任务。

实现代码：

package test.client; 

import java.util.concurrent.ArrayBlockingQueue; 

/** 
 * 
 * @version 2008-11-22           
 */ 
public class TestMain { 

    public static void main(String[] args) throws InterruptedException { 

        //计时队列 
        ArrayBlockingQueue<TimerTask> queue1 = new ArrayBlockingQueue<TimerTask>(1000, true); 

        //处理队列 
        ArrayBlockingQueue<TimerTask> queue2 = new ArrayBlockingQueue<TimerTask>(1000, true); 

        //可以增加CheckThread DisposeThread线程进一步提高处理速度 降低延迟 
        Thread t1 = new Thread(new AddThread(queue1)); 
        Thread t2 = new Thread(new CheckThread(queue1, queue2)); 
        Thread t3 = new Thread(new DisposeThread(queue2, new TestMain())); 

        t1.start(); 
        t2.start(); 
        t3.start(); 
    } 

} 

/** 
 * 此线程添加新的计时任务到计时队列 
 */ 
class AddThread implements Runnable { 

    private ArrayBlockingQueue<TimerTask> queue; 

    public AddThread(ArrayBlockingQueue<TimerTask> queue) { 

        this.queue = queue; 
    } 

    public void run() { 

        while (true) { 
            for (int i = 0; i < 500; i++) { 
                int time = 0; 
                if(i%3==0){ 
                    time = 60*1000; 
                }else if(i%3==1){ 
                    time = 2*60*1000; 
                }else if(i%3==2){ 
                    time = 5*60*1000; 
                } 
                TimerTask tt = new TimerTask(i, System.currentTimeMillis() + time); 
                try { 
                    queue.put(tt); 
                    //                  System.out.println("添加计时任务" + i); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
            try { 
                Thread.sleep(10 * 60 * 1000); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 

/** 
 * 此线程扫描计时任务队列，检查计时是否完成 
 */ 
class CheckThread implements Runnable { 

    ArrayBlockingQueue<TimerTask> queue1; 
    ArrayBlockingQueue<TimerTask> queue2; 

    public CheckThread(ArrayBlockingQueue<TimerTask> queue1, ArrayBlockingQueue<TimerTask> queue2) { 

        this.queue1 = queue1; 
        this.queue2 = queue2; 
    } 

    public void run() { 

        while (true) { 
            for (int i = 0; i < 100; i++) { 
                TimerTask tt = null; 
                try { 
                    tt = queue1.take(); 
                    //              System.out.println("检查计时任务" + tt.getRoleID()); 
                    if (tt.getEndTime() <= System.currentTimeMillis()) { 
                        //                  System.out.println("添加执行任务" + tt.getRoleID()); 
                        queue2.put(tt); 
                    } else { 
                        queue1.put(tt); 
                    } 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
            try { 
                Thread.sleep(100); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 

/** 
 * 此线程从计时任务完成的队列中不断取出计时任务并执行逻辑 
 */ 
class DisposeThread implements Runnable { 

    TestMain tm; 
     
    ArrayBlockingQueue<TimerTask> queue2; 

    public DisposeThread(ArrayBlockingQueue<TimerTask> queue2,TestMain tm) { 

        this.queue2 = queue2; 
        this.tm = tm; 
    } 

    public void run() { 

        while (true) { 
            TimerTask tt = null; 
            try { 
                tt = queue2.take(); 
                System.out.println("提取处理任务" + tt.getRoleID()); 
                tm.test(); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
            if (tt != null) { 
                System.out.println(tt.getRoleID() + ":" + (tt.getEndTime() - System.currentTimeMillis())); 
            } 
        } 
    } 
} 

/** 
 * 计时任务 
 */ 
class TimerTask { 

    private Integer roleID; 

    private Long endTime; 

    public TimerTask(Integer roleID, Long endTime) { 

        this.roleID = roleID; 
        this.endTime = endTime; 
    } 

    public Integer getRoleID() { 

        return roleID; 
    } 

    public void setRoleID(Integer roleID) { 

        this.roleID = roleID; 
    } 

    public Long getEndTime() { 

        return endTime; 
    } 

    public void setEndTime(Long endTime) { 

        this.endTime = endTime; 
    } 
} 

