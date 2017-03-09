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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.model.items.ItemType;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.IShop;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.ScrollingBackground;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class Shop implements Screen, IShop, IPlayerStats, ModelListener {
	
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
	private Label itemPrice;
	private String itemPriceString = "Price: ";
	private Label itemAmount;
	private String itemAmountString = "Amount: ";
	
	private int amount = 1;
	
	private EventManager eManager;
	
	private Skin skin2;
	
	private Button addButton;
	private boolean addPress;
	private Button subtractButton;
	private boolean subtractPress;
	
	private long touchTime;
	
	private Skin skin;
	
	private Table itemTable;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	private Image moneyTitle;
	private Label money;
	private int playerGold;
	
	ScrollPane scroll;
	
	ScrollingBackground scrolling;
	Texture texture;
	
	private Image darken;
	
	public Shop(Group group, EventManager eManager) {
		
		uiGroup = group;
		this.eManager = eManager;
		eManager.addModelListener(this);
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
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
		
		
		// Create Window Elements
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(1280, 720);
		darken.getColor().a = 0.7f;
		
		texture = new Texture(Gdx.files.internal("UI/shopBackground.png"));
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		scrolling = new ScrollingBackground(texture);
		
		container = new Table();
		//container.setBackground(skin2.getDrawable("shopBackground"));
		container.setSize(1280, 720);
		
		itemTable = new Table();
		itemTable.setBackground(skin2.getDrawable("menuContainerPatch"));
		itemTable.setHeight(500);
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		//middleTable.setBackground(skin2.getDrawable("greenContainer"));
		middleTable.setSize(1280, 500);
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		Image heading = new Image(skin2.getDrawable("menuHeading"));
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
		
		buyButton = new ImageButton(skin2.getDrawable("buyButton"));
		
		addButton = new ImageButton(skin2.getDrawable("plusButton"));
		subtractButton = new ImageButton(skin2.getDrawable("minusButton"));
		
		itemBox = new ItemBox();
		
		itemText = new Label("Name",labelStyle);
		itemAmount = new Label("Amount: ",labelStyle);	
		itemPrice  = new Label("Price: ",labelStyle);
		
		
		// Build Window Layout
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_PLAYER_STATS, this));
		
		moneyTable.add(moneyTitle).expand().align(Align.right);
		moneyTable.row();
		moneyTable.add(money);
		
		topTable.add(heading).expand().align(Align.left);
		topTable.add(moneyTable).expand().align(Align.right);
		
		buyTable.add(itemBox).expand().align(Align.top).colspan(2).size(150, 150);
		buyTable.row();
		buyTable.add(itemText).expand().colspan(2);
		buyTable.row();
		
		buyTable.row();
		buyTable.add(itemAmount).expand().colspan(2);
		buyTable.row();
		buyTable.add(itemPrice).expand().colspan(2);
		
		Image name = new Image(skin2.getDrawable("itemName"));
		Image own = new Image(skin2.getDrawable("itemOwn"));
		
		itemTable.add(name).expandX().size(name.getWidth(), name.getHeight());
		itemTable.add(own).align(Align.right).expandX().size(own.getWidth(), own.getHeight());
		itemTable.row();
		itemTable.add(scroll).colspan(2);
		
		middleTable.add(buyTable).expand().fill().padRight(40);
		middleTable.add(itemTable).fillY();
		
//		bottomTable.add(playerGold).padLeft(15);
//		bottomTable.add(coinsImage).align(Align.left).padLeft(15);
		bottomTable.add(backButton).align(Align.left).expandX();
		bottomTable.add(subtractButton).expandX().padRight(5).size(100);
		bottomTable.add(addButton).expandX().padLeft(5).size(100);
		bottomTable.add(buyButton).expand().align(Align.right);
		
		
		container.add(topTable).size(1280, 110);
		container.row();
		container.add(middleTable).size(1280, 500);
		container.row();
		container.add(bottomTable).size(1280, 110);
		
		
		addListeners();
		
		// Get Shop Items and add them to Item Table
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, ItemType.ALL, this));
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
            		eManager.notify(new ScreenEvent(ScreenEvents.BUY_ITEM, ((SelectableItemButton)selectedItem).getItemId(), amount));
            		amount = 1;
            		itemAmount.setText(itemAmountString + amount);
            		itemPrice.setText(itemPriceString + ((SelectableItemButton)selectedItem).getPrice());
            		updateItemTable();
            	}
            }
		});
		
		addButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
            
            @Override 
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            	if(((SelectableItemButton)selectedItem)!= null) {
	            	addPress = true;
	            	touchTime = System.nanoTime();
	            	amount+=1;
	            	updatePriceAmount();
            	}
				return true;
            }
            @Override 
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            	addPress = false;
            }
		});
		
		subtractButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
            
            @Override 
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            	if(((SelectableItemButton)selectedItem)!= null) {
	            	subtractPress = true;
	            	amount-=1;
	            	updatePriceAmount();
	            	touchTime = System.nanoTime();
            	}
				return true;
            }
            @Override 
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            	subtractPress = false;
            }
		});
	}
	
	private void updatePriceAmount() {
		
		itemPrice.setColor(1, 1, 1, 1);
		
		if (amount*((SelectableItemButton)selectedItem).getPrice() > playerGold) {
			amount -= 1;
			itemPrice.setColor(1, 0, 0, 1);
		}
		
		if(amount < 1)
    		amount = 1;
		else if(amount > 99)
    		amount = 99;
		
		itemAmount.setText(itemAmountString + amount);
    	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
	}
	
	private void updateBuyTable() {
		int direction = 0;
		// Check used to allow the user to hold the button down to increase/decrease item amount. touchTime is for a delay
		if((subtractPress || addPress) && System.nanoTime() - touchTime > 1000000000 && selectedItem != null) {
			if(addPress)
				direction = 1;
			else if(subtractPress)
				direction = -1;
			if(selectedItem != null) {
        		amount += direction;
        		updatePriceAmount();
        	}
		}
		
		if(tableItems.isUpdated()) {
			itemPrice.setColor(1, 1, 1, 1);
			selectedItem = tableItems.getSelectedItem();
			itemBox.addItemImage(ResourceLoader.getItemRegionFromId(((SelectableItemButton)selectedItem).getItemId()));
			itemText.setText(((SelectableItemButton)selectedItem).getItemName());
			itemAmount.setText("Amount: 1");
			amount = 1;
			itemPrice.setText("Price: " + ((SelectableItemButton)selectedItem).getPrice());
			tableItems.setUpdated(false);
			Gdx.app.log("tableItems - Shop", "UPDATED");
		}
	}
	
	private void updateItemTable() {
		
		tableItems.reset();
		eManager.notify(new ScreenEvent(ScreenEvents.GET_SHOP_ITEMS, ItemType.ALL, this));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.PLAY_SHOP_MUSIC));
		uiGroup.addActor(scrolling);
		uiGroup.addActor(darken);
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
		darken.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addShopItem(int id, String name, int quantity, int price, int type, String itemText) {
		// TODO Auto-generated method stub
		item = new SelectableItemButton(id, name, quantity, price, type, itemText, tableItems, uiGroup, false);
		tableItems.addItem(item, 10);
		tableItems.invalidate();
		scroll.invalidate();
		tableItems.layout();
		scroll.layout();
	}

	@Override
	public void getPlayerGold(int gold) {
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
}
