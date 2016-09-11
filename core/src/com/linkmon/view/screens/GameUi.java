package com.linkmon.view.screens;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.LinkmonController;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.GameEvent;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.messagesystem.messages.ChatMessageTable;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.widgets.PBar;

public class GameUi implements Screen, ViewListener {
	
	Table containerBottom;
	Table containerTop;
	Button train;
	
	Button feed;
	Button medicine;
	Button stats;
	Button help;
	Button settings;
	
	private boolean evolve = false;
	
	private ImageButton light;
	
	Button online;
	
	Label playerGold;
	Image coinsImage;
	String goldString;
	Label time;
	
	public Label hunger;
	
	public Group ui;
	
	public GameClass game;
	public Skin skin;
	
	private EventManager eManager;
	
	private BitmapFont font;
	private Skin skin2;
	
	PBar pBar;
	
	public Label fpsLabel;
	
	private boolean lightOn = false;
	
	public GameUi(Group uiGroup, GameClass game, EventManager eManager) {
		
		this.eManager = eManager;
		this.eManager.addViewListener(this);

		this.game = game;

		ui = uiGroup;
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);

		train = new ImageButton(skin2.getDrawable("trainButton"));
		feed = new ImageButton(skin2.getDrawable("feedButton"));
		medicine = new ImageButton(skin2.getDrawable("shopButton"));
		stats = new ImageButton(skin2.getDrawable("statsButton"));
		online = new ImageButton(skin2.getDrawable("onlineButton"));
		
		float padding = (Gdx.graphics.getWidth()-train.getWidth()*5)/5/2;
		
		containerBottom = new Table();
		containerBottom.setSize(Gdx.graphics.getWidth(), 40*WorldRenderer.scaleY);
		containerBottom.setBackground(skin.getDrawable("default-rect"));
		containerBottom.add(train).padLeft(padding).padRight(padding);
		containerBottom.add(feed).padLeft(padding).padRight(padding);
		containerBottom.add(medicine).padLeft(padding).padRight(padding);
		containerBottom.add(stats).padLeft(padding).padRight(padding);
		containerBottom.add(online).padLeft(padding).padRight(padding);
		
		font = new BitmapFont(Gdx.files.internal("fontSmall-export.fnt"),
		         Gdx.files.internal("fontSmall-export.png"), false);
		
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		playerGold = new Label("Gold: ", labelStyle);
		coinsImage = new Image(skin2.getDrawable("coins"));
		hunger = new Label("Hunger: ", labelStyle);
		
		pBar = new PBar(skin2, 0, 100);
		
//		pBar.update(this.linkmon.getHungerLevel());
		
		time = new Label("Time:  "+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE), labelStyle);
		containerTop = new Table();
		containerTop.setSize(Gdx.graphics.getWidth(), 30*WorldRenderer.scaleXY);
		containerTop.setPosition(0, Gdx.graphics.getHeight() - 30*WorldRenderer.scaleXY);
		containerTop.setBackground(skin.getDrawable("default-rect"));
		
		help = new ImageButton(skin2.getDrawable("helpButton"));
		settings = new ImageButton(skin2.getDrawable("settingsButton"));
		light = new ImageButton(skin2.getDrawable("lightBulbOn"), skin2.getDrawable("lightBulbOn"), skin2.getDrawable("lightBulbOff"));
		light.setPosition(0, (Gdx.graphics.getHeight() - 30*WorldRenderer.scaleY)-light.getHeight());
		
		containerTop.add(playerGold).height(30*WorldRenderer.scaleXY).padRight(5);
		containerTop.add(coinsImage).align(Align.left).expandX();
		containerTop.add(hunger).height(30*WorldRenderer.scaleXY);
		containerTop.add(pBar).size(150*WorldRenderer.scaleXY, 10*WorldRenderer.scaleXY).expandX().align(Align.left);
		containerTop.add(time).size(Gdx.graphics.getWidth()/4, 30*WorldRenderer.scaleXY);
		
		containerTop.add(help).padTop(30*WorldRenderer.scaleXY);
		containerTop.add(settings).padTop(30*WorldRenderer.scaleXY);
		
		addListeners();
		
//		fpsLabel = new Label("",skin);
//		fpsLabel.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
//		ChatWindow chat = new ChatWindow(1, ui, eManager);
		
		ui.addActor(containerBottom);
		ui.addActor(containerTop);
		ui.addActor(light);
		
//		ui.addActor(fpsLabel);
		
//		ui.addActor(chat);
		
	}
	
	public void update() {
		
		if(Calendar.getInstance().get(Calendar.MINUTE) < 10)
			time.setText("Time:  "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":0"+Calendar.getInstance().get(Calendar.MINUTE));
		else
			time.setText("Time:  "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE));
		
//		fpsLabel.setText("FPS: "+Gdx.graphics.getFramesPerSecond());
//		
//		fpsLabel.toFront();
	}
	
	private void addListeners() {
		
		train.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.TRAIN_WINDOW));
            }
		});

		feed.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.FEED_WINDOW));
            }
		});
		
		medicine.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.SHOP_WINDOW));
            	}
		});
		
		stats.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STATS_WINDOW));
            	}
		});
		
		online.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.CONNECT_SCREEN));
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.CONNECT_TO_SERVER));
            	}
		});
		
		light.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.LIGHT_SWAP));
            	if(light.isChecked()) {
            		Gdx.app.log("MAINUI", "Light on");
            		light.setChecked(true);
            	}
            	else if(!light.isChecked()) {
            		Gdx.app.log("MAINUI", "Light off");
            		light.setChecked(false);
            	}
            }
		});
		help.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	String[] strings = new String[2];
        		strings[0] = "Hey, how are you going? I need a hand raising this Linkmon egg.";
        		strings[1] = "Blah blah blah blah blah. Stuff to type. I'm just writing stuff. I don't care what it is.";
            	eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, 1, strings));
            	}
		});
		settings.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.DEBUGGING_SCREEN));
            	}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ControllerEvent(ControllerEvents.UPDATE_LOAD));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update();
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
//		containerBottom.remove();
//		containerTop.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		containerBottom.remove();
		containerTop.remove();
		
	}

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ViewEvents.UPDATE_HUNGER_LEVEL): {
				pBar.update(event.value);
				break;
			}
			case(ViewEvents.UPDATE_GOLD): {
				playerGold.setText("Gold: "+ event.value);
				break;
			}
			case(ViewEvents.EVOLVE): {
				evolve = true;
				game.setScreen(new EvolutionScreen(ui, eManager, event.value, event.value2));
				break;
			}
		}
	}
}
