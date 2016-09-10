package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
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
	
	public ShopController(Shop shop) {
		this.shop = shop;
	}
	
	// View updates
	
	public void getShopItems(IShop window, int type) {
		
		for(GameObject itemObject : shop.getItems()) {
			ItemComponent item = ((ItemComponent)itemObject.getExtraComponents());
			
			Gdx.app.log("PC", "Test Item!  TypeId: " + type + "   ItemTypeId: " + item.getType());
			
			if(type == ItemType.ALL) {
				window.addShopItem(itemObject.getId(), itemObject.getName(), item.getQuantity(), item.getPrice(), item.getItemText());
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
