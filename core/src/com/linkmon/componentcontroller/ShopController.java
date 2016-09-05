package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.Shop;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShopItems;

public class ShopController implements ScreenListener {
	
	private Shop shop;
	
	public ShopController(Shop shop) {
		this.shop = shop;
	}
	
	// View updates
	
		public void getShopItems(IShopItems window) {
			window.getShopItems(shop.getItems());
		}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.GET_SHOP_ITEMS): {
				getShopItems((IShopItems)event.screen);
				return false;
			}
		}
	return false;
	}

}
