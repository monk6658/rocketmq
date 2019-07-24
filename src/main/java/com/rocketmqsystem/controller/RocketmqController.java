/**
 * FileName: RocketmqController
 * Author:   gg
 * Date:     2019/7/24 16:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 张雪龙           修改时间           版本号              描述
 */
package com.rocketmqsystem.controller;

import com.alibaba.fastjson.JSON;
import com.rocketmqsystem.util.LogUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RocketmqController {

    private static final Logger logger = LoggerFactory.getLogger(RocketmqController.class);
    private LogUtil logUtil = new LogUtil();


    /**使用RocketMq的生产者*/
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @RequestMapping("/api/get")
    public String get(String text) throws MQClientException, RemotingException, MQBrokerException, InterruptedException, UnsupportedEncodingException {
        Map<String,String> mymap = getBalanceSum();
        mymap.put("note",text);
        logger.info("开始发送消息："+mymap);
        Message sendMsg = new Message(
                "localhost",
                "TagA" /* Tag */,
                UUID.randomUUID().toString(),
                (JSON.toJSONString(getBalanceSum())).getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        LogUtil logUtil = new LogUtil();
        logUtil.saveNormalLog("","");
        //默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg);
        logger.info("消息发送响应信息："+sendResult.toString());
        return sendResult.toString();
    }

    private static Map<String, String> getBalanceSum() {
        Map<String, String> contentData = new HashMap<String, String>();
        contentData.put("serial", "10000002654"); // 版本号
        contentData.put("mer_no", "123456789"); // 字符集编码 可以使用UTF-8,GBK两种方式
        contentData.put("posno", "123"); // 签名方法
        contentData.put("localdate", "2019-07-22"); // 交易类型 42-历史余额查询
        contentData.put("localtime", "13:00:00"); // 机构代码
        contentData.put("amount", "0200"); // 金额
        contentData.put("type", "1"); // 加钱还是减钱  0加钱   1减钱
        contentData.put("note", "二次测试"); // 备注
        return contentData;
    }

}
