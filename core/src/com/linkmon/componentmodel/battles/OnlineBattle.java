package com.linkmon.componentmodel.battles;

import com.linkmon.componentmodel.linkmon.Move;
import com.linkmon.componentmodel.linkmon.MoveFactory;
import com.linkmon.eventmanager.EventManager;

public class OnlineBattle {
	
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
}
