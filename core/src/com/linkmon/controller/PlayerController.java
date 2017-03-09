package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.CompleteMessage;
import com.linkmon.model.CryoLinkmon;
import com.linkmon.model.Player;
import com.linkmon.model.aonevonebattle.moves.MoveFactory;
import com.linkmon.model.aonevonebattle.moves.OneVMove;
import com.linkmon.model.battles.BattleLinkmon;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.items.components.ItemComponent;
import com.linkmon.model.items.components.UsableItemComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.LinkmonStatusComponent;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShop;

public class PlayerController implements ScreenListener {
	
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}
	
	// Request to update models
	
	public void buyItem(int id, int amount) {
		GameObject item = ObjectFactory.getInstance().getObjectFromId(id);
		((ItemComponent)item.getExtraComponents()).setQuantity(amount);
		player.buyItem(item);
	}
	
	public CompleteMessage useItem(int id) {
		GameObject item = ObjectFactory.getInstance().getObjectFromId(id);
		return player.useItem(item);
	}
	
	// View updates
	
	public void getPlayerItems(IPlayerItems window, int type) {
		for(GameObject itemObject : player.getItems()) {
			ItemComponent item = ((ItemComponent)itemObject.getExtraComponents());
			
			Gdx.app.log("PC", "Test Item!  TypeId: " + type + "   ItemTypeId: " + item.getType() + "    ItemName: " + itemObject.getName());
			
			if(item.getType() == type) {
				window.addPlayerItem(itemObject.getId(), itemObject.getName(), item.getQuantity(), item.getPrice(), item.getType(), item.getItemText());
				Gdx.app.log("PC", "Adding Item ID!");
			}
			else if(type == ItemType.ALL) {
				window.addPlayerItem(itemObject.getId(), itemObject.getName(), item.getQuantity(), item.getPrice(), item.getType(), item.getItemText());
				Gdx.app.log("PC", "Adding Item ALL!");
			}
		}
	}
	
	public void getPlayerStats(IPlayerStats window) {
		window.getPlayerStats(player.getName(), player.getGold());
	}
	
	public void getLinkmonStats(ILinkmonStats window, int slot) {
		CryoLinkmon linkmon = player.getCryoLinkmon(slot);
		if(linkmon != null) {
			window.getLinkmonStats(
					linkmon.getId(),
					linkmon.getHealth(),
					linkmon.getAttack(),
					linkmon.getDefense(),
					linkmon.getSpeed(),
					0,
					null,
					linkmon.getRank()
					);
		}
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.USE_ITEM): {
				useItem(event.value);
				return false;
			}
//			case(ScreenEvents.GET_SAVED_LINKMON_STATS): {
//				getLinkmonStats((ILinkmonStats) event.screen);
//				return true;
//			}
//			case(ScreenEvents.SAVE_BATTLE_LINKMON): {
//				player.cryoCurrentLinkmon();
//				return true;
//			}
			case(ScreenEvents.BUY_ITEM): {
				buyItem(event.value, event.value2);
				return false;
			}
			case(ScreenEvents.ADD_PLAYER_GOLD): {
				player.addGold(event.value);
				return false;
			}
			case(ScreenEvents.GET_PLAYER_STATS): {
				getPlayerStats((IPlayerStats) event.screen);
				return false;
			}
			case(ScreenEvents.GET_PLAYER_ITEMS): {
				getPlayerItems((IPlayerItems) event.screen, event.value);
				return false;
			}
			case(ScreenEvents.GET_SHOP_ITEMS): {
				((IShop)event.screen).getPlayerGold(player.getGold());
				return false;
			}
		}
		
		return false;
	}

	public void update() {
		// TODO Auto-generated method stub
		player.update();
	}

	public void updateBattleTowerFlag(int opponentId) {
		// TODO Auto-generated method stub
		player.addBattleTowerFlag(opponentId);
	}

	public boolean checkBattleTowerFlag(int opponentId) {
		// TODO Auto-generated method stub
		return player.checkBattleTowerFlag(opponentId);
	}
	
	public void updateHelpFlag(int helpId) {
		// TODO Auto-generated method stub
		player.addHelpFlag(helpId);
	}

	public boolean checkHelpFlag(int helpId) {
		// TODO Auto-generated method stub
		return player.checkHelpFlag(helpId);
	}

	public int getLinkmonStatPoints() {
		// TODO Auto-generated method stub
		return player.getLinkmonStatPoints();
	}

	public void updateLinkmonStats(int addedHealth, int addedAttack, int addedDefense, int addedSpeed) {
		// TODO Auto-generated method stub
		((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().addHealth(addedHealth);
		((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().addAttack(addedAttack);
		((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().addDefense(addedDefense);
		((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats().addSpeed(addedSpeed);
	}

	public boolean getCryoStats(ILinkmonStats window, int slot) {
		CryoLinkmon linkmon = player.getCryoLinkmon(slot);
		if(linkmon != null) {
			window.getLinkmonStats(
					linkmon.getId(),
					linkmon.getHealth(),
					linkmon.getAttack(),
					linkmon.getDefense(),
					linkmon.getSpeed(),
					0,
					null,
					linkmon.getRank()
					);
			return true;
		}
		else
			return false;
	}
	
	public void saveCurrentLinkmon(int slot) {
		player.cryoCurrentLinkmon(slot);
	}

	public void getCryoMoves(IMovesScreen window, int slot) {
		// TODO Auto-generated method stub
		CryoLinkmon linkmon = player.getCryoLinkmon(slot);
		if(linkmon != null) {
			OneVMove move1 = MoveFactory.getMoveFromId(linkmon.getMove1());
			OneVMove move2 = MoveFactory.getMoveFromId(linkmon.getMove2());
			OneVMove move3 = MoveFactory.getMoveFromId(linkmon.getMove3());
			window.setLinkmonMoves(move1.getId(), move1.getName(), move1.getType(), 0, move1.getDamage(), 0, move1.getEnergy(), move1.getStatusEffect().toString());
			window.setLinkmonMoves(move2.getId(), move2.getName(), move2.getType(), 0, move2.getDamage(), 0, move2.getEnergy(), move2.getStatusEffect().toString());
			window.setLinkmonMoves(move3.getId(), move3.getName(), move3.getType(), 0, move3.getDamage(), 0, move3.getEnergy(), move3.getStatusEffect().toString());
		}
	}

	public boolean isCryoSlotFilled(int slot) {
		// TODO Auto-generated method stub
		CryoLinkmon linkmon = player.getCryoLinkmon(slot);
		if(linkmon != null)
			return true;
		else
			return false;
	}
	
	public void addLinkmonStatPoints(int totalPoints) {
		// TODO Auto-generated method stub
		player.addLinkmonStatPoints(totalPoints);
	}

	public void removeLinkmonStatPoints(int totalPoints) {
		// TODO Auto-generated method stub
		player.removeLinkmonStatPoints(totalPoints);
	}
}
