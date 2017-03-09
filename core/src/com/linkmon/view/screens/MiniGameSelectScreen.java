package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.minigames.MiniGameIds;
import com.linkmon.view.screens.widgets.ScrollingBackground;

public class MiniGameSelectScreen implements Screen {
	
	private ScrollingBackground scrolling;
	private Group uiGroup;
	
	private Table container;
	private Table buttonsTable;
	
	private Button duckRacingButton;
	private Button ringGrabButton;
	
	private Button backButton;
	
	EventManager eManager;
	
	private Table infoTable;
	
	private TextButton play;
	
	private Image duckRacingImage;
	private Image ringGrabImage;
	
	private Label duckRacingHeading;
	private Label ringGrabHeading;
	private Label duckRacingInfo;
	private Label ringGrabInfo;
	
	private int selectedGameId;
	private int selectedGameUi;
	
	public MiniGameSelectScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		this.uiGroup = group;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		Texture texture = new Texture(Gdx.files.internal("UI/shopBackground.png"));
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrolling = new ScrollingBackground(texture);
		
		container = new Table();
		container.setSize(1280, 720);
		
		buttonsTable = new Table();
		buttonsTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		
		duckRacingButton = new ImageButton(skin2.getDrawable("duckRacingButton"));
		ringGrabButton = new ImageButton(skin2.getDrawable("ringGrabButton"));
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("blankButton");
		buttonStyle.down = skin2.getDrawable("blankButtonRed");
		buttonStyle.up = skin2.getDrawable("blankButtonGreen");
		buttonStyle.font = labelStyle.font;
		
		play = new TextButton("Play!", buttonStyle);
		
		infoTable = new Table();
		infoTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
//		infoTable.setVisible(false);
		
		duckRacingImage = new Image(skin2.getDrawable("duckRacingPreview"));
		ringGrabImage = new Image(skin2.getDrawable("ringGrabPreview"));
		
		LabelStyle style = new LabelStyle();
		style.font = ResourceLoader.getLutFont("large");
		
		LabelStyle smallStyle = new LabelStyle();
		smallStyle.font = ResourceLoader.getLutFont("medium");
		
		duckRacingHeading = new Label("Duck Racing", style);
		ringGrabHeading = new Label("Ring Grab", style);
		duckRacingInfo = new Label("Just keep tapping the screen to move the duck.\nCross the finish line first to win.", smallStyle);
		ringGrabInfo = new Label("Tap the side of the screen you wish to move to.\nOne tap per velocity change.\nAvoid fireballs, collect rings.", smallStyle);
		
		buttonsTable.add(duckRacingButton).expandX();
		buttonsTable.add(ringGrabButton).expandX();
		buttonsTable.row();
		buttonsTable.add(infoTable).colspan(2).expand().fill();
		
		container.add(buttonsTable).expand().fill();
		container.row();
		container.add(backButton).align(Align.bottomLeft);
		
		addListeners();
		
		buildInfoTable(ringGrabHeading, ringGrabInfo, ringGrabImage);
		selectedGameId = MiniGameIds.COIN_ROLL;
    	selectedGameUi = ScreenType.COIN_ROLL_UI;
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
            }
		});
		
		play.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.OPEN_MINIGAME, selectedGameId));
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, selectedGameUi));
            }
		});
		
		ringGrabButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	buildInfoTable(ringGrabHeading, ringGrabInfo, ringGrabImage);
            	selectedGameId = MiniGameIds.COIN_ROLL;
            	selectedGameUi = ScreenType.COIN_ROLL_UI;
//            	eManager.notify(new ScreenEvent(ScreenEvents.OPEN_MINIGAME, MiniGameIds.COIN_ROLL));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.COIN_ROLL_UI));
            }
		});
		
		duckRacingButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	buildInfoTable(duckRacingHeading, duckRacingInfo, duckRacingImage);
            	selectedGameId = MiniGameIds.DUCK_RACING;
            	selectedGameUi = ScreenType.DUCK_RACING_UI;
//            	eManager.notify(new ScreenEvent(ScreenEvents.OPEN_MINIGAME, MiniGameIds.DUCK_RACING));
//	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.DUCK_RACING_UI));
            }
		});
	}
	
	private void buildInfoTable(Label heading, Label info, Image image) {
		
		infoTable.clearChildren();
		
		Table textTable = new Table();
		textTable.add(heading).colspan(2).padBottom(30);
		textTable.row();
		textTable.add(info);
		
		infoTable.add(image);
		infoTable.add(textTable).expand().align(Align.top);
		infoTable.row();
		infoTable.add(play).expandX().colspan(2).align(Align.right);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(scrolling);
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
		scrolling.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
