package org.jbpm.pvm.internal.util;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.jbpm.api.JbpmException;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.wire.descriptor.ArgDescriptor;

public abstract class ReflectUtil {

  private static Log log = Log.getLog(ReflectUtil.class.getName());

  static ClassLoader resolveClassLoader(ClassLoader classLoader) {
    // 1) if the user provided a classloader through the API, use that one
    if (classLoader!=null) {
      log.trace("using provided classloader");
      return classLoader;
    }
    
    // 2) if the user provided a classloader through the environment, use that one
    Environment environment = Environment.getCurrent();
    if (environment!=null) {
      classLoader = environment.getClassLoader();
      if (classLoader!=null) {
        log.trace("using environment classloader");
        return classLoader;
      }
    }

    // 3) otherwise, use the current thread's context classloader
    log.trace("using context classloader");
    return Thread.currentThread().getContextClassLoader();
  }

  public static Class<?> loadClass(ClassLoader classLoader, String className) {
    if (classLoader!=null) {
      try {
        return classLoader.loadClass(className);
      } catch (ClassNotFoundException e) {
        log.trace("couldn't load class "+className+" with given classloader "+classLoader);
      }
    }
    try {
      return Thread.currentThread().getContextClassLoader().loadClass(className);
    } catch (ClassNotFoundException e) {
      log.trace("couldn't load class "+className+" with context classloader "+classLoader);
    }
    try {
      return ReflectUtil.class.getClassLoader().loadClass(className);
    } catch (ClassNotFoundException e) {
      log.trace("couldn't load class "+className+" with pvm lib classloader "+classLoader);
    }
    throw new JbpmException("couldn't load class "+className);
  }
  
  public static InputStream getResourceAsStream(ClassLoader classLoader, String resource) {
    classLoader = resolveClassLoader(classLoader);
    log.trace("getting resource as stream "+resource);
    return classLoader.getResourceAsStream(resource);
  }
  
  public static Enumeration<URL> getResources(ClassLoader classLoader, String resource) {
    classLoader = resolveClassLoader(classLoader);
    try {
      log.trace("getting resources "+resource);
      return classLoader.getResources(resource);
    } catch (Exception e) {
      throw new JbpmException("couldn't get resources "+resource, e);
    }
  }
  
  public static URL getResource(ClassLoader classLoader, String resource) {
    classLoader = resolveClassLoader(classLoader);
    try {
      log.trace("getting resource "+resource);
      return classLoader.getResource(resource);
    } catch (Exception e) {
      throw new JbpmException("couldn't get resources "+resource, e);
    }
  }
  
  public static Object instantiate(ClassLoader classLoader, String className) {
    Object newObject;
    try {
      classLoader = resolveClassLoader(classLoader);
      Class<?> clazz = loadClass(classLoader, className);
      log.trace("instantiating "+className);
      newObject = clazz.newInstance();
    } catch (Exception e) {
      throw new JbpmException("couldn't instantiate "+className, e);
    }
    return newObject;
  }

  public static Class<?>[] loadClasses(ClassLoader classLoader, List<String> constructorArgTypeNames) {
    if (constructorArgTypeNames==null) return null;
    Class<?>[] classes = new Class[constructorArgTypeNames.size()];
    for (int i=0; i<constructorArgTypeNames.size(); i++) {
      classLoader = resolveClassLoader(classLoader);
      classes[i] = loadClass(classLoader, constructorArgTypeNames.get(i));
    }
    return classes;
  }

  public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] parameterTypes) {
    Constructor<T> constructor = null;
    try {
      constructor = clazz.getDeclaredConstructor(parameterTypes);
      
      if (log.isTraceEnabled()) log.trace("found constructor "+clazz.getName()+"("+Arrays.toString(parameterTypes)+")");
      
    } catch (SecurityException e) {
      throw new JbpmException("wasn't allowed to get constructor '"+clazz.getName()+"("+getParameterTypesText(parameterTypes)+")'", e);
    } catch (NoSuchMethodException e) {
      throw new JbpmException("couldn't find constructor '"+clazz.getName()+"("+getParameterTypesText(parameterTypes)+")'", e);
    }

    return constructor;
  }

  public static Field getField(Class<?> clazz, String fieldName) {
    return getField(clazz, fieldName, clazz);
  }

  private static Field getField(Class<?> clazz, String fieldName, Class<?> original) {
    Field field = null;

    try {
      field = clazz.getDeclaredField(fieldName);
      log.trace("found field "+fieldName+" in "+clazz.getName());
    } catch (SecurityException e) {
      throw new JbpmException("wasn't allowed to get field '"+clazz.getName()+"."+fieldName+"'", e);
    } catch (NoSuchFieldException e) {
      if (clazz.getSuperclass()!=null) {
        return getField(clazz.getSuperclass(), fieldName, original);
      } else {
        throw new JbpmException("couldn't find field '"+original.getName()+"."+fieldName+"'", e);
      }
    }
    
    return field;
  }

  public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
    return getMethod(clazz, methodName, parameterTypes, clazz);
  }

  private static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Class<?> original) {
    Method method = null;
    
    try {
      method = clazz.getDeclaredMethod(methodName, parameterTypes);
      
      if (log.isTraceEnabled()) 
        log.trace("found method "+clazz.getName()+"."+methodName+"("+Arrays.toString(parameterTypes)+")");

    } catch (SecurityException e) {
      throw new JbpmException("wasn't allowed to get method '"+clazz.getName()+"."+methodName+"("+getParameterTypesText(parameterTypes)+")'", e);
    } catch (NoSuchMethodException e) {
      if (clazz.getSuperclass()!=null) {
        return getMethod(clazz.getSuperclass(), methodName, parameterTypes, original);
      } else {
        throw new JbpmException("couldn't find method '"+original.getName()+"."+methodName+"("+getParameterTypesText(parameterTypes)+")'", e);
      }
    }
    
    return method;
  }

  private static String getParameterTypesText(Class<?>[] parameterTypes) {
    if (parameterTypes==null) return "";
    StringBuilder parameterTypesText = new StringBuilder();
    for (int i=0; i<parameterTypes.length; i++) {
      Class<?> parameterType = parameterTypes[i];
      parameterTypesText.append(parameterType.getName());
      if (i!=parameterTypes.length-1) {
        parameterTypesText.append(", ");
      }
    }
    return parameterTypesText.toString();
  }

  public static <T> T newInstance(Class<T> clazz) {
    return newInstance(clazz, null, null);
  }
  public static <T> T newInstance(Constructor<T> constructor) {
    return newInstance(null, constructor, null);
  }
  public static <T> T newInstance(Constructor<T> constructor, Object[] args) {
    return newInstance(null, constructor, args);
  }
  
  private static <T> T newInstance(Class<T> clazz, Constructor<T> constructor, Object[] args) {
    if ( (clazz==null)
         && (constructor==null)
       ) {
      throw new IllegalArgumentException("can't create new instance without clazz or constructor");
    }

    String className = null;
    try {
      log.trace("creating new instance for class '"+className+"' with args "+Arrays.toString(args));
      if (constructor==null) {
        log.trace("getting default constructor");
        constructor = clazz.getConstructor((Class[])null);
      }
      className = constructor.getDeclaringClass().getName();
      if (!constructor.isAccessible()) {
        log.trace("making constructor accessible");
        constructor.setAccessible(true);
      }
      return constructor.newInstance(args);

    } catch (Throwable t) {
      throw new JbpmException("couldn't construct new '"+className+"' with args "+Arrays.toString(args), t);
    }
  }
  
  public static Object get(Field field, Object object) {
    if (field==null) {
      throw new NullPointerException("field is null");
    }
    try {
      Object value = field.get(object);
      log.trace("got value '"+value+"' from field '"+field.getName()+"'");
      return value;
    } catch (Exception e) {
      throw new JbpmException("couldn't get '"+field.getName()+"'", e);
    }
  }

  public static void set(Field field, Object object, Object value) {
    if (field==null) {
      throw new NullPointerException("field is null");
    }
    try {
      log.trace("setting field '"+field.getName()+"' to value '"+value+"'");
      if (!field.isAccessible()) {
        log.trace("making field accessible");
        field.setAccessible(true);
      }
      field.set(object, value);
    } catch (Exception e) {
      throw new JbpmException("couldn't set '"+field.getName()+"' to '"+value+"'", e);
    }
  }
  
  public static Object invoke(Method method, Object target, Object[] args) {
    if (method==null) {
      throw new JbpmException("method is null");
    }
    try {
      log.trace("invoking '"+method.getName()+"' on '"+target+"' with "+Arrays.toString(args));
      if (!method.isAccessible()) {
        log.trace("making method accessible");
        method.setAccessible(true);
      }
      return method.invoke(target, args);
    } catch (InvocationTargetException e) {
      Throwable targetException = e.getTargetException();
      throw new JbpmException("couldn't invoke '"+method.getName()+"' with "+Arrays.toString(args)+" on "+target+": "+targetException.getMessage(), targetException);
    } catch (Exception e) {
      throw new JbpmException("couldn't invoke '"+method.getName()+"' with "+Arrays.toString(args)+" on "+target+": "+e.getMessage(), e);
    }
  }

  public static Method findMethod(Class<?> clazz, String methodName, List<ArgDescriptor> argDescriptors, Object[] args) {
    log.trace("searching for method "+methodName+" in "+clazz.getName());
    Method[] candidates = clazz.getDeclaredMethods();
    for (int i=0; i<candidates.length; i++) {
      Method candidate = candidates[i];
      if ( (candidate.getName().equals(methodName))
           && (isArgumentMatch(candidate.getParameterTypes(), argDescriptors, args))
         ) {

        if (log.isTraceEnabled()) {
          log.trace("found matching method "+clazz.getName()+"."+methodName);
        }
        
        return candidate;
      }
    }
    if (clazz.getSuperclass()!=null) {
      return findMethod(clazz.getSuperclass(), methodName, argDescriptors, args);
    }
    return null;
  }

  public static Constructor<?> findConstructor(Class<?> clazz, List<ArgDescriptor> argDescriptors, Object[] args) {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    for (int i=0; i<constructors.length; i++) {
      if (isArgumentMatch(constructors[i].getParameterTypes(), argDescriptors, args)) {
        return constructors[i];
      }
    }
    return null;
  }

  public static boolean isArgumentMatch(Class<?>[] parameterTypes, List<ArgDescriptor> argDescriptors, Object[] args) {
    int nbrOfArgs = 0;
    if (args!=null) nbrOfArgs = args.length;
    
    int nbrOfParameterTypes = 0;
    if (parameterTypes!=null) nbrOfParameterTypes = parameterTypes.length;
    
    if ( (nbrOfArgs==0)
         && (nbrOfParameterTypes==0)
       ) {
      return true;
    }
    
    if (nbrOfArgs!=nbrOfParameterTypes) {
      return false;
    }

    for (int i=0; (i<parameterTypes.length); i++) {
      Class<?> parameterType = parameterTypes[i];
      String argTypeName = (argDescriptors!=null ? argDescriptors.get(i).getTypeName() : null);
      if (argTypeName!=null) {
         if (! argTypeName.equals(parameterType.getName())) {
           return false;
         }
      } else if ( (args[i]!=null)
                  && (! parameterType.isAssignableFrom(args[i].getClass()))
                ) {
        return false;
      }
    }
    return true;
  }

  public static String getSignature(String methodName, List<ArgDescriptor> argDescriptors, Object[] args) {
    String signature = methodName+"(";
    if (args!=null) {
      for (int i=0; i<args.length; i++) {
        String argType = null;
        if (argDescriptors!=null) {
          ArgDescriptor argDescriptor = argDescriptors.get(i);
          if ( (argDescriptor!=null)
               && (argDescriptor.getTypeName()!=null)
             ) {
            argType = argDescriptor.getTypeName(); 
          }
        }
        if ( (argType==null)
             && (args[i]!=null)
           ) {
          argType = args[i].getClass().getName();
        }
        signature += argType;
        if (i<(args.length-1)) {
          signature += ", ";
        }
      }
    }
    signature+=")";
    return signature;
  }
  
  public static String getUnqualifiedClassName(Class<?> clazz) {
    if (clazz==null) {
      return null;
    }
    return getUnqualifiedClassName(clazz.getSimpleName());
  }

  public static String getUnqualifiedClassName(String className) {
    if (className==null) {
      return null;
    }
    int dotIndex = className.lastIndexOf('.');
    if (dotIndex!=-1) {
      className = className.substring(dotIndex+1);
    }
    return className;
  }
}
