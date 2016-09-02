package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.linkmon.BirthDate;
import com.linkmon.model.gameobject.linkmon.RankIds;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.StatWidget;

public class StatsWindow implements Screen, ILinkmonStats, IPlayerStats {
	
	private Table container;
	private Table table;
	private Group uiGroup;
	
	public Label health;
	public Label attack;
	public Label defense;
	public Label speed;
	
	private Button backButton;
	
	private Image linkmonRank;
	private Image healthLabel;
	private Image attackLabel;
	private Image defenseLabel;
	private Image speedLabel;
	
	private Label careMistakes;
	private Label dob;
	
	private Label careMistakesLabel;
	private Label dobLabel;
	
	private Image rankLabel;
	
	private Label playerNameLabel;

	private EventManager eManager;
	
	private Image darken;
	
	private ScreenController screenController;
	
	private ScrollPane scrollPane;
	
	Skin skin2;
	
	Skin skin;
	
	public StatsWindow(Group group, ScreenController screenController, EventManager eManager) {
		
		this.eManager = eManager;
		uiGroup = group;
		
		this.screenController = screenController;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.7f);
		
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            }
			});
	}
	
	@Override
	public void getLinkmonStats(int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		
		StatWidget widget = new StatWidget(skin, skin2, 280, 150);
		
		widget.update(attack);
		
		container.add(widget).align(Align.center);
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
		
		this.attack.setText(""+ attack);
		this.defense.setText(""+ defense);
		this.health.setText(""+ health);
		this.speed.setText(""+ speed);
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
		playerNameLabel.setText(name);
	}
	
	public void update() {
		screenController.updateWindow(this);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setPosition(0, 0);
		container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Image image = new Image(skin2.getDrawable("statsButton"));
		
		//statsLabel = new Label("Stats", skin);
		backButton = new ImageButton(skin2.getDrawable("backButton"));
		
		table = new Table(skin);
		
		table.setBackground(skin2.getDrawable("container"));
		Table heading = new Table();
		
		heading.add(image);
		table.add(heading).expandX();
		table.row();
		
		Table statsTable = new Table();
		statsTable.setBackground(skin2.getDrawable("table"));
		
		statsTable.add(new Label("Linkmon Stats", skin)).expandX().center().colspan(2);
		statsTable.row();
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		statsTable.add(rankLabel).expandX().align(Align.left);
		
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		statsTable.add(linkmonRank).expandX();
		statsTable.row();
		
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
		statsTable.add(healthLabel).expandX().align(Align.left);
		statsTable.add(health);
		statsTable.row();
		statsTable.add(attackLabel).expandX().align(Align.left);
		statsTable.add(attack);
		statsTable.row();
		statsTable.add(defenseLabel).expandX().align(Align.left);
		statsTable.add(defense);
		statsTable.row();
		statsTable.add(speedLabel).expandX().align(Align.left);
		statsTable.add(speed);
		statsTable.row();
		statsTable.add(careMistakesLabel).expandX().align(Align.left);
		statsTable.add(careMistakes);
		statsTable.row();
		statsTable.add(dobLabel).expandX().align(Align.left);
		statsTable.add(dob);
		//statsTable.debug();
		
		statsTable.row();
		
		
		playerNameLabel = new Label("", skin);
		
		statsTable.add(new Label("Player Stats", skin)).expandX().center().colspan(2);
		statsTable.row();
		
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.add(playerNameLabel);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		statsTable.row();
		statsTable.add(new Image(skin2.getDrawable("healthLabel"))).align(Align.left);
		
		scrollPane = new ScrollPane(statsTable, skin);
		
		table.add(scrollPane).expand().fill().pad(20*WorldRenderer.scaleXY);
		//table.debug();
		
		table.row();
		table.add(backButton).align(Align.right);
		
		container.add(table).size(470*WorldRenderer.scaleXY, Gdx.graphics.getHeight()/1.4f*WorldRenderer.scaleXY);
		container.row();
		
		addListeners();
		uiGroup.addActor(darken);
		uiGroup.addActor(container);
		uiGroup.toFront();
		
		update();
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
		darken.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}