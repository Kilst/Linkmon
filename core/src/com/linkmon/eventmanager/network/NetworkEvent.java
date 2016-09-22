package com.linkmon.eventmanager.network;

import com.linkmon.componentmodel.battles.BattleLinkmon;
import com.linkmon.eventmanager.GameEvent;
import com.linkmon.model.gameobject.linkmon.Move;

public class NetworkEvent extends GameEvent {
	
	public BattleLinkmon battleLinkmon;
	public Move move;
	public int myHealth;
	public int oppHealth;
	public int myEnergy;
	public int oppEnergy;
	public String serverWelcome;
	
	public int value;
	public int[] values;
	public boolean status;
	
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
	
	public NetworkEvent(int eventId, int myNewHealth, int myEnergy, int oppNewHealth, int oppEnergy) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.myHealth = myNewHealth;
		this.oppHealth = oppNewHealth;
		this.myEnergy = myEnergy;
		this.oppEnergy = oppEnergy;
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

	public NetworkEvent(int eventId, int value) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public NetworkEvent(int eventId, int[] values) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.values = values;
	}
	
	public NetworkEvent(int eventId, boolean status) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.status = status;
	}
}
