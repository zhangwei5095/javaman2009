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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.job.JobImpl;
import org.jbpm.pvm.internal.session.DbSession;


/**
 * @author Tom Baeyens
 */
public class AcquireJobsCmd implements Command<Collection<Long>> {

  private static final long serialVersionUID = 1L;

  private static final Log log = Log.getLog(AcquireJobsCmd.class.getName());
  private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS");
  
  JobExecutor jobExecutor;

  public AcquireJobsCmd(JobExecutor jobExecutor) {
    this.jobExecutor = jobExecutor;
  }

  public Collection<Long> execute(Environment environment) throws Exception {
    Collection<Long> acquiredJobDbids = new ArrayList<Long>();

    Collection<JobImpl<?>> acquiredJobs = new ArrayList<JobImpl<?>>();
    
    DbSession dbSession = environment.get(DbSession.class);
    log.debug("start querying first acquirable job...");

    JobImpl<?> job = dbSession.findFirstAcquirableJob();

    if (job!=null) {
      if (job.isExclusive()) {
        log.trace("exclusive acquirable job found ("+job+"). querying for other exclusive jobs to lock them all in one tx...");
        List<JobImpl<?>> otherExclusiveJobs = dbSession.findExclusiveJobs(job.getProcessInstance());
        acquiredJobs.addAll(otherExclusiveJobs);
      } else {
        acquiredJobs.add(job);
      }

      for (JobImpl<?> acquiredJob: acquiredJobs) {
        long lockExpirationTime = System.currentTimeMillis()+jobExecutor.getLockMillis();
        log.trace("trying to obtain a lock for '"+acquiredJob+"' with exp "+timeFormat.format(new Date(lockExpirationTime)));
        acquiredJob.acquire(jobExecutor.getName(), new Date(lockExpirationTime));
        acquiredJobDbids.add(acquiredJob.getDbid());
      }

    } else {
      log.trace("no acquirable jobs in job table");
    }
    
    log.debug("locking jobs "+acquiredJobDbids);
    
    return acquiredJobDbids;
  }

}
