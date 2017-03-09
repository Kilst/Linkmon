package com.linkmon.controller;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.game.GameClass;
import com.linkmon.model.MyVector2;
import com.linkmon.model.components.InputType;

public class LibgdxInputController implements GestureListener {
	
	private EventManager eManager;
	
	public GestureDetector gd;
	
	private GameClass game;
	
	public LibgdxInputController(EventManager eManager, GameClass game) {
		this.eManager = eManager;
		
		gd = new GestureDetector(this);
		this.game = game;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
//		Gdx.app.log("InputController", "touchDown");
		Vector3 point = game.viewport.unproject(new Vector3(x,y,0));
		// Sent to the ParticleController
		eManager.notify(new InputEvent(InputType.CLICKED, new MyVector2(point.x,point.y)));
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
		Vector3 point = game.viewport.unproject(new Vector3(x,y,0));
		eManager.notify(new InputEvent(InputType.DRAGGED, new MyVector2(point.x,point.y)));
		//eManager.notify(new InputEvent(InputType.DRAGGED, new MyVector2(x,Gdx.graphics.getHeight()-y))); // minus height, inverted y-axis
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
