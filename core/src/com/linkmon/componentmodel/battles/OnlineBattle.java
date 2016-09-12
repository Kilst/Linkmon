package com.linkmon.componentmodel.battles;

import com.linkmon.componentmodel.linkmon.Move;
import com.linkmon.componentmodel.linkmon.MoveFactory;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvents;

public class OnlineBattle implements NetworkListener {
	
	private BattleLinkmon player;
	private BattleLinkmon opponent;
	
	private String[] battleMessages;
	
	private Move playerMove;
	private Move opponentMove;
	
	private int playerHealth;
	private int opponentHealth;
	
	private int playerEffectivness;
	private int opponentEffectivness;
	
	private int playerDodge;
	private int opponentDodge;
	
	private EventManager eManager;
	
	public OnlineBattle(BattleLinkmon player, BattleLinkmon opponent, EventManager eManager) {
		
		this.eManager = eManager;
		
		this.player = player;
		this.opponent = opponent;
		
		battleMessages = new String[2];
	}
	
	public void updateBattle(int playerHealth, int playerMoveId, int playerCrit, int playerEffectivness, int playerDodge,
						int opponentHealth, int opponentMoveId, int opponentCrit, int opponentEffectivness, int opponentDodge) {
		
		
		player.setHealth(playerHealth);
		opponent.setHealth(opponentHealth);
		
		playerMove = MoveFactory.getMoveFromId(playerMoveId);
		opponentMove = MoveFactory.getMoveFromId(opponentMoveId);
		
		this.playerHealth = playerHealth;
		this.opponentHealth = opponentHealth;
		
		this.playerEffectivness = playerEffectivness;
		this.opponentEffectivness = opponentEffectivness;
		
		this.playerDodge = playerDodge;
		this.opponentDodge = opponentDodge;
		
		buildBattleString();
	}
	
	private void buildBattleString() {
		
		battleMessages[0] = "";
	}

	@Override
	public boolean onNotify(NetworkEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(NetworkEvents.BATTLE_UPDATE): {
				updateBattle(event.values[0], event.values[1], event.values[2], event.values[3], event.values[4],
						event.values[5], event.values[6], event.values[7], event.values[8], event.values[9]);
				break;
			}
		}
		return false;
	}
}
