package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.linkmon.BirthDate;
import com.linkmon.componentmodel.linkmon.StatType;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.linkmon.RankIds;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.MyProgressBar;
import com.linkmon.view.screens.widgets.StatWidget;

public class StatsWindow implements Screen, ILinkmonStats, IPlayerStats {
	
	private Image container;
	private Table table;
	private Group uiGroup;
	
	public Label health;
	public Label attack;
	public Label defense;
	public Label speed;
	
	private Button backButton;
	
	private Image linkmonRank;
	private Image healthLabel;
	private MyProgressBar healthBar;
	private Image attackLabel;
	private MyProgressBar attackBar;
	private Image defenseLabel;
	private MyProgressBar defenseBar;
	private Image speedLabel;
	private MyProgressBar speedBar;
	
	private Label careMistakes;
	private Label dob;
	
	private Label careMistakesLabel;
	private Label dobLabel;
	
	private Image rankLabel;

	private EventManager eManager;
	
	private ScrollPane scrollPane;
	
	private AnimationWidget anim;
	
	Skin skin2;
	
	Skin skin;
	
	private Label linkmonLabel;
	
	private Table statsTable;
	
	private Table playerTable;
	
	private Label playerHeading;
	
	private Label playerNameLabel;
	private Label playerNameText;
	
	private Label playerGoldLabel;
	
	private Label playerGoldText;
	
	private Table image;
	
	Table tableLeft;
	Table tableRight;
	
	public StatsWindow(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		uiGroup = group;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		
		container = new Image(skin2.getDrawable("statsBackground"));
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setPosition(0, 0);
		
		image = new Table();
		image.setBackground(skin2.getDrawable("title"));
		image.setSize(250, 136);
		Label heading = new Label("STATS", skin);
		heading.setFontScale(1.1f);
		image.add(heading).padBottom(15);
		image.setPosition((Gdx.graphics.getWidth()/2)-image.getWidth()/2, Gdx.graphics.getHeight()-image.getHeight());
		
		//statsLabel = new Label("Stats", skin);
		
		table = new Table();
		table.setBackground(skin2.getDrawable("newContainer"));
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setPosition(0, 0);
		
		backButton = new ImageButton(skin2.getDrawable("button"));
		backButton.setPosition(Gdx.graphics.getWidth()-backButton.getWidth()-70, 55);
		
		
		statsTable = new Table();
//		statsTable.setBackground(skin2.getDrawable("table"));
		statsTable.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.5f);
		statsTable.setPosition(Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/4);
//		statsTable.setBackground(skin2.getDrawable("statsTable"));
		
		linkmonLabel = new Label("Linkmon Stats", skin);
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		
		health = new Label("", skin);
		attack = new Label("", skin);
		defense = new Label("", skin);
		speed = new Label("", skin);
		careMistakes = new Label("", skin);
		dob = new Label("", skin);
		careMistakesLabel = new Label("Care Mistakes: ", skin);
		dobLabel = new Label("Date of Birth: ", skin);
		
		healthLabel = new Image(skin2.getDrawable("healthLabel"));
		attackLabel = new Image(skin2.getDrawable("attackLabel"));
		defenseLabel = new Image(skin2.getDrawable("defenseLabel"));
		speedLabel = new Image(skin2.getDrawable("speedLabel"));
		
		playerTable = new Table();
		playerTable.setBackground(skin2.getDrawable("tableHeading"));
		
		playerHeading = new Label("Player Stats", skin);
		playerNameLabel = new Label("Name:", skin);
		playerNameText = new Label("", skin);	
		playerGoldLabel = new Label("Gold:", skin);
		playerGoldText = new Label("", skin);
		
		playerTable.add(playerHeading).expandX().center().colspan(2).padTop(-60).padLeft(-130).align(Align.topLeft);
		playerTable.row();
		
		playerTable.add(playerNameLabel).align(Align.topLeft).padLeft(-160);
		playerTable.add(playerNameText).padLeft(-160);
		playerTable.row();
		playerTable.add(playerGoldLabel).align(Align.topLeft).padLeft(-160).expand();
		playerTable.add(playerGoldText).padLeft(-160).align(Align.top);
		playerTable.row();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
//		table.add(image).colspan(3);
//		table.row();
		
		table.add(playerTable).expand().fillY().padTop(20*UIRenderer.scaleXY).align(Align.left);
		table.add(statsTable).padTop(20*UIRenderer.scaleXY);
		Table linkmonTable = new Table();
		linkmonTable.add(anim).colspan(2).pad(5);
		linkmonTable.row();
		linkmonTable.add(rankLabel).expandX().align(Align.right);
		linkmonTable.add(linkmonRank).expandX().align(Align.left);
		table.add(linkmonTable).expandY();
		table.row();
//		table.add(backButton).align(Align.right).colspan(3);
		
		addListeners();
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            }
			});
	}

	@Override
	public void show() {
		
		// TODO Auto-generated method stub
		uiGroup.addActor(container);
		uiGroup.addActor(table);
		uiGroup.addActor(image);
		uiGroup.addActor(backButton);
		uiGroup.toFront();
	}
	
	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		
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
		
		anim = new AnimationWidget(id, 2f/76f);
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
		
//		this.attack.setText(""+ attack+" / 999");
//		this.attackBar.update(attack);
//		this.defense.setText(""+ defense+" / 999");
//		this.defenseBar.update(defense);
//		this.health.setText(""+ health+" / 9999");
//		this.healthBar.update(health);
//		this.speed.setText(""+ speed+" / 999");
//		this.speedBar.update(speed);
//		this.careMistakes.setText(""+ careMistakes);
		
		String minutes;
		String hours;
		
		if(dob.getMinute() < 10)
			minutes = "0"+dob.getMinute();
		else
			minutes = ""+dob.getMinute();
		
		if(dob.getHour() == 0)
			hours = "12";
		else if(dob.getHour() < 10)
			hours = "0"+dob.getHour();
		else
			hours = ""+dob.getHour();
		
		this.dob.setText(dob.getDay()+"/"+dob.getMonth()+"/"+dob.getYear()+"  "+hours+":"+minutes);
	}
	
	@Override
	public void getPlayerStats(String name, int gold) {
		// TODO Auto-generated method stub
		playerNameText.setText(name);
		playerGoldText.setText(""+gold);
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
		table.remove();
		image.remove();
		backButton.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
