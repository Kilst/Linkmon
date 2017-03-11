package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.CompleteMessage;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.items.ItemType;
import com.linkmon.view.screens.interfaces.IPlayerItems;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShop;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.LocalMessageBox;
import com.linkmon.view.screens.widgets.ScrollingBackground;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class Item implements Screen, IPlayerItems, IPlayerStats, ModelListener {
	
	private Table container;
	private SelectionTable tableItems;
	private Table buyTable;
	private Group uiGroup;
	private SelectableItemButton item;

	private ISelectable selectedItem;
	
	private Button backButton;
	private Button buyButton;
	private ItemBox itemBox;
	private Label itemText;
	private Label itemName;
	private String itemNameString = "Name: ";
	
	private int amount = 1;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	
	private Skin skin;
	
	private Table itemTable;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	private Image moneyTitle;
	private Label money;
	private int playerGold;
	
	ScrollPane scroll;
	
	private ScrollingBackground scrolling;
	
	private PlayerController playerController;
	
	public Item(Group group, EventManager eManager, PlayerController playerController) {
		this.playerController = playerController;
		uiGroup = group;
		this.eManager = eManager;
		eManager.addModelListener(this);
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		Texture texture = ResourceLoader.assetManager.get("feedBackground.png", Texture.class);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrolling = new ScrollingBackground(texture);
		
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		LabelStyle labelStyle2 = new LabelStyle();
		labelStyle2.font = ResourceLoader.getLutFont("smallLarge");
		
		LabelStyle labelStyle3 = new LabelStyle();
		labelStyle3.font = ResourceLoader.getLutFont("medium");
		
		
		// Create Window Elements
		
		container = new Table();
//		container.setBackground(new Image(ResourceLoader.assetManager.get("feedBackground.png", Texture.class)).getDrawable());
		container.setSize(1280, 720);
		
		itemTable = new Table();
		itemTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		//middleTable.setBackground(skin2.getDrawable("greenContainer"));
		middleTable.setSize(1280, 500);
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		Image heading = new Image(skin2.getDrawable("itemsTitle"));
		Table moneyTable = new Table();
		
		moneyTitle = new Image(skin2.getDrawable("money"));
		money = new Label("", labelStyle2);
		
		tableItems = new SelectionTable();
		tableItems.align(Align.topLeft);
		
		buyTable = new Table();
		buyTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		
		scroll = new ScrollPane(tableItems, scrollStyle);
		scroll.setScrollingDisabled(true, false);
		
		ImageButtonStyle backButtonStyle = new ImageButtonStyle();
		backButtonStyle.down = skin2.getDrawable("backButtonGreen");
		backButtonStyle.up = skin2.getDrawable("backButtonRed");
		
		backButton = new ImageButton(backButtonStyle);
		
		ImageButtonStyle useButtonStyle = new ImageButtonStyle();
		useButtonStyle.up = skin2.getDrawable("useButtonGreen");
		useButtonStyle.down = skin2.getDrawable("useButtonRed");
		
		buyButton = new ImageButton(useButtonStyle);
		
		itemBox = new ItemBox();
		
		itemText = new Label("Name",labelStyle);
		itemName = new Label("Info",labelStyle3);
		itemName.setWrap(true);
		itemName.setColor(0.8f, 0f, 0.8f, 1f);
		itemName.setAlignment(Align.center);
		
		
		// Build Window Layout
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
		moneyTable.add(moneyTitle).expand().align(Align.right);
		moneyTable.row();
		moneyTable.add(money);
		
		topTable.add(heading).expand().align(Align.left);
		topTable.add(moneyTable).expand().align(Align.right);
		
		buyTable.add(itemBox).align(Align.top).colspan(2).size(150, 150);
		buyTable.row();
		buyTable.add(itemText).colspan(2);
		buyTable.row();
		buyTable.add(itemName).expand().colspan(2).width(300);
		
		Image name = new Image(skin2.getDrawable("itemName"));
		Image own = new Image(skin2.getDrawable("itemOwn"));
		
		itemTable.add(name).expandX().size(name.getWidth(), name.getHeight());
		itemTable.add(own).align(Align.right).expandX().size(own.getWidth(), own.getHeight());
		itemTable.row();
		itemTable.add(scroll).colspan(2).expandY().align(Align.top);
		
		middleTable.add(itemTable).expandY().fillY().padRight(40);
		middleTable.add(buyTable).expand().fill();
		
//		bottomTable.add(playerGold).padLeft(15);
//		bottomTable.add(coinsImage).align(Align.left).padLeft(15);
		bottomTable.add(backButton).align(Align.left).expandX();
		bottomTable.add(buyButton).expand().align(Align.right);
		
		
		container.add(topTable).size(1280, 110);
		container.row();
		container.add(middleTable).size(1280, 500);
		container.row();
		container.add(bottomTable).size(1280, 110);
		
		
		addListeners();
		
		// Get Shop Items and add them to Item Table
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.ALL, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.PREVIOUS));
            }
		});
		
		buyButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(selectedItem != null) {
            		CompleteMessage message = playerController.useItem(((SelectableItemButton)selectedItem).getItemId());
            		if(!message.completed) {
            			if(((SelectableItemButton)selectedItem).getType() == ItemType.RECOVERY)
                			new LocalMessageBox(message.heading,((SelectableItemButton)selectedItem).getItemName() + " " + message.text, uiGroup);
            			else
            				new LocalMessageBox(message.heading, message.text, uiGroup);
            		}
            		else
        				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            		//eManager.notify(new ScreenEvent(ScreenEvents.USE_ITEM, ((SelectableItemButton)selectedItem).getItemId()));
//            		if(((SelectableItemButton)selectedItem).getType() == ItemType.RECOVERY)
//            			new LocalMessageBox("Can't Use", ((SelectableItemButton)selectedItem).getItemName() + " can only be used in battle", uiGroup);
//            		else {
//            			if(!playerController.useItem(((SelectableItemButton)selectedItem).getItemId())) // Need to either return a string for each error or send a message event from the model
//            				new LocalMessageBox("Can't Use", ((SelectableItemButton)selectedItem).getItemName() + "'s conditions for use not met.", uiGroup);
//            			else
//            				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
//            		}
            	}
            }
		});
	}
	
	private void updateBuyTable() {
		
		if(tableItems.isUpdated()) {
			selectedItem = tableItems.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()));
			itemText.setText(((SelectableItemButton)selectedItem).getItemName());
			itemName.setText(((SelectableItemButton)selectedItem).getItemText());
			tableItems.setUpdated(false);
		}
	}
	
	private void updateItemTable() {
		
		tableItems.reset();
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_ITEMS, ItemType.ALL, this));
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
		updateBuyTable();
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
		scrolling.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotify(ModelEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ModelEvents.UPDATE_GOLD): {
				money.setText("$"+event.value);
				playerGold = event.value;
				break;
			}
		}
	}

	@Override
	public void getPlayerStats(String name, int gold) {
		// TODO Auto-generated method stub
		money.setText("$"+gold);
		playerGold = gold;
	}

	@Override
	public void addPlayerItem(int id, String name, int quantity, int price, int type, String itemText) {
		// TODO Auto-generated method stub
		Gdx.app.log("ItemScreen Addplayeritem", "ItemTypeId: " + type + "    ItemName: " + name);
		item = new SelectableItemButton(id, name, quantity, price, type, itemText, tableItems, uiGroup, false);
		tableItems.addItem(item, 10);
		tableItems.invalidate();
		scroll.invalidate();
		tableItems.layout();
		scroll.layout();
	}
}
