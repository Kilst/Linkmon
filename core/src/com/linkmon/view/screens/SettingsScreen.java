package com.linkmon.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.SettingsController;
import com.linkmon.controller.SoundController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;

public class SettingsScreen implements Screen {
	
	private Table rootTable;
	private Image darken;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	
	private ImageButton backButton;
	private ImageButton swapButton;
	
	private Group ui;
	
	private EventManager eManager;
	
	private Table leftMiddleTable;
	private Table rightMiddleTable;
	
	private ScrollPane scrollPane;
	private Table scrollTable;
	
	private Slider soundSlider;
	private Slider musicSlider;
	
	Skin skin2;
	
	private SettingsController settingsController;
	
	public SettingsScreen(Group ui, EventManager eManager, SettingsController settingsController) {
		
		this.eManager = eManager;
		this.ui = ui;
		this.settingsController = settingsController;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		rootTable = new Table();
		rootTable.setSize(1280, 720);
		rootTable.setBackground(new Image(ResourceLoader.assetManager.get("statsBackground.png", Texture.class)).getDrawable());
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		middleTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		middleTable.setSize(1280, 500);
		
		leftMiddleTable = new Table();
		leftMiddleTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		
		rightMiddleTable = new Table();
		rightMiddleTable.setBackground(skin2.getDrawable("menuContainerInnerPatch"));
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		swapButton = new ImageButton(backButtonStyle);
		swapButton.setVisible(false);
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(1280, 720);
		darken.getColor().a = 0.7f;
		
		scrollTable = new Table();
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollPane = new ScrollPane(scrollTable, scrollStyle);
		scrollPane.setScrollingDisabled(true, false);
		
		Label volumeLabel = new Label("Sound Volume: ", labelStyle);
		Label musicVolumeLabel = new Label("Music Volume: ", labelStyle);
		
		SliderStyle sliderStyle = new SliderStyle();
		sliderStyle.knob = skin2.getDrawable("sliderKnob");
		sliderStyle.background = skin2.getDrawable("sliderBackground");
		
		soundSlider = new Slider(0, 100, 1, false, sliderStyle);
		soundSlider.setValue(settingsController.getSoundVolume()*100);
		InputListener stopTouchDown = new InputListener() {
			   public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			      event.stop();
			      return false;
			   }
			};
		soundSlider.addListener(stopTouchDown);
		
		musicSlider = new Slider(0, 100, 1, false, sliderStyle);
		musicSlider.setValue(settingsController.getMusicVolume()*100);
		musicSlider.addListener(stopTouchDown);
			
		scrollTable.add(volumeLabel);
		scrollTable.add(soundSlider).minWidth(400);
		scrollTable.row();
		scrollTable.add(musicVolumeLabel).padTop(50);
		scrollTable.add(musicSlider).minWidth(400).padTop(50);
		
		rightMiddleTable.add(scrollPane);
		middleTable.add(scrollPane).expand();
//		middleTable.add(leftMiddleTable).expandY().fillY().pad(20);
//		middleTable.add(rightMiddleTable).expand().fill().pad(20).padTop(-5).padBottom(-5);
		
		
		
		bottomTable.add(backButton).width(backButton.getWidth()).height(backButton.getHeight()).expandX().align(Align.left);
//		bottomTable.add(swapButton).width(swapButton.getWidth()).height(swapButton.getHeight()).expandX().align(Align.right);
		
		rootTable.add(topTable).size(1280, 110);
		rootTable.row();
		rootTable.add(middleTable).size(1280, 500);
		rootTable.row();
		rootTable.add(bottomTable).size(1280, 110);
		
		
		addListeners();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		ui.addActor(darken);
		ui.addActor(rootTable);
	}

	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
	        @Override 
	        public void clicked(InputEvent event, float x, float y){
	        	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
	        }
		});
		
		soundSlider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				settingsController.setSoundVolume(soundSlider.getValue()/100);
			}
		});
		musicSlider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				settingsController.setMusicVolume(musicSlider.getValue()/100);
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
		rootTable.remove();
		darken.remove();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
