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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.linkmon.BirthDate;
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
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.PBar;

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
	private PBar healthBar;
	private Image attackLabel;
	private PBar attackBar;
	private Image defenseLabel;
	private PBar defenseBar;
	private Image speedLabel;
	private PBar speedBar;
	
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
	
	private Image image;
	
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
		
		image = new Image(skin2.getDrawable("statsButton"));
		
		//statsLabel = new Label("Stats", skin);
		backButton = new ImageButton(skin2.getDrawable("backButton"));
		
		table = new Table();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setPosition(0, 0);
		
		
		statsTable = new Table();
//		statsTable.setBackground(skin2.getDrawable("table"));
		statsTable.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.5f);
		statsTable.setPosition(Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/4);
		statsTable.setBackground(skin2.getDrawable("statsTable"));
		
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
		
		healthBar = new PBar(skin2, 0, 999);
		attackBar = new PBar(skin2, 0, 999);
		defenseBar = new PBar(skin2, 0, 999);
		speedBar = new PBar(skin2, 0, 999);
		
		playerTable = new Table();
		playerTable.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.5f);
		playerTable.setPosition(Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/4);
		playerTable.setBackground(skin2.getDrawable("statsTable"));
		
		playerHeading = new Label("Player Stats", skin);
		playerNameLabel = new Label("Name:", skin);
		playerNameText = new Label("", skin);	
		playerGoldLabel = new Label("Gold:", skin);
		playerGoldText = new Label("", skin);
		
		statsTable.add(linkmonLabel).expandX().center().colspan(2);
		statsTable.row();	
		statsTable.add(rankLabel).expandX().align(Align.left);
		statsTable.add(linkmonRank).expandX();
		statsTable.row();
		statsTable.add(healthLabel).expandX().align(Align.left);
		statsTable.row();
		statsTable.add(healthBar).size(300, 20).align(Align.left);
		statsTable.add(health);
		statsTable.row();
		statsTable.add(attackLabel).expandX().align(Align.left);
		statsTable.row();
		statsTable.add(attackBar).size(300, 20).align(Align.left);
		statsTable.add(attack);
		statsTable.row();
		statsTable.add(defenseLabel).expandX().align(Align.left);
		statsTable.row();
		statsTable.add(defenseBar).size(300, 20).align(Align.left);
		statsTable.add(defense);
		statsTable.row();
		statsTable.add(speedLabel).expandX().align(Align.left);
		statsTable.row();
		statsTable.add(speedBar).size(300, 20).align(Align.left);
		statsTable.add(speed);
		statsTable.row();
		statsTable.add(careMistakesLabel).expandX().align(Align.left);
		statsTable.add(careMistakes);
		statsTable.row();
		statsTable.add(dobLabel).expandX().align(Align.left);
		statsTable.add(dob);
		//statsTable.debug();
		
		statsTable.row();
		
		
		
		playerTable.add(playerHeading).expandX().center().colspan(2);
		playerTable.row();
		
		playerTable.add(playerNameLabel).align(Align.left);
		playerTable.add(playerNameText);
		playerTable.row();
		playerTable.add(playerGoldLabel).align(Align.left);
		playerTable.add(playerGoldText);
		playerTable.row();
		
		scrollPane = new ScrollPane(statsTable, skin);
		
		table.add(image).colspan(2);
		table.row();
		table.add(playerTable).expandX().fillX().pad(20*WorldRenderer.scaleXY).align(Align.left);
		table.row();
		table.add(statsTable).expand().fillX().pad(20*WorldRenderer.scaleXY).align(Align.bottomLeft);
		table.add(anim).expand().align(Align.bottom);
		table.row();
		table.add(backButton).expandX().align(Align.right).colspan(2);
		
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
		uiGroup.toFront();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
	}
	
	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		
//		StatWidget widget = new StatWidget(skin, skin2, 280, 150);
//		
//		widget.update(attack);
//		
//		container.add(widget).align(Align.center);
		
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
		
		this.attack.setText(""+ attack+" / 999");
		this.attackBar.update(attack);
		this.defense.setText(""+ defense+" / 999");
		this.defenseBar.update(defense);
		this.health.setText(""+ health+" / 9999");
		this.healthBar.update(health);
		this.speed.setText(""+ speed+" / 999");
		this.speedBar.update(999);
		this.careMistakes.setText(""+ careMistakes);
		
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
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
