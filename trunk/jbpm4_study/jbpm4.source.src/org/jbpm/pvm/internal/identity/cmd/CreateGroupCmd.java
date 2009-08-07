/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.pvm.internal.identity.cmd;

import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;


/**
 * @author Tom Baeyens
 */
public class CreateGroupCmd implements Command<String> {

  private static final long serialVersionUID = 1L;

  protected String groupName;
  protected String groupType;
  protected String parentGroupId;

  public CreateGroupCmd(String groupName, String groupType, String parentGroupId) {
    this.groupName = groupName;
    this.groupType = groupType;
    this.parentGroupId = parentGroupId;
  }

  public String execute(Environment environment) throws Exception {
    IdentitySession identitySession = environment.get(IdentitySession.class);
    return identitySession.createGroup(groupName, groupType, parentGroupId);
  }
}
