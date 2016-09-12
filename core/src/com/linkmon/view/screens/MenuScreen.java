package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.interfaces.MyScreen;

public class MenuScreen implements Screen, MyScreen {
	
	private Table rootTable;
	
	private Image darken;
	
	private Table table;

	private ImageButton shopButton;
	private ImageButton itemButton;
	private ImageButton statsButton;
	private ImageButton cryoGenicsButton;
	private ImageButton achievementsButton;
	
	private ImageButton backButton;
	
	private Group ui;
	
	private EventManager eManager;
	
	public MenuScreen(EventManager eManager, Group ui) {
		
		this.eManager = eManager;
		
		this.ui = ui;
		
		Skin skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		
		rootTable = new Table();
		rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table = new Table();
		table.setBackground(skin.getDrawable("container"));
		
		shopButton = new ImageButton(skin.getDrawable("shopButton"));
		itemButton = new ImageButton(skin.getDrawable("itemButton"));
		statsButton = new ImageButton(skin.getDrawable("statsButton"));
		cryoGenicsButton = new ImageButton(skin.getDrawable("cryoGenicsButton"));
		achievementsButton = new ImageButton(skin.getDrawable("achievementButton"));
		backButton = new ImageButton(skin.getDrawable("backButton"));
		
		darken = new Image(skin.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.getColor().a = 0.7f;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		table.add(shopButton).pad(20);
		table.add(itemButton).pad(20);
		table.row().pad(20);
		table.add(statsButton).pad(20);
		table.add(cryoGenicsButton).pad(20);
		table.row();
		table.add(achievementsButton).pad(20).colspan(2);
		table.row();
		table.add(backButton).expandX().colspan(2).padTop(20).align(Align.right);
		
		rootTable.add(table);
		
		addListeners();
		
		ui.addActor(darken);
		ui.addActor(rootTable);
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN_PREVIOUS));
            }
		});
		
		shopButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.SHOP_WINDOW));
            }
		});
		
		itemButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
		});
		
		statsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STATS_WINDOW));
            }
		});
		
		cryoGenicsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
		});
		
		achievementsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
		});
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
		rootTable.remove();
		darken.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
