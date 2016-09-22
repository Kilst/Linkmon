package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.Shop;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.items.ItemType;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShop;

public class ShopController implements ScreenListener {
	
	private Shop shop;
	private Player player;
	
	public ShopController(Shop shop, Player player) {
		this.shop = shop;
		this.player = player;
	}
	
	// View updates
	
	private void getShopItems(IShop window, int type) {
		
		int quantity = 0;
		
		for(GameObject itemObject : shop.getItems()) {
			ItemComponent item = ((ItemComponent)itemObject.getExtraComponents());
			
			for(GameObject playerItem : player.getItems()) {
				Gdx.app.log("PC", "Test Item!  TypeId: " + type + "   ItemTypeId: " + item.getType());
				if(playerItem.getId() == itemObject.getId()) {
					quantity = ((ItemComponent)playerItem.getExtraComponents()).getQuantity();
					break;
				}
				else
					quantity = 0;
			}
			
			if(type == ItemType.ALL) {
				window.addShopItem(itemObject.getId(), itemObject.getName(), quantity, item.getPrice(), item.getItemText());
				Gdx.app.log("PC", "Adding Item!");
			}
		}
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.GET_SHOP_ITEMS): {
				getShopItems((IShop)event.screen, event.value);
				return false;
			}
		}
	return false;
	}

}
