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

import java.util.ArrayList;
import java.util.List;

import org.jbpm.internal.log.Log;

/**
 * @author Tom Baeyens
 */
public class JobExecutorThreadPool {
  
  private static final Log log = Log.getLog(JobExecutorThreadPool.class.getName());

  List<JobExecutorThread> jobExecutorThreads = new ArrayList<JobExecutorThread>();
  JobExecutor jobExecutor;

  public JobExecutorThreadPool(JobExecutor jobExecutor) {
    this.jobExecutor = jobExecutor;
  }

  public JobExecutorThread startThread() {
    String threadName = getNextThreadName();
    JobExecutorThread jobExecutorThread = new JobExecutorThread(threadName, jobExecutor);
    jobExecutorThreads.add(jobExecutorThread);
    log.trace("starting "+threadName);
    jobExecutorThread.start();
    return jobExecutorThread;
  }

  public JobExecutorThread stopThread() {
    JobExecutorThread thread = null;
    int lastIndex = jobExecutorThreads.size()-1;
    if (lastIndex>=0) {
      thread = (JobExecutorThread) jobExecutorThreads.remove(lastIndex);
      thread.deactivate();
    }
    return thread;
  }

  public void start() {
    for (int i=0; i<jobExecutor.getNbrOfThreads(); i++) {
      startThread();
    }
  }

  public void deactivate() {
    deactivate(false);
  }

  public void deactivate(boolean join) {
    for (JobExecutorThread jobExecutorThread : jobExecutorThreads) {
      jobExecutorThread.deactivate(join);
    }
  }

  protected String getNextThreadName() {
    return "JobExecutorThread" + jobExecutorThreads.size();
  }

  public List<JobExecutorThread> getJobExecutorThreads() {
    return jobExecutorThreads;
  }
}
