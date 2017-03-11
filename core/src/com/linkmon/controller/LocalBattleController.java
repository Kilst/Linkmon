package com.linkmon.controller;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.model.Player;
import com.linkmon.model.World;
import com.linkmon.model.aonevonebattle.OneVOneBattle;
import com.linkmon.model.aonevonebattle.OneVOneBattleComponent;
import com.linkmon.model.aonevonebattle.OpponentFactory;
import com.linkmon.model.aonevonebattle.OpponentId;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.battles.BattleManager;
import com.linkmon.model.battles.LocalBattleLinkmon;
import com.linkmon.model.battles.SoloBattle;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.items.components.ItemComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.localbattle.ILocalBattle;

public class LocalBattleController implements ScreenListener {
	
	private BattleManager battle;
	private Player player;
	private EventManager eManager;
	
	private SoloBattle soloBattle;
	
	private OneVOneBattle oneVBattle;
	
	private PlayerController playerController;
	
	private WorldController worldController;
	
	public LocalBattleController(Player player, EventManager eManager, PlayerController playerController, WorldController worldController) {
		this.eManager = eManager;
		this.player = player;
		this.playerController = playerController;
		
		this.worldController = worldController;
	}
	
	private void createBattle() {
		
		LocalBattleLinkmon opp1 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 0);
		opp1.setOwner("opp");
		LocalBattleLinkmon opp2 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 1);
		opp2.setOwner("opp");
		LocalBattleLinkmon opp3 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 2);
		opp3.setOwner("opp");
		
		//((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().getRank();
		
		LocalBattleLinkmon p1 = new LocalBattleLinkmon(player.getLinkmon(), false, 3);
		p1.setOwner("player");
		LocalBattleLinkmon p2 = new LocalBattleLinkmon(player.getLinkmon(), true, 4);
		p2.setOwner("player");
		LocalBattleLinkmon p3 = new LocalBattleLinkmon(player.getLinkmon(), false, 5);
		p3.setOwner("player");
		
		soloBattle = new SoloBattle(opp1,opp2,opp3,p1,p2,p3,eManager);
		
		battle = new BattleManager(soloBattle, eManager);
		soloBattle.addManager(battle);
		
		//battle = new LocalBattle(opp1,opp2,opp3,p1,p2,p3,eManager);
	}
	
	private void createOnlineBattle() {
		
		LocalBattleLinkmon opp1 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 0);
		opp1.setOwner("opp");
		LocalBattleLinkmon opp2 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 1);
		opp2.setOwner("opp");
		LocalBattleLinkmon opp3 = ObjectFactory.getInstance().createLocalBattleLinkmon(1, 2);
		opp3.setOwner("opp");
		
		//((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().getRank();
		
		LocalBattleLinkmon p1 = new LocalBattleLinkmon(player.getLinkmon(), false, 3);
		p1.setOwner("player");
		LocalBattleLinkmon p2 = new LocalBattleLinkmon(player.getLinkmon(), true, 4);
		p2.setOwner("player");
		LocalBattleLinkmon p3 = new LocalBattleLinkmon(player.getLinkmon(), false, 5);
		p3.setOwner("player");
		
//		battle = new LocalBattle(opp1,opp2,opp3,p1,p2,p3,eManager);
	}
	
	public int getOpponentId() {
		return oneVBattle.getOpponentId();
	}
	
	public int getPlayerId() {
		// TODO Auto-generated method stub
		return oneVBattle.getPlayer().getId();
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.LOCAL_BATTLE_CREATE) : {
				//createBattle();
				oneVBattle = new OneVOneBattle();
				oneVBattle.setupBattle(player.getLinkmon(), OpponentFactory.getOpponentFromId(event.value), event.value, worldController.getWorld(), eManager);
				break;
			}
			case(ScreenEvents.LOCAL_PLAYER_MOVE) : {
//				battle.setPlayerMove(event.value);
//				soloBattle.setPlayerMove(event.value);
				((OneVOneBattleComponent)oneVBattle.getPlayer().getExtraComponents()).setCurrentMove(event.value);
				break;
			}
			case(ScreenEvents.LOCAL_PLAYER_TARGET) : {
				battle.setPlayerTarget(event.value);
				soloBattle.setPlayerTarget(event.value);
				break;
			}
			case(ScreenEvents.LOCAL_PLAY_TURN) : {
				oneVBattle.playRound();
				break;
			}
			case(ScreenEvents.LOCAL_BATTLE_UPDATE_HEALTHS) : {
//				((ILocalBattle)event.screen).updateHealths(((BattleExtraComponent)battle.getLinkmons()[0].getExtraComponents()).getStats().getHealth(), 
//						((BattleExtraComponent)battle.getLinkmons()[1].getExtraComponents()).getStats().getHealth(), 
//						((BattleExtraComponent)battle.getLinkmons()[2].getExtraComponents()).getStats().getHealth(), 
//						((BattleExtraComponent)battle.getLinkmons()[3].getExtraComponents()).getStats().getHealth(), 
//						((BattleExtraComponent)battle.getLinkmons()[4].getExtraComponents()).getStats().getHealth(), 
//						((BattleExtraComponent)battle.getLinkmons()[5].getExtraComponents()).getStats().getHealth());
				break;
			}
			case(ScreenEvents.LOCAL_BATTLE_GET_MOVES) : {
				OneVOneBattleComponent bc = ((OneVOneBattleComponent)oneVBattle.getPlayer().getExtraComponents());
				((ILocalBattle)event.screen).getMoves(bc.getMove1().getName(), bc.getMove1().getDamage(), bc.getMove1().getEnergy(), bc.getMove1().getId(), bc.getMove1().getType(), bc.getMove1().getStatusEffect().toString(),
						bc.getMove2().getName(), bc.getMove2().getDamage(), bc.getMove2().getEnergy(), bc.getMove2().getId(), bc.getMove2().getType(), bc.getMove2().getStatusEffect().toString(),
						bc.getMove3().getName(), bc.getMove3().getDamage(), bc.getMove3().getEnergy(), bc.getMove3().getId(), bc.getMove3().getType(), bc.getMove3().getStatusEffect().toString());
				break;
			}
		}
		return true;
	}
	
	public void getMoves(ILocalBattle screen) {
		OneVOneBattleComponent bc = ((OneVOneBattleComponent)oneVBattle.getPlayer().getExtraComponents());
		((ILocalBattle)screen).getMoves(bc.getMove1().getName(), bc.getMove1().getDamage(), bc.getMove1().getEnergy(), bc.getMove1().getId(), bc.getMove1().getType(), bc.getMove1().getStatusEffect().toString(),
				bc.getMove2().getName(), bc.getMove2().getDamage(), bc.getMove2().getEnergy(), bc.getMove2().getId(), bc.getMove2().getType(), bc.getMove2().getStatusEffect().toString(),
				bc.getMove3().getName(), bc.getMove3().getDamage(), bc.getMove3().getEnergy(), bc.getMove3().getId(), bc.getMove3().getType(), bc.getMove3().getStatusEffect().toString());
	}
	
	public void updateHealths(ILocalBattle screen) {
		
	}
	
	public void playRound() {
		oneVBattle.playRound();
	}
	
	public boolean setMove(int choice) {
		return ((OneVOneBattleComponent)oneVBattle.getPlayer().getExtraComponents()).setCurrentMove(choice);
	}

	public World getWorld() {
		// TODO Auto-generated method stub
		return oneVBattle.getWorld();
	}
	
	public void getItems(IPlayerItems screen) {
		// TODO Auto-generated method stub
		playerController.getPlayerItems(screen, ItemType.RECOVERY);
		
	}
	
	public void useItem(int id) {
		GameObject itemRemove = null;
		for(GameObject item : player.getItems()) {
			if(item.getId() == id) {
				((ItemComponent)item.getExtraComponents()).use(item, oneVBattle.getPlayer(), oneVBattle.getWorld());
				itemRemove = item;
//				oneVBattle.setUsingItem(true);
			}
		}
		if(itemRemove != null)
			player.removeItem(itemRemove);
	}


	public void update() {
		// TODO Auto-generated method stub
		if(oneVBattle != null)
			oneVBattle.update();
	}
}
