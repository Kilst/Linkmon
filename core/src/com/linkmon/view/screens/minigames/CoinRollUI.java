package com.linkmon.view.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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

public class CoinRollUI implements Screen, MyScreen, ModelListener {
	
	Table container;
	Table containerLeft;
	Table containerRight;
	Button rightButton;
	Button leftButton;
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
	
	private Label ringCountLabel;
	private int ringCount = 0;
	private Image ringCountImage;
	
	Skin skin;
	Skin skin2;
	
	ExpandingTextWidget expandImage = null;
	private float expandImageTimer = 0;
	
	FireworksWidget fireworks;
	
	CountdownWidget countdownWidget;
	
	public CoinRollUI(EventManager eManager1, Group ui) {
		
		expandImage = null;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.ui = ui;
		this.eManager = eManager1;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		eManager.addModelListener(this);
		
		ringCount = 0;
		timer = 0;
		lastFrame = System.currentTimeMillis();
		expandImage = null;
		
//		container.add(containerLeft).expand();
//		container.add(containerRight).expand();
//		ui.addActor(container);
		
//		container = new Table();
//		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		container.setTouchable(Touchable.enabled);
		
		containerLeft = new Table();
		containerLeft.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
		containerLeft.setTouchable(Touchable.enabled);
		containerLeft.setPosition(0, 0);
		
		containerRight = new Table();
		containerRight.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
		containerRight.setTouchable(Touchable.enabled);
		containerRight.setPosition(Gdx.graphics.getWidth()/2, 0);
		leftButton = new ImageButton(skin2.getDrawable("arrowButton"));
		rightButton = new ImageButton(skin2.getDrawable("arrowButton"));
		
//		containerLeft.add(leftButton).expand().align(Align.left);
//		containerRight.add(rightButton).expand().align(Align.right);
		
		time = new Label("Time:", skin);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		ringCountImage = new Image(skin2.getDrawable("ringCount"));
		ringCountImage.setPosition(1280/2-ringCountImage.getWidth(), 720-ringCountImage.getHeight());
		ringCountLabel = new Label("0", labelStyle);
		ringCountLabel.setPosition(1280/2, 720-ringCountLabel.getHeight());
		
		containerLeft.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, -3));
            }
		});
		
		containerRight.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.MOVE_PLAYER, 3));
            }
		});
		
		ui.addActor(containerLeft);
		ui.addActor(containerRight);
		ui.addActor(ringCountLabel);
		ui.addActor(ringCountImage);
		countdownWidget = new CountdownWidget(eManager, ui);
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
		}
		
		if(expandImage != null) {
			
			expandImageTimer += delta/1000;
			
			if(expandImageTimer > 5) {
				int coinReward;
				if(win)
					coinReward = (int)(100/timer);
				else
					coinReward = (int)((100/timer)/4);
				expandImage.remove();
				expandImage = null;
				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.REWARD_SCREEN, coinReward));
			}
		}
		
		if(fireworks != null)
			fireworks.update();
		
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
		try {
			expandImage.remove();
		}
		catch(Exception e) {
			
		}
		containerLeft.remove();
		containerRight.remove();
		
		ringCountLabel.remove();
		ringCountImage.remove();
		
		fireworks = null;
		
		expandImage = null;
		
		eManager.removeModelListener(this);
		
		gameStarted = false;
		gameEnded = false;
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
			case(ModelEvents.RING_COLLECTED): {
				ringCount++;
				ringCountLabel.setText(""+ringCount);
				break;
			}
		}
	}

}