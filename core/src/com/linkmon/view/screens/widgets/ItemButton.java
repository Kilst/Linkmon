package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IItems;

public class ItemButton extends ImageButton {
	
	private GameObject item;
	
	private Label amount;
	
	private EventManager eManager;
	
	private IItems screen;

	public ItemButton(Drawable imageUp, EventManager eManager1, GameObject item, IItems view) {
		super(imageUp);
		// TODO Auto-generated constructor stub
		this.item = item;
		screen = view;
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		amount = new Label("" + ((FoodComponent)item.getExtraComponents()).getQuantity(), skin);
		this.add(amount).align(Align.bottom);
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	screen.setSelectedItem(getItem());
            }
		});
	}

	public GameObject getItem() {
		// TODO Auto-generated method stub
		return item;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
//		amount.draw(batch, alpha);
	}
}
