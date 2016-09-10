package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.items.ItemType;
import com.linkmon.componentmodel.items.UsableItemComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonStatsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
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
	
	public void feedLinkmon(GameObject item) {
		player.feedLinkmon(item);
	}
	
	public void buyItem(GameObject item) {
		player.buyItem(item);
	}
	
	public void useItem(GameObject item) {
		((UsableItemComponent)item.getExtraComponents()).use(player.getLinkmon(), player.getWorld());
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
		}
	}
	
	public void getPlayerStats(IPlayerStats window) {
		window.getPlayerStats(player.getName(), player.getGold());
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.FEED_LINKMON): {
				feedLinkmon(event.gameObject);
				return false;
			}
			case(ScreenEvents.BUY_ITEM): {
				buyItem(event.gameObject);
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
}
