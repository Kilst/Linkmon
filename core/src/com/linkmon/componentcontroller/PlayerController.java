package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.UsableItemComponent;

public class PlayerController {
	
	private Player player;
	
	public PlayerController(Player player) {
		this.player = player;
	}
	
	public void addGold(int amount) {
		player.addGold(amount);
	}
	
	public int getGold() {
		return player.getGold();
	}
	
	public void useItem(GameObject item) {
		((UsableItemComponent)item.getExtraComponents()).use(player.getLinkmon());
		
	}

}
