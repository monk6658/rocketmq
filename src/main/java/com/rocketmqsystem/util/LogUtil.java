package com.rocketmqsystem.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 
 ********************************************************* .<br>
 * [类名] LogUtil <br>
 * [描述] 日志公用类 <br>
 * [作者] 磊哥 <br>
 * [时间] 2015-12-7 下午4:35:50 <br>
 ********************************************************* .<br>
 */
@SuppressWarnings("all")
public class LogUtil {
	private StringBuffer msg;
	private static final Logger logger = LogManager.getLogger("dealPack");
	private static final Logger sysError = LogManager.getLogger("sysError");
	private static final Logger initPack = LogManager.getLogger("initPack");
	private static final Logger pushMsgPack = LogManager.getLogger("pushMsgPack");

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] saveNormalLog <br>
	 * [描述] 记录日常日志 <br>
	 * [参数] 异常对象 <br>
	 * [返回] String <br>
	 * [时间] 2015-5-15 下午4:30:57 <br>
	 ********************************************************* .<br>
	 */
	public void saveNormalLog(String str,String type) {
		StringBuffer logmessage = new StringBuffer();
		logmessage.append("【日志类型】：");
		//logmessage.append(InitConfig.logType.get(type));
		logmessage.append("【访问者地址】：");
		//logmessage.append(RunningData.getIn_ip());
		logmessage.append("【操作说明】：");
		logmessage.append(str.toString());
		logger.info(str);
	}
	
	
	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] changeExtInfo <br>
	 * [描述] 处理异常信息 <br>
	 * [参数] 异常对象 <br>
	 * [返回] String <br>
	 * [时间] 2015-5-15 下午4:30:57 <br>
	 ********************************************************* .<br>
	 */
	private String changeExtInfo(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] closeIO <br>
	 * [描述] 关闭IO流 <br>
	 * [参数] socket通讯、输入流、输出流 <br>
	 * [返回] void <br>
	 * [时间] 2015-12-7 上午10:00:34 <br>
	 ********************************************************* .<br>
	 */
	private void closeIO(FileOutputStream fs, OutputStreamWriter ow,
			BufferedWriter bw) {
		try {
			if (bw != null) {
				bw.close();
			}
			if (ow != null) {
				ow.close();
			}
			if (fs != null) {
				fs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] errinfoE <br>
	 * [描述] 记录异常通用错误 <br>
	 * [参数] exp <br>
	 * [返回] void <br>
	 * [时间] 2015-11-30 下午3:51:14 <br>
	 ********************************************************* .<br>
	 */
	public void errinfoE(Exception e) {
		msg = new StringBuffer();
		//msg.append("[Serial]:[").append(RunningData.getSerial());
		//msg.append("]").append(InitConfig.newLine);
		msg.append("[Exp]:").append(changeExtInfo(e));
		sysError.info(msg.toString());
	}
	/**
	 *********************************************************.<br>
	 * [方法] newSaveLog <br>
	 * [描述] 渠道应答 <br>
	 * [参数] 渠道应答日志 <br>
	 * [返回] Map<String,Object> <br>
	 * [时间] 2019-7-11 下午2:13:39 <br>
	 *********************************************************.<br>
	 */
	public synchronized void newSaveLog(Map<String, String> map,String type) throws Exception {
		
		try {
			StringBuffer logmessage = new StringBuffer();
			//logmessage.append("[Type]:[").append(InitConfig.logMessage.get(type)).append("]");
			//logmessage.append(InitConfig.newLine);
			if(map != null){
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
				    Map.Entry entry = (Map.Entry) it.next();
				    logmessage.append("[" + entry.getKey() + "]:[" + entry.getValue() + "]");
				//    logmessage.append(InitConfig.newLine);
			    }
			}
			String logm = logmessage.toString();
			logger.info(logm);
		} catch (Exception e) {
			errinfoE(e);
		}
	}


	public static void main(String[] args) {
		LogUtil logUtil = new LogUtil();
		logUtil.saveNormalLog("","");
	}
	
}
