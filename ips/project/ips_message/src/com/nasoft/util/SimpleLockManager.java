/*
 * Copyright (c) 2000 jPOS.org.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the jPOS project 
 *    (http://www.jpos.org/)". Alternately, this acknowledgment may 
 *    appear in the software itself, if and wherever such third-party 
 *    acknowledgments normally appear.
 *
 * 4. The names "jPOS" and "jPOS.org" must not be used to endorse 
 *    or promote products derived from this software without prior 
 *    written permission. For written permission, please contact 
 *    license@jpos.org.
 *
 * 5. Products derived from this software may not be called "jPOS",
 *    nor may "jPOS" appear in their name, without prior written
 *    permission of the jPOS project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
 * IN NO EVENT SHALL THE JPOS PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the jPOS Project.  For more
 * information please see <http://www.jpos.org/>.
 */

package com.nasoft.util;

/*
 * $Log: SimpleLockManager.java,v $ Revision 1.6 2003/10/13 10:46:16 apr tabs
 * expanded to spaces
 * 
 * Revision 1.5 2003/05/16 04:11:04 alwyns Import cleanups.
 * 
 * Revision 1.4 2000/11/02 12:09:18 apr Added license to every source file
 * 
 * Revision 1.3 2000/03/01 14:44:45 apr Changed package name to org.jpos
 * 
 * Revision 1.2 2000/02/03 00:41:55 apr .
 * 
 * Revision 1.1 2000/01/30 23:32:53 apr pre-Alpha - CVS sync
 * 
 */

/**
 * @author apr@cs.com.uy
 * @since jPOS 1.1
 * @version $Id: SimpleLockManager.java,v 1.6 2003/10/13 10:46:16 apr Exp $
 */

import java.util.Hashtable;
import java.util.Map;

public class SimpleLockManager implements LockManager
{
  Map locks;

  public SimpleLockManager()
  {
    locks = new Hashtable();
  }

  public class SimpleTicket implements Ticket
  {
    String resourceName;
    long   expiration;

    public SimpleTicket(String resourceName, long duration)
    {
      this.resourceName = resourceName;
      this.expiration = System.currentTimeMillis() + duration;
    }

    public boolean renew(long duration)
    {
      if (!isExpired())
      {
        this.expiration = System.currentTimeMillis() + duration;
        return true;
      }
      return false;
    }

    public long getExpiration()
    {
      return expiration;
    }

    public boolean isExpired()
    {
      return System.currentTimeMillis() > expiration;
    }

    public String getResourceName()
    {
      return resourceName;
    }

    public void cancel()
    {
      expiration = 0;
      locks.remove(resourceName);
      synchronized (this)
      {
        notify();
      }
    }

    public String toString()
    {
      return super.toString() + "[" + resourceName + "/" + isExpired() + "/"
          + (expiration - System.currentTimeMillis()) + "ms left]";
    }
  }

  public Ticket lock(String resourceName, long duration, long wait)
  {
    long maxWait = System.currentTimeMillis() + wait;

    while (System.currentTimeMillis() < maxWait)
    {
      Ticket t = null;
      synchronized (this)
      {
        t = (Ticket) locks.get(resourceName);
        if (t == null)
        {
          t = new SimpleTicket(resourceName, duration);
          locks.put(resourceName, t);
          return t;
        } else if (t.isExpired())
        {
          t.cancel();
          continue;
        }
      }
      synchronized (t)
      {
        try
        {
          t.wait(Math.min(1000, wait));
        } catch (InterruptedException e)
        {
        }
      }
    }
    return null;
  }
}
