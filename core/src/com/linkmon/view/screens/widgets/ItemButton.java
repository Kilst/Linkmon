package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
import com.linkmon.componentmodel.items.ItemComponent;
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
	
	private TextureRegion green;
	
	private boolean selected = false;

	public ItemButton(Drawable imageUp, EventManager eManager1, GameObject item, IItems view) {
		super();
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));

		this.setTouchable(Touchable.enabled);
		this.setBackground(skin2.getDrawable("cellBackground"));
		
		this.item = item;
		screen = view;
		
		amount = new Label("Own: " + ((ItemComponent)item.getExtraComponents()).getQuantity(), skin);
		
		
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
		
		this.add(amount).align(Align.left).padRight(15).width(50);
		amount.setTouchable(Touchable.disabled);
		
		green = new TextureRegion(skin2.getRegion("green"));
		
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	screen.setSelectedItem(getItem());
            	selected = true;
            }
		});
	}
	
	public void testSelected(int id) {
		if(id == item.getId()) {
			selected = false;
		}
	}

	public GameObject getItem() {
		// TODO Auto-generated method stub
		return item;
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
}
