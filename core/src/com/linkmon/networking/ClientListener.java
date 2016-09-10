package com.linkmon.networking;

import java.io.IOException;
import java.net.SocketException;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;

public class ClientListener implements Runnable {
	
	private Client client;
	private Thread listenerThread;
	private PacketHandler packetHandler;
	private EventManager eManager;
	
	private int packetSize;
	
	public ClientListener(Client client, EventManager eManager) {
		this.eManager = eManager;
		this.client = client;
		
		packetHandler = new PacketHandler(eManager, client);
		
	}
	
	public void start() {
		listenerThread = new Thread(this);
		listenerThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub		
		receivePacket();
		
	}
	
	
	public void receivePacket() {
		while(client.connected) {
			byte[] sizePacket = new byte[2];
			try {
				try {
					client.getTcpClient().getInputStream().read(sizePacket, 0, sizePacket.length);
					Gdx.app.log("Client2Listener", "Got size packet!");
				} catch (SocketException e) {
					e.printStackTrace();
					if(client.connected)
						client.disconnect("Server unresponsive!");
					client.connected = false;
					break;
				}
				try {
					byte[] receivedPacket = new byte[sizePacket[1]];
					try {
						client.getTcpClient().getInputStream().read(receivedPacket, 0, receivedPacket.length);
						Gdx.app.log("Client2Listener", "Got packet! Size: " +sizePacket[1]);
						if(sizePacket[1] == 0) {
							client.connected = false;
							client.disconnect("Disconnected from the server!");
							break;
						}
					} catch (SocketException e) {
						e.printStackTrace();
						client.connected = false;
						client.disconnect("Timeout error!");
						break;
					}
					
					// HANDLE PACKET
					packetHandler.handlePacket(receivedPacket);
					client.getData().setCurrentReceivedPacket(receivedPacket); // Set packet data
					////////////////
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Gdx.app.log("Client2Listener", "Failed to read packet.");
					client.connected = false;
					client.disconnect("I/O error!");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Gdx.app.log("Client2Listener", "Failed to read size packet.");
				client.connected = false;
				client.disconnect("        I/O error"
						      + "\nThough definitely a Time-out!");
				break;
			}
		}
	}

}
