package com.linkmon.view.screens.localbattle;

import java.util.ArrayList;
import java.util.List;

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
import com.linkmon.controller.LocalBattleController;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.linkmon.MoveIds;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.ExpandingTextWidget;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.MoveTable;
import com.linkmon.view.screens.widgets.MyProgressBar;

public class LocalBattleScreen implements Screen, MyScreen, ILocalBattle, IPlayerItems, ModelListener {
	
	private boolean battleEnded = false;
	
	int opp1Health;
	int opp2Health;
	int opp3Health;
	int p1Health;
	int p2Health;
	int p3Health;
	
	MyProgressBar opp1Bar;
	MyProgressBar opp2Bar;
	MyProgressBar opp3Bar;
	MyProgressBar p1Bar;
	MyProgressBar p2Bar;
	MyProgressBar p3Bar;
	
	private Group uiGroup;
	
	private EventManager eManager;
	private Skin skin2;
	private Skin skin;
	
	private Table container;
	private Table menuContainer;
	private Table attackContainer;
	private Table itemContainer;
	private List<BattleItemTable> itemButtons;
	
	private Table statsContainerPlayer;
	private Table statsContainerOpponent;
	
	private Table move1Container;
	private Table move2Container;
	private Table move3Container;
	
	private Button move1Button;
	private Button move2Button;
	private Button move3Button;
	
	private Button attackBack;
	private Button itemBack;
	
	private boolean playing = false;
	
	private ExpandingTextWidget winMessage;
	
	LocalBattleController localBattleController;
	
	PlayerController playerController;
	
	public LocalBattleScreen(Group group, EventManager eManager, LocalBattleController localBattleController, PlayerController playerController) {
		
		this.playerController = playerController;
		this.localBattleController = localBattleController;
		uiGroup = group;
		this.eManager = eManager;
		eManager.addModelListener(this);
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_UPDATE_HEALTHS, this));
		
		container = new Table();
		container.setSize(UIRenderer.WIDTH, UIRenderer.HEIGHT);
		//container.setBackground(skin2.getDrawable("statsBackground"));
		
		menuContainer = new Table();
		menuContainer.setBackground(skin2.getDrawable("battleMenu"));
		
		attackContainer = new Table();
		attackContainer.setBackground(skin2.getDrawable("attackMenu"));
		attackContainer.setSize(skin2.getDrawable("attackMenu").getMinWidth(), skin2.getDrawable("attackMenu").getMinHeight());
		attackContainer.setPosition(0, 0);
		attackContainer.setVisible(false);
		
		itemContainer = new Table();
		itemContainer.setBackground(skin2.getDrawable("itemMenu"));
		itemContainer.setSize(skin2.getDrawable("itemMenu").getMinWidth(), skin2.getDrawable("itemMenu").getMinHeight());
		itemContainer.setPosition(1280-skin2.getDrawable("itemMenu").getMinWidth(), 0);
		itemContainer.setVisible(false);
		
		itemButtons = new ArrayList<BattleItemTable>();
		
		statsContainerPlayer = new Table();
		statsContainerPlayer.setBackground(skin2.getDrawable("playerStatsContainer"));
//		statsContainerPlayer.setSize(skin2.getDrawable("playerContainer").getMinWidth(), skin2.getDrawable("playerContainer").getMinHeight());
//		statsContainerPlayer.setPosition(0, 720-skin2.getDrawable("playerContainer").getMinHeight());
		
		statsContainerOpponent = new Table();
		statsContainerOpponent.setBackground(skin2.getDrawable("playerStatsContainer"));
//		statsContainerOpponent.setSize(skin2.getDrawable("playerContainer").getMinWidth(), skin2.getDrawable("playerContainer").getMinHeight());
//		statsContainerOpponent.setPosition(1280-skin2.getDrawable("playerContainer").getMinWidth(), 720-skin2.getDrawable("playerContainer").getMinHeight());
		
		
		move1Button = new ImageButton(skin2.getDrawable("battleButtonAttack"));
		move2Button = new ImageButton(skin2.getDrawable("battleButtonDefend"));
		move3Button = new ImageButton(skin2.getDrawable("battleButtonItem"));
		
		attackBack = new ImageButton(skin2.getDrawable("newBackButton"));
		itemBack = new ImageButton(skin2.getDrawable("newBackButton"));
		
		opp1Bar = new MyProgressBar(skin2, opp1Health, opp1Health);
		opp2Bar = new MyProgressBar(skin2, opp2Health, opp2Health);
		opp3Bar = new MyProgressBar(skin2, opp3Health, opp3Health);
		p1Bar = new MyProgressBar(skin2, p1Health, p1Health);
		p2Bar = new MyProgressBar(skin2, p2Health, p2Health);
		p3Bar = new MyProgressBar(skin2, p3Health, p3Health);
		
		eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_UPDATE_HEALTHS, this));
		localBattleController.getMoves(this);
		localBattleController.getItems(this);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		Label playerName = new Label("Name: ", labelStyle);
		Label playerHp = new Label("HP: ", labelStyle);
		Label playerEnergy = new Label("Energy: ", labelStyle);
		
		Label opponentName = new Label("Name: ", labelStyle);
		Label opponentHp = new Label("HP: ", labelStyle);
		Label opponentEnergy = new Label("Energy: ", labelStyle);
		
//		container.add(opp1Bar).width(150);
//		container.add(p1Bar).width(150);
//		container.row();
//		container.add(opp2Bar).width(150);
//		container.add(p2Bar).width(150);
//		container.row();
//		container.add(opp3Bar).width(150);
//		container.add(p3Bar).width(150);
//		container.row().expandY();
		statsContainerPlayer.add(playerName).colspan(2);
		statsContainerPlayer.row();
		statsContainerPlayer.add(playerHp).padTop(-20);
		statsContainerPlayer.add(p1Bar).size(200, 20).padTop(-20);
		statsContainerPlayer.row();
		statsContainerPlayer.add(playerEnergy).padTop(-20);
		statsContainerPlayer.add(p2Bar).size(200, 20).padTop(-20);
		
		
		statsContainerOpponent.add(opponentName).colspan(2);
		statsContainerOpponent.row();
		statsContainerOpponent.add(opponentHp).padTop(-20);
		statsContainerOpponent.add(opp1Bar).size(200, 20).padTop(-20);
		statsContainerOpponent.row();
		statsContainerOpponent.add(opponentEnergy).padTop(-20);
		statsContainerOpponent.add(opp2Bar).size(200, 20).padTop(-20);
		
		menuContainer.add(move1Button).expand();
		menuContainer.add(move2Button).expand();
		menuContainer.add(move3Button).expand();
		
		container.add(statsContainerPlayer).expand().align(Align.topLeft);
		container.add(statsContainerOpponent).expand().align(Align.topRight);
		container.row();
		container.add(menuContainer).expand().align(Align.bottom).colspan(2);
		
		itemContainer.add(itemBack);
		
		addListeners();
		
		uiGroup.addActor(new BattleIntroWidget(uiGroup, localBattleController.getPlayerId(), localBattleController.getOpponentId()));
	}
	
	private void addListeners() {
		
		move1Button.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
//	        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAYER_MOVE, MoveIds.SHOCK));
//	        	playing = true;
	        	menuContainer.setVisible(false);
	        	attackContainer.setVisible(true);
//	        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
	        }
		});
		
		move2Button.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
//	        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAYER_MOVE, 4));
	        	localBattleController.setMove(4);
	        	playing = true;
	        	menuContainer.setVisible(false);
	        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
	        }
		});
		
		move3Button.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
//	        	localBattleController.useItem(ObjectId.HEALTH_POTION);
//	        	playing = true;
//	        	menuContainer.setVisible(false);
//	        	localBattleController.playRound();
	        	menuContainer.setVisible(false);
	        	itemContainer.setVisible(true);
	        }
		});
		
		move1Container.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	if(localBattleController.setMove(1)) {
		        	playing = true;
		        	menuContainer.setVisible(false);
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
		        	attackContainer.setVisible(false);
	        	}
	        	else {
	        		new LocalMessageBox("Energy Error", "Not enough energy!", uiGroup);
	        	}
	        }
		});
		
		move2Container.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	if(localBattleController.setMove(2)) {
		        	playing = true;
		        	menuContainer.setVisible(false);
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
		        	attackContainer.setVisible(false);
	        	}
	        	else {
	        		new LocalMessageBox("Energy Error", "Not enough energy!", uiGroup);
	        	}
	        }
		});
		
		move3Container.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	if(localBattleController.setMove(3)) {
		        	playing = true;
		        	menuContainer.setVisible(false);
		        	attackContainer.setVisible(false);
		        	eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
	        	}
	        	else {
	        		new LocalMessageBox("Energy Error", "Not enough energy!", uiGroup);
	        	}
	        }
		});
		
		attackBack.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	menuContainer.setVisible(true);
	        	attackContainer.setVisible(false);
	        }
		});
		
		itemBack.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y){
	        	menuContainer.setVisible(true);
	        	itemContainer.setVisible(false);
	        }
		});
		
		for(BattleItemTable table : itemButtons) {
			table.addClickListener(attackContainer, itemContainer, localBattleController, this);
		}
	}
	
	@Override
	public void getMoves(String move1Name, int move1Power, int move1Energy, int move1Id, int type1, String effect1,
			String move2Name, int move2Power, int move2Energy, int move2Id, int type2, String effect2, String move3Name,
			int move3Power, int move3Energy, int move3Id, int type3, String effect3) {
		// TODO Auto-generated method stub
		
//		move1Container = new Table();
		move1Container = new MoveTable(skin2, move1Id, move1Name, move1Power, move1Energy, type1, effect1, null, null, false);
//		move1Container.setBackground(skin2.getDrawable("attackMenuButton"));
//		move1Container.setSize(492, 120);
		move1Container.setTouchable(Touchable.enabled);
//		move2Container = new Table();
		move2Container = new MoveTable(skin2, move2Id, move2Name, move2Power, move2Energy, type2, effect2, null, null, false);
//		move2Container.setBackground(skin2.getDrawable("attackMenuButton"));
//		move2Container.setSize(492, 120);
		move2Container.setTouchable(Touchable.enabled);
//		move3Container = new Table();
		move3Container = new MoveTable(skin2, move3Id, move3Name, move3Power, move3Energy, type3, effect3, null, null, false);
//		move3Container.setBackground(skin2.getDrawable("attackMenuButton"));
//		move3Container.setSize(492, 120);
		move3Container.setTouchable(Touchable.enabled);
		
//		LabelStyle labelStyle = new LabelStyle();
//		labelStyle.font = ResourceLoader.getSampleFont("medium");
//		
//		Label name1 = new Label(move1Name, labelStyle);
//		Label name2 = new Label(move2Name, labelStyle);
//		Label name3 = new Label(move3Name, labelStyle);
//		
//		Label pow1 = new Label("Power: " + move1Power, labelStyle);
//		Label pow2 = new Label("Power: " + move2Power, labelStyle);
//		Label pow3 = new Label("Power: " + move3Power, labelStyle);
//		
//		Label energy1 = new Label("Energy: " + move1Energy, labelStyle);
//		Label energy2 = new Label("Energy: " + move2Energy, labelStyle);
//		Label energy3 = new Label("Energy: " + move3Energy, labelStyle);
//		
//		Label type1 = new Label("Type: ", labelStyle);
//		Label type2 = new Label("Type: ", labelStyle);
//		Label type3 = new Label("Type: ", labelStyle);
//		
//		Label status1 = new Label("Effect: " + effect1, labelStyle);
//		Label status2 = new Label("Effect: " + effect2, labelStyle);
//		Label status3 = new Label("Effect: " + effect3, labelStyle);
//		
//		Image type1Image = new Image();
//		type1Image.setDrawable(skin2.getDrawable("type-logo-fire"));
//		type1Image.setSize(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight());
//		
//		Image type2Image = new Image();
//		type2Image.setDrawable(skin2.getDrawable("type-logo-water"));
//		
//		Image type3Image = new Image();
//		type3Image.setDrawable(skin2.getDrawable("type-logo-grass"));
//		
//		
//		move1Container.add(name1).align(Align.left).expand().padLeft(20).padTop(-15);
//		move1Container.add(type1).align(Align.right).padRight(20).padTop(-15);
//		move1Container.add(type1Image).padRight(20).size(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight()).padTop(-15);
//		move1Container.row();
//		move1Container.add(pow1).align(Align.left).padLeft(20).padTop(-20);
//		move1Container.add(energy1).align(Align.right).padRight(20).padTop(-20).colspan(2);
//		move1Container.row();
//		move1Container.add(status1).expandX().padTop(-20).colspan(3).padBottom(-15);
//		
//		move2Container.add(name2).align(Align.left).expand().padLeft(20).padTop(-15);
//		move2Container.add(type2).align(Align.right).padRight(20).padTop(-15);
//		move2Container.add(type2Image).padRight(20).size(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight()).padTop(-15);
//		move2Container.row();
//		move2Container.add(pow2).align(Align.left).padLeft(20).padTop(-20);
//		move2Container.add(energy2).align(Align.right).padRight(20).padTop(-20).colspan(2);
//		move2Container.row();
//		move2Container.add(status2).expandX().padTop(-20).colspan(3).padBottom(-15);
//		
//		move3Container.add(name3).align(Align.left).expand().padLeft(20).padTop(-15);
//		move3Container.add(type3).align(Align.right).padRight(20).padTop(-15);
//		move3Container.add(type3Image).padRight(20).size(skin2.getDrawable("type-logo-fire").getMinWidth(), skin2.getDrawable("type-logo-fire").getMinHeight()).padTop(-15);
//		move3Container.row();
//		move3Container.add(pow3).align(Align.left).padLeft(20).padTop(-20);
//		move3Container.add(energy3).align(Align.right).padRight(20).padTop(-20).colspan(2);
//		move3Container.row();
//		move3Container.add(status3).expandX().padTop(-20).colspan(3).padBottom(-15);
		
		
		attackContainer.add(move1Container).expand().padTop(40).width(492).height(120);
		attackContainer.row();
		attackContainer.add(move2Container).expand().width(492).height(120);
		attackContainer.row();
		attackContainer.add(move3Container).expand().width(492).height(120);
		attackContainer.row();
		attackContainer.add(attackBack).expand().align(Align.bottomLeft);
		
//		attackContainer.debug();
//		move1Container.debug();
//		move2Container.debug();
//		move3Container.debug();
	}
	
	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, int type, String itemText) {
		// TODO Auto-generated method stub
		BattleItemTable table = new BattleItemTable(skin2, name, quantity, itemText);
		table.setId(id);
		table.setBackground(skin2.getDrawable("attackMenuButton"));
		table.setSize(skin2.getDrawable("attackMenuButton").getMinWidth(), skin2.getDrawable("attackMenuButton").getMinHeight());
		table.setTouchable(Touchable.enabled);
		
		
		itemButtons.add(table);
		
		itemContainer.add(table);
		itemContainer.row();
	}

	@Override
	public void updateHealths(int opp1, int opp2, int opp3, int p1, int p2, int p3) {
		// TODO Auto-generated method stub
		opp1Health = opp1;
		opp2Health = opp2;
		opp3Health = opp3;
		p1Health = p1;
		p2Health = p2;
		p3Health = p3;
		
		if(opp1Bar != null) {
			opp1Bar.update(opp1);
			if(opp1 < 1)
				opp1Bar.setVisible(false);
			opp2Bar.update(opp2);
			if(opp2 < 1)
				opp2Bar.setVisible(false);
			opp3Bar.update(opp3);
			if(opp3 < 1)
				opp3Bar.setVisible(false);
			p1Bar.update(p1);
			if(p1 < 1)
				p1Bar.setVisible(false);
			p2Bar.update(p2);
			if(p2 < 1)
				p2Bar.setVisible(false);
			p3Bar.update(p3);
			if(p3 < 1)
				p3Bar.setVisible(false);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_BATTLE_MUSIC));
		uiGroup.addActor(container);
		uiGroup.addActor(attackContainer);
		uiGroup.addActor(itemContainer);
	}
	
	long time = 0;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if(battleEnded) {
			if(System.currentTimeMillis() - time > 5000) {
				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
				eManager.notify(new ScreenEvent(ScreenEvents.RETURN_TO_MAIN_GAME));
			}
			move1Button.setVisible(false);
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
		container.remove();
		attackContainer.remove();
		itemContainer.remove();
		if(winMessage != null)
			winMessage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.LOCAL_BATTLE_ENDED) : {
				move1Button.setVisible(false);
	        	move2Button.setVisible(false);
	        	move3Button.setVisible(false);
				
				if(winMessage == null) {
					if(event.value == 0) {
						winMessage = new ExpandingTextWidget(skin2.getDrawable("winImage"));
						playerController.updateBattleTowerFlag(localBattleController.getOpponentId());
		        	}
		        	else
		        		winMessage = new ExpandingTextWidget(skin2.getDrawable("loseImage"));
		        		
					winMessage.setPosition(UIRenderer.WIDTH/2-winMessage.getWidth()/2, UIRenderer.HEIGHT/2-winMessage.getHeight()/2);
		    		uiGroup.addActor(winMessage);
		    		battleEnded = true;
		    		time = System.currentTimeMillis();
				}
				break;
			}
			case(ModelEvents.LOCAL_BATTLE_TURN_ENDED) : {
				playing = false;
				break;
			}
			case(ModelEvents.LOCAL_BATTLE_UPDATE_HEALTH) : {
				eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_BATTLE_UPDATE_HEALTHS, this));
				break;
			}
			case(ModelEvents.LOCAL_BATTLE_NEW_ROUND) : {
				//p2Bar.setVisible(false);
				if(p2Bar.isVisible()) {
					menuContainer.setVisible(true);
				}
				else {
					eManager.notify(new ScreenEvent(ScreenEvents.LOCAL_PLAY_TURN));
					playing = true;
				}
				break;
			}
			case(ModelEvents.NOT_ENOUGH_ENERGY) : {
				playing = false;
				break;
			}
		}
	}

	public void setPlaying(boolean b) {
		// TODO Auto-generated method stub
		playing = b;
	}
}
