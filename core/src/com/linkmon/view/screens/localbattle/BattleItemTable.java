package com.linkmon.view.screens.localbattle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.LocalBattleController;
import com.linkmon.helpers.ResourceLoader;

public class BattleItemTable extends Table {
	
	private int id;
	
	Image itemImage;
	
	Label itemName;
	Label itemQuantity;
	Label text;
	
	int quantity;
	
	public BattleItemTable(Skin skin2, String name, int quantity, String itemText) {
		
		this.quantity = quantity;
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		itemImage = new Image();
		itemImage.setDrawable(skin2.getDrawable("type-logo-fire"));
		itemImage.setSize(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight());
		
		itemName = new Label(name, labelStyle);
		itemQuantity = new Label("X " + quantity, labelStyle);
		text = new Label(itemText, labelStyle);
		
		add(itemName).align(Align.left).expand().padLeft(20);
		add(itemImage).padRight(20).size(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight());
		row();
		add(text).align(Align.left).padLeft(20);
		add(itemQuantity).align(Align.right).padRight(20).colspan(2);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addClickListener(final Table menuContainer, final Table itemContainer, final LocalBattleController localBattleController, final LocalBattleScreen screen) {
		// TODO Auto-generated method stub
		this.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	menuContainer.setVisible(false);
	        	itemContainer.setVisible(false);
	        	localBattleController.useItem(getId());
	        	screen.setPlaying(true);
	        	localBattleController.playRound();
	        	BattleItemTable.this.quantity -= 1;
	        	itemQuantity.setText("X " + BattleItemTable.this.quantity);
	        	if(BattleItemTable.this.quantity < 1)
	        		BattleItemTable.this.remove();
	        }
		});
	}

}
