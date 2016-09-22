package com.linkmon.view.screens.newgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;

public class NewGameScreen implements Screen {
	
	private Label label;
	private TextField textField;
	private Button okayButton;
	private Table container;
	private Group uiGroup;

	private Skin skin;
	private Skin skin2;
	
	private EventManager eManager;
	
	private GameClass game;
	
	public NewGameScreen(GameClass game, Group group, EventManager eManager) {
		this.game = game;
		this.eManager = eManager;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		this.eManager = eManager;
	}
	
	public void updateLabel(String text) {
		label.setText(text);
	}
	
	private void addListeners() {
		
		okayButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
            		//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
//	            	if(textField.getText() != "")
//	            		eManager.notify(new MessageEvent(MessageEvents.POOP_MISTAKE, "Incorrect name format!\na-Z 0-9 only.", false));
//	            	else
		            	game.setScreen(new EggChoiceScreen(textField.getText(), uiGroup, game, eManager));
		            	Gdx.input.setOnscreenKeyboardVisible(false);
		            	container.remove();
	            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin2.getDrawable("container"));
		
		textField = new TextField("", skin);
		
		label = new Label("Enter Your Name:", skin);
		
		okayButton = new ImageButton(skin2.getDrawable("okayButton"));

		container.add(label);
		container.row();
		container.add(textField);
		container.row();
		container.add(okayButton);
		uiGroup.addActor(container);
		
		addListeners();
		
		uiGroup.getStage().setKeyboardFocus(textField);
		//show the keyboard
		Gdx.input.setOnscreenKeyboardVisible(true);
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