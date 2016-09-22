package com.linkmon.networking;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.model.battles.BattleLinkmon;

public class TcpService implements INetworkService, Runnable {
	
	private Client client;
	private BattleLinkmon linkmon;
	EventManager eManager;
	
	private Thread connectThread;
	
	public TcpService(EventManager eManager) {
		
		this.eManager = eManager;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void connect() {
		connectThread = new Thread(this);
		connectThread.start();
	}
	
	public boolean disconnect() {
		if(!client.sending)
			client.sendPacket(new Packet(0));
		return true;
	}
	
	public void sendMove(int moveId) {
		
		try {
			if(!client.sending)
				client.sendPacket(new Packet(moveId, true));
		} catch(Exception e) {
			
		}
	}
	
	public void searchOpponent(BattleLinkmon linkmon) {
		if(!client.sending) {
			client.sendPacket(new Packet(linkmon));
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if(client == null)
				client = new Client(eManager);
			client.connect();
		} catch(Exception e) {
			client.disconnect("Netowrk Service: Server Time-out!");
		}
	}

	public void getMysteryGift() {
		// TODO Auto-generated method stub
		client.sendPacket(new Packet(PacketType.MYSTERY_GIFT));
		Gdx.app.log("NetworkService","Sending Gift packet");
	}

	public void cancelSearch() {
		// TODO Auto-generated method stub
		client.sendPacket(new Packet(PacketType.CANCEL_SEARCH));
	}
}
