package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.model.linkmon.StatType;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.StatWidget;

public class CryoScreen implements Screen, MyScreen, ILinkmonStats {
	
	private Image backgroundImage;
	private Table container;
	
	private Table linkmonTable;
	private Table buttonsTable;
	
	private TextButton backButton;
	private TextButton savedLinkmonButton;
	private TextButton saveButton;
	
	private AnimationWidget linkmonAnimation;
	
	private Skin skin;
	private Skin skin2;
	
	private Group ui;
	
	private EventManager eManager;
	
	private Image rankLabel;
	private Image linkmonRank;
	
	private Table statsTable;
	
	private CryoScreen screen = this;
	
	private int viewingLinkmon = 0;
	
	public CryoScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		this.ui = group;
	}

	@Override
	public void show() {
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		// Create Elements
		container = new Table();
		container.setBackground(skin2.getDrawable("newContainer"));
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setPosition(0, 0);
		
		linkmonTable = new Table();
		buttonsTable = new Table();
		
		backButton = new TextButton("Back", buttonStyle);
		savedLinkmonButton = new TextButton("View Saved", buttonStyle);
		saveButton = new TextButton("Save Current", buttonStyle);
		
		backgroundImage = new Image();
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		backgroundImage.setDrawable(skin2.getDrawable("statsBackground"));
		linkmonTable.setBackground(skin2.getDrawable("tableNoHeading"));
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		statsTable = new Table();
		
		// Build Layout
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		
		buttonsTable.add(backButton).align(Align.right);
		buttonsTable.row();
		buttonsTable.add(savedLinkmonButton).align(Align.right);
		buttonsTable.row();
		buttonsTable.add(saveButton).align(Align.right);
		buttonsTable.row();
		
		container.add(linkmonTable).expand().fill();
		container.add(buttonsTable);
		
		ui.addActor(backgroundImage);
		ui.addActor(container);
		
		addListeners();
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){	            	
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU_SCREEN));
	            }
		});
		
		savedLinkmonButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            	linkmonTable.clear();
        		statsTable.clear();
            	
            	if(viewingLinkmon == 0) {
	            	eManager.notify(new ScreenEvent(ScreenEvents.GET_SAVED_LINKMON_STATS, screen));
	            	savedLinkmonButton.setText("View Current");
	            	viewingLinkmon = 1;
	            	saveButton.setVisible(false);
            	}
            	else {
            		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, screen));
	            	savedLinkmonButton.setText("View Saved");
	            	viewingLinkmon = 0;
	            	saveButton.setVisible(true);
            	}
            }
		});
		
		saveButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){	            	
            	eManager.notify(new ScreenEvent(ScreenEvents.SAVE_BATTLE_LINKMON));
            }
		});
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
		backgroundImage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob,
			int rank) {
		// TODO Auto-generated method stub
		
		StatWidget widget = new StatWidget(skin, skin2, health, 9999, StatType.HEALTH);
		statsTable.add(widget);
		statsTable.row();
		
		StatWidget widget2 = new StatWidget(skin, skin2, attack, 999,  StatType.ATTACK);
		statsTable.add(widget2);
		statsTable.row();
		
		StatWidget widget3 = new StatWidget(skin, skin2, defense, 999, StatType.DEFENSE);
		statsTable.add(widget3);
		statsTable.row();
		
		StatWidget widget4 = new StatWidget(skin, skin2, speed, 999, StatType.SPEED);
		statsTable.add(widget4);
		statsTable.row();
		
		linkmonTable.add(statsTable);
		
		linkmonAnimation = new AnimationWidget(id, 2f/76f);
		// TODO Auto-generated method stub
		switch(rank) {
			case(RankIds.E) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-e"));
				break;
			}
			case(RankIds.D) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-d"));
				break;
			}
			case(RankIds.C) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-c"));
				break;
			}
			case(RankIds.B) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-b"));
				break;
			}
			case(RankIds.A) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-a"));
				break;
			}
			case(RankIds.S) : {
				linkmonRank.setDrawable(skin2.getDrawable("rank-s"));
				break;
			}
		}
		
//		String minutes;
//		String hours;
//		
//		if(dob.getMinute() < 10)
//			minutes = "0"+dob.getMinute();
//		else
//			minutes = ""+dob.getMinute();
//		
//		if(dob.getHour() == 0)
//			hours = "12";
//		else if(dob.getHour() < 10)
//			hours = "0"+dob.getHour();
//		else
//			hours = ""+dob.getHour();
//		
//		this.dob.setText(dob.getDay()+"/"+dob.getMonth()+"/"+dob.getYear()+"  "+hours+":"+minutes);
		
		
		linkmonTable.add(linkmonAnimation).colspan(2).pad(5);
		linkmonTable.row();
		linkmonTable.add().width(statsTable.getWidth());
		linkmonTable.add(rankLabel).align(Align.right);
		linkmonTable.add(linkmonRank).align(Align.left);
	}

}
