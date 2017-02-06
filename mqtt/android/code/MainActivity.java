package com.example.user.androidmqttonly;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class MainActivity extends Activity {

    //MQTT Attributes:
    private MqttClient client;
    public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
    public static final String TOPIC_NAME = "testtopic1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

							
				new Thread() {			
					public void run() {


						try {
							client = new MqttClient(BROKER_URL, MqttClient.generateClientId(), new MemoryPersistence());

							client.connect();

							MqttTopic myTopic = client.getTopic(TOPIC_NAME);

							int myValue = 75;

							MqttMessage message = new MqttMessage(String.valueOf(myValue).getBytes());

							myTopic.publish(message);

							System.out.println("Submission Successful. Topic: " + myTopic.getName() + "  Message: " + myValue);

							client.disconnect();

						} catch (MqttException e) {
							e.printStackTrace();
						}
						
					}.start();
				
				}

            }

        });

    }
}
