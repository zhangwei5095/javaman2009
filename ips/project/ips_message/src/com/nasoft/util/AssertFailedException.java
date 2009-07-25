package com.nasoft.util;

import com.nasoft.iso.ISOException;

/**
 * AssertFailedException
 * 
 * @author <a href="mailto:apr@cs.com.uy">Alejandro P. Revilla</a>
 * @version $Revision: 1.3 $ $Date: 2003/10/13 10:46:16 $
 */
public class AssertFailedException extends ISOException
{
  public AssertFailedException()
  {
    super();
  }

  public AssertFailedException(String s)
  {
    super(s);
  }

  public AssertFailedException(Exception nested)
  {
    super(nested);
  }

  public AssertFailedException(String s, Exception nested)
  {
    super(s, nested);
  }
}
