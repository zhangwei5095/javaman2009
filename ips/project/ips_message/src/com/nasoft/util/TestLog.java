package com.nasoft.util;


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;



public class TestLog {
	private  final String logFileName = "/home/tibco/logs/javalog.log";
	
//	private  final String logFileName = "c:\\NDotLogs.log";

	private  Logger log4j;
	
	private static TestLog testLog;
	
	static {
		testLog=new TestLog();
	}
	
	public static TestLog getNewInstance(){
		return testLog;
	}

	private TestLog(){
		try {
			log4j = null;
			log4j = LogManager.getLogger(TestLog.class);
			String fileName = logFileName;
			DailyRollingFileAppender appender = null;
			String pattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p:%m%n";
			PatternLayout layout = new PatternLayout();
			log4j.removeAllAppenders();

			try{
				layout.setConversionPattern(pattern);
				appender = new DailyRollingFileAppender(layout, fileName,
						"'_'yyyyMMdd'.log'");
				appender.setAppend(true);
				log4j.addAppender(appender);
			}catch(Exception e1){
//				e1.printStackTrace();
			}
			

			layout.setConversionPattern(pattern);
			ConsoleAppender appender1 = new ConsoleAppender(layout);
			log4j.addAppender(appender1);
			
			log4j.setLevel((Level) Level.INFO);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void writeLog(String msg) {
		log4j.info(msg);
	}
	
	public static void main(String[] args){
		TestLog log=new TestLog();
		log.writeLog("ddddddddddddddd");
	}
}
