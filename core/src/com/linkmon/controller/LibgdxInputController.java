package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.eventmanager.input.InputEvents;
import com.linkmon.model.MyVector2;
import com.linkmon.model.components.InputType;

public class LibgdxInputController implements GestureListener {
	
	private EventManager eManager;
	
	public GestureDetector gd;
	
	public LibgdxInputController(EventManager eManager) {
		this.eManager = eManager;
		
		gd = new GestureDetector(this);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
//		Gdx.app.log("InputController", "touchDown");
		eManager.notify(new InputEvent(InputType.CLICKED, new MyVector2(x,Gdx.graphics.getHeight()-y))); // minus height, inverted y-axis
//		eManager.notify(new InputEvent(InputType.BUTTON_EVENT, button));
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
