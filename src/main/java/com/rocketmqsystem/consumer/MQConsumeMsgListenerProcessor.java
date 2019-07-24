/**
 * FileName: MQConsumeMsgListenerProcessor
 * Author:   gg
 * Date:     2019/7/24 16:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 张雪龙           修改时间           版本号              描述
 */
package com.rocketmqsystem.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rocketmqsystem.util.HttpClientUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(MQConsumeMsgListenerProcessor.class);

    @Value("${balanceSum.url}")
    private String balanceSumurl;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if(CollectionUtils.isEmpty(msgs)){
            logger.info("接收到的消息为空，不做任何处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        int flag = 0;
        for (MessageExt msg: msgs) {
            logger.info("接收到的消息是："+new String(msg.getBody()));
            JSONObject jsonObject = HttpClientUtils.sendHttpPostForm(balanceSumurl,new String(msg.getBody()));
            if(!"200".equals(jsonObject.getString("responseCode"))){
                flag = 1;
            }
            System.out.println("成功标识:" + flag + " " + jsonObject + " " + msg.getReconsumeTimes());
        }
        return flag == 1 ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
//        MessageExt messageExt = msgs.get(0);
//        String msg = new String(messageExt.getBody());
//        if(messageExt.getTopic().equals("localhost")){
//            if(messageExt.getTags().equals("你的tag")){
//                int reconsumeTimes = messageExt.getReconsumeTimes();
//                if(reconsumeTimes == 3){
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            }
//        }
//        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

    }

}

