package com.linkmon.view.screens;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.items.ItemIds;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.MyProgressBar;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class GameUi implements Screen, IPlayerStats, ModelListener {
	
	Table containerBottom;
	Table containerTop;
	Button train;
	
	Button feed;
	Button menu;
	ImageButton help;
	ImageButton settings;
	
	private boolean evolve = false;
	
	private ImageButton light;
	
	Button online;
	
	Label playerGold;
	Image coinsImage;
	String goldString;
	Label time;
	
	MyProgressBar hungerBar;
	public Label hunger;
	
	MyProgressBar exhaustionBar;
	public Label exhaustion;
	
	public Group ui;
	
	public GameClass game;
	public Skin skin;
	
	private EventManager eManager;
	
	private BitmapFont font;
	private Skin skin2;
	
	public Label fpsLabel;
	
	private EvolutionScreen evolutionScreen;
	
	public GameUi(Group uiGroup, GameClass game, EventManager eManager) {
		
		this.eManager = eManager;
		this.eManager.addModelListener(this);

		this.game = game;

		ui = uiGroup;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		font = new BitmapFont(Gdx.files.internal("fontSmall-export.fnt"),
		         Gdx.files.internal("fontSmall-export.png"), false);
		
		SmartFontGenerator fontGen = new SmartFontGenerator();
		FileHandle exoFile = Gdx.files.internal("UI/samplefont.ttf");
		BitmapFont fontSmall = fontGen.createFont(exoFile, "exo-small", 17);
		BitmapFont fontMedium = fontGen.createFont(exoFile, "exo-medium", 24);
		BitmapFont fontLarge = fontGen.createFont(exoFile, "exo-large", 48);
		
		
		LabelStyle labelStyle = new LabelStyle();
		
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("mainUIButton");
		buttonStyle.down = skin2.getDrawable("mainUIButton");
		buttonStyle.up = skin2.getDrawable("mainUIButton");

		labelStyle.font = fontMedium;
		buttonStyle.font = fontMedium;

		train = new TextButton("Train",buttonStyle);
		feed = new TextButton("Feed", buttonStyle);
		menu = new TextButton("Menu", buttonStyle);
		online = new TextButton("Online", buttonStyle);
		
		containerBottom = new Table();
		containerBottom.setSize(1280, train.getHeight());
		containerBottom.add(menu);
		containerBottom.add(new Image(skin2.getDrawable("mainUIButtonLink")));
		containerBottom.add(train);
		containerBottom.add(new Image(skin2.getDrawable("mainUIButtonLink")));
		containerBottom.add(feed);
		containerBottom.add(new Image(skin2.getDrawable("mainUIButtonLink"))).expandX().fillX();
		containerBottom.add(online);
		
		playerGold = new Label(": ", labelStyle);
		coinsImage = new Image(skin2.getDrawable("coins"));
		
		
		hunger = new Label(": ", labelStyle);
		hunger.setFontScale(1);
		Image hungerImage = new Image(ResourceLoader.getRegionFromId(ObjectId.MEAT));
		hungerImage.setOrigin(Align.center);
		hungerImage.setScale(0.5f);
		hungerBar = new MyProgressBar(skin2, 0, 100);
		
		exhaustion = new Label("Energy: ", labelStyle);
		exhaustion.setFontScale(1);
		exhaustionBar = new MyProgressBar(skin2, 0, 100);
		
		time = new Label("Time:  "+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE), labelStyle);
		time.setFontScale(1);
		containerTop = new Table();
		containerTop.setSize(1280, 30*1);
		containerTop.setPosition(0, 720 - 30*1);
		containerTop.setBackground(skin.getDrawable("default-rect"));
		
		help = new ImageButton(skin2.getDrawable("helpButton"));
		settings = new ImageButton(skin2.getDrawable("settingsButton"));
		light = new ImageButton(skin2.getDrawable("lightBulbOn"), skin2.getDrawable("lightBulbOn"), skin2.getDrawable("lightBulbOff"));
		light.setPosition(0, (720 - 30*1)-light.getHeight()*1);
		
		
		containerTop.add(coinsImage).align(Align.right).expandX();
		containerTop.add(playerGold).height(30).padRight(5);
		
		containerTop.add(hungerImage).align(Align.right).expandX();
		containerTop.add(hunger).height(30);
		containerTop.add(hungerBar).size(150*UIRenderer.scaleX, 10).expandX().align(Align.left);
		containerTop.add(exhaustion).height(30);
		containerTop.add(exhaustionBar).size(150*UIRenderer.scaleX, 10).expandX().align(Align.left);
//		containerTop.add(time).size(1280/4, 30);
		
		containerTop.add(help).padTop(30*1);
		containerTop.add(settings).padTop(30*1);
		
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
		
		//ui.addActor(containerBottom);
		ui.addActor(containerTop);
		ui.addActor(light);
		
		addListeners();
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
		
//		train.addListener(new ClickListener(){
//            @Override 
//            public void clicked(InputEvent event, float x, float y){
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.TRAIN_WINDOW));
//            }
//		});
//
//		feed.addListener(new ClickListener(){
//            @Override 
//            public void clicked(InputEvent event, float x, float y){
//            	//eManager.notify(new ScreenEvent(ScreenEvents.START_MINIGAME));
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.FEED_WINDOW));
//            }
//		});
//		
//		menu.addListener(new ClickListener(){
//            @Override 
//            public void clicked(InputEvent event, float x, float y){
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU));
//            }
//		});
//		
//		online.addListener(new ClickListener(){
//            @Override 
//            public void clicked(InputEvent event, float x, float y){
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.CONNECT_SCREEN));
//            	}
//		});
		
		light.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.LIGHT_SWAP, !light.isChecked()));
            }
		});
		help.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	String[] strings = new String[2];
        		strings[0] = "Hey, how are you going? I need a hand raising this Linkmon egg.";
        		strings[1] = "Blah blah blah blah blah. Stuff to type. I'm just writing stuff. I don't care what it is.";
            	eManager.notify(new MessageEvent(MessageEvents.SHOW_CHAT, MessageType.GAME_MESSAGE, 1, "Heading", strings));
            	}
		});
		settings.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	Gdx.app.log("GameUi", "Clicked Settings");
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU));
            	}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_MAIN_GAME_MUSIC));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update();
		
//		if(evolve) {
//			game.setScreen(evolutionScreen);
//			evolve = false;
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
		containerTop.remove();
		light.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		containerBottom.remove();
		containerTop.remove();
		
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.UPDATE_HUNGER_LEVEL): {
				hungerBar.update(event.value);
				break;
			}
			case(ModelEvents.UPDATE_EXHAUSTION_LEVEL): {
				exhaustionBar.update(event.value);
				break;
			}
			case(ModelEvents.UPDATE_GOLD): {
				playerGold.setText("Gold: "+ event.value);
				break;
			}
			case(ModelEvents.EVOLVE): {
				evolve = true;
				evolutionScreen = new EvolutionScreen(ui, eManager, event.value, event.value2);
				break;
			}
		}
	}

	@Override
	public void getPlayerStats(String name, int gold) {
		// TODO Auto-generated method stub
		playerGold.setText(": "+ gold + "g");
	}
}
