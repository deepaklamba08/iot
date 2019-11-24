package com.iot.analysis.core.ds

import com.iot.analysis.message.handler.{MessageHandler, SimpleMqttActionListenerImpl}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.eclipse.paho.client.mqttv3.MqttMessage

class IotStreamingSource(brokerUI: String, topic: String, storageLevel: StorageLevel) extends Receiver[String](storageLevel) {

  override def onStart(): Unit =
    receive()

  override def onStop(): Unit = {

  }

  private def receive(): Unit = {
    val consumer = new MessageHandler {
      override def onMessage(message: MqttMessage): Unit =
        store(new String(message.getPayload, "UTF-8"))
    }
    val actionListener = new SimpleMqttActionListenerImpl(brokerUI, topic, consumer)
    actionListener.connect()
  }
}
