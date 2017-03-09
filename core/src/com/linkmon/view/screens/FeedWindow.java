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

public class FeedWindow implements Screen, IPlayerItems {
	
	private Image backgroundImage;
	private Table container;
	private Table table;
	private SelectionTable tableFeed;
	private Table tableItems;
	private Group uiGroup;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button feedButton;
	private ItemBox itemBox;
	private Label itemText;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Skin skin;
	
	public FeedWindow(Group group, EventManager eManager) {
		
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
		
		backgroundImage = new Image(skin2.getDrawable("feedBackground"));
		backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		container = new Table();
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin2.getDrawable("newContainer"));
		
		Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("feedButton.png"))));
		
		table = new Table();
		
		tableFeed = new SelectionTable();
		tableFeed.setBackground(skin2.getDrawable("tableNoHeading"));
		
		tableItems = new Table(skin);
		tableItems.setBackground(skin2.getDrawable("tableNoHeading"));
		
		
		backButton = new TextButton("Back", buttonStyle);
		
		feedButton = new TextButton("Feed", buttonStyle);
		
		itemBox = new ItemBox();
		itemText = new Label("Item Name",skin);
		
		
		// Build Window Layout
		
		tableItems.add(itemBox).expand().size(150*UIRenderer.scaleX, 150*UIRenderer.scaleX);
		tableItems.row();
		tableItems.add(itemText).expand();
		tableItems.row();
		tableItems.add(feedButton).expand().align(Align.bottom).pad(5*UIRenderer.scaleXY);
		
		table.add(tableItems).expandY().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		table.add(tableFeed).expand().fill().padLeft(20*UIRenderer.scaleXY).padRight(20*UIRenderer.scaleXY);
		tableFeed.align(Align.top);
		
		container.add(image);
		container.row();
		container.add(table).expand().fill();
		container.row();
		container.add(backButton).align(Align.right).padRight(-80f*UIRenderer.scaleX).padBottom(-90f*UIRenderer.scaleY);
		
		addListeners();
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.FOOD, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
            }
		});
		
		feedButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		eManager.notify(new ScreenEvent(ScreenEvents.USE_ITEM, ((SelectableItemButton)selectedItem).getItemId()));
            		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
            	}
            }
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(backgroundImage);
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(tableFeed.isUpdated()) {
			selectedItem = tableFeed.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()));
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
		container.remove();
		backgroundImage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, int type, String itemText) {
		// TODO Auto-generated method stub
		SelectableItemButton item = new SelectableItemButton(id, name, quantity, price, type, itemText, tableFeed, uiGroup);
		tableFeed.add(item).expandX().fillX();
	}

}
