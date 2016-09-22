package com.linkmon.networking;

import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class PacketHandler implements Runnable {
	
	private EventManager eManager;
	private String serverWelcome;
	private Thread handleThread;
	
	private byte[] packet;
	
	private Client client;
	
	public PacketHandler(EventManager eManager, Client client) {
		this.eManager = eManager;
		this.client = client; 
	}
	
	public void handle(byte[] packet) {
		int packetId = (int)packet[0];
		Gdx.app.log("PacketHandler","Handling packet!  ID: " + packetId);
		switch(packetId) {
			case(PacketType.CONNECT): {
				Gdx.app.log("PacketHandler","Got connect packet");
				eManager.notify(new NetworkEvent(NetworkEvents.CONNECTED));
				break;
			}
			case(PacketType.DISCONNECT): {
				Gdx.app.log("PacketHandler","Got disconnect packet");
				client.disconnect("PacketHandler: Disconnected from the server!");
				break;
			}
			case(PacketType.MYSTERY_GIFT): {
				Gdx.app.log("PacketHandler","Got Mystery Gift packet");
				byte[] bytes = new byte[4];
				bytes[0] = packet[2];
				bytes[1] = packet[3];
				bytes[2] = packet[4];
				bytes[3] = packet[5];
				eManager.notify(new NetworkEvent(NetworkEvents.RECIEVE_GIFT, Packet.byteArrayToInt(bytes)));
				break;
			}
			case(PacketType.BATTLE_SETUP): {
				Gdx.app.log("PacketHandler","Got BattleSteup packet");
				Gdx.app.log("PacketHandler","Created opponent from BattleSetup packet");
				Gdx.app.log("PacketHandler","Need to finish NetworkListener/come up with idea on how to handle");
				eManager.notify(new NetworkEvent(NetworkEvents.SET_OPPONENT, Packet.linkmonFromPacket(packet)));
				break;
			}
			case(PacketType.UPDATE_HEALTH): {
				int[] battleData = new int[11];
				int[] healths = Packet.healthFromPacket(packet);
				int[] energies = Packet.energiesFromPacket(packet);
				int[] damages = Packet.damageFromPacket(packet);
				int[] dodges = Packet.dodgeFromPacket(packet);
				byte[] moves = Packet.movesFromPacket(packet);
				byte first = packet[34];
				
				battleData[0] = healths[0];
				battleData[1] = damages[0];
				battleData[2] = moves[0];
				battleData[3] = dodges[0];
				battleData[4] = energies[0];
				
				battleData[5] = first;
				
				battleData[6] = healths[1];
				battleData[7] = damages[1];
				battleData[8] = moves[1];
				battleData[9] = dodges[1];
				battleData[10] = energies[1];
				
				eManager.notify(new NetworkEvent(NetworkEvents.UPDATE_HEALTH, battleData));
				Gdx.app.log("PacketHandler","My Health: " + healths[0] + "  Opponent Health: " + healths[1]);
				break;
			}
			case(PacketType.WIN_LOSS): {
				int[] rewards = Packet.getRewardsFromPacket(packet);
				Gdx.app.log("PacketHandler","Got winLoss packet: " + packet[1]);
				
				if(packet[22] == 0)
					eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, MessageType.NETWORK_MESSAGE, "You win!!!"));
				else
					eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, MessageType.NETWORK_MESSAGE, "You lost :("));
				
				eManager.notify(new NetworkEvent(NetworkEvents.WIN_LOSS, rewards));
				break;
			}
			case(PacketType.HEARTBEAT): {
				client.sendPacket(new Packet(PacketType.HEARTBEAT));
				Gdx.app.log("PacketHandler","Got HeartBeat!");
				break;
			}
			default: {
				try {
					//eManager.notify(new NetworkEvent(NetworkEvents.SERVER_WELCOME, new String(packet, "UTF-8")));
		    		client.getData().setServerWelcomeMessage(new String(packet, "UTF-8"));
		    		eManager.notify(new NetworkEvent(NetworkEvents.CONNECTED));
		    		//serverWelcome = null;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Gdx.app.log("PacketHandler","Server Welcome failed!");
				}
				break;
			}
		}
	}
	
	public void handlePacket(byte[] packet) {
		this.packet = packet;
		
		handleThread = new Thread(this);
		handleThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		handle(packet);
	}

}
