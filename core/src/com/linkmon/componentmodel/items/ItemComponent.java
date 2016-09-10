package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.gameobject.GameObject;

public class ItemComponent implements IExtraComponents {
	
	private int quantity;
	private int price;
	
	private int type;
	
	private String itemText;
	
	public ItemComponent(int quantity, int price, int type, String itemText) {
		this.quantity = quantity;
		this.price = price;
		this.type = type;
		this.itemText = itemText;
	}
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
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
