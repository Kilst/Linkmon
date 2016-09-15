package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.model.ModelService;
import com.linkmon.model.OnlineBattle;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.view.screens.OnlineBattleScreen;
import com.linkmon.view.screens.ScreenType;

public class OnlineBattleController implements ControllerListener {
	
	private ModelService modelService;

	private OnlineBattle battle;
	
	private BattleLinkmon player;
	private BattleLinkmon opponent;
	
	private EventManager eManager;
	
	public OnlineBattleController(ModelService modelService, EventManager eManager) {
		this.modelService = modelService;
		this.eManager = eManager;
		
		eManager.addControllerListener(this);
	}
	
	private void addNewBattle(BattleLinkmon opponent) {
		// NetworkEvent
		this.player = new BattleLinkmon(modelService.getPlayer().getLinkmon());
		this.opponent = opponent;
		battle = modelService.createOnlineBattle(player, opponent);
		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.ONLINE_BATTLE_SCREEN));
	}
	
	private void update(int myHealth, boolean myCrit, boolean myDodge, int myEffective,
			int oppHealth, boolean oppCrit, boolean oppDodge, int oppEffective) {
		battle.update(myHealth, myCrit, myDodge, myEffective, oppHealth, oppCrit, oppDodge, oppEffective);
	}
	
	private void battleEnded(boolean win) {
		if(win) {
			
		}
		else {
			
		}
			
		battle = null;
	}
	
	public void setSprites(OnlineBattleScreen battleScreen) {
//		battleScreen.getSprites(battle.playerLinkmon.getId(), battle.opponentLinkmon.getId());
	}
	
	public void updateBattleScreen(OnlineBattleScreen battleScreen) {
		if(battle != null && battle.updated) {
//			battleScreen.getSprites(battle.playerLinkmon.getId(), battle.opponentLinkmon.getId());
//			battleScreen.updateHealths(battle.playerLinkmon.getHealth(), battle.opponentLinkmon.getHealth());
			if(battleScreen.buttonTable != null)
				battleScreen.buttonTable.setVisible(true);
			// Update player move/crit/dodge
			// Update opponent move/crit/dodge
			battle.updated = false;
		}
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.SET_OPPONENT): {
				addNewBattle(event.linkmon);
				Gdx.app.log("OnlineBattleController","Got Set opponent");
				break;
			}
			case(ControllerEvents.UPDATE_HEALTH): {
				update(event.myHealth, false, false, 0, event.oppHealth, false, false, 0);
				battle.updated = true;
				Gdx.app.log("OnlineBattleController","Got health Update");
				break;
			}
		}
		return false;
	}

	public int getOpponentId() {
		// TODO Auto-generated method stub
		return opponent.getId();
	}
}
