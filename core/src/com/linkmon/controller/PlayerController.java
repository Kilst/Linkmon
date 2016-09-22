package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.Player;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.items.ItemComponent;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.items.UsableItemComponent;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.LinkmonStatusComponent;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
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
	
	private void useItem(int id) {
		GameObject item = ObjectFactory.getInstance().getObjectFromId(id);
		player.useItem(item);
	}
	
	// View updates
	
	public void getPlayerItems(IPlayerItems window, int type) {
		for(GameObject itemObject : player.getItems()) {
			ItemComponent item = ((ItemComponent)itemObject.getExtraComponents());
			
			Gdx.app.log("PC", "Test Item!  TypeId: " + type + "   ItemTypeId: " + item.getType());
			
			if(item.getType() == type) {
				window.addPlayerItem(itemObject.getId(), itemObject.getName(), item.getQuantity(), item.getPrice(), item.getItemText());
				Gdx.app.log("PC", "Adding Item!");
			}
			else if(type == ItemType.ALL) {
				window.addPlayerItem(itemObject.getId(), itemObject.getName(), item.getQuantity(), item.getPrice(), item.getItemText());
				Gdx.app.log("PC", "Adding Item!");
			}
		}
	}
	
	public void getPlayerStats(IPlayerStats window) {
		window.getPlayerStats(player.getName(), player.getGold());
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.USE_ITEM): {
				useItem(event.value);
				return false;
			}
			case(ScreenEvents.BUY_ITEM): {
				buyItem(event.value, event.value2);
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
}
