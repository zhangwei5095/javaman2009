package org.ndot.ips.comm;


public class ChannelContral  implements Runnable{
		IPSReportChannel channel;

		public ChannelContral(IPSReportChannel channel) {
			this.channel = channel;
		}

		public void run() {
			try {
				if (channel.isStop()) {
					channel.close();
				}else{
					channel.runServer();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

