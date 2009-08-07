package org.jbpm.pvm.internal.svc;

import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;

public interface AuthorizationSession {

  void checkPermission(Command<?> command, Environment environment);
}
