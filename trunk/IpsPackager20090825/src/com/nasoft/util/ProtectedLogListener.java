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

import java.util.Vector;

import com.nasoft.core.Configurable;
import com.nasoft.core.Configuration;
import com.nasoft.core.ConfigurationException;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOField;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOUtil;


/**
 * Protects selected fields from LogEvents.
 * 
 * ProtectedLogListener acts like a filter for Event logs, it should be defined
 * _before_ other standard LogListeners such as SimpleLogListener or
 * RotateLogListeners.<br>
 * i.e.
 * 
 * <pre>
 *  &lt;logger name=&quot;qsp&quot;&gt;
 *    &lt;log-listener class=&quot;org.jpos.util.SimpleLogListener&quot;/&gt;
 *    &lt;log-listener class=&quot;org.jpos.util.ProtectedLogListener&quot;&gt;
 *      &lt;property name=&quot;protect&quot; value=&quot;2 35 45 55&quot; /&gt;
 *      &lt;property name=&quot;wipe&quot;    value=&quot;48&quot; /&gt;
 *    &lt;/log-listener&gt;
 *    &lt;log-listener class=&quot;org.jpos.util.RotateLogListener&quot;&gt;
 *      &lt;property name=&quot;file&quot; value=&quot;/tmp/qsp.log&quot; /&gt;
 *      &lt;property name=&quot;window&quot; value=&quot;86400&quot; /&gt;
 *      &lt;property name=&quot;copies&quot; value=&quot;5&quot; /&gt;
 *      &lt;property name=&quot;maxsize&quot; value=&quot;1000000&quot; /&gt;
 *    &lt;/log-listener&gt;
 *  &lt;/logger&gt;
 * </pre>
 * 
 * Order is important. In the previous example SimpleLogListener will dump
 * unprotected LogEvents while RotateLogListener will dump protected ones (for
 * selected fields)
 * 
 * @author <a href="mailto:apr@cs.com.uy">Alejandro P. Revilla</a>
 * @version $Revision: 1.6 $ $Date: 2003/10/13 10:46:16 $
 * @see com.nasoft.core.Configurable
 * @since jPOS 1.3.3
 */
public class ProtectedLogListener implements LogListener, Configurable
{
  int[]         protectFields = null;
  int[]         wipeFields    = null;
  Configuration cfg           = null;

  public ProtectedLogListener()
  {
    super();
  }

  /**
   * Configure this ProtectedLogListener<br>
   * Properties:<br>
   * <ul>
   * <li>[protect] blank separated list of fields to be protected
   * <li>[wipe] blank separated list of fields to be wiped
   * </ul>
   * 
   * @param cfg
   *          Configuration
   * @throws ConfigurationException
   */
  public void setConfiguration(Configuration cfg) throws ConfigurationException
  {
    this.cfg = cfg;
    protectFields = ISOUtil.toIntArray(cfg.get("protect", ""));
    wipeFields = ISOUtil.toIntArray(cfg.get("wipe", ""));
  }

  public synchronized LogEvent log(LogEvent ev)
  {
    Vector payLoad = ev.getPayLoad();
    int size = payLoad.size();
    for (int i = 0; i < size; i++)
    {
      Object obj = payLoad.elementAt(i);
      if (obj instanceof ISOMsg)
      {
        ISOMsg m = (ISOMsg) ((ISOMsg) obj).clone();
        try
        {
          checkProtected(m);
          checkHidden(m);
        } catch (ISOException e)
        {
          ev.addMessage(e);
        }
        payLoad.setElementAt(m, i);
      }
    }
    return ev;
  }

  private void checkProtected(ISOMsg m) throws ISOException
  {
    for (int i = 0; i < protectFields.length; i++)
    {
      int f = protectFields[i];
      if (m.hasField(f))
        m.set(new ISOField(f, ISOUtil.protect(m.getString(f))));
    }
  }

  private void checkHidden(ISOMsg m) throws ISOException
  {
    for (int i = 0; i < wipeFields.length; i++)
    {
      int f = wipeFields[i];
      if (m.hasField(f))
        m.set(new ISOField(f, "[WIPED]"));
    }
  }
}
