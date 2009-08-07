/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.pvm.internal.jobexecutor;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jbpm.api.JbpmException;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.cmd.CommandService;


/** manager for job execution threads and their configuration.
 * 
 * @author Tom Baeyens, Guillaume Porcher
 */
public class JobExecutor implements Serializable {
  private static final Log log = Log.getLog(JobExecutor.class.getName());

  private static final long serialVersionUID = 1L;

  // configuration parameters
  
  CommandService commandService;
  String name = "JobExecutor-"+getHostName();
  int nbrOfThreads = 3;
  int idleMillis = 5000; // default normal poll interval is 5 seconds
  int idleMillisMax = 1000*60*2; // default max poll interval in case of continuous exceptions is 2 minutes
  int historySize = 200;
  int lockMillis = 1000*60*30; // default max lock time is 30 minutes

  // runtime state

  Command<Collection<Long>> acquireJobsCommand;
  Command<Date> nextDueDateCommand;
  
  boolean isActive = false;

  JobExecutorThreadPool jobExecutorThreadPool;
  
  DispatcherThread dispatcherThread = null;
  
  /** queue to dispatch collections of jobDbids to the JobExecutorThreads, which are 
   * competing readers. */
  BlockingQueue<Collection<Long>> jobDbidsQueue = null;

  List<JobHistoryEntry> history = new ArrayList<JobHistoryEntry>();
  
  /** starts the {@link DispatcherThread} and {@link JobExecutorThread}s for this job executor */
  public synchronized void start() {
    if (commandService==null) {
      throw new JbpmException("no command executor available in job executor");
    }
    if (! isActive) {
      acquireJobsCommand = new AcquireJobsCmd(this);
      nextDueDateCommand = new GetNextDueDateCmd(this);
      
      // the max capacity of the jobDbidsQueue is set to nbrOfJobExecutorThreads.  
      // That way, the distpatcher thread will be stalled if enough jobs are acquired.
      jobDbidsQueue = new ArrayBlockingQueue<Collection<Long>>(nbrOfThreads, true);

      isActive = true;
      log.trace("starting job executor threads for job executor '"+name+"'...");
      jobExecutorThreadPool = new JobExecutorThreadPool(this);
      jobExecutorThreadPool.start();

      log.trace("starting dispatcher thread for job executor '"+name+"'...");
      dispatcherThread = new DispatcherThread(this);
      dispatcherThread.start();
      
    } else {
      log.trace("ignoring start: job executor '"+name+"' is already started'");
    }
  }
  
  /** stops with join set to false.
   * @see #stop(boolean) */ 
  public synchronized void stop() {
    stop(false);
  }
  
  /** signals to all threads managed by this job executor to stop.  Stopping the 
   * dispatcher thread is blocking till the dispatcher is no more alive.  If join is set to true, 
   * this method will block until all job executor threads 
   * are joined and actually dead. If join is false, the job executor threads are only told to 
   * stop, without waiting till they are actually stopped. It may be that job executor threads 
   * are in the middle of executing a job and they may finish after this method returned.
   */
  public synchronized void stop(boolean join) {
    log.debug("stopping job executor");
    if (isActive) {
      isActive = false;
      dispatcherThread.deactivate(true);
      waitTillQueueEmpty();
      jobExecutorThreadPool.deactivate(join);
    } else {
      log.trace("ignoring stop: job executor '"+name+"' not started");
    }
  }

  protected void waitTillQueueEmpty() {
    while (! jobDbidsQueue.isEmpty()) {
      log.trace("waiting for job-id-queue to become empty");
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        log.trace("waiting for job-id-queue to become empty got interrupted");
      }
    }
  }

  public void jobWasAdded() {
    if ( (dispatcherThread!=null)
         && (dispatcherThread.isActive())
       ) {
      dispatcherThread.jobWasAdded();
    }
  }

  protected static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      return "unknown";
    }
  }

  protected BlockingQueue<Collection<Long>> getJobDbidsQueue() {
    return jobDbidsQueue;
  }

  // getters //////////////////////////////////////////////////////////////////
  
  public String getName() {
    return name;
  }
  public int getHistorySize() {
    return historySize;
  }
  public int getIdleMillis() {
    return idleMillis;
  }
  public boolean isActive() {
    return isActive;
  }
  public int getIdleMillisMax() {
    return idleMillisMax;
  }
  public int getLockMillis() {
    return lockMillis;
  }
  public int getNbrOfThreads() {
    return nbrOfThreads;
  }
  public CommandService getCommandExecutor() {
    return commandService;
  }
  public Command<Collection<Long>> getAcquireJobsCommand() {
    return acquireJobsCommand;
  }
  public Command<Date> getNextDueDateCommand() {
    return nextDueDateCommand;
  }
  public DispatcherThread getDispatcherThread() {
    return dispatcherThread;
  }
  public List<JobHistoryEntry> getHistory() {
    return history;
  }

  // configuration setters ////////////////////////////////////////////////////

  public void setCommandExecutor(CommandService commandService) {
    this.commandService = commandService;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setNbrOfJobExecutorThreads(int nbrOfJobExecutorThreads) {
    this.nbrOfThreads = nbrOfJobExecutorThreads;
  }
  public void setIdleInterval(int idleInterval) {
    this.idleMillis = idleInterval;
  }
  public void setMaxIdleInterval(int maxIdleInterval) {
    this.idleMillisMax = maxIdleInterval;
  }
  public void setHistoryMaxSize(int historyMaxSize) {
    this.historySize = historyMaxSize;
  }
  public void setMaxLockTime(int maxLockTime) {
    this.lockMillis = maxLockTime;
  }
}
