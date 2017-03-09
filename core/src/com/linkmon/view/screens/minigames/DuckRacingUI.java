package com.linkmon.view.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.CountdownWidget;
import com.linkmon.view.screens.widgets.ExpandingTextWidget;
import com.linkmon.view.screens.widgets.FireworksWidget;

public class DuckRacingUI implements Screen, MyScreen, ModelListener {
	
	Table container;
	Button right;
	Button left;
	Group ui;
	EventManager eManager;
	
	private boolean gameStarted = false;
	private boolean gameEnded = false;
	
	private boolean win = false;
	
	private float timer = 0;
	private long lastFrame = System.currentTimeMillis();
	private Label time;
	
	private Label countdownLabel;
	private int countdown = 3;
	private float countdownTimer = 0;
	private boolean removeCountDown = false;
	
	Skin skin2;
	
	ExpandingTextWidget expandImage = null;
	private float expandImageTimer = 0;
	
	FireworksWidget fireworks;
	
	CountdownWidget countdownWidget;
	
	public DuckRacingUI(EventManager eManager1, Group ui) {
		this.ui = ui;
		this.eManager = eManager1;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		eManager.addModelListener(this);
		
		gameEnded = false;
		gameStarted = false;
		
		expandImage = null;
		lastFrame = System.currentTimeMillis();
		expandImageTimer = 0;
		timer = 0;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		container = new Table();
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setTouchable(Touchable.enabled);
		right = new Button(skin);
		left = new Button(skin);
		
		time = new Label("Time:", skin);
		
		countdownWidget = new CountdownWidget(eManager, ui);
		
		container.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, 2));
            }
		});
		left.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
//            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, -2));
            	eManager.notify(new ScreenEvent(ScreenEvents.RESTART_MINI_GAME));
            }
		});
		
		container.add(time).expand();
		ui.addActor(container);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		delta = (System.currentTimeMillis() - lastFrame);
		lastFrame = System.currentTimeMillis();
		
		gameStarted = countdownWidget.update(delta);
		
		if(gameStarted && !gameEnded) {			
			
			timer += delta/1000;
			time.setText("Time: " + String.format("%.2f", timer));
			
			Gdx.app.log("DuckRacingUI", "Timer: " + timer);
		}
		
		if(expandImage != null) {
			
			expandImageTimer += delta/1000;
			
			if(expandImageTimer > 5) {
				int coinReward;
				if(win)
					coinReward = (int)(100/timer);
				else
					coinReward = (int)((100/timer)/4);
				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.REWARD_SCREEN, coinReward));
			}
		}
		
		if(fireworks != null)
			fireworks.update();
		
//		if(right.isPressed()) {
//			eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, 1));
//		}
//		if(left.isPressed()) {
//			eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, -1));
//		}
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
		if(expandImage != null)
			expandImage.remove();
		container.remove();
		
		fireworks = null;
		
		eManager.removeModelListener(this);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.MINIGAME_WIN): {
				gameEnded = true;
				gameStarted = false;
				expandImage = new ExpandingTextWidget(skin2.getDrawable("winImage"), (1280/2), 720 - 200);
				ui.addActor(expandImage);
//				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.REWARD_SCREEN));
				
				fireworks = new FireworksWidget(eManager);
				
				win = true;
				break;
			}
			case(ModelEvents.MINIGAME_LOSE): {
				gameEnded = true;
				gameStarted = false;
				expandImage = new ExpandingTextWidget(skin2.getDrawable("loseImage"), (1280/2), 720 - 200);
				ui.addActor(expandImage);
//				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.REWARD_SCREEN));
				
				win = false;
				break;
			}
		}
	}

}