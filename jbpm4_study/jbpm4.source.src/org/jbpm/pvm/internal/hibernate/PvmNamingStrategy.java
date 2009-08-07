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
package org.jbpm.pvm.internal.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.util.StringHelper;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.util.ReflectUtil;

/**
 * @author Tom Baeyens
 */
public class PvmNamingStrategy extends ImprovedNamingStrategy {
  
  private static final long serialVersionUID = 1L;
  private static final Log log = Log.getLog(PvmNamingStrategy.class.getName());

  String prefix;
  
  public PvmNamingStrategy(String prefix) {
    if (! "".equals(prefix)) {
      this.prefix = prefix;
    }
  }

  // TABLE NAMES //////////////////////////////////////////////////////////////
  
  public String classToTableName(String className) {
    String name = ReflectUtil.getUnqualifiedClassName(className);
    if (name.endsWith("Impl")) {
      name = name.substring(0, name.length()-4);
    }
    name = prefix+name.toUpperCase();
    if (log.isTraceEnabled()) {
      log.trace("classToTableName("+className+") ==> "+name);
    }
    return name;
  }

  public String tableName(String tableName) {
    if (prefix!=null) {
      return prefix+tableName;
    }
    if (log.isTraceEnabled()) {
      log.trace("tableName("+tableName+") ==> "+tableName);
    }
    return tableName;
  }
  
  public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
    String name = StringHelper.unqualify(propertyName);
    name = prefix+name.toUpperCase()+"_"; 
    if (log.isTraceEnabled()) {
      log.trace("collectionTableName("+ownerEntity+", "+ownerEntityTable+", "+associatedEntity+", "+associatedEntityTable+", "+propertyName+") ==> "+name);
    }
    return name;
  }

  // COLUMN NAMES /////////////////////////////////////////////////////////////

  public String propertyToColumnName(String propertyName) {
    String name = propertyName;
    
    name = StringHelper.unqualify(name);
    name = name.toUpperCase()+"_";
    
    if (log.isTraceEnabled()) {
      log.trace("propertyToColumnName("+propertyName+") ==> "+name);
    }

    return name;
  }
  
  public String logicalColumnName(String columnName, String propertyName) {
    String name = (columnName!=null ? columnName : propertyName);
    name = name.replace('.', '_');
    name = name.toUpperCase();
    if (!name.endsWith("_")) {
      name = name+"_";
    }
    if (log.isTraceEnabled()) {
      log.trace("logicalColumnName("+columnName+", "+propertyName+") ==> "+name);
    }
    return name;
  }

  public String columnName(String columnName) {
    String name = columnName.toUpperCase();
    if (log.isTraceEnabled()) {
      log.trace("columnName("+columnName+") ==> "+name);
    }
    return name;
  }

  public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
    String name = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
    if (log.isTraceEnabled()) {
      log.trace("foreignKeyColumnName("+propertyName+", "+propertyEntityName+", "+propertyTableName+", "+referencedColumnName+") ==> "+name);
    }
    return name;
  }

  public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
    String name = super.logicalCollectionColumnName(columnName, propertyName, referencedColumn);
    if (log.isTraceEnabled()) {
      log.trace("logicalCollectionColumnName("+columnName+", "+propertyName+", "+referencedColumn+") ==> "+name);
    }
    return name;
  }

  public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
    String name = super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName);
    if (log.isTraceEnabled()) {
      log.trace("logicalCollectionTableName("+tableName+", "+ownerEntityTable+", "+associatedEntityTable+", "+propertyName+") ==> "+name);
    }
    return name;
  }

  public String joinKeyColumnName(String joinedColumn, String joinedTable) {
    String name = super.joinKeyColumnName(joinedColumn, joinedTable);
    if (log.isTraceEnabled()) {
      log.trace("joinKeyColumnName("+joinedColumn+", "+joinedTable+") ==> "+name);
    }
    return name;
  }
}
