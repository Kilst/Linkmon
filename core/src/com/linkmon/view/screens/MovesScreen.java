package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectableMoveButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class MovesScreen implements Screen, IMovesScreen {

	private Image backgroundImage;
	private Table rootTable;
	private Table table;
	private SelectionTable tableCurrentMoves;
	private SelectionTable tableSelectableMoves;
	private ScrollPane scrollPane;
	private Group uiGroup;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button swapButton;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	MovesScreen screen = this;
	
	public MovesScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		
		uiGroup = group;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.background = skin2.getDrawable("tableNoHeading");
		
		// Create Window Elements
		
		backgroundImage = new Image(skin2.getDrawable("statsBackground"));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		rootTable = new Table();
		rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Table heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(250, 136);
		Label title = new Label("MOVES", skin);
		title.setFontScale(1.1f);
		heading.add(title).padBottom(15);
		heading.setPosition((Gdx.graphics.getWidth()/2)-heading.getWidth()/2, Gdx.graphics.getHeight()-heading.getHeight());
		
		table = new Table();
		table.setBackground(skin2.getDrawable("newContainer"));
		
		tableCurrentMoves = new SelectionTable();
		tableCurrentMoves.setBackground(skin2.getDrawable("tableNoHeading"));
		
		tableSelectableMoves = new SelectionTable();
		
		backButton = new TextButton("Back", buttonStyle);
		
		swapButton = new TextButton("Swap", buttonStyle);
		
		
		Table headingTable = new Table();
		scrollPane = new ScrollPane(tableSelectableMoves, scrollStyle);
		headingTable.add(new Image(skin2.getDrawable("tableNoHeading"))).width(200);
		headingTable.row();
		headingTable.add(scrollPane).expand().fill();
		
		// Build Window Layout
		
		table.add(heading).expandX().colspan(2).padTop(-100);
		table.row();
		
		
		table.add(tableCurrentMoves).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		table.add(headingTable).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		tableSelectableMoves.align(Align.top);
		table.row();
		table.add(swapButton).expandX().colspan(2).padTop(20);
		table.row();
		table.add(backButton).expandX().colspan(2).padTop(20).padBottom(-55).padRight(-45).align(Align.bottomRight);
		
		
		rootTable.add(table).expand().fill();
		
		addListeners();
		
		// Get all Player items and add to tableItems
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            }
		});
		
		swapButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(((SelectableMoveButton)tableCurrentMoves.getSelectedItem()) != null && 
            			((SelectableMoveButton)tableSelectableMoves.getSelectedItem()) != null) {
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_MOVE, ((SelectableMoveButton)tableCurrentMoves.getSelectedItem()).getMoveId(), ((SelectableMoveButton)tableSelectableMoves.getSelectedItem()).getMoveId()));
	            	tableCurrentMoves.reset();
	            	tableSelectableMoves.reset();
	            	eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, screen));
            	}
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(backgroundImage);
		uiGroup.addActor(rootTable);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(tableCurrentMoves.isUpdated()) {
			tableSelectableMoves.reset();
			eManager.notify(new ScreenEvent(ScreenEvents.GET_CHOOSABLE_MOVES, ((SelectableMoveButton)tableCurrentMoves.getSelectedItem()).getSlot(), this));
			tableCurrentMoves.setUpdated(false);
		}
		
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
		backgroundImage.remove();
		rootTable.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy) {
		// TODO Auto-generated method stub
		SelectableMoveButton item = new SelectableMoveButton(id, name, type, slot, damage, ignoreDamage, energy, tableCurrentMoves);
		tableCurrentMoves.addItem(item);
	}

	@Override
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy) {
		// TODO Auto-generated method stub
		SelectableMoveButton item = new SelectableMoveButton(id, name, type, slot, damage, ignoreDamage, energy, tableSelectableMoves);
		tableSelectableMoves.addItem(item);
	}

}