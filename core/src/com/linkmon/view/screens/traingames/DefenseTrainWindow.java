package com.linkmon.view.screens.traingames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.model.linkmon.StatType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.ScreenType;

public class DefenseTrainWindow implements Screen {
	
	private Image background;
	private Table container;
	private Table actorTable;
	private Group uiGroup;
	private Button stopButton;
	
	private EventManager eManager;
	
	private int trainingBagHealth = 20;
	
	private ExpandingTextWidget text;
	
	private SwingTimer swing;
	
	private Timer timer;
	
	private Skin skin2;
	
	public DefenseTrainWindow(Group group, EventManager eManager) {
		this.eManager = eManager;
		uiGroup = group;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		container.setBackground(skin.getDrawable("default-rect"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		background = new Image(skin2.getDrawable("trainingBackground"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		actorTable = new Table();
		actorTable.setBackground(skin.getDrawable("default-rect"));
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		stopButton = new TextButton("Stop", buttonStyle);
		swing = new SwingTimer(800);
		
		actorTable.add(swing).size(800, 60).expand().align(Align.bottom);
		actorTable.row();
		actorTable.add(stopButton);
		
		container.add(actorTable).expand().fillX().align(Align.bottom);

		addListeners();
	}
	
	private void addListeners() {
		
		stopButton.addListener(new ClickListener(){
	            @Override 
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
	            	if(swing.getSineNum() > 350 && swing.getSineNum() < 450) {
	            		eManager.notify(new ScreenEvent(ScreenEvents.TRAIN_LINKMON, StatType.DEFENSE));
	            		text = new ExpandingTextWidget(skin2.getDrawable("winImage"));
	            	}
	            	else
	            		text = new ExpandingTextWidget(skin2.getDrawable("loseImage"));
	            		
	            	text.setPosition(Gdx.graphics.getWidth()/2-text.getWidth()/2, Gdx.graphics.getHeight()/2-text.getHeight()/2);
            		uiGroup.addActor(text);
            		timer = new Timer(7, false);
            		timer.start();
            		
	            	stopButton.setTouchable(Touchable.disabled);
	            	return true;
	            }
			});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(background);
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(timer != null && timer.checkTimer())
			eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
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
		text.remove();
		container.remove();
		background.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
