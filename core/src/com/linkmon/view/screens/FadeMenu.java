package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.aonevonebattle.OpponentId;
import com.linkmon.model.items.ItemType;
import com.linkmon.model.minigames.MiniGameIds;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.MyScrollPane;
import com.linkmon.view.screens.widgets.ScrollingBackground;

public class FadeMenu implements Screen, MyScreen, IPlayerStats {
	
	// Testing out fading the menus
	
	private Table rootTable;
	
	private Image darken;
	
	private Image heading;
	
	private Table topTable;
	private Table middleTable;
	private Table buttonTable;
	private Table bottomTable;

	private ImageButton shopButton;
	private ImageButton itemButton;
	private ImageButton statsButton;
	private ImageButton movesButton;
	private ImageButton cryoGenicsButton;
	private ImageButton achievementsButton;
	private ImageButton onlineBattleButton;
	private ImageButton trainButton;
	private ImageButton battleTowerButton;
	private ImageButton miniGamesButton;
	
	private ImageButton leftButton;
	private ImageButton rightButton;
	
	private ImageButton backButton;
	
	private Image moneyTitle;
	private Label money;
	
	private Group ui;
	
	private EventManager eManager;
	
	MyScrollPane scroll;
	
	ScrollingBackground scrolling;
	Texture texture;
	
	private float scrollX;
	
	private boolean buttonPressed = false;
	private int pointer = -5;
	
	private List<Button> buttonList;
	private boolean enableButtons = false;
	
	private boolean fadeOut = false;
	private boolean fadeIn = true;
	
	public FadeMenu(Group ui, EventManager eManager) {
		
		fadeOut = false;
		fadeIn = true;
		
		this.eManager = eManager;
		
		this.ui = ui;
		
		buttonList = new ArrayList<Button>();
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		Image heading = new Image(skin2.getDrawable("menuHeading"));
		
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("smallLarge");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		rootTable = new Table(){
			@Override
			public void act(float delta) {
				if (rootTable.getColor().a >= 0f) {
					if(fadeOut)
						rootTable.getColor().a -= 0.05f;
					else if(fadeIn) {
						if(rootTable.getColor().a < 1f)
							rootTable.getColor().a += 0.05f;
						else if(rootTable.getColor().a >= 1f) {
							fadeIn = false;
							rootTable.getColor().a = 1f;
						}
						
					}
				}
				else if(rootTable.getColor().a < 0f) {
					rootTable.getColor().a = 0f;
					removeActors();
				}
			}
};
		rootTable.setSize(1280, 720);
		texture = ResourceLoader.assetManager.get("UI/statsBackground.png", Texture.class);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrolling = new ScrollingBackground(texture);
		
		topTable = new Table();
		topTable.setSize(Gdx.graphics.getWidth(), 135);
		
		middleTable = new Table();
		//middleTable.setBackground(skin2.getDrawable("greenContainer"));
		middleTable.setSize(1280, 500);
		
		buttonTable = new Table();
		buttonTable.setSize(1280-400, 500);
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		Table moneyTable = new Table();
		
		moneyTitle = new Image(skin2.getDrawable("money"));
		money = new Label("", labelStyle);
		
		scroll = new MyScrollPane(buttonTable, scrollStyle);
		scroll.setScrollingDisabled(false, true);

		
		shopButton = new ImageButton(skin2.getDrawable("newShopButton"));
		itemButton = new ImageButton(skin2.getDrawable("itemButton"));
		statsButton = new ImageButton(skin2.getDrawable("newStatsButton"));
		movesButton = new ImageButton(skin2.getDrawable("movesButton"));
		cryoGenicsButton = new ImageButton(skin2.getDrawable("cryoLabButton"));
		achievementsButton = new ImageButton(skin2.getDrawable("newAchievementsButton"));
		onlineBattleButton = new ImageButton(skin2.getDrawable("onlineBattleButton"));
		trainButton = new ImageButton(skin2.getDrawable("trainButton"));
		battleTowerButton = new ImageButton(skin2.getDrawable("battleTowerButton"));
		miniGamesButton = new ImageButton(skin2.getDrawable("miniGamesButton"));
		
		leftButton = new ImageButton(skin2.getDrawable("arrowButton"));
		rightButton = new ImageButton(skin2.getDrawable("arrowButton"));
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(1280, 720);
		darken.getColor().a = 0.7f;
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
		moneyTable.add(moneyTitle).expand().align(Align.right);
		moneyTable.row();
		moneyTable.add(money);
		
		topTable.add(heading).expand().align(Align.left);
		topTable.add(moneyTable).expand().align(Align.right);
		
		buttonTable.add(shopButton).size(shopButton.getWidth()).pad(10).padLeft(80);
		buttonTable.add(itemButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(statsButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(movesButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(miniGamesButton).size(shopButton.getWidth()).pad(10);
		
		buttonTable.row().pad(10);
		
		buttonTable.add(trainButton).size(shopButton.getWidth()).pad(10).padLeft(80);
		buttonTable.add(cryoGenicsButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(achievementsButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(battleTowerButton).size(shopButton.getWidth()).pad(10);
		
		buttonTable.add(onlineBattleButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(onlineBattleButton).size(shopButton.getWidth()).pad(10);
		buttonTable.add(onlineBattleButton).size(shopButton.getWidth()).pad(10);
		
		buttonTable.add(onlineBattleButton).size(shopButton.getWidth()).pad(10);
		
		middleTable.add(leftButton).align(Align.left).expand();
		leftButton.getImage().setScaleX(-1f);
		leftButton.getImage().setOriginX(leftButton.getWidth()/2);
		
		middleTable.add(scroll).width(UIRenderer.WIDTH-(leftButton.getWidth()*2));
		middleTable.add(rightButton).align(Align.right).expand();
		
		bottomTable.add(backButton).expandX().align(Align.left);
		
		rootTable.add(topTable).size(1280, 110);
		rootTable.row();
		rootTable.add(middleTable).size(1280, 500);
		rootTable.row();
		rootTable.add(bottomTable).size(1280, 110);
		
		//rootTable.debug();
		//middleTable.debug();
		//buttonTable.debug();
		
		addListeners();
		
		removeListeners();
	}
	
	private void removeActors() {
		rootTable.remove();
		scrolling.remove();
		
		fadeOut = false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		fadeIn = true;
		enableButtons = true;
		scrolling.getColor().a = 0;
		rootTable.getColor().a = 0;
		ui.addActor(scrolling);
		ui.addActor(darken);
		ui.addActor(rootTable);
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_MENU_MUSIC));
	}
	
	private void removeListeners() {
		buttonList.add(achievementsButton);
		buttonList.add(battleTowerButton);
		buttonList.add(cryoGenicsButton);
		buttonList.add(itemButton);
		buttonList.add(leftButton);
		buttonList.add(miniGamesButton);
		buttonList.add(movesButton);
		buttonList.add(onlineBattleButton);
		buttonList.add(backButton);
		buttonList.add(rightButton);
		buttonList.add(rightButton);
		buttonList.add(statsButton);
		buttonList.add(shopButton);
		buttonList.add(trainButton);
		
//		rightButton.removeListener(rightButton.getClickListener());
//		
//		leftButton.removeListener(leftButton.getClickListener());
//		
//		backButton.removeListener(backButton.getClickListener());
//		
//		shopButton.removeListener(shopButton.getClickListener());
//		
//		itemButton.removeListener(itemButton.getClickListener());
//		
//		trainButton.removeListener(trainButton.getClickListener());
//		
//		battleTowerButton.removeListener(battleTowerButton.getClickListener());
//		
//		statsButton.removeListener(statsButton.getClickListener());
//		
//		movesButton.removeListener(movesButton.getClickListener());
//		
//		cryoGenicsButton.removeListener(cryoGenicsButton.getClickListener());
//		
//		achievementsButton.removeListener(achievementsButton.getClickListener());
//		
//		miniGamesButton.removeListener(miniGamesButton.getClickListener());
//		
//		onlineBattleButton.removeListener(onlineBattleButton.getClickListener());
	}
	
	private void setButtonsTouchable(Touchable touchable) {
		for(Button button : buttonList) {
			button.setTouchable(touchable);
		}
	}

	private void addListeners() {
		
		rightButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	if(scrollX < 2)
	        		scrollX += 1;
	        	scroll.scrollTo((scroll.getWidth()-60)*scrollX, scroll.getY(), (UIRenderer.WIDTH-(leftButton.getWidth()*2)-60), 0);
	        }
		});
		
		leftButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	if(scrollX > 0)
	        		scrollX -= 1;
	        	scroll.scrollTo((scroll.getWidth()-60)*scrollX, scroll.getY(), (UIRenderer.WIDTH-(leftButton.getWidth()*2)-60), 0);
	        }
		});
		
		backButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_DECLINE));
	        }
		});
		
		shopButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.SHOP));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		itemButton.addListener(new ClickListener(){
			
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ITEM));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		trainButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointers,int button) {
				if(!buttonPressed) {
					super.touchDown(event, x, y, pointers, button);
					buttonPressed = true;
					pointer = pointers;
					setButtonsTouchable(Touchable.disabled);
					return true;
				}
				else
					return false;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer,int button) {
//				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.TRAIN_WINDOW));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        	super.touchUp(event, x, y, pointer, button);
	        	enableButtons = true;
	        	buttonPressed = false;
			}
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	super.clicked(event, x, y);
//	        	if(pointer == 0) {
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.TRAIN_WINDOW));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
//	        	}
	        }
		});
		
		battleTowerButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.BATTLE_TOWER));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		statsButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointers,int button) {
				if(!buttonPressed) {
					super.touchDown(event, x, y, pointers, button);
					buttonPressed = true;
					pointer = pointers;
					setButtonsTouchable(Touchable.disabled);
					return true;
				}
				else
					return false;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer,int button) {
//				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STATS_WINDOW));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        	super.touchUp(event, x, y, pointer, button);
	        	enableButtons = true;
	        	buttonPressed = false;
			}
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	super.clicked(event, x, y);
//	        	if(pointer == 0) {
		        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STATS_WINDOW));
		        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
//	        	}
	        }
		});
		
		movesButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MOVE));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		cryoGenicsButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.CRYO_SCREEN));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		achievementsButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
//	        	eManager.notify(new ScreenEvent(ScreenEvents.OPEN_MINIGAME, MiniGameIds.COIN_ROLL));
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.SETTINGS));
	        }
		});
		
		miniGamesButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MINIGAME_SELECT_SCREEN));
	        	eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BUTTON_ACCEPT));
	        }
		});
		
		onlineBattleButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	
//	        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_CREATE, OpponentId.levelThree));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.START_LOCAL_BATTLE));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.LOCAL_BATTLE));
	        }
		});
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		// BugFix: two buttons could be pressed at the same time, so we have to disable all the buttons each touchDown
		// and set enableButtons = true in touchUp. This way, no 2 events can fire on the same frame.
		// You could open 2 menus at the same time previously
		if(enableButtons) {
			setButtonsTouchable(Touchable.enabled);
			enableButtons = false;
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
		fadeOut = true;
		darken.remove();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPlayerStats(String name, int gold) {
		// TODO Auto-generated method stub
		money.setText("$"+gold);
	}
}