package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.LinkmonController;
import com.linkmon.controller.PlayerController;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.network.NetworkEvent;
import com.linkmon.eventmanager.network.NetworkEvents;
import com.linkmon.eventmanager.network.NetworkListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.linkmon.Move;
import com.linkmon.model.gameobject.linkmon.MoveFactory;
import com.linkmon.view.GameSprite;
import com.linkmon.view.LinkmonSprite;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IBattleView;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.BattleStats;

public class OnlineBattleScreen implements Screen, IBattleView {
	
	private Image background;
	private Table container;
	private Group uiGroup;
	volatile private Button attack1;
	volatile private Button attack2;
	private Skin skin;
	
	public Move playerMove;
	public Move opponentMove;
	
	
	private BattleStats oppTable;
	private BattleStats playerTable;
	public Table buttonTable;
	
	Label oppHealth;
	Label oppName;
	
	Label health;
	Label name;
	
	private EventManager eManager;
	private boolean finishedLoading = false;
	
	public int playerHealth;
	public int opponentHealth;
	
	private AnimationWidget myLinkmonSprite;
	private AnimationWidget oppLinkmonSprite;
	
	
	private Move move1;
	private Move move2;
	
	private Skin skin2;
	
	public OnlineBattleScreen(Group group, EventManager eManager) {
		this.eManager = eManager;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
	}
	
	public void update() {
		eManager.notify(new ScreenEvent(ScreenEvents.UPDATE_ONLINE_BALLTE, this));
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		background = new Image(skin2.getDrawable("statsBackground"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		oppTable = new BattleStats();
		
		container.add(oppTable).expand().align(Align.bottomLeft);
		container.row();
		
		
		playerTable = new BattleStats();

		container.add(playerTable).expand().align(Align.bottomRight);
		container.row();
		
		buttonTable = new Table();
		buttonTable.setBackground(skin2.getDrawable("table"));
		
		attack1 = new TextButton("Attack1", skin);
		buttonTable.add(attack1).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		attack2 = new TextButton("Attack2", skin);
		buttonTable.add(attack2).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY).bottom();
		
		container.add(buttonTable).height(Gdx.graphics.getHeight()/5f).expandX().fillX();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_ONLINE_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_ONLINE_SPRITES, this));
		
		uiGroup.addActor(background);
		uiGroup.addActor(oppLinkmonSprite);
		uiGroup.addActor(myLinkmonSprite);
		uiGroup.addActor(container);
		uiGroup.toFront();
		addListeners();
		finishedLoading  = true;
		
		container.debug();
	}
	
	private void addListeners() {
		
		attack1.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	try {
		            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, 11));
		            	playerMove = move1;
		            	buttonTable.setVisible(false);
	            	}
	            	catch (Exception e){
	            		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            	}
	            }
			});
		
		attack2.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SEND_MOVE, 12));
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
		background.remove();
		oppLinkmonSprite.remove();
		myLinkmonSprite.remove();
		container.remove();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getSprites(int myLinkmonId, int opponentLinkmonId) {
		// TODO Auto-generated method stub
		myLinkmonSprite = new AnimationWidget(myLinkmonId, 2/76f);
		oppLinkmonSprite = new AnimationWidget(opponentLinkmonId, 2/76f);
		myLinkmonSprite.setPosition(Gdx.graphics.getWidth()-myLinkmonSprite.getWidth()-100, Gdx.graphics.getHeight()/5f);
		oppLinkmonSprite.setPosition(100, Gdx.graphics.getHeight()-myLinkmonSprite.getHeight());
	}

	@Override
	public void updateHealths(int myNewHealth, int oppNewHealth) {
		// TODO Auto-generated method stub
		
		playerTable.update(myNewHealth);
		oppTable.update(oppNewHealth);
		buttonTable.setVisible(true);
	}

	@Override
	public void getMoves(int move1, int move2) {
		// TODO Auto-generated method stub
		this.move1 = MoveFactory.getMoveFromId(move1);
		this.move2 = MoveFactory.getMoveFromId(move2);
	}

	@Override
	public void getStats(int myNewHealth, String name, int oppNewHealth, String opponentName) {
		// TODO Auto-generated method stub
		playerTable.setStats(name, myNewHealth);
		oppTable.setStats(opponentName, oppNewHealth);
		buttonTable.setVisible(true);
	}

	@Override
	public void battleEnded() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ONLINE_SCREEN));
	}

	@Override
	public void showMessage() {
		// TODO Auto-generated method stub
		
	}
}
