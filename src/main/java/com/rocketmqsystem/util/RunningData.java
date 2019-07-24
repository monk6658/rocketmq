package com.rocketmqsystem.util;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.concurrent.FastThreadLocal;

import java.util.Map;


/**
 * 
 ********************************************************* .<br>
 * [类名] RunningData <br>
 * [描述] 当前线程共享变量 <br>
 * [作者] 小段 <br>
 * [时间] 2014-11-14 下午5:07:12 <br>
 ********************************************************* .<br>
 */
public class RunningData {

	/** 渠道交易流水 */
	private static FastThreadLocal<String> instance_name = new FastThreadLocal<String>();


	public static String getInstanceName() {
		return instance_name.get();
	}

	public static void setnstanceName(String instance_name) {
		RunningData.instance_name.set(instance_name);
	}

	/**
	 * 清除缓存
	 */
	public static void flushAll(){
		FastThreadLocal.removeAll();
	}


}
