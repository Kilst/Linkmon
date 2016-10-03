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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
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

	private TextButton shopButton;
	private TextButton itemButton;
	private TextButton statsButton;
	private TextButton movesButton;
	private TextButton cryoGenicsButton;
	private TextButton achievementsButton;
	
	private TextButton backButton;
	
	private Group ui;
	
	private EventManager eManager;
	
	public MenuScreen(Group ui, EventManager eManager) {
		
		this.eManager = eManager;
		
		this.ui = ui;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		rootTable = new Table();
		rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table = new Table();
		table.setBackground(skin2.getDrawable("newContainer"));
		
		shopButton = new TextButton("Shop", buttonStyle);
		itemButton = new TextButton("Items", buttonStyle);
		statsButton = new TextButton("Stats", buttonStyle);
		movesButton = new TextButton("Moves", buttonStyle);
		cryoGenicsButton = new TextButton("CryoGenics", buttonStyle);
		achievementsButton = new TextButton("Achievements", buttonStyle);
		
		backButton = new TextButton("Back", buttonStyle);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
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
		table.add(movesButton).pad(20);
		table.row().pad(20);
		table.add(cryoGenicsButton).pad(20);
		table.add(achievementsButton).pad(20);
		table.row();
		table.add(backButton).expandX().colspan(2).padTop(20).padBottom(-55).padRight(-45).align(Align.bottomRight);
		
		rootTable.add(table);
		
		addListeners();
		
		ui.addActor(darken);
		ui.addActor(rootTable);
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
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
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ITEM_WINDOW));
            }
		});
		
		statsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STATS_WINDOW));
            }
		});
		
		movesButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MOVES_SCREEN));
            }
		});
		
		cryoGenicsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.CRYO_SCREEN));
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
