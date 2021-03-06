package org.jbpm.pvm.internal.wire.binding;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.JbpmException;
import org.jbpm.pvm.internal.util.ReflectUtil;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.descriptor.CollectionDescriptor;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

public abstract class AbstractCollectionBinding extends WireDescriptorBinding {

  public AbstractCollectionBinding(String tagName) {
    super(tagName);
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    CollectionDescriptor descriptor = createDescriptor();
    
    String className = XmlUtil.attribute(element,"class");
    
    // verify if the given classname is specified and implements the collection interface
    if (verify(className, getCollectionInterface(), parse, parser)) {
      descriptor.setClassName(className);
    }
    
    Boolean isSynchronized = XmlUtil.attributeBoolean(element, "synchronized", false, parse);
    if (isSynchronized!=null) {
      descriptor.setSynchronized(isSynchronized.booleanValue());
    }
    
    List<Descriptor> valueDescriptors = new ArrayList<Descriptor>();
    List<Element> elements = XmlUtil.elements(element);
    for (Element valueElement: elements) {
      Descriptor valueDescriptor = (Descriptor) parser.parseElement(valueElement, parse, WireParser.CATEGORY_DESCRIPTOR);
      if (valueDescriptor!=null) {
        valueDescriptors.add(valueDescriptor);
      }
    }
    descriptor.setValueDescriptors(valueDescriptors);
    return descriptor;
  }

  /** verifies if the given classname is specified and implements the collection interface */
  public static boolean verify(String className, Class< ? > collectionInterface, Parse parse, Parser parser) {
    if (className==null) {
      return false;
    }

    try {
      Class<?> collectionClass = ReflectUtil.loadClass(parse.getClassLoader(), className);
      
      if (collectionInterface.isAssignableFrom(collectionClass)) {
        return true;
      } else {
        parse.addProblem("class "+ className+" is not a "+collectionInterface.getName());
      }
    } catch (JbpmException e) {
      parse.addProblem("class "+className+" could not be found");
    }
    return false;
  }

  protected abstract Class<?> getCollectionInterface();
  protected abstract CollectionDescriptor createDescriptor();
}
