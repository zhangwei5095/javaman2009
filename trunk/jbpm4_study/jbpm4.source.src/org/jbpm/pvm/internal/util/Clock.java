package org.jbpm.pvm.internal.util;

import java.util.Date;

public abstract class Clock {
  
  protected static Date currentTime = null;
  
  public static Date getCurrentTime() {
    if (currentTime==null) {
      return new Date();
    }
    return currentTime;
  }
  
  public static void setCurrentTime(Date currentTime) {
    Clock.currentTime = currentTime;
  }
}
