package com.dmm.ip.util;
 
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
 
/**
 * 
 * 用于解析的纯真数据库的日志工厂
 * 日志工厂
 */
public class LogFactory {
	
	private static final Logger logger;
	
	static {
		logger = Logger.getLogger("stdout");
		logger.setLevel(Level.DEBUG);
	}
 
	public static void log(String info, Level level, Throwable ex) {
		logger.log(level, info, ex);
	}
	
	public static Level  getLogLevel(){
		return logger.getLevel();
	}
 
}