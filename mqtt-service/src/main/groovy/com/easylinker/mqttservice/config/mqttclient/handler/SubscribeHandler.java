package com.easylinker.mqttservice.config.mqttclient.handler;

/**
 * 订阅消息处理器
 *
 * @author wwhai
 * @date 2019/7/31 20:21
 * @email:751957846@qq.com 瞅啥瞅？代码拿过来我看看有没有BUG。
 */
public abstract class SubscribeHandler {
    public abstract void onSuccess(String topic, int qos);

    public abstract void onError(Exception e);

}
