package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import com.linkmon.model.items.ItemType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class ItemWindow implements Screen, IPlayerItems {
	
	private Image backgroundImage;
	private Table rootTable;
	private Table table;
	private Table tableUse;
	private SelectionTable tableItems;
	private Group uiGroup;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button useButton;
	private ItemBox itemBox;
	private Label itemText;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	public ItemWindow(Group group, EventManager eManager) {
		
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
		
		// Create Window Elements
		
		backgroundImage = new Image(skin2.getDrawable("shopBackground"));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		rootTable = new Table();
		rootTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Table heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(250, 136);
		Label title = new Label("ITEMS", skin);
		title.setFontScale(1.1f);
		heading.add(title).padBottom(15);
		heading.setPosition((Gdx.graphics.getWidth()/2)-heading.getWidth()/2, Gdx.graphics.getHeight()-heading.getHeight());
		
		table = new Table();
		table.setBackground(skin2.getDrawable("newContainer"));
		
		tableUse = new Table();
		tableUse.setBackground(skin2.getDrawable("tableNoHeading"));
		
		tableItems = new SelectionTable();
		tableItems.setBackground(skin2.getDrawable("tableNoHeading"));
		
		backButton = new TextButton("Back", buttonStyle);
		
		useButton = new TextButton("Use", buttonStyle);
		
		itemBox = new ItemBox();
		itemText = new Label("Item Name",skin);
		
		
		// Build Window Layout
		
		table.add(heading).expandX().colspan(2).padTop(-100);
		table.row();
		
		tableUse.add(itemBox).expand();
		tableUse.row();
		tableUse.add(itemText).expand();
		tableUse.row();
		tableUse.add(useButton).expand().align(Align.bottom).pad(5*UIRenderer.scaleXY);
		
		table.add(tableUse).width(200f*UIRenderer.scaleXY).expandY().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		table.add(tableItems).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		tableItems.align(Align.top);
		table.row();
		table.add(backButton).expandX().colspan(2).padTop(20).padBottom(-55).padRight(-45).align(Align.bottomRight);
		
		
		rootTable.add(table).expand().fill();
		
		addListeners();
		
		// Get all Player items and add to tableItems
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.ALL, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN_PREVIOUS));
            }
		});
		
		useButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		useItem();
            	}
            }
		});
	}
	
	private void useItem() {
		// Use item
		eManager.notify(new ScreenEvent(ScreenEvents.USE_ITEM, ((SelectableItemButton)selectedItem).getItemId()));
		// Reset itemstable and usetable labels
		selectedItem = null;
		tableItems.reset(); // Overridden method
		itemBox.addItemImage(null);
		itemText.setText("");
		// Rebuild ItemsTable
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.ALL, this));
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
		if(tableItems.isUpdated()) {
			selectedItem = tableItems.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()).getTexture());
			itemText.setText(((SelectableItemButton)selectedItem).getItemName());
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
		rootTable.remove();
		backgroundImage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, String itemText) {
		// TODO Auto-generated method stub
		SelectableItemButton item = new SelectableItemButton(id, name, quantity, price, itemText, tableItems, uiGroup);
		tableItems.addItem(item);
	}

}
