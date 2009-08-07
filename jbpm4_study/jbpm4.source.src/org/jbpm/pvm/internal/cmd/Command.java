package org.jbpm.pvm.internal.cmd;

import java.io.Serializable;

import org.jbpm.pvm.internal.env.Environment;

public interface Command<T> extends Serializable {
  
  T execute(Environment environment) throws Exception;
}
