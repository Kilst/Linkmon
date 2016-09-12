package com.linkmon.eventmanager.network;

import com.linkmon.eventmanager.GameEvent;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.model.gameobject.linkmon.Move;

public class NetworkEvent extends GameEvent {
	
	public BattleLinkmon battleLinkmon;
	public Move move;
	public int myHealth;
	public int oppHealth;
	public String serverWelcome;
	
	public int screen;
	public int[] values;
	
	public NetworkEvent(int eventId, BattleLinkmon battleLinkmon) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.battleLinkmon = battleLinkmon;
	}

	public NetworkEvent(int eventId, Move move) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.move = move;
	}
	
	public NetworkEvent(int eventId, int myHealth, int oppHealth) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.myHealth = myHealth;
		this.oppHealth = oppHealth;
	}

	public NetworkEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public NetworkEvent(int eventId, String welcome) {
		super(eventId);
		// TODO Auto-generated constructor stub
		serverWelcome = welcome;
	}

	public NetworkEvent(int eventId, int screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.screen = screen;
	}
	
	public NetworkEvent(int eventId, int[] values) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.values = values;
	}
}
