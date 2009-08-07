package org.jbpm.pvm.internal.wire.descriptor;

import org.jbpm.api.ProcessEngine;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.wire.WireContext;

/** the {@link EnvironmentFactory} of the current {@link WireContext}.
 * 
 * @author Tom Baeyens
 * @author Guillaume Porcher (documentation)
 */
public class ProcessEngineDescriptor extends AbstractDescriptor {

  private static final long serialVersionUID = 1L;
  
  protected ProcessEngine processEngine;

  public ProcessEngineDescriptor(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public Object construct(WireContext wireContext) {
    return processEngine;
  }
}
