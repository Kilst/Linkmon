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
import com.linkmon.controller.LinkmonController;
import com.linkmon.controller.PlayerController;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.model.gameobject.linkmon.Move;
import com.linkmon.model.gameobject.linkmon.MoveFactory;
import com.linkmon.view.GameSprite;
import com.linkmon.view.LinkmonSprite;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.IBattleView;

public class OnlineBattleScreen implements Screen, IBattleView {
	private Table container;
	private Group uiGroup;
	volatile private Button attack1;
	volatile private Button attack2;
	private Skin skin;
	
	public Move playerMove;
	public Move opponentMove;
	
	
	private Table oppTable;
	private Table playerTable;
	public Table buttonTable;
	
	Label oppHealth;
	Label oppName;
	
	Label health;
	Label name;
	
	private EventManager eManager;
	private boolean finishedLoading = false;
	
	public int playerHealth;
	public int opponentHealth;
	
	private LinkmonSprite myLinkmonSprite;
	private LinkmonSprite oppLinkmonSprite;
	
	private ScreenController screenController;
	
	private Move move1;
	private Move move2;
	
	public OnlineBattleScreen(Group group, ScreenController screenController, EventManager eManager) {
		this.eManager = eManager;
		this.screenController = screenController;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		//Gdx.app.log("OnlineBattleScreen","Added Listener?");
	}
	
	public void update() {
		screenController.updateOnlineBattleScreen(this);
//		screenController.updateWindow(this);
	}
	
	
	public void updateLinkmons() {
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		screenController.updateWindow(this);
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		oppTable = new Table();
		oppHealth = new Label("Health: " + oppHealth, skin);
		oppName  = new Label("Opponent", skin);
		
		Table oppImageTable = new Table();
		oppImageTable.add(oppLinkmonSprite);
		Table oppStatsTable = new Table();
		oppStatsTable.add(oppName);
		oppStatsTable.row();
		oppStatsTable.add(oppHealth);
		
		oppTable.add(oppImageTable).size(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);
		oppTable.add(oppStatsTable).size(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);
		
		container.add(oppTable).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		container.row();
		
		
		playerTable = new Table();
		health = new Label(""+ playerHealth, skin);
		name  = new Label("", skin);
		
		Table playerImageTable = new Table();
		playerImageTable.add(myLinkmonSprite);
		Table playerStatsTable = new Table();
		playerStatsTable.add(name);
		playerStatsTable.row();
		playerStatsTable.add(health);
		
		playerTable.add(playerStatsTable).size(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);
		playerTable.add(playerImageTable).size(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);

		container.add(playerTable).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		container.row();
		
		buttonTable = new Table();
		
		attack1 = new TextButton("Attack1", skin);
		buttonTable.add(attack1).size(128*WorldRenderer.scaleX, 64*WorldRenderer.scaleY).bottom();
		attack2 = new TextButton("Attack2", skin);
		buttonTable.add(attack2).size(128*WorldRenderer.scaleX, 64*WorldRenderer.scaleY).bottom();
		
		container.add(buttonTable).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		
		uiGroup.addActor(container);
		uiGroup.toFront();
		addListeners();
		finishedLoading  = true;
		
		
	}
	
	private void addListeners() {
		
		attack1.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	try {
		            	eManager.notify(new ControllerEvent(ControllerEvents.SEND_MOVE, move1));
		            	playerMove = move1;
		            	buttonTable.setVisible(false);
	            	}
	            	catch (Exception e){
	            		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            	}
	            }
			});
		
		attack2.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ControllerEvent(ControllerEvents.SEND_MOVE, move2));
            	playerMove = move2;
            	buttonTable.setVisible(false);
            }
		});
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
		container.setVisible(false);
		container.remove();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getSprites(int myLinkmonId, int opponentLinkmonId) {
		// TODO Auto-generated method stub
		myLinkmonSprite = new LinkmonSprite(myLinkmonId);
		oppLinkmonSprite = new LinkmonSprite(opponentLinkmonId);
	}

	@Override
	public void getOpponentStats(int health, int attack, int defense, int speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHealths(int myNewHealth, int oppNewHealth) {
		// TODO Auto-generated method stub
		this.playerHealth = myNewHealth;
		this.opponentHealth = oppNewHealth;
		
		health.setText("Health: " + playerHealth);
		oppHealth.setText("Health: " + opponentHealth);
	}
	
	public void showBattleMessage(String text) {
		
	}

	@Override
	public void getMoves(int move1, int move2) {
		// TODO Auto-generated method stub
		this.move1 = MoveFactory.getMoveFromId(move1);
		this.move2 = MoveFactory.getMoveFromId(move2);
	}

	@Override
	public void updateOpponentMove(int oppMove) {
		// TODO Auto-generated method stub
		
	}
}
