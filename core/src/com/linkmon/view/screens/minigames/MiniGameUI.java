package com.linkmon.view.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.interfaces.MyScreen;

public class MiniGameUI implements Screen, MyScreen {
	
	Table container;
	Button right;
	Button left;
	Group ui;
	EventManager eManager;
	
	public MiniGameUI(EventManager eManager1, Group ui) {
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.ui = ui;
		this.eManager = eManager1;
		container = new Table();
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		right = new Button(skin);
		left = new Button(skin);
		
		right.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, 3));
            }
		});
		left.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, -3));
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		
		container.add(left).width(Gdx.graphics.getWidth()/2).height(50);
		container.add(right).width(Gdx.graphics.getWidth()/2).height(50);
		
		ui.addActor(container);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if(right.isPressed()) {
			eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, 1));
		}
		if(left.isPressed()) {
			eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, -1));
		}
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
