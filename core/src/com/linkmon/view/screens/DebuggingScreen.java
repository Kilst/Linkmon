package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.LoadingWidget;

public class DebuggingScreen implements Screen, MyScreen {
	
	private Label objects;
	private String objectsString = "GameObjects in World: ";
	private Label fps;
	private String fpsString = "FPS: ";
	private Label linkmonPos;
	private String linkmonPosString = "Linkmon Position: ";
	private Button backButton;
	private Table container;
	private Group uiGroup;
	
	private Skin skin;
	
	EventManager eManager;
	
	LoadingWidget load;
	
	public DebuggingScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		objects = new Label(objectsString, skin);
		fps = new Label(fpsString, skin);
		linkmonPos = new Label(linkmonPosString, skin);
		
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new Button(back);
		
		container.add(objects).expand();
		container.row();
		container.add(fps).expand();
		container.row();
		container.add(linkmonPos).expand();
		container.row();
		container.add(backButton).expand().align(Align.bottomRight);
		
		uiGroup.addActor(container);
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
	});
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.DEBUGGING, this));
		fps.setText(fpsString+Gdx.graphics.getFramesPerSecond());
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

	public void updateObjectCount(int size) {
		// TODO Auto-generated method stub
		objects.setText(objectsString + size);
	}

	public void updateLinkmonPosition(float x, float y) {
		// TODO Auto-generated method stub
		linkmonPos.setText(linkmonPosString + " X: " + x + " Y: " + y);
	}

}
