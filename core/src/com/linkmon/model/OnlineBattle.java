package com.linkmon.model;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.view.screens.ScreenType;

public class OnlineBattle {
	
	public BattleLinkmon playerLinkmon;
	public BattleLinkmon opponentLinkmon;
	
	public int myMoveId;
	public int oppMoveId;
	
	public boolean updated = true;

	public OnlineBattle(BattleLinkmon playerLinkmon, BattleLinkmon opponentLinkmon, EventManager eManager) {
		this.playerLinkmon = playerLinkmon;
		this.opponentLinkmon = opponentLinkmon;
		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.ONLINE_BATTLE_SCREEN));
	}
	
	public void update(int myHealth, boolean myCrit, boolean myDodge, int myEffective,
			int oppHealth, boolean oppCrit, boolean oppDodge, int oppEffective) {
		
		playerLinkmon.setHealth(myHealth);
		playerLinkmon.setCrit(myCrit);
		playerLinkmon.setDodge(myDodge);
		playerLinkmon.setEffectiveness(myEffective);
		
		opponentLinkmon.setHealth(oppHealth);
		opponentLinkmon.setCrit(oppCrit);
		opponentLinkmon.setDodge(oppDodge);
		opponentLinkmon.setEffectiveness(oppEffective);
		
		updated = true;
	}
}
