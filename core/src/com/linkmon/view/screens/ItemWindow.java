package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.items.FoodComponent;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.items.Food;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IItems;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.widgets.ItemButton;

public class ItemWindow implements Screen, IPlayerItems {
	
	private Table container;
	private Table table;
	private Group uiGroup;

	
	private Button backButton;
	
	private EventManager eManager;
	
	private ScreenController screenController;
	
	public ItemWindow(Group group, ScreenController screenController, EventManager eManager) {
		this.eManager = eManager;
		this.screenController = screenController;
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		uiGroup = group;
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("medicineButton.png"))));
		
		
		table = new Table(skin);
		table.setBackground(skin.getDrawable("default-rect"));
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new ImageButton(back);
		
		container.add(image);
		container.row();
		container.add(table).size(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/1.4f*WorldRenderer.scaleY);
		container.row();
		container.add(backButton).align(Align.right).size(128*WorldRenderer.scaleX, 64*WorldRenderer.scaleY);
		addListeners();
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		screenController.updateWindow(this);
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerItems(List<GameObject> itemList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedItem(ItemButton selectedButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, String itemText) {
		// TODO Auto-generated method stub
		
	}

}
