package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.view.screens.widgets.LoadingWidget;

public class ConnectScreen implements Screen {
	
	private Label label;
	private Button cancelButton;
	private Table container;
	private Group uiGroup;

	private Skin skin;
	
	private boolean connected;
	
	EventManager eManager;
	
	LoadingWidget load;
	
	public ConnectScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
	}
	
	public void updateLabel(String text) {
		label.setText(text);
	}
	
	private void addListeners() {
		
		cancelButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ControllerEvent(ControllerEvents.CLOSE_CONNECTION));
	            	
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            	
	            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		connected = true;
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		label = new Label("Connecting..", skin);
		label.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		cancelButton = new TextButton("Cancel",skin);
		
		load = new LoadingWidget();
		load.setPosition(Gdx.graphics.getWidth()/2-load.getWidth()/2, 0);
		
		container.add(label);
		container.row();
		container.add(cancelButton);
		container.row();
		uiGroup.addActor(container);
		uiGroup.addActor(load);
		
		addListeners();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		load.act(delta);
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
		label.remove();
		load.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}
