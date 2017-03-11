package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.HelpFlags;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.model.linkmon.RankIds;
import com.linkmon.model.linkmon.StatType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.StatWidget;
import com.linkmon.view.screens.widgets.messages.LocalChatMessage;

public class TrainWindow  extends BaseMenuScreen implements Screen, ILinkmonStats {
	
	private Group uiGroup;
	
	public Label health;
	public Label attack;
	public Label defense;
	public Label speed;
	
	private int addedHealth;
	private int addedAttack;
	private int addedDefense;
	private int addedSpeed;
	
	private ImageButton backButton;
	private ImageButton applyButton;
	
	private Image linkmonRank;
	
	private Image rankLabel;

	private EventManager eManager;
	
	private AnimationWidget anim;
	
	Skin skin2;
	
	Skin skin;
	
	private Table statsTable;
	
	private Table linkmonTable;
	
	private Table pointsLeftTable;
	
	private StatWidget widgetHealth;
	private ImageButton addButtonHealth;
	private StatWidget widgetAttack;
	private ImageButton addButtonAttack;
	private StatWidget widgetDefense;
	private ImageButton addButtonDefense;
	private StatWidget widgetSpeed;
	private ImageButton addButtonSpeed;
	
	private ImageButton minusButtonHealth;
	private ImageButton minusButtonAttack;
	private ImageButton minusButtonDefense;
	private ImageButton minusButtonSpeed;
	
	Table tableLeft;
	Table tableRight;
	
	private int statPoints = 0;
	private Label statPointsLabel;
	
	private PlayerController playerController;
	
	public TrainWindow(Group group, EventManager eManager, PlayerController playerController) {
		
		this.eManager = eManager;
		uiGroup = group;
		this.playerController = playerController;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("medium");
		
		LabelStyle labelStyle1 = new LabelStyle();
		labelStyle1.font = ResourceLoader.getSampleFont("large");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		addedHealth = 0;
		addedAttack = 0;
		addedDefense = 0;
		addedSpeed = 0;
		
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
		
		ImageButtonStyle applyButtonStyle = new ImageButtonStyle();
		applyButtonStyle.up = skin2.getDrawable("applyButtonGreen");
		applyButtonStyle.down = skin2.getDrawable("applyButtonRed");
		
		applyButton = new ImageButton(applyButtonStyle);
		applyButton.setVisible(false);
		
		statsTable = new Table();
		statsTable.setSize(UIRenderer.WIDTH/2, UIRenderer.HEIGHT/1.5f);
		statsTable.setPosition(UIRenderer.WIDTH/12, UIRenderer.HEIGHT/4);
		
		rankLabel = new Image(skin2.getDrawable("rankLabel"));
		
		linkmonRank = new Image(skin2.getDrawable("rank-s"));
		
		Table linkmonStatsTable = new Table();
		linkmonStatsTable.setBackground(new Image(ResourceLoader.assetManager.get("bigContainerBlue.png", Texture.class)).getDrawable());
		
		linkmonTable = new Table();
		
		statPoints = playerController.getTrainingPoints();
		
		Label statsLeft = new Label("Points Left:", labelStyle);
		statPointsLabel = new Label(""+statPoints, labelStyle1);
		
		pointsLeftTable = new Table();
		pointsLeftTable.setBackground(new Image(ResourceLoader.assetManager.get("bigContainerBlue.png", Texture.class)).getDrawable());
		pointsLeftTable.add(statsLeft);
		pointsLeftTable.row();
		pointsLeftTable.add(statPointsLabel);
		
		Image heading = new Image(skin2.getDrawable("trainTitle"));
		
		addButtonHealth = new ImageButton(skin2.getDrawable("plusButton"));
		addButtonAttack = new ImageButton(skin2.getDrawable("plusButton"));
		addButtonDefense = new ImageButton(skin2.getDrawable("plusButton"));
		addButtonSpeed = new ImageButton(skin2.getDrawable("plusButton"));
		
		statPointCheck();
		
		minusButtonHealth = new ImageButton(skin2.getDrawable("minusButton"));
		minusButtonHealth.setVisible(false);
		minusButtonAttack = new ImageButton(skin2.getDrawable("minusButton"));
		minusButtonAttack.setVisible(false);
		minusButtonDefense = new ImageButton(skin2.getDrawable("minusButton"));
		minusButtonDefense.setVisible(false);
		minusButtonSpeed = new ImageButton(skin2.getDrawable("minusButton"));
		minusButtonSpeed.setVisible(false);
		
		// Build Layout
		
		topTable.add(heading).expand().align(Align.left);
		
		linkmonStatsTable.add(statsTable).align(Align.left);
		
		linkmonStatsTable.add(linkmonTable).expand();
		linkmonStatsTable.row();
		
		middleTable.add(linkmonStatsTable).expand().pad(20);
		
		middleTable.add(pointsLeftTable).expandY().pad(20);
		
		
		
		bottomTable.add(backButton).align(Align.left).expandX();
		bottomTable.add(applyButton).align(Align.right).expandX();
		
		container.add(topTable).size(1280, 110);
		container.row();
		container.add(middleTable).size(1280, 500);
		container.row();
		container.add(bottomTable).size(1280, 110);
		
		
		// Update LinkmonTable
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		
		addListeners();
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
	            }
			});
		
		applyButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
//            	playerController.addLinkmonStatPoints(statPoints-(statPoints-addedHealth+addedAttack+addedDefense+addedSpeed));
            	playerController.removeTrainingPoints(addedHealth+addedAttack+addedDefense+addedSpeed);
            	playerController.updateLinkmonStats(addedHealth*10, addedAttack, addedDefense, addedSpeed);
            	statPoints = playerController.getTrainingPoints();
            	addedHealth = 0;
            	addedAttack = 0;
            	addedDefense = 0;
            	addedSpeed = 0;
            	minusButtonHealth.setVisible(false);
            	minusButtonAttack.setVisible(false);
            	minusButtonDefense.setVisible(false);
            	minusButtonSpeed.setVisible(false);
            	statPointCheck();
            	new LocalMessageBox("Stats Updated", "Applied the stat changes!", uiGroup);
            }
		});
		
		addButtonHealth.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	minusButtonHealth.setVisible(true);
            	addedHealth+=1;
            	statPointCheck();
            	widgetHealth.setStat(addedHealth*10);
            }
		});
		
		addButtonAttack.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	minusButtonAttack.setVisible(true);
            	addedAttack+=1;
            	statPointCheck();
            	widgetAttack.setStat(addedAttack);
            }
		});
		
		addButtonDefense.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	minusButtonDefense.setVisible(true);
            	addedDefense+=1;
            	statPointCheck();
            	widgetDefense.setStat(addedDefense);
            }
		});
		
		addButtonSpeed.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	minusButtonSpeed.setVisible(true);
            	addedSpeed+=1;
            	statPointCheck();
            	widgetSpeed.setStat(addedSpeed);
            }
		});
		
		minusButtonHealth.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	addedHealth-=1;
            	if(addedHealth == 0)
            		minusButtonHealth.setVisible(false);
            	statPointCheck();
            	widgetHealth.setStat(addedHealth*10);
            }
		});
		
		minusButtonAttack.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	addedAttack-=1;
            	if(addedAttack == 0)
            		minusButtonAttack.setVisible(false);
            	statPointCheck();
            	widgetAttack.setStat(addedAttack);
            }
		});
		
		minusButtonDefense.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	addedDefense-=1;
            	if(addedDefense == 0)
            		minusButtonDefense.setVisible(false);
            	statPointCheck();
            	widgetDefense.setStat(addedDefense);
            }
		});
		
		minusButtonSpeed.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	addedSpeed-=1;
            	if(addedSpeed == 0)
            		minusButtonSpeed.setVisible(false);
            	statPointCheck();
            	widgetSpeed.setStat(addedSpeed);
            }
		});
	}
	
	private void statPointCheck() {
		int total = addedHealth+addedAttack+addedDefense+addedSpeed;
		if(statPoints-total == 0) {
			addButtonHealth.setVisible(false);
			addButtonAttack.setVisible(false);
			addButtonDefense.setVisible(false);
			addButtonSpeed.setVisible(false);
		}
		else {
			addButtonHealth.setVisible(true);
			addButtonAttack.setVisible(true);
			addButtonDefense.setVisible(true);
			addButtonSpeed.setVisible(true);
		}
		
		if(total > 0)
			applyButton.setVisible(true);
		else
			applyButton.setVisible(false);
		
		statPointsLabel.setText("" + (statPoints-total));
			
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(container);
		
		if(playerController.checkHelpFlag(HelpFlags.TRAIN_HELP)) {
			playerController.updateHelpFlag(HelpFlags.TRAIN_HELP);
			String[] strings = new String[3];
			strings[0] = "Welcome to Training!";
			strings[1] = "Here you can train your Linkmon for battles.";
    		strings[2] = "You gain stat points every day, based on how well you've been treating your Linkmon.";
    		new LocalChatMessage(1, 0, strings, uiGroup, eManager).show();
		}
	}
	
	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		
		linkmonTable.clear();
		statsTable.clear();
		
		
		
		widgetHealth = new StatWidget(skin, skin2, health, 999, StatType.HEALTH);
		statsTable.add(widgetHealth);
		statsTable.add(minusButtonHealth).size(80);
		statsTable.add(addButtonHealth).size(80);
		statsTable.row();
		
		widgetAttack = new StatWidget(skin, skin2, attack, 99,  StatType.ATTACK);
		statsTable.add(widgetAttack);
		statsTable.add(minusButtonAttack).size(80);
		statsTable.add(addButtonAttack).size(80);
		statsTable.row();
		
		widgetDefense = new StatWidget(skin, skin2, defense, 99, StatType.DEFENSE);
		statsTable.add(widgetDefense);
		statsTable.add(minusButtonDefense).size(80);
		statsTable.add(addButtonDefense).size(80);
		statsTable.row();
		
		widgetSpeed = new StatWidget(skin, skin2, speed, 99, StatType.SPEED);
		statsTable.add(widgetSpeed);
		statsTable.add(minusButtonSpeed).size(80);
		statsTable.add(addButtonSpeed).size(80);
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
		
		
		linkmonTable.add(anim).colspan(2).pad(5);
		linkmonTable.row();
		linkmonTable.add(rankLabel).expandX().align(Align.right);
		linkmonTable.add(linkmonRank).expandX().align(Align.left);
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
