package com.iot.analysis.message.handler;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MessageHandler {

    public void onMessage(MqttMessage message);
}
