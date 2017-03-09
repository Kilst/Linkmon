package com.linkmon.view.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.ScreenType;
import com.linkmon.view.screens.widgets.SelectableItemButton;

public class MiniGameRewardScreen implements Screen {
	
	private Table container;
	private Table rewardsTable;
	private Image heading;
	
	private Image coinImage;
	private int coins = 0;
	private Label coinLabel;
	
	Group uiGroup;
	
	Skin skin;
	Skin skin2;
	
	EventManager eManager;
	
	TextButton back;
	TextButton replay;
	
	public MiniGameRewardScreen(Group group, EventManager eManager, int coinAmount) {
		
		uiGroup = group;
		this.eManager = eManager;
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		container = new Table();
		container.setSize(1280, 720);
		
		rewardsTable = new Table();
		rewardsTable.setSize(800, 600);
		rewardsTable.setBackground(skin2.getDrawable("container"));
		
		heading = new Image(skin2.getDrawable("rewardsLabel"));
		
		coinImage = new Image(skin2.getDrawable("coins"));
		coins = coinAmount;
		coinLabel = new Label(coins+"g", skin);
		
		back = new TextButton("Back", skin);
		replay = new TextButton("Replay", skin);
		
		rewardsTable.add(heading).colspan(2);
		rewardsTable.row();
		rewardsTable.add(coinImage);
		rewardsTable.add(coinLabel);
		rewardsTable.row();
		rewardsTable.add(back).padTop(140).size(80, 40);
		rewardsTable.add(replay).padTop(140).size(80, 40);
		
		container.add(rewardsTable);
		
		addListeners();
		
		eManager.notify(new ScreenEvent(ScreenEvents.ADD_PLAYER_GOLD, coins));
	}
	
	private void addListeners() {
		
		back.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.RETURN_TO_MAIN_GAME));
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU));
//            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MINIGAME_SELECT_SCREEN));
            }
		});
		
		replay.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.RESTART_MINI_GAME));
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(container);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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

}
