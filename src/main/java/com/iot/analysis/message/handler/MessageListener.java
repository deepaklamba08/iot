package com.iot.analysis.message.handler;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;

public abstract class MessageListener implements IMqttActionListener {

    public abstract void connect();
}

