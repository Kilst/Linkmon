package com.linkmon.model.items.components;

import com.linkmon.helpers.CompleteMessage;
import com.linkmon.model.World;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.items.actions.IItemAction;

public class ItemComponent implements IExtraComponents {
	
	private IItemAction action;
	
	private int quantity;
	private int price;
	
	private int type;
	
	private String itemText;
	
	public ItemComponent(IItemAction action, int quantity, int price, int type, String itemText) {
		this.action = action;
		this.quantity = quantity;
		this.price = price;
		this.type = type;
		this.itemText = itemText;
	}
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
	}
	
	public CompleteMessage use(GameObject item, GameObject linkmon, World world) {
		return action.use(item, linkmon, world);
	}

	public int getQuantity() {
		return quantity;
	}

	public void add(int quantity) {
		this.quantity += quantity;
	}
	
	public void remove(int quantity) {
		this.quantity -= quantity;
		if(this.quantity < 1)
			this.quantity = 0;
	}

	public int getPrice() {
		return price;
	}

	public void setQuantity(int amount) {
		// TODO Auto-generated method stub
		this.quantity = amount;
	}

	public void setPrice(int price) {
		// TODO Auto-generated method stub
		this.price = price;
		
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
}
