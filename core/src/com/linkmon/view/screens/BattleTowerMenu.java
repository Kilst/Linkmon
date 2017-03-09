package com.linkmon.view.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.aonevonebattle.OpponentFactory;
import com.linkmon.model.aonevonebattle.OpponentId;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.ScrollingBackground;

public class BattleTowerMenu implements Screen, MyScreen {
	
	
	private Table rootTable;
	private Table container;
	
	private Table opponentSelectContainer;
	
	private Table opponentTable;
	
	private Table buttonTable;
	
	private TextButton levelOne;
	private TextButton levelTwo;
	private TextButton levelThree;
	
	private Skin skin2;
	
	private Group uiGroup;
	
	private EventManager eManager;
	
	private PlayerController playerController;
	
	private Button back;
	
	private Button opponentBack;
	
	private ScrollingBackground scrollingSky;
	private ScrollingBackground scrollingClouds;
	
	private int currentOpponentsId = 0;
	
	public BattleTowerMenu(Group group, EventManager eManager, PlayerController playerController) {
		
		this.playerController = playerController;
		this.eManager = eManager;
		
		uiGroup = group;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		Texture textureSky = ResourceLoader.assetManager.get("battleTowerBackgroundSky.png", Texture.class);
		textureSky.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrollingSky = new ScrollingBackground(textureSky);
		scrollingSky.setScrollSpeed(-80, 0);
		
		rootTable = new Table();
		rootTable.setSize(1280, 720);
		rootTable.setBackground(new Image(ResourceLoader.assetManager.get("battleTowerBackgroundTower.png", Texture.class)).getDrawable());
		
		Texture texture = ResourceLoader.assetManager.get("battleTowerBackgroundClouds.png", Texture.class);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrollingClouds = new ScrollingBackground(texture);
		scrollingClouds.setScrollSpeed(80, 0);
		
		container = new Table();
		container.setSize(1280, 720);
		
		opponentSelectContainer = new Table();
		opponentSelectContainer.setSize(1280, 720);
		opponentSelectContainer.setVisible(false);
		
		opponentTable = new Table();
		opponentTable.setBackground(skin2.getDrawable("battleTowerMenu"));
		
		buttonTable = new Table();
		buttonTable.setBackground(skin2.getDrawable("battleTowerMenu"));
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("blankButton");
		buttonStyle.down = skin2.getDrawable("blankButtonRed");
		buttonStyle.up = skin2.getDrawable("blankButton");
		buttonStyle.font = ResourceLoader.getLutFont("large");
		
		levelOne = new TextButton("Level 1", buttonStyle);
		levelTwo = new TextButton("Level 2", buttonStyle);
		levelThree = new TextButton("Level 3", buttonStyle);
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		back = new ImageButton(backButtonStyle);
		opponentBack = new ImageButton(backButtonStyle);
		
		buttonTable.add(levelOne).expand().padTop(40);
		buttonTable.row();
		buttonTable.add(levelTwo).expand();
		buttonTable.row();
		buttonTable.add(levelThree).expand();
		buttonTable.row();
		buttonTable.add(back).expand().align(Align.bottomLeft);
		
		
		container.add(buttonTable);
		
		
		opponentSelectContainer.add(opponentTable).expand().align(Align.left);
		
		addListeners();
	}
	
	private void addListeners() {
		
		levelOne.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	container.setVisible(false);
	        	opponentSelectContainer.setVisible(true);
	        	setOpponents(OpponentId.levelOne);
	        	currentOpponentsId = OpponentId.levelOne;
	        }
		});
		
		levelTwo.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	container.setVisible(false);
	        	opponentSelectContainer.setVisible(true);
	        	setOpponents(OpponentId.levelTwo);
	        	currentOpponentsId = OpponentId.levelTwo;
	        }
		});
		
		levelThree.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	container.setVisible(false);
	        	opponentSelectContainer.setVisible(true);
	        	setOpponents(OpponentId.levelThree);
	        	currentOpponentsId = OpponentId.levelThree;
	        }
		});
		
		back.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
	        }
		});
		
		opponentBack.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	container.setVisible(true);
	        	opponentSelectContainer.setVisible(false);
	        }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BATTLE_TOWER_MUSIC));
		uiGroup.addActor(scrollingSky);
		uiGroup.addActor(rootTable);
		uiGroup.addActor(scrollingClouds);
		uiGroup.addActor(container);
		uiGroup.addActor(opponentSelectContainer);
		// Rebuild the currently open opponentTable
		if(currentOpponentsId != 0)
			setOpponents(currentOpponentsId);
		
	}
	
	public void setOpponents(int oppId) {
		
		opponentTable.clear();
		
		final int[] ids = OpponentFactory.getIds(oppId);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		if(ids[0] == OpponentId.levelOneOne || playerController.checkBattleTowerFlag(ids[0])) {
			Table opp1 = new Table();
			opp1.setBackground(skin2.getDrawable("attackMenuButton"));
			opp1.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			opp1.setTouchable(Touchable.enabled);
			
			Image itemImage = new Image();
			itemImage.setDrawable(skin2.getDrawable("completeStamp"));
			itemImage.setSize(skin2.getDrawable("completeStamp").getMinWidth(), skin2.getDrawable("completeStamp").getMinHeight());
			
			Label opp1Text = new Label("Opponent 1", labelStyle);
			
			
			opp1.add(opp1Text).expandX();
			opp1.add(itemImage);
			
			opponentTable.add(opp1).expand().padTop(40);
			opponentTable.row();
			
			if(playerController.checkBattleTowerFlag(ids[0]+1))
				itemImage.setVisible(true);
			else
				itemImage.setVisible(false);
		
			opp1.addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y){
		        	eManager.notify(new ScreenEvent(ScreenEvents.START_LOCAL_BATTLE));
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_CREATE, ids[0]));
		        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.LOCAL_BATTLE));
		        }
			});
		}
		else {
			Table opp1 = new Table();
			opp1.setBackground(skin2.getDrawable("attackMenuButton"));
			opp1.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			Image image = new Image(skin2.getDrawable("lock"));
			opp1.add(image).expand();
			opponentTable.add(opp1).expand().padTop(40);
			opponentTable.row();
		}
			
		
		if(playerController.checkBattleTowerFlag(ids[1])) {
			Table opp2 = new Table();
			opp2.setBackground(skin2.getDrawable("attackMenuButton"));
			opp2.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			opp2.setTouchable(Touchable.enabled);
			Label opp2Text = new Label("Opponent 2", labelStyle);
			
			Image itemImage2 = new Image();
			itemImage2.setDrawable(skin2.getDrawable("completeStamp"));
			itemImage2.setSize(skin2.getDrawable("completeStamp").getMinWidth(), skin2.getDrawable("completeStamp").getMinHeight());
			
			
			
			opp2.add(opp2Text).expandX();
			opp2.add(itemImage2);
			
			opponentTable.add(opp2).expand();
			opponentTable.row();
			
			if(playerController.checkBattleTowerFlag(ids[1]+1))
				itemImage2.setVisible(true);
			else
				itemImage2.setVisible(false);
			
			opp2.addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y){
		        	eManager.notify(new ScreenEvent(ScreenEvents.START_LOCAL_BATTLE));
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_CREATE, ids[1]));
		        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.LOCAL_BATTLE));
		        }
			});
		}
		else {
			Table opp1 = new Table();
			opp1.setBackground(skin2.getDrawable("attackMenuButton"));
			opp1.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			Image image = new Image(skin2.getDrawable("lock"));
			opp1.add(image).expand();
			opponentTable.add(opp1).expand();
			opponentTable.row();
		}
		
		if(playerController.checkBattleTowerFlag(ids[2])) {
			Table opp3 = new Table();
			opp3.setBackground(skin2.getDrawable("attackMenuButton"));
			opp3.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			opp3.setTouchable(Touchable.enabled);
			
			Image itemImage3 = new Image();
			itemImage3.setDrawable(skin2.getDrawable("completeStamp"));
			itemImage3.setSize(skin2.getDrawable("completeStamp").getMinWidth(), skin2.getDrawable("completeStamp").getMinHeight());
			
			Label opp3Text = new Label("Opponent 3", labelStyle);
			
			opp3.add(opp3Text).expandX();
			opp3.add(itemImage3);
			
			opponentTable.add(opp3).expand();
			opponentTable.row();
			
			if(playerController.checkBattleTowerFlag(ids[2]+1))
				itemImage3.setVisible(true);
			else
				itemImage3.setVisible(false);
			
			opp3.addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y){
		        	eManager.notify(new ScreenEvent(ScreenEvents.START_LOCAL_BATTLE));
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_CREATE, ids[2]));
		        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.LOCAL_BATTLE));
		        }
			});
		}
		else {
			Table opp1 = new Table();
			opp1.setBackground(skin2.getDrawable("attackMenuButton"));
			opp1.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			Image image = new Image(skin2.getDrawable("lock"));
			opp1.add(image).expand();
			opponentTable.add(opp1).expand();
			opponentTable.row();
		}
		
		if(playerController.checkBattleTowerFlag(ids[3])) {
			Table opp4 = new Table();
			opp4.setBackground(skin2.getDrawable("attackMenuButton"));
			opp4.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			opp4.setTouchable(Touchable.enabled);
			
			Image itemImage4 = new Image();
			itemImage4.setDrawable(skin2.getDrawable("completeStamp"));
			itemImage4.setSize(skin2.getDrawable("completeStamp").getMinWidth(), skin2.getDrawable("completeStamp").getMinHeight());
			
			Label opp4Text = new Label("Opponent 4", labelStyle);
			
			opp4.add(opp4Text).expandX();
			opp4.add(itemImage4);
			
			opponentTable.add(opp4).expand();
			opponentTable.row();
			
			if(playerController.checkBattleTowerFlag(ids[3]+1))
				itemImage4.setVisible(true);
			else
				itemImage4.setVisible(false);
			
			opp4.addListener(new ClickListener(){
		        @Override
		        public void clicked(InputEvent event, float x, float y){
		        	eManager.notify(new ScreenEvent(ScreenEvents.START_LOCAL_BATTLE));
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_CREATE, ids[3]));
		        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.LOCAL_BATTLE));
		        }
			});
		}
		else {
			Table opp1 = new Table();
			opp1.setBackground(skin2.getDrawable("attackMenuButton"));
			opp1.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
			Image image = new Image(skin2.getDrawable("lock"));
			opp1.add(image).expand();
			opponentTable.add(opp1).expand();
			opponentTable.row();
		}
		
		
		opponentTable.add(opponentBack).expand().align(Align.bottomLeft);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		scrollingSky.act(delta);
		scrollingClouds.act(delta);
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
		opponentSelectContainer.remove();
		scrollingSky.remove();
		scrollingClouds.remove();
		rootTable.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
