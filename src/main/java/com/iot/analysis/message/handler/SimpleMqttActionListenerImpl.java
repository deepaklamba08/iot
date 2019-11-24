package com.iot.analysis.message.handler;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SimpleMqttActionListenerImpl implements IMqttActionListener {

    private final String topic;
    private final String serverURI;
    private final MqttCallback mqttCallback;

    private String clientId;
    private MqttAsyncClient client;
    private MemoryPersistence memoryPersistence;

    private IMqttToken connectToken;

    public SimpleMqttActionListenerImpl(String serverURI, String topic, MessageHandler messageHandler) {
        this.serverURI = serverURI;
        this.topic = topic;
        this.mqttCallback = new MessageCallbackHandler(topic, messageHandler);
    }

    public void onFailure(IMqttToken arg0, Throwable exception) {
    }

    public void onSuccess(IMqttToken asyncActionToken) {
        if (asyncActionToken.equals(this.connectToken)) {
            //successfully connected..
            try {
                this.client.subscribe(this.topic, 2, null, this);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void connect() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            this.memoryPersistence = new MemoryPersistence();
            this.clientId = MqttAsyncClient.generateClientId();
            this.client = new MqttAsyncClient(this.serverURI, this.clientId, this.memoryPersistence);
            this.client.setCallback(this.mqttCallback);
            this.connectToken = this.client.connect(options, null, this);
        } catch (MqttException e) {
            throw new IllegalStateException(e);
        }
    }
}
