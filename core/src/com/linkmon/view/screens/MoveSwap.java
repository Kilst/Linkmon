package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
import com.linkmon.model.items.ItemType;
import com.linkmon.view.screens.interfaces.IMovesScreen;
import com.linkmon.view.screens.interfaces.IShop;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.ISelectable;
import com.linkmon.view.screens.widgets.ItemBox;
import com.linkmon.view.screens.widgets.SelectableItemButton;
import com.linkmon.view.screens.widgets.SelectableMoveButton;
import com.linkmon.view.screens.widgets.SelectionTable;

public class MoveSwap implements Screen, IMovesScreen {
	
	private Image img;
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
	
	private Table heading;
	
	private Table itemTable;
	
	private Table topTable;
	private Table middleTable;
	private Table bottomTable;
	
	ScrollPane scroll;
	
	private int moveid;
	private int moveSlot;
	
	public MoveSwap(Group group, EventManager eManager, int moveid) {
		
		this.moveid = moveid;
		
		uiGroup = group;
		this.eManager = eManager;
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
		
		
		// Create Window Elements
		
		img = new Image(skin2.getDrawable("shopBackground"));
		img.setSize(1280, 720);
		
		container = new Table();
		//container.setBackground(skin2.getDrawable("newContainer"));
		container.setSize(1280, 720);
		
		itemTable = new Table();
		itemTable.setBackground(skin2.getDrawable("bigContainer"));
		
		topTable = new Table();
		topTable.setSize(1280, 135);
		
		middleTable = new Table();
		//middleTable.setBackground(skin2.getDrawable("greenContainer"));
		middleTable.setSize(1280, 500);
		
		bottomTable = new Table();
		bottomTable.setSize(1280, 135);
		
		Image heading = new Image(skin2.getDrawable("menuHeading"));
		//heading.setSize(250, 136);
		
		tableItems = new SelectionTable();
		tableItems.align(Align.topLeft);
		
		buyTable = new Table();
		buyTable.setBackground(skin2.getDrawable("littleContainer"));
		
		scroll = new ScrollPane(tableItems, scrollStyle);
		scroll.setScrollingDisabled(true, false);
		
		backButton = new ImageButton(skin2.getDrawable("newBackButton"));
		
		buyButton = new TextButton("Buy", buttonStyle);
		
		addButton = new ImageButton(skin2.getDrawable("newAddButton"));
		subtractButton = new ImageButton(skin2.getDrawable("newSubtractButton"));
		
		itemBox = new ItemBox();
		
		itemText = new Label("Placeholder",labelStyle);
		itemAmount = new Label("Amount: ",labelStyle);	
		itemPrice  = new Label("Price: ",labelStyle);
		
		
		// Build Window Layout
		
		topTable.add(heading).width(heading.getWidth()).height(heading.getHeight()).expand().align(Align.left);
		
		buyTable.add(itemBox).expand().align(Align.top).colspan(2).size(150, 150);
		buyTable.row();
		buyTable.add(itemText).expand().colspan(2);
		buyTable.row();
		buyTable.add(subtractButton).expandX().align(Align.right).padRight(5);
		buyTable.add(addButton).expandX().align(Align.left).padLeft(5);
		buyTable.row();
		buyTable.add(itemAmount).expand().colspan(2);
		buyTable.row();
		buyTable.add(itemPrice).expand().colspan(2);
		
		itemTable.add(new Image(skin2.getDrawable("itemName")));
		itemTable.row();
		itemTable.add(scroll).height(500-new Image(skin2.getDrawable("itemName")).getHeight()).colspan(2);
		
		middleTable.add(buyTable).expandX().padRight(40);
		middleTable.add(itemTable);
		
		bottomTable.add(backButton).align(Align.left).expandX();
		bottomTable.add(buyButton).expand().align(Align.right);
		
		
		container.add(topTable).size(1280, 135);
		container.row();
		container.add(middleTable).size(1280, 500);
		container.row();
		container.add(bottomTable).size(1280, 135);
		
		
		addListeners();
		
		// Get Shop Items and add them to Item Table
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_MOVES, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_CHOOSABLE_MOVES, moveSlot, this));
	}
	
	private void addListeners() {
		
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MENU_SCREEN));
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
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
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
	            	if(amount < 1)
	            		amount = 1;
	            	itemAmount.setText(itemAmountString + amount);
	            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
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
	
	private void updateBuyTable() {
		int direction = 0;
		if((subtractPress || addPress) && System.nanoTime() - touchTime > 1000000000 && selectedItem != null) {
			if(addPress)
				direction = 1;
			else if(subtractPress)
				direction = -1;
			if(selectedItem != null) {
        		amount += direction;
        		if(amount < 1)
            		amount = 1;
        		else if(amount > 99)
            		amount = 99;
        		itemAmount.setText(itemAmountString + amount);
            	itemPrice.setText(itemPriceString + amount*((SelectableItemButton)selectedItem).getPrice());
        	}
		}
		
		if(tableItems.isUpdated()) {
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
		uiGroup.addActor(img);
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
		img.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLinkmonMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		if(moveid == id) {
			itemText.setText(name);
			moveSlot = slot;
		}
	}

	@Override
	public void setChoosableMoves(int id, String name, int type, int slot, int damage, int ignoreDamage, int energy, String effect) {
		// TODO Auto-generated method stub
		SelectableMoveButton item = new SelectableMoveButton(id, name, type, slot, damage, ignoreDamage, energy, tableItems);
		tableItems.addItem(item);
	}
}
