package com.nasoft.util;

/**
 * @author Alejandro P. Revilla
 * @version $Revision: 1.2 $ $Date: 2003/10/13 10:46:16 $
 */
public class Asserter
{
  public Asserter()
  {
    super();
  }

  public Asserter check(Object obj) throws AssertFailedException
  {
    if (obj == null)
      throw new AssertFailedException();
    return this;
  }

  public Asserter check(Object obj1, Object obj2) throws AssertFailedException
  {
    check(obj1).check(obj2);
    if (!obj1.equals(obj2))
      throw new AssertFailedException();
    return this;
  }

  public Asserter check(boolean b) throws AssertFailedException
  {
    if (!b)
      throw new AssertFailedException();
    return this;
  }
}
