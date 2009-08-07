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

import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cmd.CommandService;
import org.jbpm.pvm.internal.cmd.ExecuteJobCmd;


/**
 * @author Tom Baeyens, Guillaume Porcher
 */
public class JobExecutorThread extends Thread {
  
  private static final Log log = Log.getLog(JobExecutorThread.class.getName());
  
  JobExecutor jobExecutor; 
  boolean isActive = true;

  public JobExecutorThread(String name, JobExecutor jobExecutor) {
    super(name);
    this.jobExecutor = jobExecutor;
  }

  public void run() {
    log.info("starting...");
    try {
      BlockingQueue<Collection<Long>> queue = jobExecutor.getJobDbidsQueue();
      if (queue == null) {
    	  log.debug("no queue to take jobs from");
      } else {
        while (isActive) {
          try {
            log.trace("taking jobs from queue");
            Collection<Long> jobDbids = null;
            jobDbids = queue.take();
            log.debug("took job(s) "+jobDbids+" from queue");

            for (Long jobDbid: jobDbids) {
              CommandService commandService = jobExecutor.getCommandExecutor();
              commandService.execute(new ExecuteJobCmd(jobDbid));
            }
          } catch (InterruptedException e) {
            log.trace("waiting for acquired jobs got interrupted");
          } catch (Exception e) {
            log.error("exception in job executor thread", e);
          }
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      log.info(getName()+" leaves cyberspace");
    }
  }
  
  public void deactivate() {
    deactivate(false);
  }
  
  public void deactivate(boolean join) {
    if (isActive) {
      log.trace("deactivating "+getName());
      isActive = false;
      interrupt();
      if (join) {
        while (isAlive()) {
          try {
            join();
          } catch (InterruptedException e) {
            log.trace("joining "+getName()+" got interrupted");
          }
        }
      }
    } else {
      log.trace("ignoring deactivate: "+getName()+" is not active");
    }
  }
}
