/*
 * Copyright (c) 2004 jPOS.org 
 *
 * See terms of license at http://jpos.org/license.html
 *
 */

package com.nasoft.iso;

/**
 * Channels that can use socket factories need to implement this.
 * 
 * @author <a href="mailto:alwynschoeman@yahoo.com">Alwyn Schoeman</a>
 * @version $Revision: 1.1 $ $Date: 2004/05/05 14:20:43 $
 */

public interface FactoryChannel
{
  /**
   * @param sfac
   *          a socket factory
   */
  public void setSocketFactory(ISOClientSocketFactory sfac);
}
