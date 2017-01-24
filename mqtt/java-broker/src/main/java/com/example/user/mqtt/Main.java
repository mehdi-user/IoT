package com.example.user.mqtt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathConfig;
import io.moquette.server.config.IConfig;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {

	static class PublisherListener extends AbstractInterceptHandler {
		@Override
		public void onPublish(InterceptPublishMessage message) {
			System.out.println("Moquette mqtt broker message intercepted, topic: " + message.getTopicName()
					+ ", message: " + new String(message.getPayload().array()));
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		
		// Creating an MQTT Broker using Moquette
		
		final IConfig classPathConfig = new ClasspathConfig();

		final Server mqttBroker = new Server();
		final List<? extends InterceptHandler> userHandlers = Arrays.asList(new PublisherListener());
		mqttBroker.startServer(classPathConfig, userHandlers);

		System.out.println("Moquette mqtt broker started, press ctrl-c to shutdown..");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Stopping moquette mqtt broker..");
				mqttBroker.stopServer();
				System.out.println("Moquette mqtt broker stopped.");
			}
		});

		Thread.sleep(4000);

		// Creating an MQTT Client using Eclipse Paho to test the server
		
		String topic = "testtopicpaho";
		String content = "Temp 75";
		int qos = 2;
		String broker = "tcp://0.0.0.0:1883";
		String clientId = "paho-java-client";

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("paho-client connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("paho-client connected to broker");
			System.out.println("paho-client publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("paho-client message published");
			sampleClient.disconnect();
			System.out.println("paho-client disconnected");
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}
}
