package com.linkmon.view.screens.widgets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SelectionTable extends Table {
	
	private ISelectable selectedItem;
	
	private List<ISelectable> items;
	
	private boolean updated = false;
	
	public SelectionTable() {
		super();
		
		items = new ArrayList<ISelectable>();
	}

	public ISelectable getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ISelectable selectedItem) {
		Gdx.app.log("tableItems - Table", "UPDATED");
		for(ISelectable item : items) {
			item.setSelected(false);
		}
		
		if(selectedItem != null)
			selectedItem.setSelected(true);
		
		this.selectedItem = selectedItem;
		updated = true;
	}
	
	public void addItem(ISelectable item) {
		this.add((Table)item).expandX();
		this.row();
		
		items.add((ISelectable)item);
	}
	
	public void addItem(ISelectable item, float pad) {
		this.add((Table)item).expandX().padBottom(pad);
		this.row();
		
		items.add((ISelectable)item);
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		setSelectedItem(null);
		setUpdated(false);
		clear();
	}

}
