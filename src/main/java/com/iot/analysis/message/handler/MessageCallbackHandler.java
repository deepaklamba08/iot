package com.iot.analysis.message.handler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageCallbackHandler  implements MqttCallback {

    private final String topic;
    private final MessageHandler messageHandler;

    public MessageCallbackHandler(String topic, MessageHandler messageHandler) {
        this.topic = topic;
        this.messageHandler = messageHandler;
    }

    public void connectionLost(Throwable cause) {

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (!topic.equals(this.topic)) {
            //topic is different..
            return;
        }
        this.messageHandler.onMessage(message);
    }

    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
