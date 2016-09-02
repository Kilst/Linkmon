package com.linkmon.view.screens.interfaces;

import java.util.List;

import com.linkmon.model.gameobject.items.Item;

public interface IPlayerItems extends IItems {
	public void getPlayerItems(List<Item> items);
}
