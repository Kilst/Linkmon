package com.linkmon.networking;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class Client {
	
	private Socket client;
	private SocketHints hints;
	
	volatile public boolean sending = false;
	public boolean connected = false;
	
	private String serverMessage;
	
	private NetworkingData data;
	
	private ClientListener listener;
	
	EventManager eManager;
	
	public Client(EventManager eManager) {
		this.eManager = eManager;
		
		data = new NetworkingData();
	}
	
	public void connect() {
		hints = new SocketHints();
		hints.connectTimeout = 2000;
		hints.socketTimeout = 40000;
		client = Gdx.net.newClientSocket(Protocol.TCP, "60.242.191.232", 5566, hints);
		this.connected = true;
		listener = new ClientListener(this, eManager);
		listener.start();
		sendPacket(new Packet(1));
	}
	
	public void sendPacket(Packet packet) {
		try{
			sending = true;
			data.setCurrentSentPacket(packet.getPacketBytes());
			new ClientSender(this, packet);
		} catch (Exception e) {
			disconnect("Send packet error!");
		}
	}
	
	public Socket getTcpClient() {
		return client;
	}
	
	public void disconnect(String message) {
		
		
			try {
				sendPacket(new Packet(0));
				connected = false;
				client.dispose();
				getTcpClient().dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		eManager.notify(new MessageEvent(MessageEvents.DISCONNECTED_SERVER, MessageType.NETWORK_MESSAGE, "Disconnect",  message));
		listener = null;
		Gdx.app.log("Client", "Bad Practice: calling swap screen from client disconnect()!!");
		
	}

	public NetworkingData getData() {
		return data;
	}
}
