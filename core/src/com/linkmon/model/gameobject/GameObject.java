package com.linkmon.model.gameobject;

import com.badlogic.gdx.Gdx;

public abstract class GameObject {
	
	private int id;
	
	private float x;
	private float y;
	
	private float moveX;
	private float moveY;
	
	private int direction = Direction.LEFT;
	private boolean isMoving = false;
	
	public GameObject(int id) {
		this.id = id;
		this.x = 0;
		this.y = 0;
	}
	
	public GameObject(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		direction = Direction.LEFT;
	}
	
	public void setMoveTo(float x, float y) {
		moveX = x;
		moveY = y;
	}
	
	public void setMoveToX(float x) {
		moveX = x;
		if(this.x > moveX)
			direction = Direction.LEFT;
		else if(this.x < moveX)
			direction = Direction.RIGHT;
	}
	
	private void move() {
		if(x > moveX) {
			x -= 1;
			isMoving = true;
		}
		else if(x < moveX) {
			x += 1;
			isMoving = true;
		}
		else {
			moveX = x;
			isMoving = false;
		}
	}
	
	public void update() {
		move();
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
