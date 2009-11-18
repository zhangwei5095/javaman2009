package org.ndot.adapter;
import flex.messaging.Destination;
import flex.messaging.MessageDestination;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.messaging.adapters.MessagingAdapter;
/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Flex_J2ee
 * 
 *<P>
 *
 * 文件名： SimpleCustomAdapter.java
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
 * 创建时间: 2009-9-18
 * 
 */
public class SimpleCustomAdapter extends MessagingAdapter{
	 private SimpleCustomAdapterControl controller;

	public SimpleCustomAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleCustomAdapter(boolean enableManagement) {
		super(enableManagement);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object invoke(Message message) {
		MessageService msgService = (MessageService)message;
		msgService.pushMessageToClients(message, true);
		msgService.sendPushMessageFromPeer(message, true);
		return null;
	}
	@Override
	public void setDestination(Destination destination)
    {
        Destination dest = (MessageDestination)destination;
        super.setDestination(dest);
    }
	@Override
	 protected void setupAdapterControl(Destination destination)
	    {
	        controller = new SimpleCustomAdapterControl(this, destination.getControl());
	        controller.register();
	        setControl(controller);
	    }
}
