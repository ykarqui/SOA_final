package ar.edu.iua.soa.cse.business;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {

    private static final String CONNECTION_URL = "ws://192.168.0.8:9001";
    private static final String SUBSCRIPTION = "/home/range";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static MqttClient client;

    public void startSubscription() throws MqttException {
    	
    	System.out.println("== START SUBSCRIBER ==");
	
	    client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId());
	
	    MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);
	    
	    System.out.println("Callback");
	    // This callback is required to receive the message
	    client.setCallback(new MqttCallback() {	
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out.println("Topic " + topic + ": " + new String(message.getPayload()));
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectionLost(Throwable cause) {
				// TODO Auto-generated method stub
				
			}
		});
	    client.connect(connOpts);
	    client.subscribe(SUBSCRIPTION);
    }
    
    public void stopSubscription() throws MqttException {
    	client.disconnect();
    }

    public MqttConnectOptions setUpConnectionOptions(String username, String password) {
    	System.out.println("Options");
	    MqttConnectOptions connOpts = new MqttConnectOptions();
	    connOpts.setCleanSession(true);
	    connOpts.setUserName(username);
	    connOpts.setPassword(password.toCharArray());
	    return connOpts;
    }

}