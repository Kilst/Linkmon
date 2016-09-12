package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IItems;

public class ItemButton extends Table {
	
	private Label amount;
	
	private IItems screen;
	
	private TextureRegion green;
	
	private boolean selected = false;
	
	private String itemName;
	
	private int itemId;
	
	private ItemButton itemButton = this;
	
	private int price;
	
	public ItemButton(int id, String name, int quantity, int price, String itemText, IItems view) {
		// TODO Auto-generated constructor stub
		super();
		
		this.itemId = id;
		this.itemName = name;
		this.price = price;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));

		this.setTouchable(Touchable.enabled);
		this.setBackground(skin2.getDrawable("cellBackground"));

		screen = view;
		
		amount = new Label("Own: " + quantity, skin);
		
		
		TextureRegion region = ResourceLoader.getItemRegionFromId(id);
		
		Image itemImg = new Image(region);
		
		Image img = new Image(new NinePatch(skin2.getPatch("spacer")));
		img.getColor().a = 0.5f;
		
		Image img2 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img2.getColor().a = 0.5f;
		
		Label itemNameLabel = new Label(name+"\n\n"+itemText, skin);
		
		
		this.add(itemImg).size(80*UIRenderer.scaleXY, 80*UIRenderer.scaleXY).pad(5*UIRenderer.scaleXY).align(Align.left);
		itemImg.setTouchable(Touchable.disabled);
		
		this.add(img).size(6, 80).align(Align.left).padLeft(15).padRight(15);
		img.setTouchable(Touchable.disabled);
		
		this.add(itemNameLabel).expandX();
		itemNameLabel.setTouchable(Touchable.disabled);
		
		this.add(img2).size(6, 80).align(Align.left).padLeft(15).padRight(15);
		img2.setTouchable(Touchable.disabled);
		
		this.add(amount).align(Align.left).padRight(15).width(50);
		amount.setTouchable(Touchable.disabled);
		
		green = new TextureRegion(skin2.getRegion("green"));
		
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	screen.setSelectedItem(itemButton);
            	selected = true;
            }
		});
	}

	public void testSelected(int id) {
		if(id != itemId) {
			selected = false;
		}
	}
	
	public int getItemId() {
		return itemId;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		batch.setColor(1, 1, 1, 0.7f);
		if(selected)
			batch.draw(green, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		batch.setColor(1, 1, 1, 1);
		super.draw(batch, alpha);
//		amount.draw(batch, alpha);
	}

	public String getItemName() {
		// TODO Auto-generated method stub
		return itemName;
	}

	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
}
