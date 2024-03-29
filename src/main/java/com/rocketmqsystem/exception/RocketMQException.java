/**
 * FileName: RocketMQException
 * Author:   gg
 * Date:     2019/7/24 16:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 张雪龙           修改时间           版本号              描述
 */
package com.rocketmqsystem.exception;

import com.rocketmqsystem.constants.ErrorCode;

public class RocketMQException extends AppException {
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     */
    public RocketMQException() {
        super();
    }

    public RocketMQException(Throwable e) {
        super(e);
    }

    public RocketMQException(ErrorCode errorType) {
        super(errorType);
    }

    public RocketMQException(ErrorCode errorCode, String... errMsg) {
        super(errorCode, errMsg);
    }

    /**
     * 封装异常
     *
     * @param errorCode
     * @param errMsg
     * @param isTransfer
     *            是否转换异常信息，如果为false,则直接使用errMsg信息
     */
    public RocketMQException(ErrorCode errorCode, String errMsg, Boolean isTransfer) {
        super(errorCode, errMsg, isTransfer);
    }

    public RocketMQException(ErrorCode errCode, Throwable cause, String... errMsg) {
        super(errCode, cause, errMsg);
    }
}

