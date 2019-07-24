/**
 * FileName: RunTimeUtil
 * Author:   gg
 * Date:     2019/7/24 17:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 张雪龙           修改时间           版本号              描述
 */
package com.rocketmqsystem.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RunTimeUtil {
    private static AtomicInteger index = new AtomicInteger();
    public static int getPid() {
        String info = getRunTimeInfo();
        int pid = (new Random()).nextInt();
        int index = info.indexOf("@");
        if(index > 0) {
            pid = Integer.parseInt(info.substring(0, index));
        }
        return pid;
    }
    public static String getRunTimeInfo() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String info = runtime.getName();
        return info;
    }
    public static String getRocketMqUniqeInstanceName() {
        return "pid" + getPid() + "_index" + index.incrementAndGet();
    }
}