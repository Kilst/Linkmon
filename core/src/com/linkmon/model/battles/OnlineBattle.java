package com.linkmon.model.battles;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.model.linkmon.Move;
import com.linkmon.model.linkmon.MoveFactory;
import com.linkmon.model.linkmon.MoveIds;

public class OnlineBattle {
	
	private BattleLinkmon player;
	private BattleLinkmon opponent;
	
	private String opponentName = "a";
	
	private String[][] battleMessages;
	
	private Move playerMove;
	private Move opponentMove;
	
	private int playerHealth;
	private int opponentHealth;
	
	private int playerDamage;
	private int opponentDamage;
	
	private boolean playerDodge;
	private boolean opponentDodge;
	
	private int playerEnergy;
	private int opponentEnergy;
	
	private boolean first;
	
	private EventManager eManager;
	
	private boolean updated = true;
	
	private boolean ended = false;
	
	public OnlineBattle(BattleLinkmon player, BattleLinkmon opponent, EventManager eManager) {
		
		this.eManager = eManager;
		
		this.player = player;
		this.opponent = opponent;
		
		battleMessages = new String[2][];
	}
	
	public void updateBattle(int playerHealth, int playerDamage, int playerMoveId, int playerDodge, int playerEnergy, int first,
						int opponentHealth, int opponentDamage, int opponentMoveId, int opponentDodge, int opponentEnergy) {
		
		if(first == 0)
			this.first = false;
		else
			this.first = true;
		
		player.setHealth(playerHealth);
		opponent.setHealth(opponentHealth);
		
		playerMove = MoveFactory.getMoveFromId(playerMoveId);
		opponentMove = MoveFactory.getMoveFromId(opponentMoveId);

		this.playerDamage = playerDamage;
		this.opponentDamage = opponentDamage;
		
		if(playerDodge == 0)
			this.playerDodge = false;
		else
			this.playerDodge = true;
		if(opponentDodge == 0)
			this.opponentDodge = false;
		else
			this.opponentDodge = true;
		
		this.playerEnergy = playerEnergy;
		player.setEnergy(playerEnergy);
		this.opponentEnergy = opponentEnergy;
		opponent.setEnergy(opponentEnergy);
		
		buildBattleString();
		
		//eManager.notify(new ModelEvent(ModelEvents.UPDATE_ONLINE_BATTLE, playerHealth, opponentHealth, playerEnergy, opponentEnergy, battleMessages));
		
		setUpdated(true);
	}
	
	private void buildBattleString() {
		
		String[] playerMessages = new String[2];
		String[] opponentMessages = new String[2];
		
		if(playerMove.getId() == MoveIds.DEFEND) {
			playerMessages[0] = "You are defending!";
			playerMessages[1] = "Damaged reduced by 50% for 1 turn!";
		}
		else {
			playerMessages[0] = "You are using " + playerMove.getName();
			if(opponentDodge)
				playerMessages[1] = "Opponent evaded the attack!";
			else
				playerMessages[1] = "It did " + playerDamage + " points of damage!";
		}
			
		if(opponentMove.getId() == MoveIds.DEFEND) {
			opponentMessages[0] = "Opponent is defending!";
			opponentMessages[1] = "Damaged reduced by 50% for 1 turn!";
		}
		else {
			opponentMessages[0] = "Opponent is using " + opponentMove.getName();
			if(playerDodge)
				opponentMessages[1] = "You evaded the attack!";
			else
				opponentMessages[1] = "It did " + opponentDamage + " points of damage!";
		}
		
		
		if(first) {
			battleMessages[0] = playerMessages;
			battleMessages[1] = opponentMessages;
		}
		else {
			battleMessages[0] = opponentMessages;
			battleMessages[1] = playerMessages;
		}
	}

	public BattleLinkmon getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	public BattleLinkmon getOpponent() {
		// TODO Auto-generated method stub
		return opponent;
	}

	public String getOpponentName() {
		return opponentName;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public boolean isEnded() {
		// TODO Auto-generated method stub
		return ended;
	}

	public void setEnded(boolean ended) {
		// TODO Auto-generated method stub
		this.ended = ended;
	}
	
	public String[][] getBattleMessages() {
		return battleMessages;
	}
}
