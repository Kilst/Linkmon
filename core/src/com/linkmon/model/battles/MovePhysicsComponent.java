package com.linkmon.model.battles;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.MyVector2;
import com.linkmon.model.components.IPhysicsComponent;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public class MovePhysicsComponent implements IPhysicsComponent {
	
	public float angle;
	
	private double gradient;
	
	private float moveX;
	private float moveY;
	private float yIntersect;
	
	private int direction;
	
	public boolean running = true;
	
	public float getAngle()
	{
	    return angle;
	}
	
	public void setAngle(float x, float y, GameObject move, GameObject object)
	{
		direction = object.direction;
		
		if(direction == Direction.RIGHT)
			yIntersect = move.getY(); // Starts at the y-int
		else
			yIntersect = y - move.getHeight()/2; // Runs until y-int. The y intersect has to be the target y
		
		double dx;
		// Minus to correct for coord re-mapping
		double dy;
		
	    dx = x - move.getX();
	    dy = y - move.getY()-move.getHeight()/2;

	    double inRads = Math.atan2(dy,dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	        inRads = Math.abs(inRads);
	    else
	        inRads = 2*Math.PI - inRads;

	    angle = (float)Math.toDegrees(inRads);
	    
	    gradient = (dy/dx);
	    
	    double grad = Math.tan(inRads);
	    
	    Gdx.app.log("MovePhysics", "Angle: " + angle + "  Gradient: " + gradient + "  Gradient: " + grad);
	}

	@Override
	public void update(GameObject object, List<GameObject> objects) {
		// TODO Auto-generated method stub
		if(running) {
			if((object.getX()+object.getWidth()/2)*direction > moveX*direction) {
				object.getWorld().removeObjectFromWorld(object);
				running = false;
			}
			
			object.direction = direction;
			move(object);
		}
	}

	@Override
	public void move(GameObject object) {
		// TODO Auto-generated method stub
		
		float newX = object.getX()+(10*object.direction);
		float newY = (float) ((gradient*object.getX())+yIntersect);
		
		object.setX(newX);
		object.setY(newY);
		
//		 Gdx.app.log("MovePhysics", "X: " + object.getX() + "  Y: " + object.getY() + " yIntersect: " + yIntersect);
//		 Gdx.app.log("MovePhysics", "Angle: " + angle + "  Gradient: " + gradient);
	}

	@Override
	public void setMoveTo(float x, float y) {
		// TODO Auto-generated method stub
		moveX = x;
		moveY = y;
		
		Gdx.app.log("MovePhysics", "MoveX: " + x + "  MoveY: " + y);
	}

	@Override
	public void setMoveToX(float x) {
		// TODO Auto-generated method stub
		
	}

}
