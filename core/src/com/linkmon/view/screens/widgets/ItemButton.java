package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IItems;

public class ItemButton extends Table {
	
	private GameObject item;
	
	private Label amount;
	
	private EventManager eManager;
	
	private IItems screen;

	public ItemButton(Drawable imageUp, EventManager eManager1, GameObject item, IItems view) {
		super();
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));

		this.setTouchable(Touchable.enabled);
		this.setBackground(skin2.getDrawable("container"));
		
		this.item = item;
		screen = view;
		
		amount = new Label("Own: " + ((FoodComponent)item.getExtraComponents()).getQuantity(), skin);
		
		
		TextureRegion region = ResourceLoader.getItemRegionFromId(item.getId());
		
		Image itemImg = new Image(region);
		
		Image img = new Image(new NinePatch(skin2.getPatch("spacer")));
		img.getColor().a = 0.5f;
		
		Image img2 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img2.getColor().a = 0.5f;
		
		Label itemName = new Label(item.getName()+"\n"+"Adds 100 to hunger!", skin);
		
		
		this.add(itemImg).size(80*WorldRenderer.scaleXY, 80*WorldRenderer.scaleXY).pad(5*WorldRenderer.scaleXY).align(Align.left);
		itemImg.setTouchable(Touchable.disabled);
		
		this.add(img).size(6, 80).align(Align.left).padLeft(15).padRight(15);
		img.setTouchable(Touchable.disabled);
		
		this.add(itemName).expandX();
		itemName.setTouchable(Touchable.disabled);
		
		this.add(img2).size(6, 80).align(Align.left).padLeft(15).padRight(15);
		img2.setTouchable(Touchable.disabled);
		
		this.add(amount);
		amount.setTouchable(Touchable.disabled);
		
		
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
