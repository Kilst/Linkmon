package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.gameobject.GameObject;
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

public class PlayerController implements ScreenListener {
	
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}
	
	// Request to update models
	
	public void feedLinkmon(GameObject item) {
		player.feedLinkmon(item);
	}
	
	public void addGold(int amount) {
		player.addGold(amount);
	}
	
	public void buyItem(int itemId, int quantity) {
		player.addItem(itemId, quantity);
	}
	
	public void useItem(GameObject item) {
		((UsableItemComponent)item.getExtraComponents()).use(player.getLinkmon());
		
	}
	
	
	
	// View updates
	
	public void getPlayerItems(IPlayerItems window) {
		window.getPlayerItems(player.getItems());
	}
	
	public void getPlayerStats(IPlayerStats window) {
		window.getPlayerStats(player.getName(), player.getGold());
	}
	
	public void getLinkmonStats(ILinkmonStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStats();
		LinkmonStatusComponent status = ((LinkmonExtraComponents)player.getLinkmon().getExtraComponents()).getStatus();
		window.getLinkmonStats(
				stats.getHealth(),
				stats.getAttack(),
				stats.getDefense(),
				stats.getSpeed(),
				status.getCareMistakes(),
				status.getBirthDate(),
				stats.getRank()
				);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
		case(ScreenEvents.FEED_LINKMON): {
			feedLinkmon(event.gameObject);
			return false;
		}
		case(ScreenEvents.GET_PLAYER_STATS): {
			getPlayerStats((IPlayerStats) event.screen);
			return false;
		}
		case(ScreenEvents.GET_PLAYER_ITEMS): {
			getPlayerItems((IPlayerItems) event.screen);
			return false;
		}
		case(ScreenEvents.GET_LINKMON_STATS): {
			getLinkmonStats((ILinkmonStats) event.screen);
			return false;
		}
	}
	return false;
	}
}
