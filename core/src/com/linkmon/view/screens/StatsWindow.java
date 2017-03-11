package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.model.linkmon.StatType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.MyProgressBar;
import com.linkmon.view.screens.widgets.StatWidget;

public class StatsWindow extends BaseMenuScreen implements Screen, ILinkmonStats, IPlayerStats {
	
	private Group uiGroup;
	
	public Label health;
	public Label attack;
	public Label defense;
	public Label speed;
	
	private ImageButton backButton;
	
	private Image linkmonRank;
	
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
	private Table linkmonTable;
	
	private Label playerHeading;
	
	private Label playerNameLabel;
	private Label playerNameText;
	
	Label linkmonDOBText;
	
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
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		LabelStyle labelStyleLut = new LabelStyle();
		labelStyleLut.font = ResourceLoader.getLutFont("medium");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		
		
		// Create Elements

		
		container = new Table();
		container.setBackground(new Image(ResourceLoader.assetManager.get("statsBackground.png", Texture.class)).getDrawable());
		container.setSize(1280, 720);
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		middleTable.setSize(1280, 500);
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		
		statsTable = new Table();
		statsTable.setSize(UIRenderer.WIDTH/2, UIRenderer.HEIGHT/1.5f);
		statsTable.setPosition(UIRenderer.WIDTH/12, UIRenderer.HEIGHT/4);
		
		linkmonLabel = new Label("Linkmon Stats", skin);
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		
		Table infoTable = new Table();
		
		playerTable = new Table();
		playerTable.setBackground(new Image(ResourceLoader.assetManager.get("bigContainerBlue.png", Texture.class)).getDrawable());
		
		Table playerTitle = new Table();
		playerTitle.setBackground(skin2.getDrawable("title"));
		playerTitle.pack(); // Calculate current size early
		Label headingPlayer = new Label("Player", labelStyle);
		playerTitle.add(headingPlayer).align(Align.center).padBottom(playerTitle.getHeight()/6);
		
//		playerHeading = new Label("Player Stats", skin);
		playerNameLabel = new Label("Name:", labelStyleLut);
		playerNameText = new Label("", skin);	
		playerGoldLabel = new Label("Gold:", labelStyleLut);
		playerGoldText = new Label("", skin);
		
		Table linkmonInfoTable = new Table();
		linkmonInfoTable.setBackground(new Image(ResourceLoader.assetManager.get("bigContainerBlue.png", Texture.class)).getDrawable());
		
		Table linkmonInfoTitle = new Table();
		linkmonInfoTitle.setBackground(skin2.getDrawable("title"));
		linkmonInfoTitle.pack(); // Calculate current size early
		Label headingLinkmonInfo = new Label("Linkmon", labelStyle);
		linkmonInfoTitle.add(headingLinkmonInfo).align(Align.center).padBottom(linkmonInfoTitle.getHeight()/6);
		
		
		MyProgressBar pBarHunger = new MyProgressBar(skin2, 25, 100);
		pBarHunger.update(25);
		MyProgressBar pBarHappy = new MyProgressBar(skin2, 75, 100);
		pBarHappy.update(75);
//		Label linkmonHeading = new Label("Player Stats", skin);
		Label linkmonDOBLabel = new Label("DOB :", labelStyleLut);
		linkmonDOBText = new Label("", labelStyleLut);	
		Image linkmonHungerImage = new Image(skin2.getDrawable("meat"));
//		linkmonHungerImage.setScale(0.7f);
		Label linkmonHungerLabel = new Label(" :", labelStyleLut);
		Image linkmonHappyImage = new Image(skin2.getDrawable("happy"));
//		linkmonHappyImage.setScale(0.9f);
		Label linkmonHappyLabel = new Label(" :", labelStyleLut);
		
		linkmonInfoTable.add(linkmonInfoTitle).expandX().center().colspan(3).padTop(linkmonInfoTitle.getHeight()/2*-1);
		linkmonInfoTable.row();
//		playerTable.add(playerHeading).expandX().center().colspan(2).padTop(-60).padLeft(-130).align(Align.topLeft);
//		playerTable.row();
		
		linkmonInfoTable.add(linkmonDOBLabel).align(Align.left).padLeft(10);
		linkmonInfoTable.add(linkmonDOBText).align(Align.center).expandX().colspan(2);
		linkmonInfoTable.row();
		linkmonInfoTable.add(linkmonHungerImage).align(Align.left).padLeft(10).size(40);
		linkmonInfoTable.add(linkmonHungerLabel).align(Align.left).expandX();
		linkmonInfoTable.add(pBarHunger).align(Align.center).expandX().width(200);
		linkmonInfoTable.row();
		linkmonInfoTable.add(linkmonHappyImage).align(Align.topLeft).padLeft(10).size(40);
		linkmonInfoTable.add(linkmonHappyLabel).align(Align.topLeft).expand();
		linkmonInfoTable.add(pBarHappy).align(Align.top).expand().width(200);
//		linkmonInfoTable.debug();
		
		Table linkmonStatsTable = new Table();
		linkmonStatsTable.setBackground(new Image(ResourceLoader.assetManager.get("bigContainerBlue.png", Texture.class)).getDrawable());
		
		linkmonTable = new Table();
		
		Image heading = new Image(skin2.getDrawable("statsTitle"));
		
		// Build Layout
		
		topTable.add(heading).expand().align(Align.left);
		
		
		playerTable.add(playerTitle).expandX().center().colspan(2).padTop(playerTitle.getHeight()/2*-1);
		playerTable.row();
//		playerTable.add(playerHeading).expandX().center().colspan(2).padTop(-60).padLeft(-130).align(Align.topLeft);
//		playerTable.row();
		
		playerTable.add(playerNameLabel).align(Align.left);
		playerTable.add(playerNameText).align(Align.center);
		playerTable.row();
		playerTable.add(playerGoldLabel).align(Align.topLeft).expand();
		playerTable.add(playerGoldText).align(Align.top);
		playerTable.row();
		
		middleTable.row();
		infoTable.add(playerTable).expand().pad(10);
		infoTable.row();
		infoTable.add(linkmonInfoTable).expand().pad(10);
		middleTable.add(infoTable).expand().align(Align.left).pad(20);
		
		linkmonStatsTable.add(statsTable).align(Align.left).expand();
		
		linkmonStatsTable.add(linkmonTable).expand();
		linkmonStatsTable.row();
		
		middleTable.add(linkmonStatsTable).expandY().pad(20);
		
		bottomTable.add(backButton).align(Align.left).expandX();
		
		container.add(topTable).size(1280, 110);
		container.row();
		container.add(middleTable).size(1280, 500);
		container.row();
		container.add(bottomTable).size(1280, 110);
		
		addListeners();
		
		
		// Update LinkmonTable
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		// Update PlayerTable
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
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
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		
		linkmonTable.clear();
		statsTable.clear();
		
		StatWidget widget = new StatWidget(skin, skin2, health, 999, StatType.HEALTH);
		statsTable.add(widget);
		statsTable.row();
		
		StatWidget widget2 = new StatWidget(skin, skin2, attack, 99,  StatType.ATTACK);
		statsTable.add(widget2);
		statsTable.row();
		
		StatWidget widget3 = new StatWidget(skin, skin2, defense, 99, StatType.DEFENSE);
		statsTable.add(widget3);
		statsTable.row();
		
		StatWidget widget4 = new StatWidget(skin, skin2, speed, 99, StatType.SPEED);
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
		
		linkmonDOBText.setText(dob.getDay()+"/"+dob.getMonth()+"/"+dob.getYear()+"  "+hours+":"+minutes);
		
		
		linkmonTable.add(anim).colspan(2).pad(5);
		linkmonTable.row();
		linkmonTable.add(rankLabel).expandX().align(Align.right);
		linkmonTable.add(linkmonRank).expandX().align(Align.left);
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
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
