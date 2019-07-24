package com.rocketmqsystem.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 ********************************************************* .<br>
 * [类名] DateUtil <br>
 * [描述] 日期工具类 <br>
 * [作者] 小段 <br>
 * [时间] 2015-12-7 下午4:18:36 <br>
 ********************************************************* .<br>
 */
public class DateUtil {

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] getCurrentDateTime <br>
	 * [描述] 获取当前系统时间 <br>
	 * [参数] 格式 <br>
	 * [返回] String <br>
	 * [时间] 2015-12-7 下午4:25:24 <br>
	 ********************************************************* .<br>
	 */
	public static String getCurrentDateTime(String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(new Date());
		return strDate;
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] ToStrDate <br>
	 * [描述] 将时间、日期拼接成yyyy-MM-dd hh:mm:ss格式 <br>
	 * [参数] 时间、日期 <br>
	 * [返回] String <br>
	 * [时间] 2015-12-7 下午4:20:13 <br>
	 ********************************************************* .<br>
	 */
	public static String toStrDate(String time, String date) throws Exception {
		StringBuffer dateStr = new StringBuffer();
		dateStr.append(getCurrentDateTime("yyyy"));
		dateStr.append("-");
		dateStr.append(date.substring(0, 2));
		dateStr.append("-");
		dateStr.append(date.substring(2));
		dateStr.append(" ");
		dateStr.append(time.substring(0, 2));
		dateStr.append(":");
		dateStr.append(time.substring(2, 4));
		dateStr.append(":");
		dateStr.append(time.substring(4));
		return dateStr.toString();
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] sub12 <br>
	 * [描述] 将yyyy-MM-dd转换成mmdd <br>
	 * [参数] 日期 <br>
	 * [返回] String <br>
	 * [时间] 2015-12-7 下午4:22:58 <br>
	 ********************************************************* .<br>
	 */
	public static String sub12(String date) throws Exception {
		String[] dates = date.split("-");
		return (DateUtil.appendField(dates[1], dates[2]));
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] sub13 <br>
	 * [描述] 将hh：mm：ss转换成hhmmss <br>
	 * [参数] 时间 <br>
	 * [返回] String <br>
	 * [时间] 2015-12-7 下午4:23:45 <br>
	 ********************************************************* .<br>
	 */
	public static String sub13(String time) throws Exception {
		String[] times = time.split(":");
		return (DateUtil.appendField(times[0], times[1], times[2]));
	}

	/**
	 ********************************************************* .<br>
	 * [方法] appendField <br>
	 * [描述] 拼接字符串 <br>
	 * [参数] 将所有参数拼接 <br>
	 * [返回] String <br>
	 * [时间] 2016-3-10 上午10:43:53 <br>
	 ********************************************************* .<br>
	 */
	public static String appendField(Object ... params) {
		StringBuffer sbf = new StringBuffer();
		for (Object str : params) {
			sbf.append(str);
		}
		return sbf.toString();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] hasMillisecond <br>
	 * [描述] TODO(当前时间到凌晨一点的毫秒数) <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] long <br>
	 * [时间] 2015-9-21 下午4:25:40 <br>
	 *********************************************************.<br>
	 */
	public static long hasMillisecond() throws Exception {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
		Date endDate = dft.parse(dft.format(date.getTime()));
		long between = 0;
		Date begin = dfs.parse(dfs.format(new Date()));
		Date end = dfs.parse(dft.format(endDate) + " 01:00:00.000");
		between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
		return between;
	}
}
