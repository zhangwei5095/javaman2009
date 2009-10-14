/*
 * Copyright (c) 2004 jPOS.org 
 *
 * See terms of license at http://jpos.org/license.html
 *
 */
package com.nasoft.util;

import java.util.Iterator;

/**
 * A specific log listener that filters all LogEvents that doesn't contain any
 * exception.
 * 
 * @author Alejandro Revilla
 * @version $Revision: 1.1 $ $Date: 2004/06/10 01:15:02 $
 */
public class ExceptionLogFilter implements LogListener
{
  public ExceptionLogFilter()
  {
    super();
  }

  public synchronized LogEvent log(LogEvent evt)
  {
    Iterator iter = evt.getPayLoad().iterator();
    while (iter.hasNext())
    {
      if (iter.next() instanceof Throwable)
        return evt;
    }
    return null;
  }
}
