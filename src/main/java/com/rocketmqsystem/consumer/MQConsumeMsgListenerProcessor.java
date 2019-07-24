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
import com.rocketmqsystem.util.DateUtil;
import com.rocketmqsystem.util.HttpClientUtils;
import com.rocketmqsystem.util.RunningData;
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
    private MessageExt msgExt;//

    @Value("${balanceSum.url}")
    private String balanceSumurl;


    /**
     * 消费者监听信息(失败会重新消费)
     * @author zxl
     * @param msgs 监听数据信息
     * @param context
     * @return 消费者处理状态
     * @time 2019-7-24 19:00:12
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if(CollectionUtils.isEmpty(msgs)){
            logger.info("接收到的消息为空，不做任何处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        int flag = 0;//消息传递返回标识
        try{
            for (MessageExt msg: msgs) {
                msgExt = msg;
                logger.info("接收到的消息是："+new String(msg.getBody()));
                String methodName = DateUtil.appendField("deal_", msg.getTopic(),"_",msg.getTags());
                flag =  (int)getClass().getMethod(methodName,new Class[] {}).invoke(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag == 1 ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

    /**
     * 处理topic主体的balanceSum标识
     * @author zxl
     * @return 0失败 1成功
     * @time 2019-7-24 19:00:12
     */
    public int deal_localhost_balanceSum(){
        int flag = 0;
        JSONObject jsonObject = HttpClientUtils.sendHttpPostForm(balanceSumurl,new String(msgExt.getBody()));
        if(!"200".equals(jsonObject.getString("responseCode"))){
            flag = 1;
        }
        System.out.println("成功标识:" + flag + " " + jsonObject + " " + msgExt.getReconsumeTimes());
        return flag;
    }

}

