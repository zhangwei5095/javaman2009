package org.ndot.activemq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Flex_J2ee
 * 
 *<P>
 *
 * 文件名： Sender.java
 * 
 *<P>
 *
 * 功 能:
 * 
 *
 *<P>
 *
 *
 * 作 者: SunJincheng
 * 
 *<P>
 *
 * 创建时间: 2009-9-19
 * 
 */
public class Sender implements Runnable {
	 
	private Context envCtx = null;
	 
	 private ActiveMQConnectionFactory connectionFactory ;
	 
	 private TopicConnection connection ;
	 
	 private TopicSession session ;
	 
	 private ActiveMQTopic destination ;
	 
	 private TopicPublisher publisher;
	 
	 private Random random = new Random();
	 
	 private int i = 0 ;
	 
	 private void initializer(){
	  Context initCtx;
	  try {
	   initCtx = new InitialContext();
//	   envCtx = (Context)initCtx.lookup("java:comp/env");
	   envCtx=initCtx;
	   connectionFactory = (ActiveMQConnectionFactory)envCtx.lookup("java:comp/env/jms/flex/ActiveMqConnectionFactory");
	   destination = (ActiveMQTopic)envCtx.lookup("jms/JMSChat");
	   connection = connectionFactory.createTopicConnection();
	   session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	   publisher = session.createPublisher(destination);
	   connection.start();
	  } catch (NamingException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (JMSException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }  
	 }
	 
	 private void send(){
	  try {
	   TextMessage message = session.createTextMessage();   
	   String value = String.valueOf(random.nextInt(100));
	   message.setText(value);
	   publisher.publish(message);
	   System.out.println("send " + value + " ok.");
	  } catch (JMSException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }  
	 }
	 
	 private void sendObj(){
	  try {
	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");  
	   String time = simpleDateFormat.format(new Date());
	   String index = String.valueOf(i++);
	   String x = String.valueOf(random.nextInt(100));
	   SampleBean bean = new SampleBean();
	   bean.setId(index);
	   bean.setName(time);
	   bean.setValue(x);
	   ObjectMessage objectMessage = session.createObjectMessage();
	   objectMessage.setObject(bean);
	   publisher.send(objectMessage);
	    
	   System.out.println("send obj x="+x+"-------------"+time);
	  } catch (JMSException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 }

	 /* (non-Javadoc)
	  * @see java.lang.Runnable#run()
	  */
	 public void run() {  
	  try {
	   initializer();
	   connection.start();   
	   while(true){
	    sendObj();
	    //int qq = random.nextInt(10);
	    Thread.sleep(1000);    
	   }
	  } catch (JMSException e) {
	   e.printStackTrace();
	  } catch (InterruptedException e) {
	   e.printStackTrace();
	  } catch(Exception e){
	   e.printStackTrace();
	  }
	  
	 }
	 public static void main(String[] args){
			new Thread(new Sender()).start();
	 }
	}

