package com.example.user.gcmexample;



import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


public class ServerExampleGcm
{
	private static final String REG_ID = "AAAAAAAAAAAAAAAAAAA";
	private static final String SENDER_ID = "BBBBBBBBBBBBBBBBB";

	public static void main( String[] args ) throws Exception
	{

		new Thread() {
			
			public String value1 = "55.20";
			public String value2 = "45.25";


			public void run() {

				try {

					Sender sender = new Sender(SENDER_ID);

					// To send message with payload data
					Message message = new Message.Builder()
							.collapseKey("key1") // only one with the key is sent if device is offline (max 4 keys)
							.timeToLive(7200) // 2 hours
							.delayWhileIdle(true) // deprecated
							.addData("message", value1 + "," + value2)
							.build();

					// Parameters more info:
					// https://developers.google.com/cloud-messaging/http-server-ref

					Result result = sender.send(message, REG_ID, 1);

					// For catching the problems related to the reception
					System.out.println("Message Result: " + result.toString());

				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}.start();

	}        
}
