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

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Allow runtime binding of jPOS's components (ISOChannels, Logger, MUXes, etc)
 * 
 * @author <a href="mailto:apr@cs.com.uy">Alejandro P. Revilla</a>
 * @version $Revision: 1.7 $ $Date: 2003/10/13 10:46:16 $
 */
public class NameRegistrar implements Loggeable
{
  private static NameRegistrar instance = new NameRegistrar();
  private Map                  registrar;

  public static class NotFoundException extends Exception
  {
    public NotFoundException()
    {
      super();
    }

    public NotFoundException(String detail)
    {
      super(detail);
    }
  }

  private NameRegistrar()
  {
    super();
    registrar = new Hashtable();
  }

  public static Map getMap()
  {
    return getInstance().registrar;
  }

  /**
   * @return singleton instance
   */
  public static NameRegistrar getInstance()
  {
    return instance;
  }

  /**
   * register object
   * 
   * @param key -
   *          key with which the specified value is to be associated.
   * @param value -
   *          value to be associated with the specified key
   */
  public static void register(String key, Object value)
  {
    getMap().put(key, value);
  }

  /**
   * @param key
   *          key whose mapping is to be removed from registrar.
   */
  public static void unregister(String key)
  {
    getMap().remove(key);
  }

  /**
   * @param key
   *          key whose associated value is to be returned.
   * @throws NotFoundException
   *           if key not present in registrar
   */
  public static Object get(String key) throws NotFoundException
  {
    Object obj = getMap().get(key);
    if (obj == null)
      throw new NotFoundException(key);
    return obj;
  }

  public void dump(PrintStream p, String indent)
  {
    String inner = indent + "  ";
    p.println(indent + "<name-registrar>");
    Iterator iter = registrar.entrySet().iterator();
    while (iter.hasNext())
    {
      Map.Entry entry = (Map.Entry) iter.next();
      p.println(inner + "<name>" + entry.getKey().toString() + "</name>");
      p.println(inner + "<class>" + entry.getValue().getClass().getName()
          + "</class>");
    }
    p.println(indent + "</name-registrar>");
  }
}
