package com.linkmon.networking;

import java.io.IOException;
import java.net.SocketException;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;

public class ClientSender implements Runnable {
	
	private Client client;
	private Thread senderThread;
	private Packet packet;
	
	public ClientSender(Client client, Packet packet) {
		this.client = client;
		this.packet = packet;
		senderThread = new Thread(this);
		senderThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(client.connected)
			sendPacket();
	}
	
	private void sendPacket() {
		// Send Packet
		try {
			
			byte[] sizePacket = new byte[2]; // Create size packet
			sizePacket[0] = packet.getPacketBytes()[0]; // add id
			sizePacket[1] = packet.getPacketBytes()[1]; // add size
			try{
				Gdx.app.log("Client2Sender", "Sending size packet!");
				client.getTcpClient().getOutputStream().write(sizePacket); // Size
				Gdx.app.log("Client2Sender", "Sending packet!");
				client.getTcpClient().getOutputStream().write(packet.getPacketBytes()); // Packet
			}
			catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				client.disconnect("Sender: Timeout error!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			client.disconnect("Sender: I/O error!");
		}
		
		client.sending = false;
	}

}
